package swm.s3.coclimb.api.adapter.out.persistence.media;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import swm.s3.coclimb.api.application.port.out.media.MediaLoadPort;
import swm.s3.coclimb.api.application.port.out.media.MediaUpdatePort;
import swm.s3.coclimb.api.exception.errortype.media.MediaNotFound;
import swm.s3.coclimb.domain.Media;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MediaRepository implements MediaLoadPort, MediaUpdatePort {

    private final MediaJpaRepository mediaJpaRepository;

    public List<Media> findAll() {
        return mediaJpaRepository.findAll();
    }

    public List<Media> findAllVideos() {
        return mediaJpaRepository.findAllVideos();
    }

    public void save(Media media) {
        mediaJpaRepository.save(media);
    }

    public Media getById(Long id) {
        return mediaJpaRepository.findById(id).orElseThrow(MediaNotFound::new);
    }

    public void deleteById(Long id) {
        mediaJpaRepository.deleteById(id);
    }
}
