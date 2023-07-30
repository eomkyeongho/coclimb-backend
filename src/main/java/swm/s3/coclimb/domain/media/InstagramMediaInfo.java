package swm.s3.coclimb.domain.media;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@Getter
public class InstagramMediaInfo {
    @Column(name = "instagram_media_id")
    private String mediaId; // mediaId vs Id
    @Column(name = "instagram_user_id")
    private String userId;
    @Column(name = "instagram_permalink")
    private String permalink;

    @Builder
    public InstagramMediaInfo(String userId, String mediaId, String permalink) {
        this.userId = userId;
        this.mediaId = mediaId;
        this.permalink = permalink;
    }
}
