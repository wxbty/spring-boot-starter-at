package site.zfei.at.file;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "at.common")
public class AtConfigurationProperties {

    private Boolean paramCheck;


}
