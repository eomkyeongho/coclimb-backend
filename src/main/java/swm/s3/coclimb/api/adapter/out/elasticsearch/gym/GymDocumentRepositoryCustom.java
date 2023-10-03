package swm.s3.coclimb.api.adapter.out.elasticsearch.gym;

import java.util.List;

public interface GymDocumentRepositoryCustom {

    List<String> autoCompleteName(String name, int size);
}
