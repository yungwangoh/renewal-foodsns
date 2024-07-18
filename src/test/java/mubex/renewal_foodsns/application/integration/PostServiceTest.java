package mubex.renewal_foodsns.application.integration;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import mubex.renewal_foodsns.application.MemberService;
import mubex.renewal_foodsns.application.PostService;
import mubex.renewal_foodsns.common.TestContainer;
import mubex.renewal_foodsns.domain.dto.response.PostResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.web.multipart.MultipartFile;

@SpringBootTest
@DisplayNameGeneration(ReplaceUnderscores.class)
@Sql(
        value = "/data.sql",
        executionPhase = ExecutionPhase.AFTER_TEST_METHOD
)
public class PostServiceTest extends TestContainer {

    @Autowired
    private PostService postService;

    @Autowired
    private MemberService memberService;

    @BeforeEach
    void setUp() {
        String email = "qwer1234@gmail.com";
        String password = "qwer1234";
        String nickName = "안녕";

        memberService.signUp(email, nickName, password, 1);
    }

    @Test
    void 게시물_저장() throws IOException {
        // given
        String title = "야호";
        String text = "야호맨";
        Long memberId = 1L;

        List<MultipartFile> multipartFiles = getMultipartFiles();

        // when
        PostResponse postResponse = postService.create(title, text, memberId, multipartFiles);

        // then
        assertThat(postResponse.title()).isEqualTo(title);
        assertThat(postResponse.text()).isEqualTo(text);
        assertThat(postResponse.postImageResponses()).hasSize(getMultipartFiles().size());
    }

    @Test
    void 게시물_업데이트() throws IOException {
        // given
        String title = "호야";
        String text = "홀ㄴ아ㅣㅗㅎ렁나ㅣㅗㅎ";
        Long memberId = 1L;

        PostResponse postResponse = createPost();
        List<MultipartFile> multipartFiles = getMultipartFiles();

        // when
        PostResponse update = postService.update(postResponse.id(), title, text, memberId, multipartFiles);

        // then
        assertThat(update.title()).isEqualTo(title);
        assertThat(update.text()).isEqualTo(text);
        assertThat(update.postImageResponses()).hasSize(getMultipartFiles().size());
    }

    @Test
    void 게시물_좋아요() throws IOException {
        // given
        Long memberId = 1L;

        PostResponse postResponse = createPost();

        // when
        postService.increaseHeart(memberId, postResponse.id());

        PostResponse response = postService.find(postResponse.id());

        // then
        assertThat(response.heart()).isEqualTo(1);
    }

    @Test
    void 게시물_신고() throws IOException {
        // given
        Long memberId = 1L;

        PostResponse postResponse = createPost();

        // when
        postService.increaseReport(memberId, postResponse.id());

        PostResponse response = postService.find(postResponse.id());

        // then
        assertThat(response.report()).isEqualTo(1);
    }

    private List<MultipartFile> getMultipartFiles() throws IOException {
        String name = "image";
        String originalFileName = "test.jpeg";
        String contentType = "image/png";
        String fileUrl = "/Users/yungwang-o/Documents/FoodSNS.png";

        List<MultipartFile> mockMultipartFiles = new ArrayList<>();
        mockMultipartFiles.add(
                new MockMultipartFile(name, originalFileName, contentType, new FileInputStream(fileUrl))
        );

        return mockMultipartFiles;
    }

    private PostResponse createPost() throws IOException {
        String title = "야호";
        String text = "야호맨";
        Long memberId = 1L;

        List<MultipartFile> multipartFiles = getMultipartFiles();

        return postService.create(title, text, memberId, multipartFiles);
    }
}