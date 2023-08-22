package swm.s3.coclimb.api.adapter.out.persistence.gym.dto;

public interface GymNearby {
    Long getId();
    String getName();
    float getLatitude();
    float getLongitude();
    float getDistance();
    String getAddress();

    String getImageUrl();
}
