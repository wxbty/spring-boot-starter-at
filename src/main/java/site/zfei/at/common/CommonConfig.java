package site.zfei.at.common;


import org.aspectj.lang.JoinPoint;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import site.zfei.at.common.filter.CorsFilter;
import site.zfei.at.coxt.AtHandlerInterceptor;
import site.zfei.at.coxt.IRequest;
import site.zfei.at.file.AtFileConfigurationProperties;
import site.zfei.at.trace.WebLogBean;

@Configuration
@EnableConfigurationProperties({AtFileConfigurationProperties.class})
public class CommonConfig {

    private final AtFileConfigurationProperties properties;

    public CommonConfig(AtFileConfigurationProperties properties) {
        this.properties = properties;
    }

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
                }
            }

            @Override
            public void postAfter(Object result, JoinPoint jp, WebLogBean logBean) {

            }
        };
    }


}