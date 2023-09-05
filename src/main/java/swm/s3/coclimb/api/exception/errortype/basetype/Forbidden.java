package swm.s3.coclimb.api.exception.errortype.basetype;

import org.springframework.http.HttpStatus;

public abstract class Forbidden extends CustomException {

    public Forbidden(String message) {
        super(message);
    }

    @Override
    public int getStatusCode() {
        return 403;
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.FORBIDDEN;
    }
}
