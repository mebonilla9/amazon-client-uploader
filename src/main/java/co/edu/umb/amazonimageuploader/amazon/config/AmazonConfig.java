package co.edu.umb.amazonimageuploader.amazon.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmazonConfig {

  @Bean
  public AmazonS3 amazonS3(){
    AWSCredentials awsCredentials = new BasicAWSCredentials(
      "AKIA4DFBIZM6WKTQM5UQ",
      "KKY3K8RmEb7I6ThHJOq8dkiDQ7+CyprGs3Hptswa"
    );
    return AmazonS3ClientBuilder
      .standard()
      .withRegion(Regions.US_EAST_1)
      .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
      .build();
  }
}
