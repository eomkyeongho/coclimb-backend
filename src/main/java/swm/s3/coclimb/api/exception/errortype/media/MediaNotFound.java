package swm.s3.coclimb.api.exception.errortype.media;

import swm.s3.coclimb.api.exception.errortype.basetype.NotFound;

public class MediaNotFound extends NotFound {

    public MediaNotFound() {
        super("해당 미디어를 찾을 수 없습니다.");
    }
}
