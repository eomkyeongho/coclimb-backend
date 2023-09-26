package swm.s3.coclimb.api.adapter.out.persistence.gymlike;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import swm.s3.coclimb.api.application.port.out.persistence.gymlike.GymLikeLoadPort;
import swm.s3.coclimb.api.application.port.out.persistence.gymlike.GymLikeUpdatePort;
import swm.s3.coclimb.api.exception.errortype.gymlike.GymLikeNotFound;
import swm.s3.coclimb.domain.gymlike.GymLike;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class GymLikeRepository implements GymLikeUpdatePort, GymLikeLoadPort {

    private final GymLikeJpaRepository gymLikeJpaRepository;

    @Override
    @Transactional
    public void save(GymLike gymLike) {
        gymLikeJpaRepository.save(gymLike);
    }

    @Override
    public List<GymLike> findByUserId(Long userId) {
        return gymLikeJpaRepository.findByUserId(userId);
    }

    @Override
    public GymLike getByUserIdAndGymName(Long userId, String gymName) {
        return gymLikeJpaRepository.findByUserIdAndGymName(userId, gymName).orElseThrow(GymLikeNotFound::new);
    }

    @Override
    public Optional<GymLike> findByUserIdAndGymName(Long userId, String gymName) {
        return gymLikeJpaRepository.findByUserIdAndGymName(userId, gymName);
    }

    @Override
    public void delete(GymLike gymLike) {
        gymLikeJpaRepository.delete(gymLike);
    }

    @Override
    public void deleteAllByUserId(Long userId) {
        gymLikeJpaRepository.deleteAllByUserId(userId);
    }
}
