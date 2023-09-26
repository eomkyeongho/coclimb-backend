package swm.s3.coclimb.api.adapter.in.web.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import swm.s3.coclimb.api.adapter.in.web.user.dto.UserInfoResponse;
import swm.s3.coclimb.api.application.port.in.user.UserCommand;
import swm.s3.coclimb.api.application.port.in.user.UserQuery;
import swm.s3.coclimb.config.argumentresolver.LoginUser;
import swm.s3.coclimb.domain.user.User;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserQuery userQuery;
    private final UserCommand userCommand;

    @GetMapping("/users/me")
    public ResponseEntity<UserInfoResponse> getMyInfo(@LoginUser User user) {
        return ResponseEntity.ok(UserInfoResponse.builder()
                .username(user.getName())
                .instagramUsername(user.getInstagramUserInfo().getName())
                .build());
    }

    @DeleteMapping("/users/me")
    public ResponseEntity<Void> deleteMyInfo(@LoginUser User user) {
        userCommand.deleteUser(user);
        return ResponseEntity.noContent().build();
    }
}
