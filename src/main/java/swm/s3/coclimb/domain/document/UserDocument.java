package swm.s3.coclimb.domain.document;

import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;
import swm.s3.coclimb.domain.user.User;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Document(indexName = "users")
public class UserDocument {

    @Id
    private String id;
    private String name;

    @Builder
    public UserDocument(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public static UserDocument fromDomain(User user) {
        return UserDocument.builder()
                .id(String.valueOf(user.getId()))
                .name(user.getName())
                .build();
    }

}
