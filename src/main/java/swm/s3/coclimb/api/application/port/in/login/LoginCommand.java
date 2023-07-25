package swm.s3.coclimb.api.application.port.in.login;

import com.fasterxml.jackson.core.JsonProcessingException;
import swm.s3.coclimb.api.application.port.in.login.dto.SessionDataDto;

public interface LoginCommand {

    SessionDataDto authenticateWithInstagram(String code) throws JsonProcessingException;
}
