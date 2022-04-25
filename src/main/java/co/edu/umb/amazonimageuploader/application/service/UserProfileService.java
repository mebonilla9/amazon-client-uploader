package co.edu.umb.amazonimageuploader.application.service;

import co.edu.umb.amazonimageuploader.amazon.bucket.BucketName;
import co.edu.umb.amazonimageuploader.amazon.filestore.FileStore;
import co.edu.umb.amazonimageuploader.domain.entity.UserProfile;
import co.edu.umb.amazonimageuploader.domain.repository.UserProfileDataAccessService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

import static org.apache.http.entity.ContentType.*;

@Service
public record UserProfileService(UserProfileDataAccessService userProfileDataAccessService,
                                 FileStore fileStore) {

  public List<UserProfile> getUserProfiles() {
    return userProfileDataAccessService.getUserProfiles();
  }

  public void uploadUserProfileImage(UUID userProfileId, MultipartFile file) throws IOException {
    // 1. Check if image is not empty
    isFileEmpty(file);
    // 2. If file is an image
    isImage(file);
    // 3. The user exists in our database?
    UserProfile user = getUserProfileOrThrow(userProfileId);
    // 4. Grab some metadata from file if any
    Map<String, String> metadata = extractMetadata(file);
    // 5. Store the image in s3 and update database with s3 image link
    String path = String.format("%s/%s", BucketName.PROFILE_IMAGE.getBucketName(), user.getUserProfileId());
    String fileName = String.format("%s-%s", UUID.randomUUID(),file.getOriginalFilename());

    fileStore.save(
      path,
      fileName,
      Optional.of(metadata),
      file.getInputStream()
    );

    user.setUserProfileImageLink(Optional.of(fileName));
  }

  public byte[] downloadUserProfileImage(UUID userProfileId) {
    UserProfile user = getUserProfileOrThrow(userProfileId);
    String path = String.format("%s/%s",
      BucketName.PROFILE_IMAGE.getBucketName(),
      user.getUserProfileId()
    );
    return user.getUserProfileImageLink()
      .map(key -> fileStore.download(path,key))
      .orElse(new byte[0]);
  }

  private Map<String, String> extractMetadata(MultipartFile file) {
    Map<String, String> metadata = new HashMap<>();
    metadata.put("Content-Type", file.getContentType());
    metadata.put("Content-Length", String.valueOf(file.getSize()));
    return metadata;
  }

  private UserProfile getUserProfileOrThrow(UUID userProfileId) {
    return userProfileDataAccessService
      .getUserProfiles()
      .stream()
      .filter(userProfile -> userProfile.getUserProfileId().equals(userProfileId))
      .findFirst()
      .orElseThrow(() -> new IllegalArgumentException(String.format("User profile %s not found", userProfileId)));
  }

  private void isImage(MultipartFile file) {
    if (Arrays.asList(IMAGE_JPEG, IMAGE_PNG, IMAGE_GIF, IMAGE_SVG).contains(file.getContentType())) {
      throw new IllegalStateException("File must be an image");
    }
  }

  private void isFileEmpty (MultipartFile file) {
    if (file.isEmpty()) {
      throw new IllegalStateException("Cannot upload empty file [ " + file.getSize() + "]");
    }
  }
}
