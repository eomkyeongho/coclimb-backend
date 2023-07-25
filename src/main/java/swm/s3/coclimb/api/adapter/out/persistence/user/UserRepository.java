package swm.s3.coclimb.api.adapter.out.persistence.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import swm.s3.coclimb.api.application.port.out.user.UserLoadPort;
import swm.s3.coclimb.api.application.port.out.user.UserUpdatePort;
import swm.s3.coclimb.domain.User;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepository implements UserLoadPort, UserUpdatePort {

    private final UserJpaRepository userJpaRepository;

    @Override
    public Optional<User> findByInstagramUserId(Long instagramUserId) {
        return userJpaRepository.findByInstagramUserId(instagramUserId);
    }

    @Override
    public void save(User user) {
        userJpaRepository.save(user);
    }
}
