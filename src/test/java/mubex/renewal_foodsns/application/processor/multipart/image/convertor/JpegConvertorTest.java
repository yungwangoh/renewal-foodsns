package mubex.renewal_foodsns.application.processor.multipart.image.convertor;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import mubex.renewal_foodsns.application.processor.multipart.selector.CompressionSelector;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

@SpringBootTest(classes = {
        JpegConvertor.class
})
@DisplayNameGeneration(ReplaceUnderscores.class)
class JpegConvertorTest {

    @Autowired
    private JpegConvertor jpegConvertor;

    @Test
    void jpeg_손실_압축_한_것과_기존_파일과_비교한다() throws IOException {
        // given
        MultipartFile multipartFile = getMultipartFiles().getFirst();

        // when
        MultipartFile file = jpegConvertor.compress(multipartFile, CompressionSelector.LOSS);

        // then
        double originFileSize = multipartFile.getSize() / 1024.0;
        double newFileSize = file.getSize() / 1024.0;

        double compressionRatio = (newFileSize / originFileSize) * 100;
        double compressRate = 100 - compressionRatio;

        System.out.printf("Original File Size: %.2f KB%n", originFileSize);
        System.out.printf("Converted File Size: %.2f KB%n", newFileSize);
        System.out.printf("Compression Rate: %.2f%%%n", compressRate);
        Assertions.assertTrue(file.getResource().exists());
    }

    private List<MultipartFile> getMultipartFiles() throws IOException {
        String name = "image";
        String originalFileName = "test.png";
        String contentType = "image/png";
        String fileUrl = "/Users/yungwang-o/Documents/test.png";

        List<MultipartFile> mockMultipartFiles = new ArrayList<>();
        mockMultipartFiles.add(
                new MockMultipartFile(name, originalFileName, contentType, new FileInputStream(fileUrl)));
        return mockMultipartFiles;
    }
}