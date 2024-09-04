package mubex.renewal_foodsns.application.processor.multipart.image.convertor;

import com.sksamuel.scrimage.ImmutableImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import mubex.renewal_foodsns.application.processor.multipart.image.ImageProcessor;
import mubex.renewal_foodsns.application.processor.multipart.image.resize.ImageResizeSupport;
import mubex.renewal_foodsns.application.processor.multipart.impl.CustomMultipartFile;
import mubex.renewal_foodsns.application.processor.multipart.selector.CompressionSelector;
import mubex.renewal_foodsns.common.util.FileUtils;
import mubex.renewal_foodsns.common.util.FileUtils.FileExt;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Component
public class JpegConvertor implements ImageProcessor {

    @Override
    public MultipartFile resize(final MultipartFile multipartFile, final int w, final int h) {

        return ImageResizeSupport.support(multipartFile)
                .size(w, h)
                .toMultipartFile();
    }

    @Override
    public MultipartFile compress(final MultipartFile multipartFile, final CompressionSelector selector) {

        if (FileExt.JPEG.isCheck(Objects.requireNonNull(multipartFile.getOriginalFilename()))) {
            return multipartFile;
        }

        try (InputStream is = multipartFile.getInputStream()) {

            final File file = ImmutableImage.loader()
                    .fromStream(is)
                    .output(CompressionSelector.selectCompressionMethodToJpeg(selector),
                            new File(FileUtils.changeFileExt(
                                    Objects.requireNonNull(multipartFile.getOriginalFilename()),
                                    FileExt.JPEG.addDotExt()))
                    );

            return CustomMultipartFile.builder()
                    .size(file.length())
                    .inputStream(new FileInputStream(file))
                    .name(multipartFile.getName())
                    .originalFilename(multipartFile.getOriginalFilename())
                    .contentType(MediaType.IMAGE_JPEG_VALUE)
                    .build();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
