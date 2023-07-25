package swm.s3.coclimb.api.application.port.out.user;

import swm.s3.coclimb.domain.User;

import java.util.Optional;

public interface UserLoadPort {
    Optional<User> findByInstagramUserId(Long instagramUserId);
}
