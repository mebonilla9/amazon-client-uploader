package co.edu.umb.amazonimageuploader.application.controller;

import co.edu.umb.amazonimageuploader.application.service.UserProfileService;
import co.edu.umb.amazonimageuploader.domain.entity.UserProfile;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
public record UserProfileController(UserProfileService userProfileService) {

  @GetMapping
  public ResponseEntity<List<UserProfile>> getUsersProfiles() {
    return new ResponseEntity<>(userProfileService.getUserProfiles(), HttpStatus.OK);
  }

  @PostMapping(
    path = "{userProfileId}/image/upload",
    consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE
  )
  public ResponseEntity<?> uploadUserProfileImage(@PathVariable("userProfileId") UUID userProfileId,
                                                  @RequestParam("file") MultipartFile file) {
    try {
      userProfileService.uploadUserProfileImage(userProfileId, file);
      return new ResponseEntity<>(HttpStatus.CREATED);
    } catch (IOException e) {
      throw new IllegalStateException(e);
    }
  }

  @GetMapping(
    path = "{userProfileId}/image/download",
    produces = MediaType.APPLICATION_OCTET_STREAM_VALUE
  )
  public byte[] downloadUserProfileImage(@PathVariable("userProfileId") UUID userProfileId){
    byte[] imageArray = userProfileService.downloadUserProfileImage(userProfileId);
    return imageArray;
  }
}
