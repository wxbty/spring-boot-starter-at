package site.zfei.at.coxt;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import site.zfei.at.trace.ContextFilter;

@Configuration
public class LogConfig {

    @Bean
    public GlobalController globalController() {
        return new GlobalController();
    }

    @Bean
    public ContextFilter contextFilter() {
        return new ContextFilter();
    }
}