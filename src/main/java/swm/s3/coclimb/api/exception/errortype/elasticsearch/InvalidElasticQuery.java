package swm.s3.coclimb.api.exception.errortype.elasticsearch;

import swm.s3.coclimb.api.exception.errortype.basetype.BadRequest;

public class InvalidElasticQuery extends BadRequest {
    public InvalidElasticQuery() {
        super("Elasticsearch Query IO Error.");
//        addField("name", FieldErrorType.NOT_MATCH);
    }
}
