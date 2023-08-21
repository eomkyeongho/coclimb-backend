package swm.s3.coclimb.api.application.port.out.persistence.gymlike;

import swm.s3.coclimb.domain.gymlike.GymLike;

import java.util.List;

public interface GymLikeLoadPort {
    List<GymLike> findByUserId(Long userId);
    GymLike getByUserIdAndGymId(Long userId, Long gymId);
}
