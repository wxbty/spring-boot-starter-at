package site.zfei.at.common;


import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import site.zfei.at.common.filter.CorsFilter;
import site.zfei.at.file.AtFileConfigurationProperties;

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


}