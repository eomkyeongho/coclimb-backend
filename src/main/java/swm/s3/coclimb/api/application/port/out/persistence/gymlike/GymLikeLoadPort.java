package swm.s3.coclimb.api.application.port.out.persistence.gymlike;

import swm.s3.coclimb.domain.gymlike.GymLike;

import java.util.List;
import java.util.Optional;

public interface GymLikeLoadPort {
    List<GymLike> findByUserId(Long userId);
    GymLike getByUserIdAndGymName(Long userId, String gymName);
    Optional<GymLike> findByUserIdAndGymName(Long userId, String gymName);
}
