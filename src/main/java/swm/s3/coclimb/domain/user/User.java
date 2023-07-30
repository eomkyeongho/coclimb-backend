package swm.s3.coclimb.domain.user;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue
    private Long id;

    private String name;


    @Embedded
    private InstagramInfo instagramInfo;

    @Builder
    public User(String name, InstagramInfo instagramInfo) {
        this.name = name;
        this.instagramInfo = instagramInfo;
    }

}
