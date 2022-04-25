package co.edu.umb.amazonimageuploader.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class UserProfile {
  private final UUID userProfileId;
  private final String username;
  // S3 key
  private Optional<String> userProfileImageLink;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    UserProfile that = (UserProfile) o;
    return Objects.equals(userProfileId, that.userProfileId) &&
      Objects.equals(username, that.username) &&
      Objects.equals(userProfileImageLink, that.userProfileImageLink);
  }

  @Override
  public int hashCode() {
    return Objects.hash(userProfileId, username, userProfileImageLink);
  }
}
