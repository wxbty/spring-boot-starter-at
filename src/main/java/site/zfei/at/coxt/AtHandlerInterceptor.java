package site.zfei.at.coxt;

import org.aspectj.lang.JoinPoint;
import site.zfei.at.trace.WebLogBean;

/**
 * @author dyj
 */
public interface AtHandlerInterceptor {

    /**
     * 拦截前动作
     *
     * @param jp      切入点
     * @param logBean 日志bean
     * @return 返回对象
     */
    void postBefore(JoinPoint jp, WebLogBean logBean);

    /**
     * 拦截后动作
     *
     * @param result  处理结果
     * @param jp      切入点
     * @param logBean 日志bean
     * @return 返回对象
     */
    void postAfter(Object result, JoinPoint jp, WebLogBean logBean);

    /**
     * 执行顺序,越小越靠后
     *
     * @return 返回
     */
    default int order() {
        return 0;
    }
}
