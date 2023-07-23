package swm.s3.coclimb.api.adapter.in.web.login;


import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import swm.s3.coclimb.api.adapter.out.instagram.InstagramOAuthRecord;

import java.net.URI;

@RestController
@RequiredArgsConstructor
public class LoginController {

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
}
