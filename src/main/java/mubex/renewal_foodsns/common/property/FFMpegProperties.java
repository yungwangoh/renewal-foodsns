package mubex.renewal_foodsns.common.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "ffmpeg")
public record FFMpegProperties(String path, String probePath) {
}
