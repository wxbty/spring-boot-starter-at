package site.zfei.at.coxt;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.WebUtils;
import site.zfei.at.trace.WebLogBean;
import site.zfei.at.util.IPUntils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author fgh
 */
@Slf4j
public abstract class WebAdvisor {

    protected String configHost;

    protected Object around(ProceedingJoinPoint jp) throws Throwable {
        WebLogBean logBean = WebLogBean.getLogBean();

        before(logBean);

        postBefore(jp, logBean);

        Object result = jp.proceed();

        postAfter(result, jp, logBean);

        after(logBean, result);

        return result;
    }

    protected void postBefore(ProceedingJoinPoint jp, WebLogBean logBean) {

    }

    protected void postAfter(Object result, ProceedingJoinPoint jp, WebLogBean logBean) {

    }

    protected void before(WebLogBean logBean) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        ContentCachingRequestWrapper requestWrapper = WebUtils.getNativeRequest(request, ContentCachingRequestWrapper.class);
        String body = new String(requestWrapper != null ? requestWrapper.getContentAsByteArray() : ByteArrays.EMPTY);
        if (StringUtils.isEmpty(body)) {
            body = ByteArrays.EMPTY_BODY;
        }
        logBean.setPayload(body);

        if (Global.isPrintCurl(request.getRequestURI())) {
            log.info(IPUntils.getCurl(request, configHost));
        }
    }

    protected void after(WebLogBean logBean, Object result) {
        if (Global.printResult) {
            logBean.setResult(result);
        }
    }

    protected void setConfigHost(String configHost) {
        this.configHost = configHost;
    }

    static class ByteArrays {

        /**
         * The Empty Byte Array
         */
        public static final byte[] EMPTY = new byte[0];

        public static final String EMPTY_BODY = "{}";

    }
}
