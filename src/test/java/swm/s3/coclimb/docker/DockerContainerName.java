package swm.s3.coclimb.docker;

public enum DockerContainerName {
    ELASTICSEARCH("test-elasticsearch"),
    MYSQL("test-mysql");
    private final String value;

    DockerContainerName(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
