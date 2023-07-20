package swm.s3.coclimb.api.application.port.in.auth;

import swm.s3.coclimb.api.application.port.in.auth.dto.SessionDataDto;

public interface AuthCommand {

    SessionDataDto authenticateWithInstagram(String code);
}
