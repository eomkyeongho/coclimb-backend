package swm.s3.coclimb.api.adapter.in.web.auth;

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
    public ResponseEntity<Void> authInstagram(@RequestBody InstagramAuthRequest instagramAuthRequest) {
        SessionDataDto sessionData = authCommand.authenticateWithInstagram(instagramAuthRequest.getCode());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }
}
