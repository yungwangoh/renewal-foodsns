package mubex.renewal_foodsns.application.repository;

import java.util.List;
import mubex.renewal_foodsns.domain.dto.response.Feed;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface FeedRepository {

    List<Feed> findFeedsToAuthorId(Long authorId);

    Slice<Feed> findFeedsToMemberId(Long memberId, Pageable pageable);
}
