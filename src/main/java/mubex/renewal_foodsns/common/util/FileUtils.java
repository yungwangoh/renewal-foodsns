package mubex.renewal_foodsns.common.util;

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

    public enum FileExt {
        JPEG("jpeg"),
        JPG("jpg"),
        PNG("png"),
        WEBP("webp"),
        AVIF("avif"),
        ;

        private final String ext;

        FileExt(final String ext) {
            this.ext = ext;
        }

        public boolean isCheck(final String originalFileName) {
            int lastIndexOf = originalFileName.lastIndexOf('.');

            if (lastIndexOf == -1) {
                return false;
            }

            return originalFileName.substring(lastIndexOf + 1).equals(ext);
        }

        public String getExt() {
            return "." + ext;
        }
    }
}
