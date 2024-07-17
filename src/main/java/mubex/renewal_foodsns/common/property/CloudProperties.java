package mubex.renewal_foodsns.common.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.cloud.gcp.storage")
public record CloudProperties(String bucket, Credentials credentials) {

    public record Credentials(String location) {
    }
}
