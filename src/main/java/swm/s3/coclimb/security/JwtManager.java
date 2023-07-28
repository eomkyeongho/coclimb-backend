package swm.s3.coclimb.security;

import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import swm.s3.coclimb.config.AppConfig;
import swm.s3.coclimb.config.ServerClock;

import java.sql.Timestamp;
import java.util.Date;

@RequiredArgsConstructor
@Component
public class JwtManager {
    private final AppConfig appConfig;
    private final ServerClock serverClock;

    public String issueToken(String subject) {
        Timestamp iat = Timestamp.valueOf(serverClock.getDateTime());
        return Jwts.builder()
                .setIssuer("CoClimb")
                .setSubject(subject)
                .setAudience("all")
                .setIssuedAt(iat)
                .setExpiration(new Date(iat.getTime() + appConfig.getValidTime()))//만료시간 - ms단위;1000=1초
                .signWith(appConfig.getSecretKey())
                .compact();
    }


}
