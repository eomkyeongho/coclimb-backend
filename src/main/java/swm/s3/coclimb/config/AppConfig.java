package swm.s3.coclimb.config;

import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.crypto.SecretKey;
import java.util.Base64;

@Getter
@ConfigurationProperties(prefix = "app-config")
@Slf4j
public class AppConfig {
    private final byte[] jwtKey;
    private final long validTime;
    private final SecretKey secretKey;

    public AppConfig(String jwtKey, String validTime) {
        this.jwtKey = Base64.getDecoder().decode(jwtKey);
        this.secretKey = Keys.hmacShaKeyFor(this.jwtKey);
        this.validTime = Long.parseLong(validTime)*1000;
    }
}
