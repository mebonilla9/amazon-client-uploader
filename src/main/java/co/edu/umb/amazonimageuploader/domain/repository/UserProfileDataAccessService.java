package co.edu.umb.amazonimageuploader.domain.repository;

import co.edu.umb.amazonimageuploader.domain.entity.UserProfile;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public record UserProfileDataAccessService(FakeUserProfileDataStore dataStore) {

  public List<UserProfile> getUserProfiles(){
    return dataStore.getUserProfiles();
  }

}
