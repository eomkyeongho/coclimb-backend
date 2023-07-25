package swm.s3.coclimb.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Media {
    @Id
    @GeneratedValue
    @Column(name="media_id")
    Long id;

    // common attributes
    Long userId;
    String mediaType;
    String platform; // instagram or original
    String mediaUrl;
    String thumbnailUrl;

    // for instagram
    String instagramUserId;
    String instagramMediaId;
    String instagramPermalink;

    @Builder
    public Media(Long userId, String mediaType, String platform, String instagramMediaId, String instagramPermalink, String mediaUrl, String thumbnailUrl, String instagramUserId) {
        this.userId = userId;
        this.mediaType = mediaType;
        this.platform = platform;
        this.instagramMediaId = instagramMediaId;
        this.instagramPermalink = instagramPermalink;
        this.mediaUrl = mediaUrl;
        this.thumbnailUrl = thumbnailUrl;
        this.instagramUserId = instagramUserId;
    }
}
