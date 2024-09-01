package mubex.renewal_foodsns.application.processor.multipart;

import com.sksamuel.scrimage.webp.WebpWriter;

public enum LosslessSelector {
    LOSS,
    LOSS_LESS,
    ;

    public static WebpWriter selectLossOrLossless(final LosslessSelector selector) {
        if (selector == LOSS) {
            return WebpWriter.DEFAULT;
        } else {
            return WebpWriter.MAX_LOSSLESS_COMPRESSION;
        }
    }
}
