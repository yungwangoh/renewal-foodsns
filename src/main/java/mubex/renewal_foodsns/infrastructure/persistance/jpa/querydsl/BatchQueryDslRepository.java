package mubex.renewal_foodsns.infrastructure.persistance.jpa.querydsl;

import static mubex.renewal_foodsns.domain.entity.QPost.post;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import mubex.renewal_foodsns.domain.document.PostDocument;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BatchQueryDslRepository {

    private final JPAQueryFactory queryFactory;

    public List<PostDocument> fetchPostDocumentToZeroOffset(final Long id, final long fetchSize) {
        return queryFactory
                .select(Projections.constructor(PostDocument.class,
                        post.id,
                        post.title,
                        post.text,
                        post.heart,
                        post.views,
                        post.inDeleted))
                .from(post)
                .where(post.id.gt(id))
                .orderBy(post.id.asc())
                .limit(fetchSize)
                .fetch();
    }
}
