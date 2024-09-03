package mubex.renewal_foodsns.application.processor.multipart.image.compression;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.function.Supplier;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageOutputStream;
import mubex.renewal_foodsns.common.util.FileUtils.FileExt;

public class ImageCompressionSupport {

    private final FileExt fileExt;

    private InputStream is;
    private int compressionFactor;
    private Path path;

    private ImageCompressionSupport(final FileExt fileExt) {
        this.fileExt = fileExt;
    }

    public static ImageCompressionSupport support(final FileExt fileExt) {
        return new ImageCompressionSupport(fileExt);
    }

    public ImageCompressionSupport from(final InputStream inputStream) {
        this.is = inputStream;
        return this;
    }

    public ImageCompressionSupport from(final Path path) {
        this.path = path;
        return this;
    }

    /**
     * @param compressionFactor 0 (produce the lowest quality instead of lowest capacity) ~ 100 (produce the highest
     *                          quality instead of lowest capacity)
     */
    public ImageCompressionSupport factor(final int compressionFactor) {
        if (!(compressionFactor > -1 && compressionFactor < 100)) {
            throw new IllegalArgumentException("Compression factor must be between 0 and 100");
        }

        this.compressionFactor = compressionFactor;
        return this;
    }

    public File toFile(final Supplier<File> supplier) {

        BufferedImage image = getImage();

        File file = supplier.get();

        Iterator<ImageWriter> it = ImageIO.getImageWritersByFormatName(fileExt.getExt());

        if (!it.hasNext()) {
            throw new IllegalStateException("No image writer found for format " + fileExt.name());
        }

        ImageWriter imageWriter = it.next();
        ImageWriteParam param = imageWriter.getDefaultWriteParam();

        if (compressionFactor == 100) {
            param.setCompressionMode(ImageWriteParam.MODE_DISABLED);
        } else if (compressionFactor > -1 && compressionFactor < 100) {
            param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            param.setCompressionQuality(compressionFactor / 100f);
        } else {
            param.setCompressionMode(ImageWriteParam.MODE_COPY_FROM_METADATA);
        }

        try (FileImageOutputStream os = new FileImageOutputStream(file)) {
            imageWriter.setOutput(os);
            IIOImage iioImage = new IIOImage(image, null, null);
            imageWriter.write(null, iioImage, param);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            imageWriter.dispose();
        }

        return file;
    }

    private BufferedImage getImage() {
        try {
            BufferedImage bufferedImage = ImageIO.read(is);

            boolean alpha = bufferedImage.getColorModel().hasAlpha();

            return new BufferedImage(
                    bufferedImage.getWidth(),
                    bufferedImage.getHeight(),
                    alpha ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
