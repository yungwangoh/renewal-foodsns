package mubex.renewal_foodsns.infrastructure.persistance.jpa;

import static org.assertj.core.api.Assertions.assertThat;

import mubex.renewal_foodsns.domain.entity.Post;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.util.StopWatch;

@SpringBootTest
@DisplayNameGeneration(ReplaceUnderscores.class)
class PostJpaRepositoryTest {

    @Autowired
    private PostJpaRepository postJpaRepository;

    @Test
    void MYSQL_FULL_TEXT_SEARCH_로_조회한다() {
        // given
        String searchText = "누구든";

        StopWatch stopWatch = new StopWatch();

        PageRequest pageRequest = PageRequest.of(0, 10);

        // when
        stopWatch.start();
        Slice<Post> fts = postJpaRepository.findTitleOrTextBySearchText(searchText, pageRequest);
        stopWatch.stop();

        // then
        assertThat(fts).hasSize(10);
        System.out.println("Time elapsed: " + stopWatch.getTotalTimeSeconds());
    }
}