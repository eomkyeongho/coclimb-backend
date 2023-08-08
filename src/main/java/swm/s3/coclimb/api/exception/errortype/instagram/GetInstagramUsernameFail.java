package swm.s3.coclimb.api.exception.errortype.instagram;

import swm.s3.coclimb.api.exception.errortype.basetype.InternalServerError;

public class GetInstagramUsernameFail extends InternalServerError {
    public GetInstagramUsernameFail() {
        super("인스타그램 유저 이름 받아오기 실패");
    }
}
