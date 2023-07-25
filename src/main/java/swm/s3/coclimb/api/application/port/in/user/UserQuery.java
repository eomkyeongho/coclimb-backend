package swm.s3.coclimb.api.application.port.in.user;

import swm.s3.coclimb.domain.User;

import java.util.Optional;

public interface UserQuery {
    User getUserByInstagramUserId(Long instagramUserId);

    Optional<User> findUserByInstagramUserId(Long instagramUserId);
}
