package swm.s3.coclimb.api.application.port.out.persistence.gymlike;

import swm.s3.coclimb.domain.gymlike.GymLike;

public interface GymLikeUpdatePort {
    void save(GymLike gymLike);
    void delete(GymLike gymLike);
}
