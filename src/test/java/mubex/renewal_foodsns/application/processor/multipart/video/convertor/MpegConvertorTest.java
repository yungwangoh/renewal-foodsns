package mubex.renewal_foodsns.application.processor.multipart.video.convertor;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import mubex.renewal_foodsns.application.processor.multipart.selector.CompressionSelector;
import mubex.renewal_foodsns.common.property.FFMpegProperties;
import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFprobe;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

@SpringBootTest(classes = {
        FFmpeg.class,
        FFprobe.class,
        MpegConvertor.class
})
@DisplayNameGeneration(ReplaceUnderscores.class)
@EnableConfigurationProperties(FFMpegProperties.class)
class MpegConvertorTest {

    @Autowired
    private MpegConvertor mpegConvertor;

    @Test
    void 영상을_MP4로_압축하고_압축률이_얼마인지_확인한다() throws IOException {
        // given
        MultipartFile multipartFile = getMultipartFiles().getFirst();

        // when
        MultipartFile file = mpegConvertor.compress(multipartFile, CompressionSelector.LOSS);

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
        String name = "test";
        String originalFileName = "test.MOV";
        String contentType = "video/mov";
        String fileUrl = "/Users/yungwang-o/Documents/test.MOV";

        List<MultipartFile> mockMultipartFiles = new ArrayList<>();
        mockMultipartFiles.add(
                new MockMultipartFile(name, originalFileName, contentType, new FileInputStream(fileUrl)));
        return mockMultipartFiles;
    }
}