package mubex.renewal_foodsns.common.util;

import lombok.Getter;

public class FileUtils {

    public static String changeFileExt(final String originFileName, final String newExt) {

        final int lastIdx = originFileName.lastIndexOf('.');

        if (lastIdx == -1) {
            return originFileName + newExt;
        }

        return originFileName.substring(0, lastIdx) + newExt;
    }

    public static String extractFileExt(final String originFileName) {
        return originFileName.substring(originFileName.lastIndexOf('.') + 1);
    }

    public static String parseContentType(final String contentType) {
        int idxOf = contentType.lastIndexOf('/');

        if (idxOf == -1) {
            throw new IllegalArgumentException("Invalid content type: " + contentType);
        }

        return contentType.substring(0, idxOf);
    }

    @Getter
    public enum FileExt {
        JPEG("jpeg"),
        JPG("jpg"),
        PNG("png"),
        WEBP("webp"),
        AVIF("avif"),
        MP4("mp4"),
        ;

        private final String ext;

        FileExt(final String ext) {
            this.ext = ext;
        }

        public boolean isCheck(final String originalFileName) {
            final int lastIndexOf = originalFileName.lastIndexOf('.');

            if (lastIndexOf == -1) {
                return false;
            }

            return originalFileName.substring(lastIndexOf + 1).equals(ext);
        }

        public String addDotExt() {
            return "." + ext;
        }
    }
}
