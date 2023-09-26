package swm.s3.coclimb.api.adapter.out.persistence.media;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;
import swm.s3.coclimb.api.IntegrationTestSupport;
import swm.s3.coclimb.domain.media.Media;
import swm.s3.coclimb.domain.user.User;

import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

class MediaRepositoryTest extends IntegrationTestSupport {

    @Test
    @DisplayName("UserId로 모든 미디어를 삭제할 수 있다.")
    @Transactional
    void deleteAllByUserId() {
        //given
        userJpaRepository.save(User.builder().build());
        User user = userJpaRepository.findAll().get(0);
        mediaJpaRepository.saveAll(IntStream.range(0,5).mapToObj(i -> Media.builder()
                .user(user)
                .build()).toList());

        //when
        mediaRepository.deleteAllByUserId(user.getId());
        List<Media> sut = mediaJpaRepository.findAll();

        //then
        assertThat(sut).isEmpty();
    }
}