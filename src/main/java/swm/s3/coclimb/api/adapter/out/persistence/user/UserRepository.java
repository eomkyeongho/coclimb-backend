package swm.s3.coclimb.api.adapter.out.persistence.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import swm.s3.coclimb.api.application.port.out.persistence.user.UserLoadPort;
import swm.s3.coclimb.api.application.port.out.persistence.user.UserUpdatePort;
import swm.s3.coclimb.api.exception.errortype.user.UserNotFound;
import swm.s3.coclimb.domain.document.UserDocument;
import swm.s3.coclimb.domain.user.User;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepository implements UserLoadPort, UserUpdatePort {

    private final UserJpaRepository userJpaRepository;
    private final UserDocumentRepository userDocumentRepository;

    @Override
    public Optional<User> findByInstagramUserId(Long instagramUserId) {
        return userJpaRepository.findByInstagramUserInfoId(instagramUserId);
    }

    @Override
    public Optional<User> findByKakaoUserId(Long kakaoUserId) {
        return userJpaRepository.findByKakaoUserInfoId(kakaoUserId);
    }

    @Override
    public Long save(User user) {
        User savedUser = userJpaRepository.save(user);
        userDocumentRepository.save(UserDocument.fromDomain(savedUser));
        return savedUser.getId();
    }

    @Override
    public void delete(User user) {
        userDocumentRepository.delete(UserDocument.fromDomain(user));
        userJpaRepository.delete(user);
    }

    @Override
    public User getById(Long id) {
        return userJpaRepository.findById(id)
                .orElseThrow(UserNotFound::new);
    }
}
