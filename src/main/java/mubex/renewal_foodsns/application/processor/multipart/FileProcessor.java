package mubex.renewal_foodsns.application.processor.multipart;

import org.springframework.web.multipart.MultipartFile;

public interface FileProcessor {

    MultipartFile resize(MultipartFile multipartFile, int w, int h);

    MultipartFile compress(MultipartFile multipartFile, CompressionSelector selector);
}
