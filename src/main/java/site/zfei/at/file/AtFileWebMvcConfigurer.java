package site.zfei.at.file;

import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


public class AtFileWebMvcConfigurer implements WebMvcConfigurer {

    private final String uploadPath;
    private final String visitPath;

    public AtFileWebMvcConfigurer(String uploadPath, String visitPath) {
        this.uploadPath = System.getProperty("user.dir") + uploadPath;
        this.visitPath = visitPath;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.
                addResourceHandler(visitPath + "/**")
                .addResourceLocations("file:" + uploadPath+"/")
                .resourceChain(false);
    }
}
