package site.zfei.at.common;


import com.github.pagehelper.PageHelper;
import org.aspectj.lang.JoinPoint;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import site.zfei.at.coxt.AtHandlerInterceptor;
import site.zfei.at.coxt.IRequest;
import site.zfei.at.coxt.PageAble;
import site.zfei.at.file.AtFileConfigurationProperties;
import site.zfei.at.trace.WebLogBean;

@Configuration
@EnableConfigurationProperties({AtFileConfigurationProperties.class})
public class CommonConfig {

    @Bean
    @ConditionalOnProperty(name = "at.common.cors.enable", havingValue = "true", matchIfMissing = true)
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*") // 允许跨域的源，这里设置为允许所有源
                        .allowedMethods("*") // 允许的请求方法，例如 GET、POST
                        .allowedHeaders("*") // 允许的请求头，例如 Content-Type、Authorization
                        .exposedHeaders("*") // 允许暴露的响应头
                        .allowCredentials(true) // 是否允许发送身份验证信息（例如cookies）到服务器
                        .maxAge(3600); // 预检请求的有效期，单位为秒
            }
        };
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