package mubex.renewal_foodsns.application.processor.multipart.selector;

import java.util.Objects;
import lombok.RequiredArgsConstructor;
import mubex.renewal_foodsns.application.processor.multipart.image.ImageProcessor;
import mubex.renewal_foodsns.application.processor.multipart.video.VideoProcessor;
import mubex.renewal_foodsns.common.util.FileUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@RequiredArgsConstructor
public class FileProcessSelector {

    private final ImageProcessor imageProcessor;
    private final VideoProcessor videoProcessor;

    public MultipartFile select(final MultipartFile multipartFile) {

        switch (FileUtils.parseContentType(Objects.requireNonNull(multipartFile.getContentType()))) {
            case "image" -> {
                return imageProcessor.compress(multipartFile, CompressionSelector.LOSS);
            }
            case "video" -> {
                return videoProcessor.compress(multipartFile, CompressionSelector.LOSS);
            }
        }

        throw new IllegalArgumentException("Unsupported content type: " + multipartFile.getContentType());
    }
}
