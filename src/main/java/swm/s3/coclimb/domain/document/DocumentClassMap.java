package swm.s3.coclimb.domain.document;

import java.util.Map;

public class DocumentClassMap {
    private static Map<String, Class<?>> targetMap = Map.of(
            Document.GYM.getIndex(), GymDocument.class,
            Document.USER.getIndex(), UserDocument.class
    );

    public static Class<?> getClass(Document document) {
        return targetMap.get(document.getIndex());
    }
}
