package mubex.renewal_foodsns.common.util;

import org.springframework.web.util.UriComponentsBuilder;

public class UriUtil {

    public static final String GCS = "https://storage.cloud.google.com/";
    public static final String GCS_URI = "https://storage.cloud.google.com/{bucket}/{uuid}";

    public static String generate(String bucket, String fileName) {
        return UriComponentsBuilder.fromHttpUrl(GCS_URI).build(bucket, fileName).toString();
    }
}
