package swm.s3.coclimb.domain.media;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="medias")
public class Media {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // common attributes
    private Long userId;
    private String mediaType;
    private String platform; // instagram or original
    private String mediaUrl;
    private String thumbnailUrl;

    // for instagram
    @Embedded
    private InstagramMediaInfo instagramMediaInfo;

    @Builder
    public Media(Long userId, String mediaType, String platform, String mediaUrl, String thumbnailUrl, InstagramMediaInfo instagramMediaInfo) {
        this.userId = userId;
        this.mediaType = mediaType;
        this.platform = platform;
        this.mediaUrl = mediaUrl;
        this.thumbnailUrl = thumbnailUrl;
        this.instagramMediaInfo = instagramMediaInfo;
    }
}
