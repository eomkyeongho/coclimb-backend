package swm.s3.coclimb.domain.document;

public enum Document {
    GYM("gyms"), USER("users"), NONE("none");
    private final String index;

    Document(String index) { this.index = index; }

    public String getIndex() { return index; }
}
