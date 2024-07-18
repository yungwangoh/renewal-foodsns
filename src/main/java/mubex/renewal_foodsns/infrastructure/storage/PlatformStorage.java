package mubex.renewal_foodsns.infrastructure.storage;

import org.springframework.web.multipart.MultipartFile;

public interface PlatformStorage {

    String process(MultipartFile multipartFile);

    String update(MultipartFile multipartFile);

    String process(byte[] content, String format);
}
