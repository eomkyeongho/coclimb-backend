package swm.s3.coclimb.api.adapter.out;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import swm.s3.coclimb.api.adapter.out.elasticsearch.gym.GymDocumentRepository;
import swm.s3.coclimb.api.adapter.out.persistence.gym.GymRepository;
import swm.s3.coclimb.api.adapter.out.persistence.gym.dto.GymNearby;
import swm.s3.coclimb.api.exception.errortype.gym.GymNotFound;
import swm.s3.coclimb.domain.gym.Gym;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
//@Component
public class GymLoadFacade  {
    private final GymRepository gymRepository;
    private final GymDocumentRepository gymDocumentRepository;


    public boolean existsByName(String name) {
        return gymRepository.existsByName(name);
    }


    public Optional<Gym> findByName(String name) {
        return gymRepository.findByName(name);
    }


    public Gym getByNameAtEs(String name) {
        return gymDocumentRepository.findByName(name)
                .orElseThrow(GymNotFound::new)
                .toDomain();
}


    public List<Gym> findAll() {
        return gymRepository.findAll();
    }


    public Page<Gym> findPage(PageRequest pageRequest) {
        return gymRepository.findPage(pageRequest);
    }


    public List<GymNearby> findNearby(float latitude, float longitude, float distance) {
        return gymRepository.findNearby(latitude, longitude, distance);
    }

    @Deprecated
    public List<Gym> searchByName(String name) {
        return gymRepository.searchByName(name);
    }


    public List<String> autoCorrectGymNames(String name, int size) {
        return gymDocumentRepository.autoCompleteName(name, size);
//        return gymElasticsearchQuery.autoCorrectName(name, size);
    }
}
