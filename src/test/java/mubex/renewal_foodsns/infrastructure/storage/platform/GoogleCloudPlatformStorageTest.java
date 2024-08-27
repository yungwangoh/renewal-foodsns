package mubex.renewal_foodsns.infrastructure.storage.platform;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import mubex.renewal_foodsns.common.property.CloudProperties;
import mubex.renewal_foodsns.common.util.UriUtil;
import mubex.renewal_foodsns.infrastructure.storage.PlatformStorage;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.multipart.MultipartFile;

@SpringBootTest(classes = {
        PlatformStorage.class,
        GoogleCloudPlatformStorage.class,
})
@DisplayNameGeneration(ReplaceUnderscores.class)
@EnableConfigurationProperties(CloudProperties.class)
@ActiveProfiles("test")
@Disabled
class GoogleCloudPlatformStorageTest {

    @Autowired
    private PlatformStorage platformStorage;

    @Test
    void 이미지_저장_테스트() throws IOException {
        // given
        List<MultipartFile> multipartFiles = getMultipartFiles();

        // when
        String process = platformStorage.process(multipartFiles.getFirst());

        // then
        assertThat(process).startsWith(UriUtil.GCS.getUri());
    }

    @Test
    void 썸네일_저장_테스트() throws IOException {
        // given
        MultipartFile multipartFile = getMultipartFiles().getFirst();

        // when
        String process = platformStorage.process(multipartFile.getBytes(), multipartFile.getContentType());

        // then
        assertThat(process).startsWith(UriUtil.GCS.getUri());
    }

    private List<MultipartFile> getMultipartFiles() throws IOException {
        String name = "image";
        String originalFileName = "test.jpeg";
        String contentType = "image/png";
        String fileUrl = "/Users/yungwang-o/Documents/FoodSNS.png";

        List<MultipartFile> mockMultipartFiles = new ArrayList<>();
        mockMultipartFiles.add(
                new MockMultipartFile(name, originalFileName, contentType, new FileInputStream(fileUrl)));
        return mockMultipartFiles;
    }
}