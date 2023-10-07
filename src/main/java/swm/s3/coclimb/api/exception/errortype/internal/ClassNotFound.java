package swm.s3.coclimb.api.exception.errortype.internal;

import swm.s3.coclimb.api.exception.FieldErrorType;
import swm.s3.coclimb.api.exception.errortype.basetype.NotFound;

public class ClassNotFound extends NotFound {
    public ClassNotFound() {
        super("해당 이름의 클래스를 찾을 수 없습니다.");
        addField("className", FieldErrorType.NOT_MATCH);
    }
}
