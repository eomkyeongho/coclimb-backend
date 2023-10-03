package swm.s3.coclimb.domain.gym;

import jakarta.persistence.Embedded;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.elasticsearch.annotations.Document;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Document(indexName = "gyms")
public class GymDocument {

    @Id
    private String id;
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
    public GymDocument(String id, String name, String address, String phone, String imageUrl, String instagramId, String homepageUrl, String gradingSystem, Location location) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.imageUrl = imageUrl;
        this.instagramId = instagramId;
        this.homepageUrl = homepageUrl;
        this.gradingSystem = gradingSystem;
        this.location = location;
    }

    public Gym toDomain() {
        return Gym.builder()
                .id(Long.valueOf(id))
                .name(name)
                .phone(phone)
                .address(address)
                .imageUrl(imageUrl)
                .instagramId(instagramId)
                .homepageUrl(homepageUrl)
                .gradingSystem(gradingSystem)
                .location(location)
                .build();
    }

    public static GymDocument fromDomain(Gym gym) {
        return GymDocument.builder()
                .id(gym.getId().toString())
                .name(gym.getName())
                .phone(gym.getPhone())
                .address(gym.getAddress())
                .imageUrl(gym.getImageUrl())
                .instagramId(gym.getInstagramId())
                .homepageUrl(gym.getHomepageUrl())
                .gradingSystem(gym.getGradingSystem())
                .location(gym.getLocation())
                .build();
    }
}
