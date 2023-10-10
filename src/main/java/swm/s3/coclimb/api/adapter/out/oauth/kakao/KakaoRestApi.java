package swm.s3.coclimb.api.adapter.out.oauth.kakao;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import swm.s3.coclimb.api.adapter.out.oauth.kakao.dto.KakaoTokenResponse;
import swm.s3.coclimb.api.exception.errortype.kakao.IssueKakaoTokenFail;
import swm.s3.coclimb.api.exception.errortype.kakao.RetrieveKakaoUserInfoFail;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class KakaoRestApi {

    private final WebClient authClient = WebClient.create("https://kauth.kakao.com");
    private final WebClient apiClient = WebClient.create("https://kapi.kakao.com");
    private final KakaoOAuthRecord kakaoOAuthRecord;

    public KakaoTokenResponse getToken(String code) {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", "authorization_code");
        formData.add("client_id", kakaoOAuthRecord.getClientId());
        formData.add("redirect_uri", kakaoOAuthRecord.getRedirectUri());
        formData.add("code", code);

        KakaoTokenResponse response = authClient.post()
                .uri("/oauth/token")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData(formData))
                .exchangeToMono(clientResponse -> {
                    if (clientResponse.statusCode().is2xxSuccessful()) {
                        return clientResponse.bodyToMono(KakaoTokenResponse.class);
                    } else {
                        return Mono.error(new IssueKakaoTokenFail());
                    }
                }).block();

        return response;
    }

    public Long getUserId(String accessToken) {
        return apiClient.get()
                .uri("/v2/user/me")
                .header("Authorization", "Bearer " + accessToken)
                .exchangeToMono(clientResponse -> {
                    if (clientResponse.statusCode().is2xxSuccessful()) {
                        return clientResponse.bodyToMono(Map.class);
                    } else {
                        return Mono.error(new RetrieveKakaoUserInfoFail());
                    }
                }).blockOptional()
                .map(map -> Long.parseLong(map.get("id").toString()))
                .orElseThrow(IssueKakaoTokenFail::new);
    }
}
