package swm.s3.coclimb.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import swm.s3.coclimb.config.aspect.logtrace.LogTraceAspect;
import swm.s3.coclimb.config.aspect.logtrace.LogTraceImpl;
import swm.s3.coclimb.config.propeties.JwtProperties;

@Getter
@Configuration
@RequiredArgsConstructor
public class AppConfig {

    private final JwtProperties jwtProperties;

    @Bean
    public LogTraceAspect logTraceAspect() {
        return new LogTraceAspect(new LogTraceImpl());
    }

}
