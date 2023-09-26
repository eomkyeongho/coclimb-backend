package swm.s3.coclimb.api.adapter.out.persistence.gymlike;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import swm.s3.coclimb.domain.gymlike.GymLike;

import java.util.List;
import java.util.Optional;

public interface GymLikeJpaRepository extends JpaRepository<GymLike, Long> {

    @EntityGraph(attributePaths = {"user", "gym"})
    List<GymLike> findByUserId(Long userId);

    @EntityGraph(attributePaths = {"user", "gym"})
    Optional<GymLike> findByUserIdAndGymName(Long userId, String gymName);

    @Modifying(clearAutomatically = true)
    @Query("delete from GymLike g where g.user.id = :userId")
    void deleteAllByUserId(@Param("userId") Long userId);
}
