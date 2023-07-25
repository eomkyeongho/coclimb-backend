package swm.s3.coclimb.api.adapter.in.web.user;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import swm.s3.coclimb.api.adapter.in.web.user.dto.UserInfoResponse;
import swm.s3.coclimb.api.application.port.in.user.UserQuery;
import swm.s3.coclimb.domain.User;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserQuery userQuery;

    @GetMapping("/users/me")
    public ResponseEntity<UserInfoResponse> getMe(HttpSession httpSession) {
        User user = userQuery.getUserByInstagramUserId((Long) httpSession.getAttribute("instagramUserId"));

        return ResponseEntity.ok(UserInfoResponse.builder()
                .username(user.getUsername())
                .instagramUserId(user.getInstagramUserId())
                .build());
    }
}
