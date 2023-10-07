package swm.s3.coclimb.config;

import lombok.RequiredArgsConstructor;
import org.apache.http.conn.ssl.TrustAllStrategy;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;
import swm.s3.coclimb.config.propeties.ElasticProperties;

@Configuration
@RequiredArgsConstructor
public class ElasticsearchConfig extends ElasticsearchConfiguration {

    private final ElasticProperties elasticProperties;

    @Override
    public ClientConfiguration clientConfiguration() {

        try {
            return ClientConfiguration.builder()
                    .connectedTo(elasticProperties.getServerUrl())
//                    .usingSsl(new SSLContextBuilder().loadTrustMaterial(null, TrustAllStrategy.INSTANCE).build())
                    .withBasicAuth(elasticProperties.getUserName(), elasticProperties.getPassword())
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
