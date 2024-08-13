package mubex.renewal_foodsns.infrastructure.persistance.elasticsearch;

import mubex.renewal_foodsns.domain.document.PostDocument;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface PostElasticSearchRepository extends ElasticsearchRepository<PostDocument, Long> {

    Slice<PostDocument> findByTitleContainingOrTextContaining(String title, String text, Pageable pageable);
}
