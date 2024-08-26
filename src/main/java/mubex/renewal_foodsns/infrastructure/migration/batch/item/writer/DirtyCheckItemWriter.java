package mubex.renewal_foodsns.infrastructure.migration.batch.item.writer;

import java.util.List;
import java.util.stream.StreamSupport;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mubex.renewal_foodsns.domain.document.PostDocument;
import mubex.renewal_foodsns.domain.dto.response.PostResponse;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;

@Slf4j
@RequiredArgsConstructor
public class DirtyCheckItemWriter implements ItemWriter<PostResponse> {

    private final ElasticsearchOperations elasticsearchOperations;

    private static final String INDEX_NAME = "post";

    @Override
    public void write(final Chunk<? extends PostResponse> chunk) throws Exception {

        List<UpdateQuery> updateQueries = StreamSupport.stream(chunk.spliterator(), true)
                .map(this::toPostDocument)
                .map(this::getUpdateQuery)
                .toList();

        log.info("writing {} posts", updateQueries.size());

        elasticsearchOperations.bulkUpdate(updateQueries, IndexCoordinates.of(INDEX_NAME));
    }

    private UpdateQuery getUpdateQuery(final PostDocument postDocument) {
        return UpdateQuery.builder(postDocument.getId().toString())
                .withDocAsUpsert(true)
                .withDocument(elasticsearchOperations.getElasticsearchConverter().mapObject(postDocument))
                .build();
    }

    private PostDocument toPostDocument(final PostResponse postResponse) {
        return PostDocument.builder()
                .id(postResponse.id())
                .title(postResponse.title())
                .text(postResponse.text())
                .heart(postResponse.heart())
                .views(postResponse.views())
                .inDeleted(postResponse.visible())
                .build();
    }
}
