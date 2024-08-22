package mubex.renewal_foodsns.infrastructure.persistance.elasticsearch.querydsl;

import static org.assertj.core.api.Assertions.assertThat;

import mubex.renewal_foodsns.domain.document.PostDocument;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.elasticsearch.DataElasticsearchTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.SearchHits;

@DataElasticsearchTest
@Import(ElasticSearchQueryDslRepository.class)
class ElasticSearchQueryDslRepositoryTest {

    private static final Logger log = LoggerFactory.getLogger(ElasticSearchQueryDslRepositoryTest.class);

    @Autowired
    private ElasticSearchQueryDslRepository elasticSearchQueryDslRepository;

    @Test
    void 엘라스틱서치의_전문검색을_이용하여_게시물을_조회한다() {
        // given
        String searchText = "무죄";
        PageRequest pr = PageRequest.of(0, 10);

        // when
        SearchHits<PostDocument> postDocumentSearchHits = elasticSearchQueryDslRepository.findTitleOrTextBySearchText(
                searchText, pr);

        // then
        log.info("postDocumentSearchHits: {}", postDocumentSearchHits.getSearchHits());
        assertThat(postDocumentSearchHits).hasSize(10);
    }
}