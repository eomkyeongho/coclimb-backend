package swm.s3.coclimb.domain.user;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import swm.s3.coclimb.domain.BaseTimeEntity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Embedded
    private InstagramUserInfo instagramUserInfo;

    @Embedded
    private KakaoUserInfo kakaoUserInfo;

    @Builder
    public User(String name, InstagramUserInfo instagramUserInfo, KakaoUserInfo kakaoUserInfo) {
        this.name = name;
        this.instagramUserInfo = instagramUserInfo;
        this.kakaoUserInfo = kakaoUserInfo;
    }

}
