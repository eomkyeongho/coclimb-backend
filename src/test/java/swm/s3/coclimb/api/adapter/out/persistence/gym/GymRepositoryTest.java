package swm.s3.coclimb.api.adapter.out.persistence.gym;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import swm.s3.coclimb.api.IntegrationTestSupport;
import swm.s3.coclimb.api.adapter.out.persistence.gym.dto.GymNearby;
import swm.s3.coclimb.domain.gym.Gym;
import swm.s3.coclimb.domain.gym.Location;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class GymRepositoryTest extends IntegrationTestSupport {

    @AfterEach
    void tearDown() {
        gymJpaRepository.deleteAllInBatch();
    }

    @Test
    @DisplayName("해당하는 이름의 암장이 존재하는지 확인한다.")
    void existsByName() throws Exception {
        // given
        gymJpaRepository.save(Gym.builder()
                .name("존재하는 암장")
                .build());

        // when, then
        assertThat(gymRepository.existsByName("존재하는 암장")).isTrue();
        assertThat(gymRepository.existsByName("존재하지 않는 암장")).isFalse();
    }

    @Test
    @DisplayName("해당하는 이름의 암장을 조회한다.")
    void findByName() throws Exception {
        // given
        gymJpaRepository.save(Gym.builder()
                .name("테스트 암장")
                .build());

        // when
        Optional<Gym> sut = gymRepository.findByName("테스트 암장");

        // then
        assertThat(sut).isNotNull();
        assertThat(sut.get().getName()).isEqualTo("테스트 암장");
    }

    
    // 37.5454, 126.9882
    // 37.5567, 126.9709
    // 약 1.976km 지점 예시

    @Test
    @DisplayName("지정된 거리 이내에 가까운 암장을 조회한다.")
    void findNearby() {
        //given
        gymJpaRepository.save(Gym.builder()
                .name("테스트 암장")
                .location(Location.of(37.5454f, 126.9882f))
                .build());

        //when
        List<GymNearby> sut1 = gymRepository.findNearby(37.5567f, 126.9709f, 3f);
        List<GymNearby> sut2 = gymRepository.findNearby(37.5567f, 126.9709f, 1f);

        //then
        assertThat(sut1.size()).isEqualTo(1);
        assertThat(sut2.size()).isEqualTo(0);
    }
}