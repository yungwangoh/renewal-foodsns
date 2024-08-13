package mubex.renewal_foodsns.application.job.item.writer;

import java.util.List;
import mubex.renewal_foodsns.domain.document.PostDocument;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;

public class DataMigrationItemWriter implements ItemWriter<PostDocument> {

    private final ElasticsearchOperations elasticsearchOperations;
    private final String indexName;

    public DataMigrationItemWriter(final ElasticsearchOperations elasticsearchOperations, final String indexName) {
        this.elasticsearchOperations = elasticsearchOperations;
        this.indexName = indexName;
    }

    @Override
    public void write(final Chunk<? extends PostDocument> chunk) throws Exception {
        List<IndexQuery> indexQueries = chunk.getItems()
                .stream()
                .map(this::getIndexQuery)
                .toList();

        elasticsearchOperations.bulkIndex(indexQueries, IndexCoordinates.of(indexName));
    }

    private IndexQuery getIndexQuery(final PostDocument item) {
        return new IndexQueryBuilder()
                .withId(item.id().toString())
                .withObject(item)
                .build();
    }
}
