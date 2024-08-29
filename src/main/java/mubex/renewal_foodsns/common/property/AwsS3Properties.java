package mubex.renewal_foodsns.common.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.cloud.aws.s3")
public record AwsS3Properties(String bucket) {
}
