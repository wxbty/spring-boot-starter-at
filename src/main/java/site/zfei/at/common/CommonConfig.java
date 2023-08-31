package site.zfei.at.common;


import com.github.pagehelper.PageHelper;
import org.aspectj.lang.JoinPoint;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import site.zfei.at.common.filter.CorsFilter;
import site.zfei.at.coxt.AtHandlerInterceptor;
import site.zfei.at.coxt.IRequest;
import site.zfei.at.coxt.PageAble;
import site.zfei.at.file.AtFileConfigurationProperties;
import site.zfei.at.trace.WebLogBean;

@Configuration
@EnableConfigurationProperties({AtFileConfigurationProperties.class})
public class CommonConfig {

    @Bean
    public CorsFilter corsFilter() {
        return new CorsFilter();
    }

    @Bean
    @ConditionalOnProperty(name = "at.common.param_check", havingValue = "true", matchIfMissing = true)
    public AtHandlerInterceptor paramsCheckInterceptor() {
        return new AtHandlerInterceptor() {
            @Override
            public void postBefore(JoinPoint jp, WebLogBean logBean) {
                Object[] args = jp.getArgs();
                for (Object arg : args) {
                    if (arg instanceof IRequest) {
                        ((IRequest) arg).checkParam();
                    }
                    if (arg instanceof PageAble) {
                        PageAble query = (PageAble) arg;
                        PageHelper.startPage(query.getPageNo(), query.getPageSize());
                    }
                }
            }

            @Override
            public void postAfter(Object result, JoinPoint jp, WebLogBean logBean) {
                PageHelper.clearPage();
            }
        };
    }


}