package swm.s3.coclimb.api.adapter.out.instagram;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Mono;
import swm.s3.coclimb.api.adapter.out.instagram.dto.LongLivedTokenResponseDto;
import swm.s3.coclimb.api.adapter.out.instagram.dto.ShortLivedTokenResponseDto;
import swm.s3.coclimb.api.exception.errortype.instagram.IssueLongLivedTokenFail;
import swm.s3.coclimb.api.exception.errortype.instagram.IssueShortLivedTokenFail;
import swm.s3.coclimb.api.exception.errortype.instagram.RefreshTokenFail;


@Component
@RequiredArgsConstructor
public class InstagramRestApiManager {

    private final InstagramWebClient instagramWebClient;
    private final InstagramOAuthRecord instagramOAuthRecord;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public ShortLivedTokenResponseDto getShortLivedAccessTokenAndUserId(String code) throws JsonProcessingException {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("client_id", instagramOAuthRecord.clientId());
        formData.add("client_secret", instagramOAuthRecord.clientSecret());
        formData.add("grant_type", "authorization_code");
        formData.add("redirect_uri", instagramOAuthRecord.redirectUri());
        formData.add("code", code);

        String response = instagramWebClient.basicDisplayClient().post()
                .uri("/oauth/access_token")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData(formData))
                .exchangeToMono(clientResponse -> {
                    if (clientResponse.statusCode().is2xxSuccessful()) {
                        return clientResponse.bodyToMono(String.class);
                    } else {
                        return Mono.error(new IssueShortLivedTokenFail());
                    }
                }).block();

        return objectMapper.readValue(response, ShortLivedTokenResponseDto.class);
    }

    public LongLivedTokenResponseDto getLongLivedAccessToken(String shortLivedAccessToken) throws JsonProcessingException {
        String targetUri = String.format("/access_token?grant_type=ig_exchange_token&client_secret=%s&access_token=%s",
                instagramOAuthRecord.clientSecret(), shortLivedAccessToken);


        String response = instagramWebClient.graphClient().get()
                .uri(targetUri)
                .exchangeToMono(clientResponse -> {
                    if (clientResponse.statusCode().is2xxSuccessful()) {
                        return clientResponse.bodyToMono(String.class);
                    } else {
                        return Mono.error(new IssueLongLivedTokenFail());
                    }
                }).block();

        return objectMapper.readValue(response, LongLivedTokenResponseDto.class);
    }

    public LongLivedTokenResponseDto refreshLongLivedToken(String longLivedAccessToken) throws JsonProcessingException {
        String targetUri = String.format("/refresh_access_token?grant_type=ig_refresh_token&access_token=%s",
                longLivedAccessToken);

        String response = instagramWebClient.graphClient().get()
                .uri(targetUri)
                .exchangeToMono(clientResponse -> {
                    if (clientResponse.statusCode().is2xxSuccessful()) {
                        return clientResponse.bodyToMono(String.class);
                    } else {
                        return Mono.error(new RefreshTokenFail());
                    }
                }).block();

        return objectMapper.readValue(response, LongLivedTokenResponseDto.class);
    }
}
