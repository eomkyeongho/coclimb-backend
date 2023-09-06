package swm.s3.coclimb.config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.TransportUtils;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.message.BasicHeader;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import swm.s3.coclimb.api.adapter.out.elasticsearch.ElasticProperties;
import swm.s3.coclimb.config.aspect.logtrace.LogTraceAspect;
import swm.s3.coclimb.config.aspect.logtrace.LogTraceImpl;
import swm.s3.coclimb.config.security.JwtProperties;

@Getter
@Configuration
@RequiredArgsConstructor
public class AppConfig {

    private final JwtProperties jwtProperties;
    private final ElasticProperties elasticProperties;

    @Bean
    public LogTraceAspect logTraceAspect() {
        return new LogTraceAspect(new LogTraceImpl());
    }

    @Bean
    public ElasticsearchClient elasticsearchClient() {

        // Create the low-level client
        RestClientBuilder restClientBuilder = RestClient
                .builder(HttpHost.create(elasticProperties.getServerUrl()));
        RestClient restClient = restClientBuilder
                .setDefaultHeaders(new Header[]{
                        new BasicHeader("Authorization", "ApiKey " + elasticProperties.getApiKey())
                })
                .setHttpClientConfigCallback(hc -> hc
                        .setSSLContext(TransportUtils
                                .sslContextFromCaFingerprint(elasticProperties.getFingerPrint()))
                )
                .build();
        // Create the transport with a Jackson mapper
        ElasticsearchTransport transport = new RestClientTransport(
                restClient, new JacksonJsonpMapper());

        // And create the API client
        return new ElasticsearchClient(transport);
    }
}
