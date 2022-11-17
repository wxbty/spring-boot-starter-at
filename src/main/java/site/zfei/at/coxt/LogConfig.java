package site.zfei.at.coxt;


import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import site.zfei.at.trace.ContextFilter;

@Configuration
@EnableConfigurationProperties({AtTraceConfigurationProperties.class})
public class LogConfig {

    private final AtTraceConfigurationProperties properties;

    @Autowired
    private ApplicationContext applicationContext;

    public LogConfig(AtTraceConfigurationProperties properties) {
        this.properties = properties;
    }

    @Bean
    public GlobalController globalController() {
        return new GlobalController();
    }

    @Bean
    public ContextFilter contextFilter() {
        return new ContextFilter();
    }

    @Bean
    @ConditionalOnExpression("${at.trace.enable:true}")
    public AtAdvisor webAdvisor() {
        return new AtAdvisor(applicationContext, this.properties.getServerHost());
    }

    @Bean
    public ControllerAdviceHandler controllerAdviceHandler() {
        return new ControllerAdviceHandler(properties);
    }

}