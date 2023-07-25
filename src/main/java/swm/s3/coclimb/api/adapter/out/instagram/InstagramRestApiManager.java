package swm.s3.coclimb.api.adapter.out.instagram;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Mono;
import swm.s3.coclimb.api.adapter.out.instagram.dto.InstagramMediaResponseDto;
import swm.s3.coclimb.api.adapter.out.instagram.dto.LongLivedTokenResponseDto;
import swm.s3.coclimb.api.adapter.out.instagram.dto.ShortLivedTokenResponseDto;
import swm.s3.coclimb.api.exception.errortype.instagram.IssueInstagramLongLivedTokenFail;
import swm.s3.coclimb.api.exception.errortype.instagram.IssueInstagramShortLivedTokenFail;
import swm.s3.coclimb.api.exception.errortype.instagram.RefreshInstagramTokenFail;
import swm.s3.coclimb.api.exception.errortype.instagram.RetrieveInstagramMediaFail;

import java.util.List;


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
                        return Mono.error(new IssueInstagramShortLivedTokenFail());
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
                        return Mono.error(new IssueInstagramLongLivedTokenFail());
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
                        return Mono.error(new RefreshInstagramTokenFail());
                    }
                }).block();

        return objectMapper.readValue(response, LongLivedTokenResponseDto.class);
    }

    public List<InstagramMediaResponseDto> getMyMedias(String accessToken) throws JsonProcessingException {
        String targetUri = String.format("/me/media?fields=id,media_type,media_url,permalink,thumbnail_url&access_token=%s",
                accessToken);

        String response = instagramWebClient.graphClient().get()
                .uri(targetUri)
                .exchangeToMono(clientResponse -> {
                    if (clientResponse.statusCode().is2xxSuccessful()) {
                        return clientResponse.bodyToMono(String.class);
                    } else {
                        return Mono.error(new RetrieveInstagramMediaFail());
                    }
                }).block();

        JSONObject jsonObject = new JSONObject(response);

        return objectMapper.readValue(jsonObject.getJSONArray("data").toString(),
                objectMapper.getTypeFactory().constructCollectionType(List.class, InstagramMediaResponseDto.class));
    }
}
