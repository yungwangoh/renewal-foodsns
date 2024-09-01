package mubex.renewal_foodsns.application.processor.multipart.image.convertor;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import mubex.renewal_foodsns.application.processor.multipart.LosslessSelector;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

@SpringBootTest(classes = {
        WebpConvertor.class
})
@DisplayNameGeneration(ReplaceUnderscores.class)
class WebpConvertorTest {

    @Autowired
    private WebpConvertor webpConvertor;

    @Test
    void 이미지를_리사이징한다() throws IOException {
        // given
        MultipartFile multipartFile = getMultipartFiles().getFirst();

        // when
        MultipartFile file = webpConvertor.resize(multipartFile, 1000, 1000);

        // then
        assertTrue(file.getResource().exists());
    }

    @Test
    void webp_손실_압축_한_것과_기존_파일과_비교한다() throws IOException {
        // given
        MultipartFile multipartFile = getMultipartFiles().getFirst();

        // when
        MultipartFile file = webpConvertor.convert(multipartFile, LosslessSelector.LOSS);

        // then
        double originFileSize = multipartFile.getSize() / 1024.0;
        double newFileSize = file.getSize() / 1024.0;

        double compressionRatio = (newFileSize / originFileSize) * 100;
        double compressRate = 100 - compressionRatio;

        System.out.printf("Original File Size: %.2f KB%n", originFileSize);
        System.out.printf("Converted File Size: %.2f KB%n", newFileSize);
        System.out.printf("Compression Rate: %.2f%%%n", compressRate);
        assertTrue(file.getResource().exists());
    }

    private List<MultipartFile> getMultipartFiles() throws IOException {
        String name = "image";
        String originalFileName = "FoodSNS.png";
        String contentType = "image/png";
        String fileUrl = "/Users/yungwang-o/Documents/FoodSNS.png";

        List<MultipartFile> mockMultipartFiles = new ArrayList<>();
        mockMultipartFiles.add(
                new MockMultipartFile(name, originalFileName, contentType, new FileInputStream(fileUrl)));
        return mockMultipartFiles;
    }
}