package mubex.renewal_foodsns.common.larger;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;
import mubex.renewal_foodsns.application.repository.MemberRepository;
import mubex.renewal_foodsns.application.repository.PostRepository;
import mubex.renewal_foodsns.domain.entity.Member;
import mubex.renewal_foodsns.domain.entity.Post;
import mubex.renewal_foodsns.domain.type.MemberRank;
import net.datafaker.Faker;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StopWatch;

@SpringBootTest
public class LargeDataSetUp {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void init() {
        Faker faker = new Faker(Locale.KOREAN);

        Member member = Member.create("닉네임", "qwer1234@A", "qwer1234@naver.com", 1, 0, 0, false, MemberRank.NORMAL,
                false);

        Member saveMember = memberRepository.save(member);

        ExecutorService es = Executors.newFixedThreadPool(10);

        StopWatch stopWatch = new StopWatch();

        stopWatch.start();
        IntStream.rangeClosed(0, 1000)
                .forEach(idx -> CompletableFuture.supplyAsync(() -> {
                    List<Post> posts = generatePosts(faker, saveMember);

                    postRepository.saveAll(posts);

                    return posts;
                }, es).toCompletableFuture().join());

        stopWatch.stop();

        System.out.println("total time: " + stopWatch.getTotalTimeSeconds());
    }

    private List<Post> generatePosts(final Faker faker, final Member saveMember) {
        return IntStream.iterate(0, i -> i + 1)
                .limit(1000)
                .parallel()
                .mapToObj(idx -> dummyData(faker, saveMember))
                .toList();
    }

    private static Post dummyData(final Faker faker, final Member saveMember) {
        String title = faker.text().text(10, 20);
        String text = faker.text().text(100, 500);
        long heart = faker.random().nextLong(0, 10000000);
        int report = faker.random().nextInt(0, 1000);
        long view = faker.random().nextLong(0, 1000000000);
        boolean blackList = faker.random().nextBoolean();

        return Post.create(title, text, heart, report, view, blackList, saveMember);
    }
}
