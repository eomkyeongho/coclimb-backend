package swm.s3.coclimb.api.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import swm.s3.coclimb.api.application.port.in.user.UserCommand;
import swm.s3.coclimb.api.application.port.in.user.UserQuery;
import swm.s3.coclimb.api.application.port.out.aws.AwsS3UpdatePort;
import swm.s3.coclimb.api.application.port.out.persistence.gymlike.GymLikeUpdatePort;
import swm.s3.coclimb.api.application.port.out.persistence.media.MediaLoadPort;
import swm.s3.coclimb.api.application.port.out.persistence.media.MediaUpdatePort;
import swm.s3.coclimb.api.application.port.out.persistence.user.UserLoadPort;
import swm.s3.coclimb.api.application.port.out.persistence.user.UserUpdatePort;
import swm.s3.coclimb.domain.media.Media;
import swm.s3.coclimb.domain.user.InstagramUserInfo;
import swm.s3.coclimb.domain.user.KakaoUserInfo;
import swm.s3.coclimb.domain.user.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService implements UserQuery, UserCommand {

    private final UserLoadPort userLoadPort;
    private final UserUpdatePort userUpdatePort;
    private final MediaUpdatePort mediaUpdatePort;
    private final MediaLoadPort mediaLoadPort;
    private final GymLikeUpdatePort gymLikeUpdatePort;
    private final AwsS3UpdatePort awsS3UpdatePort;


    @Override
    public Optional<User> findUserByInstagramUserId(Long instagramUserId) {
        return userLoadPort.findByInstagramUserId(instagramUserId);
    }

    @Override
    public Optional<User> findUserByKakaoUserId(Long kakaoUserId) {
        return userLoadPort.findByKakaoUserId(kakaoUserId);
    }

    @Override
    @Transactional
    public Long createUserByInstagramInfo(InstagramUserInfo instagramUserInfo) {
        return userUpdatePort.save((User.builder()
                .name(instagramUserInfo.getName())
                .instagramUserInfo(instagramUserInfo)
                .build()));
    }

    @Override
    @Transactional
    public Long createUserByKakaoInfo(KakaoUserInfo kakaoUserInfo) {
        return userUpdatePort.save((User.builder()
                .name(UUID.randomUUID().toString())
                .kakaoUserInfo(kakaoUserInfo)
                .build()));
    }

    @Override
    @Transactional
    public void deleteUser(User user) {
        List<Media> medias = mediaLoadPort.findByUserId(user.getId());
        IntStream.range(0, medias.size()).forEach(i -> {
            awsS3UpdatePort.deleteFile(medias.get(i).getMediaUrl());
            awsS3UpdatePort.deleteFile(medias.get(i).getThumbnailUrl());
        });
        mediaUpdatePort.deleteAllByUserId(user.getId());
        gymLikeUpdatePort.deleteAllByUserId(user.getId());
        userUpdatePort.delete(user);
    }
}
