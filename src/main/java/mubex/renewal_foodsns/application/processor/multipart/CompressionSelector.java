package mubex.renewal_foodsns.application.processor.multipart;

import com.sksamuel.scrimage.webp.WebpWriter;

public enum CompressionSelector {
    LOSS,
    LOSS_LESS,
    ;

    public static WebpWriter selectLossOrLossless(final CompressionSelector selector) {
        if (selector == LOSS) {
            return WebpWriter.DEFAULT;
        } else {
            return WebpWriter.MAX_LOSSLESS_COMPRESSION;
        }
    }
}
