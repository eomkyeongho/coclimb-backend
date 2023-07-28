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

    private String username;


    @Embedded
    private Instagram instagram;

    @Builder
    public User(String username, Instagram instagram) {
        this.username = username;
        this.instagram = instagram;
    }

}
