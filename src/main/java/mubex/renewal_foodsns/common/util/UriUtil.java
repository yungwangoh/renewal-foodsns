package mubex.renewal_foodsns.common.util;

import lombok.Getter;
import org.springframework.web.util.UriComponentsBuilder;

@Getter
public enum UriUtil {

    COMMENT_URI("https://localhost:8080/api/v1/{postId}/comments/{commentId}"),
    GCS("https://storage.cloud.google.com/"),
    GCS_URI("https://storage.cloud.google.com/{bucket}/{uuid}");

    private final String uri;

    UriUtil(final String uri) {
        this.uri = uri;
    }

    public String generate(final Object... obj) {
        return UriComponentsBuilder.fromHttpUrl(this.uri).build(obj).toString();
    }
}
