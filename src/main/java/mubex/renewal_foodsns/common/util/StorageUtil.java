package mubex.renewal_foodsns.common.util;

import java.util.UUID;
import org.springframework.web.util.UriComponentsBuilder;

public enum StorageUtil {

    CLOUD_FRONT("http://d1tqtrge1a6m8r.cloudfront.net/{file-name}"),
    ;

    private final String uri;

    StorageUtil(final String uri) {
        this.uri = uri;
    }

    public String generate(final Object... obj) {
        return UriComponentsBuilder.fromHttpUrl(this.uri).build(obj).toString();
    }

    public static String generateS3Key(final String fileName) {
        return UUID.randomUUID() + "_" + fileName;
    }

    public static String generateS3Key(final String userIdentifier, final String fileName) {
        return userIdentifier + "/" + generateS3Key(fileName);
    }
}
