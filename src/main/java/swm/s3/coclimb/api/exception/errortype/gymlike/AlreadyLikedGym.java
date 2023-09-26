package swm.s3.coclimb.api.exception.errortype.gymlike;

import swm.s3.coclimb.api.exception.errortype.basetype.Conflict;

public class AlreadyLikedGym extends Conflict {
    public AlreadyLikedGym() {
        super("이미 찜한 암장입니다.");
    }
}
