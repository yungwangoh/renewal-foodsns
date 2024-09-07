package mubex.renewal_foodsns.infrastructure.persistance;

import java.util.List;
import lombok.RequiredArgsConstructor;
import mubex.renewal_foodsns.application.repository.FeedRepository;
import mubex.renewal_foodsns.common.exception.ExceptionResolver;
import mubex.renewal_foodsns.common.exception.NotFoundException;
import mubex.renewal_foodsns.domain.entity.Feed;
import mubex.renewal_foodsns.infrastructure.persistance.jpa.FeedJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class FeedRepositoryImpl implements FeedRepository {

    private final FeedJpaRepository feedJpaRepository;

    @Override
    public Feed save(final Feed feed) {
        return feedJpaRepository.save(feed);
    }

    @Override
    public Feed findById(final Long id) {
        return feedJpaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ExceptionResolver.NOT_FOUND_FEED));
    }

    @Override
    public List<Feed> findFanoutByPostId(final Long postId) {
        return feedJpaRepository.findByFollower(postId);
    }
}
