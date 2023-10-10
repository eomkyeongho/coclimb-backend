package swm.s3.coclimb.api.exception.errortype.kakao;

import swm.s3.coclimb.api.exception.errortype.basetype.InternalServerError;

public class RetrieveKakaoUserInfoFail extends InternalServerError {
    public RetrieveKakaoUserInfoFail() {
        super("카카오 유저 정보를 가져오는데 실패했습니다.");
    }
}
