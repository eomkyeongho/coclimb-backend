package swm.s3.coclimb.api.adapter.out.persistence.gymlike;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;
import swm.s3.coclimb.api.IntegrationTestSupport;
import swm.s3.coclimb.domain.gym.Gym;
import swm.s3.coclimb.domain.gymlike.GymLike;
import swm.s3.coclimb.domain.user.User;

import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

class GymLikeRepositoryTest extends IntegrationTestSupport {

    @Test
    @DisplayName("UserId로 모든 찜한 암장을 삭제할 수 있다.")
    @Transactional
    void deleteAllByUserId() {
        //given
        userJpaRepository.save(User.builder().build());
        gymJpaRepository.saveAll(IntStream.range(0,5).mapToObj(i -> Gym.builder()
                .name("암장" + String.valueOf(i))
                .build()).toList()
        );
        User user = userJpaRepository.findAll().get(0);
        gymLikeJpaRepository.saveAll(IntStream.range(0,5).mapToObj(i -> GymLike.builder()
                .user(user)
                .gym(gymJpaRepository.findAll().get(i))
                .build()).toList()
        );

        //when
        gymLikeRepository.deleteAllByUserId(user.getId());
        List<GymLike> sut = gymLikeJpaRepository.findAll();

        //then
        assertThat(sut).isEmpty();
    }

}