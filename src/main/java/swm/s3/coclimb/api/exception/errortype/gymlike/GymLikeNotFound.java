package swm.s3.coclimb.api.exception.errortype.gymlike;

import swm.s3.coclimb.api.exception.errortype.basetype.NotFound;

public class GymLikeNotFound extends NotFound {
    public GymLikeNotFound() {
        super("해당 암장을 찜하지 않았습니다.");
    }
}
