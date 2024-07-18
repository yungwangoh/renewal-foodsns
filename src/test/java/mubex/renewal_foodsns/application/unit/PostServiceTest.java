package mubex.renewal_foodsns.application.unit;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.anyString;
import static org.mockito.BDDMockito.given;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import mubex.renewal_foodsns.application.PostImageService;
import mubex.renewal_foodsns.application.PostService;
import mubex.renewal_foodsns.common.mapper.map.PostMapper;
import mubex.renewal_foodsns.common.mapper.map.PostPageMapper;
import mubex.renewal_foodsns.domain.entity.Post;
import mubex.renewal_foodsns.domain.repository.MemberRepository;
import mubex.renewal_foodsns.domain.repository.PostHeartRepository;
import mubex.renewal_foodsns.domain.repository.PostReportRepository;
import mubex.renewal_foodsns.domain.repository.PostRepository;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(ReplaceUnderscores.class)
public class PostServiceTest {

    @InjectMocks
    private PostService postService;

    @Mock
    private PostRepository postRepository;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private PostImageService postImageService;

    @Mock
    private PostHeartRepository postHeartRepository;

    @Mock
    private PostReportRepository postReportRepository;

    @Mock
    private PostMapper postMapper;

    @Mock
    private PostPageMapper postPageMapper;

    @Test
    void 유저가_게시물_이미지를_10개를_초과하면_예외_발생() {

        String title = "하이";
        String text = "하이용";
        Long memberId = 1L;

        assertThatThrownBy(() -> postService.create(title, text, memberId, getMultipartFiles(11)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 유저가_게시물을_중복_해서_작성하면_예외_발생() {

        String title = "하이";
        String text = "하이용";
        Long memberId = 1L;

        given(postRepository.existsByTitle(anyString())).willReturn(true);

        assertThatThrownBy(() -> postService.create(title, text, memberId, List.of()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 유저가_게시물_좋아요를_중복해서_누르면_예외_발생() {

        Long memberId = 1L;
        Long postId = 1L;

        given(postHeartRepository.existsByMemberId(anyLong())).willReturn(true);

        assertThatThrownBy(() -> postService.increaseHeart(memberId, postId))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 유저가_게시물_신고를_중복해서_누르면_예외_발생() {

        Long memberId = 1L;
        Long postId = 1L;

        given(postReportRepository.existsByMemberId(anyLong())).willReturn(true);

        assertThatThrownBy(() -> postService.increaseReport(memberId, postId))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 이미_삭제한_게시물을_삭제하려면_예외_발생() {

        Long postId = 1L;

        Post post = Post.create("안녕", "ㅎㅇ", 0, 0, 0, true, null);

        given(postRepository.findById(anyLong())).willReturn(post);

        assertThatThrownBy(() -> postService.delete(postId))
                .isInstanceOf(IllegalArgumentException.class);
    }

    private List<MultipartFile> getMultipartFiles(int idx) throws IOException {
        List<MultipartFile> files = new ArrayList<>();

        String fileUrl = "/Users/yungwang-o/Documents/FoodSNS.png";

        MultipartFile multipartFile = new MockMultipartFile("image", "image.jpg",
                "image/jpa", new FileInputStream(fileUrl));

        for (int i = 0; i < idx; i++) {
            files.add(multipartFile);
        }

        return files;
    }
}
