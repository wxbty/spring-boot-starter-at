package site.zfei.at.util;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.ContentType;
import cn.hutool.http.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.util.ContentCachingRequestWrapper;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author fgh
 */
@Slf4j
public class IPUntils {

    private static final String FORMAT_HEADER = "-H \"%1$s:%2$s\"";
    private static final String FORMAT_METHOD = "-X %1$s";
    private static final String FORMAT_BODY = "-d '%1$s'";
    private static final String FORMAT_URL = "\"%1$s\"";
    private static final String CONTENT_TYPE = "Content-Type";

    private static final Logger logger = LoggerFactory.getLogger(IPUntils.class);

    public static String getOutIPV4() {
        String ip = "";
        String chinaz = "https://ip.chinaz.com";

        StringBuilder inputLine = new StringBuilder();
        String read = "";
        URL url;
        HttpURLConnection urlConnection;
        BufferedReader in = null;
        try {
            url = new URL(chinaz);
            urlConnection = (HttpURLConnection) url.openConnection();
            in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), StandardCharsets.UTF_8));
            while ((read = in.readLine()) != null) {
                inputLine.append(read).append("\r\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        Pattern p = Pattern.compile("\\<dd class\\=\"fz24\">(.*?)\\<\\/dd>");
        Matcher m = p.matcher(inputLine.toString());
        if (m.find()) {
            ip = m.group(1);
        }
        return ip;
    }

    /**
     * <p>
     * HttpServletRequest 转化为 CURL 命令
     * </p>
     */
    public static String getCurl(HttpServletRequest request, String configHost) {
        String curl;
        try {
            List<String> parts = new ArrayList<>();
            parts.add("curl");
            String url = request.getRequestURL().toString();
            String method = request.getMethod();
            String contentType = request.getContentType();
            String queryString = request.getQueryString();
            parts.add(String.format(FORMAT_METHOD, method.toUpperCase()));

            Map<String, String> headers = new HashMap<>(16);
            Enumeration<String> headerNames = request.getHeaderNames();
            while (headerNames.hasMoreElements()) {
                String key = headerNames.nextElement();
                headers.put(key, request.getHeader(key));
            }
            headers.forEach((k, v) -> parts.add(String.format(FORMAT_HEADER, k, v)));
            if (StrUtil.isNotEmpty(contentType) && !headers.containsKey(CONTENT_TYPE)) {
                parts.add(String.format(FORMAT_HEADER, CONTENT_TYPE, contentType));
            }
            if (StrUtil.isNotEmpty(queryString)) {
                url = HttpUtil.urlWithForm(url, queryString, CharsetUtil.CHARSET_UTF_8, false);
            }
            if (ContentType.isFormUrlEncode(contentType) && CollUtil.isNotEmpty(request.getParameterMap())) {
                request.getParameterMap().forEach((k, v) ->
                        parts.add(StrUtil.format("--data-urlencode '{}={}'", k, ArrayUtil.get(v, 0))));
            }
            if (StrUtil.startWithIgnoreCase(contentType, ContentType.JSON.toString())) {
                ContentCachingRequestWrapper wrapper = (ContentCachingRequestWrapper) request;
                String body = new String(wrapper.getContentAsByteArray());
                if (StrUtil.isNotEmpty(body)) {
                    parts.add(String.format(FORMAT_BODY, body));
                }
            }
            if (url.contains("127.0.0.1") || url.contains("localhost")) {
                String targetUrl;
                if (!StringUtils.isEmpty(configHost)) {
                    targetUrl = configHost;
                } else {
                    targetUrl = IPUntils.getOutIPV4();
                }
                url = url.replaceAll("127.0.0.1", targetUrl);
                url = url.replaceAll("localhost", targetUrl);
            }
            parts.add(String.format(FORMAT_URL, url));
            curl = StrUtil.join(" ", parts);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            curl = null;
        }
        return curl;
    }

    /**
     * get ip
     * 获取真实ip，nginx开启
     *
     * @param request       request
     * @param useInternalIp useInternalIp
     * @return ip
     */
    public static String getIp(HttpServletRequest request, boolean useInternalIp) {
        try {
            String header1 = request.getHeader("X-Forwarded-For");
            String header2 = request.getHeader("Proxy-Client-IP");
            String header3 = request.getHeader("WL-Proxy-Client-IP");

            String header = null;
            if (!StringUtils.isEmpty(header1) && !"unknown".equalsIgnoreCase(header1)) {
                header = header1;
            } else if (!StringUtils.isEmpty(header2) && !"unknown".equalsIgnoreCase(header2)) {
                header = header2;
            } else if (!StringUtils.isEmpty(header3) && !"unknown".equalsIgnoreCase(header3)) {
                header = header3;
            }

            String realIp = null;
            if (!StringUtils.isEmpty(header)) {
                String[] ips = header.split(",");
                for (String ip : ips) {
                    // 过滤2g/3g网关添加的内网ip
                    ip = ip.trim();
                    if (!RegexKit.isInternalIp(ip)) {
                        realIp = ip;
                        break;
                    }
                }

                // 只有内网ip并且应用允许的情况下才取内网ip
                if (realIp == null && useInternalIp) {
                    realIp = ips[0];
                }
            }

            if (!StringUtils.isEmpty(realIp)) {
                return realIp.trim();
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

        return request.getRemoteAddr();
    }

    /**
     * get ip (exclude internal ips)
     *
     * @param request request
     * @return ip
     */
    public static String getIp(HttpServletRequest request) {
        return getIp(request, false);
    }


    /**
     * get headers
     *
     * @param request request
     * @return headers
     */
    public static Map<String, List<String>> getHeaders(HttpServletRequest request) {
        Map<String, List<String>> headers = new HashMap<>();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = headerNames.nextElement();
            ArrayList<String> value = Collections.list(request.getHeaders(key));
            headers.put(key, value);
        }

        return headers;
    }

    static class RegexKit {


        private static final String ipDigitPattern = "(25[0-5]|2[0-4]\\d|1\\d{2}|[1-9]?\\d)";

        private static final Pattern internalIpPattern = Pattern
                .compile(String.format("(10(\\.%s){3})|(172\\.(1[6-9]|2\\d|3[01])(\\.%s){2})|(192\\.168(\\.%s){2})|(127\\.0\\.0\\.%s)",
                        ipDigitPattern, ipDigitPattern, ipDigitPattern, ipDigitPattern));

        public static boolean isInternalIp(String str) {
            return internalIpPattern.matcher(str).matches();
        }

    }
}
