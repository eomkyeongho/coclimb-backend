package swm.s3.coclimb.domain.gym;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import swm.s3.coclimb.domain.BaseTimeEntity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "gyms")
public class Gym extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String address;
    private String phone;
    @Length(max = 1024)
    private String imageUrl;
    private String instagramId;
    @Length(max = 1024)
    private String homepageUrl;
    private String gradingSystem;

    @Embedded
    private Location location;

    @Builder
    public Gym(String name, String address, String phone, String imageUrl, String instagramId, String homepageUrl, String gradingSystem, Location location) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.imageUrl = imageUrl;
        this.instagramId = instagramId;
        this.homepageUrl = homepageUrl;
        this.gradingSystem = gradingSystem;
        this.location = location;
    }

    public void remove() {
        this.address = null;
        this.phone = null;
        this.imageUrl = null;
        this.instagramId = null;
        this.homepageUrl = null;
        this.gradingSystem = null;
        this.location = null;
    }

    public void update(Gym updateInfo) {
        this.name = (updateInfo.name == null) ? name : updateInfo.name;
        this.address = (updateInfo.address == null) ? address : updateInfo.address;
        this.phone = (updateInfo.phone == null) ? phone : updateInfo.phone;
        this.imageUrl = (updateInfo.imageUrl == null) ? imageUrl : updateInfo.imageUrl;
        this.instagramId = (updateInfo.instagramId == null) ? instagramId : updateInfo.instagramId;
        this.homepageUrl = (updateInfo.homepageUrl == null) ? homepageUrl : updateInfo.homepageUrl;
        this.gradingSystem = (updateInfo.gradingSystem == null) ? gradingSystem : updateInfo.gradingSystem;
        this.location = (updateInfo.location == null) ? location : updateInfo.location;
    }

}
