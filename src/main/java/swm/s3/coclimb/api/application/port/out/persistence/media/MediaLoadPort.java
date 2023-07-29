package swm.s3.coclimb.api.application.port.out.persistence.media;

import swm.s3.coclimb.domain.Media;

import java.util.List;

public interface MediaLoadPort {
    List<Media> findAll();
    List<Media> findAllVideos();
}
