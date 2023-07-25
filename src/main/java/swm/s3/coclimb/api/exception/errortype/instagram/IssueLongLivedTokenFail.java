package swm.s3.coclimb.api.exception.errortype.instagram;

import swm.s3.coclimb.api.exception.errortype.basetype.InternalServerError;

public class IssueLongLivedTokenFail extends InternalServerError {
    public IssueLongLivedTokenFail() {
        super("장기 토큰 발급 실패");
    }
}
