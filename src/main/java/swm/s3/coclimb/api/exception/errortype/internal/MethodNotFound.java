package swm.s3.coclimb.api.exception.errortype.internal;

import swm.s3.coclimb.api.exception.FieldErrorType;
import swm.s3.coclimb.api.exception.errortype.basetype.NotFound;

public class MethodNotFound extends NotFound {
    public MethodNotFound() {
        super("해당 이름의 메서드를 찾을 수 없습니다.");
        addField("methodName", FieldErrorType.NOT_MATCH);
    }
}
