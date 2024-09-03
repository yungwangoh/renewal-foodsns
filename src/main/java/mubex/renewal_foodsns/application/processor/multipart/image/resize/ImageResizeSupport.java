package mubex.renewal_foodsns.application.processor.multipart.image.resize;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import javax.imageio.ImageIO;
import mubex.renewal_foodsns.application.processor.multipart.impl.CustomMultipartFile;
import mubex.renewal_foodsns.common.util.FileUtils;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.web.multipart.MultipartFile;

public class ImageResizeSupport {

    private final MultipartFile multipartFile;

    private int w, h;

    private ImageResizeSupport(final MultipartFile multipartFile) {
        this.multipartFile = multipartFile;
    }

    public static ImageResizeSupport support(final MultipartFile multipartFile) {
        return new ImageResizeSupport(multipartFile);
    }

    public ImageResizeSupport size(final int w, final int h) {
        this.w = w;
        this.h = h;

        return this;
    }

    public MultipartFile toMultipartFile() {

        try (InputStream is = multipartFile.getInputStream()) {
            final BufferedImage bufferedImage = Thumbnails.of(is)
                    .size(w, h)
                    .asBufferedImage();

            final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(
                    bufferedImage,
                    FileUtils.extractFileExt(Objects.requireNonNull(multipartFile.getOriginalFilename())),
                    byteArrayOutputStream
            );

            return CustomMultipartFile.builder()
                    .size(byteArrayOutputStream.size())
                    .inputStream(new ByteArrayInputStream(byteArrayOutputStream.toByteArray()))
                    .name(multipartFile.getName())
                    .originalFilename(multipartFile.getOriginalFilename())
                    .contentType(multipartFile.getContentType())
                    .build();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
