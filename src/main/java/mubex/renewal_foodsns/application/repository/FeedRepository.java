package mubex.renewal_foodsns.application.repository;

import java.util.List;
import mubex.renewal_foodsns.domain.entity.Feed;

public interface FeedRepository {

    Feed save(Feed feed);

    Feed findById(Long id);

    List<Feed> findFanoutByPostId(Long postId);
}
