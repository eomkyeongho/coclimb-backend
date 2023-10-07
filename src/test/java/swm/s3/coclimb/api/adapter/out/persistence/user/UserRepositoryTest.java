package swm.s3.coclimb.api.adapter.out.persistence.user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import swm.s3.coclimb.api.IntegrationTestSupport;
import swm.s3.coclimb.api.exception.errortype.user.UserNotFound;
import swm.s3.coclimb.domain.document.UserDocument;
import swm.s3.coclimb.domain.user.User;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UserRepositoryTest extends IntegrationTestSupport {

    @Test
    @DisplayName("id로 사용자 정보를 조회한다.")
    void getById() throws Exception {
        // given
        Long userId = userJpaRepository.save(User.builder()
                        .name("username")
                        .build()).getId();

        // when
        User sut = userRepository.getById(userId);

        // then
        assertThat(sut)
                .hasFieldOrPropertyWithValue("name", "username");
    }

    @Test
    @DisplayName("id로 사용자 정보 조회 시, 결과가 존재하지 않으면 에러가 발생한다.")
    void getUserNotExistsById() throws Exception {
        // given
        Long invalidUserId = 1L;

        // when, then
        assertThatThrownBy(() -> userRepository.getById(invalidUserId))
                .isInstanceOf(UserNotFound.class);
    }

    @Test
    @DisplayName("유저 정보를 저장 시, 추가로 Document 정보가 Elasticsarch에 저장된다.")
    void saveUserWithElasticsearch() throws Exception {
        // given
        User user = User.builder()
                .name("user")
                .build();

        // when
        Long savedId = userRepository.save(user);
        UserDocument sut = userDocumentRepository.findById(String.valueOf(savedId)).orElse(null);

        // then
        assertThat(sut).isNotNull();
        assertThat(sut.getName()).isEqualTo(user.getName());
    }

    @Test
    @DisplayName("유저 정보를 삭제 시, 추가로 Document 정보가 Elasticsarch에서 삭제된다.")
    void deleteUserWithElasticsearch() throws Exception {
        // given
        User user = User.builder()
                .name("user")
                .build();
        User savedUser = userJpaRepository.save(user);
        userDocumentRepository.save(UserDocument.fromDomain(savedUser));

        // when
        userRepository.delete(savedUser);
        UserDocument sut = userDocumentRepository.findById(String.valueOf(savedUser.getId())).orElse(null);

        // then
        assertThat(sut).isNull();
    }
}