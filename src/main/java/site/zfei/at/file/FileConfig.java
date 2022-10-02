package site.zfei.at.file;


import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({AtFileConfigurationProperties.class})
public class FileConfig {

    private final AtFileConfigurationProperties properties;

    public FileConfig(AtFileConfigurationProperties properties) {
        this.properties = properties;
    }

    @Bean
    public AtFileWebMvcConfigurer sFileWebMvcConfigurer() {
        return new AtFileWebMvcConfigurer(properties.getUploadPath(), properties.getVisitPath());
    }

    @Bean
    public FileUploadController fileUploadController() {
        return new FileUploadController();
    }
}