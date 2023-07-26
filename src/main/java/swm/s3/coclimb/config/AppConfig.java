package swm.s3.coclimb.config;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.LocalDateTime;
import java.util.Base64;

@Getter
@ConfigurationProperties(prefix = "app-config")
public class AppConfig {
    private final byte[] jwtKey;
    private final int validTime;

    public AppConfig(String jwtKey, String validTime) {
        this.jwtKey = Base64.getDecoder().decode(jwtKey);
        this.validTime = Integer.parseInt(validTime)*1000;
    }

    public LocalDateTime getServerTime() {
        return LocalDateTime.now();
    }
}
