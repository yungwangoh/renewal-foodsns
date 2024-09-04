package mubex.renewal_foodsns.application.processor.multipart;

import lombok.RequiredArgsConstructor;
import mubex.renewal_foodsns.application.processor.multipart.image.ImageProcessor;
import mubex.renewal_foodsns.application.processor.multipart.selector.CompressionSelector;
import mubex.renewal_foodsns.infrastructure.storage.PlatformStorage;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@RequiredArgsConstructor
public class MultiPartFileProcessor {

    private final PlatformStorage platformStorage;
    private final ImageProcessor imageProcessor;

    public String write(final MultipartFile multipartFile) {

        MultipartFile mf = imageProcessor.resize(multipartFile, 1000, 1000);

        MultipartFile convertFile = imageProcessor.compress(mf, CompressionSelector.LOSS);

        return platformStorage.process(convertFile);
    }

    public String thumbnail(final byte[] content, final String format) {
        return platformStorage.process(content, format);
    }

    public String update(final MultipartFile multipartFile) {
        return platformStorage.process(multipartFile);
    }
}
