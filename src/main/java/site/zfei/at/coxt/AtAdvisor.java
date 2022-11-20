package site.zfei.at.coxt;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.ApplicationContext;
import site.zfei.at.trace.WebLogBean;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Aspect
public class AtAdvisor extends WebAdvisor {

    public AtAdvisor(ApplicationContext applicationContext, String serverHost) {
        this.serverHost = serverHost;
        this.applicationContext = applicationContext;
    }

    private final String serverHost;

    private final ApplicationContext applicationContext;

    @Pointcut("@within(org.springframework.web.bind.annotation.RestController)")
    public void control() {
    }

    @Around("control()")
    public Object aroundController(ProceedingJoinPoint jp) throws Throwable {
        return around(jp);
    }

    @Override
    protected void postBefore(ProceedingJoinPoint jp, WebLogBean logBean) {
        Map<String, AtHandlerInterceptor> beanMap = applicationContext.getBeansOfType(AtHandlerInterceptor.class);
        beanMap.values().stream().sorted((a,b)->b.order()-a.order()).collect(Collectors.toList()).forEach(h -> h.postBefore(jp, logBean));
    }

    @Override
    protected void postAfter(Object result, ProceedingJoinPoint jp, WebLogBean logBean) {
        Map<String, AtHandlerInterceptor> beanMap = applicationContext.getBeansOfType(AtHandlerInterceptor.class);
        beanMap.values().stream().sorted(Comparator.comparingInt(AtHandlerInterceptor::order)).collect(Collectors.toList()).forEach(h -> h.postAfter(result, jp, logBean));
    }

    @Override
    protected void before(WebLogBean logBean) {
        setConfigHost(serverHost);
        super.before(logBean);
    }
}
