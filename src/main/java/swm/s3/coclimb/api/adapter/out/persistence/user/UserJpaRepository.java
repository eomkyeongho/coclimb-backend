package swm.s3.coclimb.api.adapter.out.persistence.user;

import org.springframework.data.jpa.repository.JpaRepository;
import swm.s3.coclimb.domain.User;

import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<User, Long> {
    Optional<User> findByInstagramUserId(Long instagramUserId);
}
