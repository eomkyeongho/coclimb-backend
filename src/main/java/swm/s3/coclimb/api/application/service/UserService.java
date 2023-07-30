package swm.s3.coclimb.api.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import swm.s3.coclimb.api.application.port.in.user.UserCommand;
import swm.s3.coclimb.api.application.port.in.user.UserQuery;
import swm.s3.coclimb.api.application.port.out.persistence.user.UserLoadPort;
import swm.s3.coclimb.api.application.port.out.persistence.user.UserUpdatePort;
import swm.s3.coclimb.domain.user.InstagramInfo;
import swm.s3.coclimb.domain.user.User;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService implements UserQuery, UserCommand {

    private final UserLoadPort userLoadPort;
    private final UserUpdatePort userUpdatePort;


    @Override
    public Optional<User> findUserByInstagramUserId(Long instagramUserId) {
        return userLoadPort.findByInstagramUserId(instagramUserId);
    }

    @Override
    @Transactional
    public Long createUserByInstagramInfo(InstagramInfo instagramInfo) {
        return userUpdatePort.save((User.builder()
                .name(UUID.randomUUID().toString().substring(8))
                .instagramInfo(InstagramInfo.builder()
                        .userId(instagramInfo.getUserId())
                        .accessToken(instagramInfo.getAccessToken())
                        .tokenExpireTime(instagramInfo.getTokenExpireTime())
                        .build())
                .build()));
    }
}
