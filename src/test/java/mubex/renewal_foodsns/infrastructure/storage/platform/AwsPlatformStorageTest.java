package mubex.renewal_foodsns.infrastructure.storage.platform;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import mubex.renewal_foodsns.common.TestContainer;
import mubex.renewal_foodsns.common.property.AwsS3Properties;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.multipart.MultipartFile;

@SpringBootTest
@Import(TestContainer.class)
@DisplayNameGeneration(ReplaceUnderscores.class)
@EnableConfigurationProperties(AwsS3Properties.class)
@ActiveProfiles("test")
class AwsPlatformStorageTest {

    @Autowired
    private AwsS3PlatformStorage awsS3PlatformStorage;

    @Test
    void 이미지를_업로드한다() throws IOException {
        // given
        List<MultipartFile> multipartFiles = getMultipartFiles();

        // when
        String fileUrl = awsS3PlatformStorage.process(multipartFiles.getFirst());

        // then
        System.out.println(fileUrl);
    }

    @Test
    void 유저_식별자를_추가하면_폴더가_생성되고_폴더_안에_파일이_업로드된다() throws IOException {
        // given
        List<MultipartFile> multipartFiles = getMultipartFiles();

        // when
        String fileUrl = awsS3PlatformStorage.process("test", multipartFiles.getFirst());

        // then
        System.out.println(fileUrl);
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