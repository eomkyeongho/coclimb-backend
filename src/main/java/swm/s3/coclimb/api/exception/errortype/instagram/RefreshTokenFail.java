package swm.s3.coclimb.api.exception.errortype.instagram;

import swm.s3.coclimb.api.exception.errortype.basetype.InternalServerError;

public class RefreshTokenFail extends InternalServerError {
    public RefreshTokenFail() {
        super("토큰 갱신 실패");
    }
}
