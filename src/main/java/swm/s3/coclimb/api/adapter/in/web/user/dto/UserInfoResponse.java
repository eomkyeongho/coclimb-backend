package swm.s3.coclimb.api.adapter.in.web.user.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserInfoResponse {
    String username;
    Long instagramUserId;
}
