package site.zfei.at.trace;

import cn.hutool.core.util.RandomUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import site.zfei.at.coxt.Context;
import site.zfei.at.util.IPUntils;

import javax.servlet.FilterChain;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author fgh
 */
@WebFilter
@Slf4j
public class ContextFilter extends OncePerRequestFilter {



    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        boolean isFirstRequest = !isAsyncDispatch(request);
        HttpServletRequest requestToUse = request;

        if (isFirstRequest && !(request instanceof ContentCachingRequestWrapper)) {
            requestToUse = new ContentCachingRequestWrapper(request);
        }

        if (isFirstRequest) {
            beforeRequest(requestToUse);
        }
        try {
            filterChain.doFilter(requestToUse, response);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (!isAsyncStarted(requestToUse)) {
                afterRequest();
            }
        }
    }

    protected void beforeRequest(HttpServletRequest request) {
        WebLogBean.start();
        WebLogBean logBean = WebLogBean.getLogBean();
        logBean.setTrace(Trace.start(logBean.getBeginTime()));
        logBean.setPath(request.getRequestURI());
        logBean.setClientIp(IPUntils.getIp(request));
        logBean.setHeaders(IPUntils.getHeaders(request));
        logBean.setParams(request.getParameterMap());
    }

    protected void afterRequest() {
        Trace.stop();
        WebLogBean.stop();
        Context.removeContext();
    }


    static class Trace {

        public static final String KEY_TRACE = "X-B3-TraceId";

        public static String createTrace(long millis) {
            return millis + "-" + RandomUtil.randomInt(0, 100000);
        }

        public static String setTrace(String trace) {
            Context.current().setGlobalAttachment(KEY_TRACE, trace);
            return trace;
        }


        public static String start(long millis) {
            return setTrace(createTrace(millis));
        }

        public static void stop() {
            setTrace(null);
        }
    }





}
