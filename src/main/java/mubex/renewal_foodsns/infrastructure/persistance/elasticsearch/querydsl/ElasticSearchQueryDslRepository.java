package mubex.renewal_foodsns.infrastructure.persistance.elasticsearch.querydsl;

import co.elastic.clients.elasticsearch._types.query_dsl.Operator;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import co.elastic.clients.elasticsearch._types.query_dsl.TextQueryType;
import lombok.RequiredArgsConstructor;
import mubex.renewal_foodsns.domain.document.PostDocument;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ElasticSearchQueryDslRepository {

    private final ElasticsearchOperations elasticsearchOperations;

    public SearchHits<PostDocument> findTitleOrTextBySearchText(final String searchText, final Pageable pageable) {

        final Query query = QueryBuilders.multiMatch(builder -> builder
                .analyzer("nori")
                .fields("title", "text")
                .query(searchText)
                .type(TextQueryType.BestFields)
                .operator(Operator.Or)
        );

        final NativeQuery nativeQuery = new NativeQueryBuilder()
                .withQuery(query)
                .withPageable(pageable)
                .build();

        return elasticsearchOperations.search(nativeQuery, PostDocument.class);
    }
}
