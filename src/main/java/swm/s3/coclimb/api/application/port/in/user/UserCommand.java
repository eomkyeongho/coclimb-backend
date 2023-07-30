package swm.s3.coclimb.api.application.port.in.user;

import swm.s3.coclimb.domain.user.InstagramInfo;

public interface UserCommand {
    Long createUserByInstagramInfo(InstagramInfo instagram);
}
