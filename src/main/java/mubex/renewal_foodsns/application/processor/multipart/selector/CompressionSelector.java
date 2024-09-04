package mubex.renewal_foodsns.application.processor.multipart.selector;

import com.sksamuel.scrimage.nio.JpegWriter;
import com.sksamuel.scrimage.webp.WebpWriter;

public enum CompressionSelector {
    LOSS,
    LOSS_LESS,
    ;

    public static WebpWriter selectCompressionMethodToWebp(final CompressionSelector selector) {
        if (selector == LOSS) {
            return WebpWriter.DEFAULT;
        } else {
            return WebpWriter.MAX_LOSSLESS_COMPRESSION;
        }
    }

    public static JpegWriter selectCompressionMethodToJpeg(final CompressionSelector selector) {
        if (selector == LOSS) {
            return JpegWriter.Default;
        }

        throw new IllegalArgumentException("jpeg can't non-compression...");
    }
}
