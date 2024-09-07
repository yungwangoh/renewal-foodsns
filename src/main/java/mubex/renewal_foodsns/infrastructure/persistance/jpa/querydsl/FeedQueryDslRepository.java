package mubex.renewal_foodsns.infrastructure.persistance.jpa.querydsl;

import java.util.List;
import mubex.renewal_foodsns.domain.entity.Feed;

public interface FeedQueryDslRepository {

    List<Feed> findByFollower(Long postId);
}
