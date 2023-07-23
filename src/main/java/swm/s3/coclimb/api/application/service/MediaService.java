package swm.s3.coclimb.api.application.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import swm.s3.coclimb.api.adapter.out.instagram.InstagramRestApiManager;
import swm.s3.coclimb.api.adapter.out.instagram.dto.InstagramMediaResponseDto;
import swm.s3.coclimb.api.application.port.in.media.MediaQuery;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MediaService implements MediaQuery {

    private final InstagramRestApiManager instagramRestApiManager;
    @Override
    public List<InstagramMediaResponseDto> getMyInstagramVideos(String accessToken) throws JsonProcessingException {
        List<InstagramMediaResponseDto> myMedias = instagramRestApiManager.getMyMedias(accessToken);
        List<InstagramMediaResponseDto> myVideos = new ArrayList<InstagramMediaResponseDto>();

        for(InstagramMediaResponseDto media : myMedias) {
            if (media.getMediaType().equals("VIDEO")) {
                myVideos.add(media);
            }
        }

        return myVideos;
    }
}
