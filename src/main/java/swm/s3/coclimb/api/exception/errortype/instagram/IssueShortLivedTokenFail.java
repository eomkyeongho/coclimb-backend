package swm.s3.coclimb.api.exception.errortype.instagram;

import swm.s3.coclimb.api.exception.errortype.basetype.InternalServerError;

public class IssueShortLivedTokenFail extends InternalServerError {
    public IssueShortLivedTokenFail() {
        super("단기 토큰 발급 실패");
    }
}
