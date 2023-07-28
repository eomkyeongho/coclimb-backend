package swm.s3.coclimb.api.application.port.in.user;

import swm.s3.coclimb.domain.user.Instagram;

public interface UserCommand {
    Long createUserByInstagram(Instagram instagram);
}
