package swm.s3.coclimb.api.adapter.out.persistence.media;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import swm.s3.coclimb.domain.media.Media;

import java.util.List;
import java.util.Optional;

public interface MediaJpaRepository extends JpaRepository<Media, Long> {

    @EntityGraph(attributePaths = {"user"})
    Page<Media> findByIdNotNull(PageRequest pageRequest);
    Optional<Media> findByInstagramMediaInfoId(String instagramMediaId);

    Page<Media> findByMediaProblemInfoGymName(String gymName, PageRequest pageRequest);

    @EntityGraph(attributePaths = {"user"})
    Page<Media> findByUserId(Long userId, PageRequest pageRequest);

    @EntityGraph(attributePaths = {"user"})
    List<Media> findByUserId(Long userId);

    @Modifying(clearAutomatically = true)
    @Query("delete from Media m where m.user.id = :userId")
    void deleteAllByUserId(@Param("userId") Long userId);
}
