package swm.s3.coclimb.api.application.port.out.user;

import swm.s3.coclimb.domain.User;

public interface UserLoadPort {
    User findByInstagramUserId(Long instagramUserId);
}
