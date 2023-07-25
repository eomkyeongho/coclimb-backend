package swm.s3.coclimb.api.adapter.in.web.login;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import swm.s3.coclimb.api.adapter.in.web.login.dto.InstagramAuthRequest;
import swm.s3.coclimb.api.adapter.out.instagram.InstagramOAuthRecord;
import swm.s3.coclimb.api.application.port.in.login.LoginCommand;
import swm.s3.coclimb.api.application.port.in.login.dto.SessionDataDto;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@Validated
public class LoginController {

    private final LoginCommand loginCommand;
    private final InstagramOAuthRecord instagramOAuthRecord;

    @GetMapping("/login/instagram")
    public ResponseEntity<?> redirectInstagramLoginPage() {
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("https://api.instagram.com/oauth/authorize?client_id=" + instagramOAuthRecord.clientId()
                + "&redirect_uri=" + instagramOAuthRecord.redirectUri()
                + "&scope=user_profile,user_media&response_type=code"));

        return ResponseEntity
                .status(302)
                .headers(headers)
                .build();
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok().build();
    }

    @PostMapping("/auth/instagram")
    public ResponseEntity<?> loginWithInstagram(@RequestBody @Valid InstagramAuthRequest instagramAuthRequest, HttpSession httpSession
    , HttpServletResponse response) throws JsonProcessingException {
        SessionDataDto sessionData = loginCommand.authenticateWithInstagram(instagramAuthRequest.getCode());

        httpSession.setAttribute("instagramUserId", sessionData.getInstagramUserId());
        httpSession.setAttribute("instagramAccessToken", sessionData.getInstagramAccessToken());
        setInstagramCookie(response, sessionData.getInstagramAccessToken(), sessionData.getInstagramUserId());

        return ResponseEntity.ok().build();
    }

    private void setInstagramCookie(HttpServletResponse response, String instagramAccessToken, Long instagramUserId) {
        Cookie tokenCookie = new Cookie("instagramAccessToken", instagramAccessToken);

        tokenCookie.setPath("/");
        tokenCookie.setMaxAge(60 * 60 * 24 * 7);
        response.addCookie(tokenCookie);

        Cookie userIdCookie = new Cookie("instagramUserId", instagramUserId.toString());

        userIdCookie.setPath("/");
        userIdCookie.setMaxAge(60 * 60 * 24 * 7);
        response.addCookie(userIdCookie);
    }
}
