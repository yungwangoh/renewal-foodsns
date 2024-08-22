package mubex.renewal_foodsns.infrastructure.persistance.elasticsearch;

import mubex.renewal_foodsns.infrastructure.persistance.elasticsearch.querydsl.ElasticSearchQueryDslRepository;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.elasticsearch.DataElasticsearchTest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;

@DataElasticsearchTest
@DisplayNameGeneration(ReplaceUnderscores.class)
class PostElasticSearchRepositoryTest {

    @Autowired
    private PostElasticSearchRepository postElasticSearchRepository;

    @Autowired
    private ElasticsearchOperations elasticsearchOperations;

    @Autowired
    private ElasticSearchQueryDslRepository elasticSearchQueryDslRepository;

    @Test
    void 엘라스틱서치_몇개의_데이터가_들어갔는지_확인한다() {

        long count = postElasticSearchRepository.count();

        System.out.println(count);
    }
}