package swm.s3.coclimb.api.application.port.out.media;

import swm.s3.coclimb.domain.Media;

public interface MediaUpdatePort {
    void save(Media media);
}
