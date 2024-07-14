package mubex.renewal_foodsns.application.processor;

import lombok.RequiredArgsConstructor;
import mubex.renewal_foodsns.infrastructure.storage.PlatformStorage;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class MultiPartFileProcessor {

    private final PlatformStorage platformStorage;

    public String write(MultipartFile multipartFile) {
        return platformStorage.process(multipartFile);
    }
}
