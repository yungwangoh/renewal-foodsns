package mubex.renewal_foodsns.infrastructure.persistance.jpa.querydsl;

import java.util.List;
import mubex.renewal_foodsns.domain.dto.response.Feed;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface FeedQueryDslRepository {

    List<Feed> findFeedsToAuthorId(Long memberId);

    Slice<Feed> findFeedsToMemberId(Long memberId, Pageable pageable);
}
