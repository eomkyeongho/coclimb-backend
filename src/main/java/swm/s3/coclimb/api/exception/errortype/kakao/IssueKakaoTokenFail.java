package swm.s3.coclimb.api.exception.errortype.kakao;

import swm.s3.coclimb.api.exception.errortype.basetype.InternalServerError;

public class IssueKakaoTokenFail extends InternalServerError {
    public IssueKakaoTokenFail() {
        super("카카오 토큰 발급 실패");
    }
}
