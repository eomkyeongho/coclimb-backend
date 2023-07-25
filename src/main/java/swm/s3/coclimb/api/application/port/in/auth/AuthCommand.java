package swm.s3.coclimb.api.application.port.in.auth;

import com.fasterxml.jackson.core.JsonProcessingException;
import swm.s3.coclimb.api.application.port.in.auth.dto.SessionDataDto;

public interface AuthCommand {

    SessionDataDto authenticateWithInstagram(String code) throws JsonProcessingException;
}
