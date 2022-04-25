package co.edu.umb.amazonimageuploader.domain.repository;

import co.edu.umb.amazonimageuploader.domain.entity.UserProfile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class FakeUserProfileDataStore {

  private static final List<UserProfile> USER_PROFILES = new ArrayList<>();

  static {
    USER_PROFILES.add(
      UserProfile.builder()
        .userProfileId(UUID.fromString("42c737a1-3e71-4c32-89e8-f5d551ff8ffc"))
        .username("janetjones")
        .build()
    );
    USER_PROFILES.add(
      UserProfile.builder()
        .userProfileId(UUID.fromString("d9333e69-e5d0-40dd-b2f2-93991a768697"))
        .username("antoniojunior")
        .build()
    );
  }

  public List<UserProfile> getUserProfiles(){
    return USER_PROFILES;
  }

}
