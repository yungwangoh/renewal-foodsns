package mubex.renewal_foodsns.infrastructure.storage;

import jakarta.annotation.Nullable;
import org.springframework.web.multipart.MultipartFile;

public interface PlatformStorage {

    String process(MultipartFile multipartFile);

    default String process(@Nullable String userIdentifier, MultipartFile multipartFile) {
        if (userIdentifier == null) {
            return process(multipartFile);
        }

        return null;
    }

    String update(MultipartFile multipartFile);

    String process(byte[] content, String format);
}
