package mubex.renewal_foodsns.application.processor.multipart.image.compression;

import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.function.Supplier;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import mubex.renewal_foodsns.common.util.FileUtils.FileExt;

@Deprecated
public class ImageCompressionSupport {

    private final FileExt fileExt;

    private InputStream is;
    private int compressionFactor;
    private Path path;
    private File file;

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

    public ImageCompressionSupport from(final File file) {
        this.file = file;

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

    public File toFile(final Supplier<File> newFile) {
        File outputFile = newFile.get();

        try (InputStream input = new FileInputStream(file);
             OutputStream output = new FileOutputStream(outputFile)) {

            BufferedImage image = getImage(input);

            Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName(fileExt.getExt());
            if (!writers.hasNext()) {
                throw new IllegalStateException("No image writer found for format " + fileExt.name());
            }

            ImageWriter writer = writers.next();
            try (ImageOutputStream ios = ImageIO.createImageOutputStream(output)) {
                writer.setOutput(ios);
                ImageWriteParam param = writer.getDefaultWriteParam();

                if (param.canWriteCompressed()) {
                    param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
                    param.setCompressionQuality(1 - (compressionFactor / 100f));
                }

                writer.write(null, new IIOImage(image, null, null), param);
            } finally {
                writer.dispose();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return outputFile;
    }

    private BufferedImage getImage(final InputStream is) {
        try {
            BufferedImage bufferedImage = ImageIO.read(is);

            boolean alpha = bufferedImage.getColorModel().hasAlpha();

            if (!alpha) {
                return bufferedImage;
            }

            // jpg is no alpha chanel
            BufferedImage outputImage = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(),
                    BufferedImage.TYPE_INT_RGB);

            ColorConvertOp op = new ColorConvertOp(ColorSpace.getInstance(ColorSpace.CS_sRGB), null);

            op.filter(bufferedImage, outputImage);

            return outputImage;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
