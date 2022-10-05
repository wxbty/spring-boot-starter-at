package site.zfei.at.coxt;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "at.trace")
public class AtTraceConfigurationProperties {

    private String serverHost;
    private boolean enable;

}
