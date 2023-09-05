package swm.s3.coclimb.api.exception.errortype.media;

import swm.s3.coclimb.api.exception.errortype.basetype.Forbidden;

public class MediaOwnerNotMatched extends Forbidden {
    public MediaOwnerNotMatched() {
        super("해당 미디어의 소유자가 아닙니다.");
    }
}
