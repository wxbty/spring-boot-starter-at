package site.zfei.at.coxt;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import site.zfei.at.trace.WebLogBean;

@Aspect
public class AtAdvisor extends WebAdvisor{

    public AtAdvisor(String serverHost) {
        this.serverHost = serverHost;
    }

    private final String serverHost;

    @Pointcut("@within(org.springframework.web.bind.annotation.RestController)")
    public void control() {
    }

    @Around("control()")
    public Object aroundController(ProceedingJoinPoint jp) throws Throwable {
        return around(jp);
    }

    @Override
    protected void before(WebLogBean logBean) {
        setConfigHost(serverHost);
        super.before(logBean);
    }
}
