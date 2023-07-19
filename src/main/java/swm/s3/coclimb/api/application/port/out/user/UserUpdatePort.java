package swm.s3.coclimb.api.application.port.out.user;

import swm.s3.coclimb.domain.User;

public interface UserUpdatePort {
    void save(User user);
}
