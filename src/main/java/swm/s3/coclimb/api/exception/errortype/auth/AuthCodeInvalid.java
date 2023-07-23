package swm.s3.coclimb.api.exception.errortype.auth;

import swm.s3.coclimb.api.exception.errortype.basetype.BadRequest;

public class AuthCodeInvalid extends BadRequest {
    public AuthCodeInvalid() {
        super("인증 코드가 유효하지 않습니다.");
        addField("code", "유효하지 않은 인증 코드입니다.");
    }
}
