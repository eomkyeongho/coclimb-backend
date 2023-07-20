package swm.s3.coclimb.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "TB_USER")
public class User {

    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    private String username;

    // instagram information
    private Long instagramUserId;
    private String instagramAccessToken;
    private LocalDate instagramTokenExpireDate;

    @Builder
    public User(String username, Long instagramUserId, String instagramAccessToken, LocalDate instagramTokenExpireDate) {
        this.username = username;
        this.instagramUserId = instagramUserId;
        this.instagramAccessToken = instagramAccessToken;
        this.instagramTokenExpireDate = instagramTokenExpireDate;
    }

    public void update(User updatePart) {
        this.username = (updatePart.username == null) ? this.username : updatePart.username;
        this.instagramUserId = (updatePart.instagramUserId == null) ? this.instagramUserId : updatePart.instagramUserId;
        this.instagramAccessToken = (updatePart.instagramAccessToken == null) ? this.instagramAccessToken : updatePart.instagramAccessToken;
        this.instagramTokenExpireDate = (updatePart.instagramTokenExpireDate == null) ? this.instagramTokenExpireDate : updatePart.instagramTokenExpireDate;
    }
}
