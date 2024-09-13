package mubex.renewal_foodsns.infrastructure.persistance;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mubex.renewal_foodsns.application.repository.FeedRepository;
import mubex.renewal_foodsns.domain.dto.response.Feed;
import mubex.renewal_foodsns.infrastructure.persistance.jpa.querydsl.FeedQueryDslRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class FeedRepositoryImpl implements FeedRepository {

    private final FeedQueryDslRepository feedQueryDslRepository;

    @Override
    public List<Feed> findFeedsToAuthorId(final Long authorId) {
        return feedQueryDslRepository.findFeedsToAuthorId(authorId);
    }

    @Override
    public Slice<Feed> findFeedsToMemberId(final Long memberId, final Pageable pageable) {
        return feedQueryDslRepository.findFeedsToMemberId(memberId, pageable);
    }
}
