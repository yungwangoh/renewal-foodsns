package mubex.renewal_foodsns.application.processor.multipart.image.convertor;

import com.sksamuel.scrimage.ImmutableImage;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import javax.imageio.ImageIO;
import lombok.extern.slf4j.Slf4j;
import mubex.renewal_foodsns.application.processor.multipart.CompressionSelector;
import mubex.renewal_foodsns.application.processor.multipart.image.ImageProcessor;
import mubex.renewal_foodsns.application.processor.multipart.impl.CustomMultipartFile;
import mubex.renewal_foodsns.common.util.FileUtils;
import mubex.renewal_foodsns.common.util.FileUtils.FileExt;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Component
public class WebpConvertor implements ImageProcessor {

    @Override
    public MultipartFile resize(final MultipartFile multipartFile, final int w, final int h) {

        try (InputStream is = multipartFile.getInputStream()) {
            final BufferedImage bufferedImage = Thumbnails.of(is)
                    .size(w, h)
                    .asBufferedImage();

            log.info("w = {}, h = {}", bufferedImage.getWidth(), bufferedImage.getHeight());

            final ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(
                    bufferedImage,
                    FileUtils.extractFileExt(Objects.requireNonNull(multipartFile.getOriginalFilename())),
                    baos
            );

            return CustomMultipartFile.builder()
                    .size(baos.size())
                    .inputStream(new ByteArrayInputStream(baos.toByteArray()))
                    .name(multipartFile.getName())
                    .originalFilename(multipartFile.getOriginalFilename())
                    .contentType(multipartFile.getContentType())
                    .build();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public MultipartFile compress(final MultipartFile multipartFile, final CompressionSelector selector) {

        if (FileExt.WEBP.isCheck(Objects.requireNonNull(multipartFile.getOriginalFilename()))) {
            return multipartFile;
        }

        try (InputStream is = multipartFile.getInputStream()) {
            final File file = ImmutableImage.loader()
                    .fromStream(is)
                    .output(CompressionSelector.selectLossOrLossless(selector),
                            new File(FileUtils.changeFileExt(
                                    Objects.requireNonNull(multipartFile.getOriginalFilename()),
                                    FileExt.WEBP.getExt()))
                    );

            return CustomMultipartFile.builder()
                    .size(file.length())
                    .inputStream(new FileInputStream(file))
                    .name(multipartFile.getName())
                    .originalFilename(multipartFile.getOriginalFilename())
                    .contentType("image/webp")
                    .build();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
