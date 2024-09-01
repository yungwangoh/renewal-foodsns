package mubex.renewal_foodsns.application.processor.multipart;

import lombok.RequiredArgsConstructor;
import mubex.renewal_foodsns.infrastructure.storage.PlatformStorage;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@RequiredArgsConstructor
public class MultiPartFileProcessor {

    private final PlatformStorage platformStorage;

    public String write(final MultipartFile multipartFile) {
        return platformStorage.process(multipartFile);
    }

    public String thumbnail(final byte[] content, final String format) {
        return platformStorage.process(content, format);
    }

    public String update(final MultipartFile multipartFile) {
        return platformStorage.process(multipartFile);
    }
}
