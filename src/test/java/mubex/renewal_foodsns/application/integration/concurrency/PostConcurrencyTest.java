package mubex.renewal_foodsns.application.integration.concurrency;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import mubex.renewal_foodsns.application.PostService;
import mubex.renewal_foodsns.application.repository.MemberRepository;
import mubex.renewal_foodsns.application.repository.PostHeartRepository;
import mubex.renewal_foodsns.common.TestContainer;
import mubex.renewal_foodsns.domain.dto.response.PostResponse;
import mubex.renewal_foodsns.domain.entity.Member;
import mubex.renewal_foodsns.domain.entity.PostHeart;
import mubex.renewal_foodsns.domain.type.MemberRank;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Disabled
@Import(TestContainer.class)
public class PostConcurrencyTest {

    @Autowired
    private PostService postService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PostHeartRepository postHeartRepository;

    @Test
    void 게시물의_좋아요를_멀티쓰레드로_정합성을_테스트한다() {
        // given
        int max = 100;
        String nickName = "qwer1234";
        String password = "qwer1234@A";
        String email = "qwer1234@gmail.com";

        Member member = Member.create(nickName, password, email, "", 0, 0, false, MemberRank.NORMAL, false);

        Member saveMember = memberRepository.save(member);

        PostResponse postResponse = postService.create("test", "test", saveMember.getId(), Set.of("test"),
                List.of());

        // when
        try (ExecutorService es = Executors.newFixedThreadPool(10)) {

            for (int i = 0; i < max; i++) {
                es.execute(() -> postService.increaseHeart(member.getId(), postResponse.id(), 1));
            }
        }

        // then
        PostResponse response = postService.find(postResponse.id());
        List<PostHeart> postHearts = postHeartRepository.findByMemberId(member.getId());

        assertThat(response.heart()).isEqualTo(max);
        assertThat(postHearts).hasSize(max);
    }
}
