package swm.s3.coclimb.api.adapter.in.web.auth;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import swm.s3.coclimb.api.adapter.in.web.auth.dto.InstagramAuthRequest;
import swm.s3.coclimb.api.application.port.in.auth.AuthCommand;
import swm.s3.coclimb.api.application.port.in.auth.dto.SessionDataDto;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class AuthController {

    private final AuthCommand authCommand;

    @PostMapping("/auth/instagram")
    public ResponseEntity<Void> loginWithInstagram(@RequestBody InstagramAuthRequest instagramAuthRequest, HttpSession httpSession
    , HttpServletResponse response) throws JsonProcessingException {
        SessionDataDto sessionData = authCommand.authenticateWithInstagram(instagramAuthRequest.getCode());

        httpSession.setAttribute("instagramUserId", sessionData.getInstagramUserId());
        httpSession.setAttribute("instagramAccessToken", sessionData.getInstagramAccessToken());
        setInstagramCookie(response, sessionData.getInstagramAccessToken(), sessionData.getInstagramUserId());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
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
