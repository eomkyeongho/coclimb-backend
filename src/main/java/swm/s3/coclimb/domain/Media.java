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

    String mediaType;
    String platform; // instagram or original

    String instagramMediaId;
    String instagramPermalink;

    String mediaUrl;
    String thumbnailUrl;

    @Builder
    public Media(String mediaType, String platform, String instagramMediaId, String instagramPermalink, String mediaUrl, String thumbnailUrl) {
        this.mediaType = mediaType;
        this.platform = platform;
        this.instagramMediaId = instagramMediaId;
        this.instagramPermalink = instagramPermalink;
        this.mediaUrl = mediaUrl;
        this.thumbnailUrl = thumbnailUrl;
    }
}
