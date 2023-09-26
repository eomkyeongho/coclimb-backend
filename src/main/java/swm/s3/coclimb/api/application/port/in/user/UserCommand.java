package swm.s3.coclimb.api.application.port.in.user;

import swm.s3.coclimb.domain.user.InstagramUserInfo;
import swm.s3.coclimb.domain.user.User;

public interface UserCommand {
    Long createUserByInstagramInfo(InstagramUserInfo instagram);

    void deleteUser(User user);
}
