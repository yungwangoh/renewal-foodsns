package mubex.renewal_foodsns.application.processor.multipart;

import mubex.renewal_foodsns.application.processor.multipart.selector.CompressionSelector;
import org.springframework.web.multipart.MultipartFile;

public interface FileProcessor {

    default MultipartFile resize(MultipartFile multipartFile, int w, int h) {
        return multipartFile;
    }

    MultipartFile compress(MultipartFile multipartFile, CompressionSelector selector);
}
