package co.edu.umb.amazonimageuploader.amazon.bucket;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BucketName {

  PROFILE_IMAGE("devmanuel-image-upload-123");

  private final String bucketName;

}
