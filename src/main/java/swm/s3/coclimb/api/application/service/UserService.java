package swm.s3.coclimb.api.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import swm.s3.coclimb.api.application.port.in.user.UserQuery;
import swm.s3.coclimb.api.application.port.out.user.UserLoadPort;
import swm.s3.coclimb.api.exception.errortype.user.UserNotFound;
import swm.s3.coclimb.domain.User;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService implements UserQuery {

    private final UserLoadPort userLoadPort;

    @Override
    public User getUserByInstagramUserId(Long instagramUserId) {
        return userLoadPort.findByInstagramUserId(instagramUserId).orElseThrow(UserNotFound::new);
    }

    private Optional<User> findUserByInstagramUserId(Long instagramUserId) {
        return userLoadPort.findByInstagramUserId(instagramUserId);
    }
}
