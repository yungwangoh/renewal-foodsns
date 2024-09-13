package mubex.renewal_foodsns.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mubex.renewal_foodsns.application.repository.FeedRepository;
import mubex.renewal_foodsns.application.repository.FollowRepository;
import mubex.renewal_foodsns.domain.dto.response.Feed;
import mubex.renewal_foodsns.domain.dto.response.PostResponse;
import mubex.renewal_foodsns.domain.entity.Follow;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FeedService {

    private final FeedRepository feedRepository;
    private final FollowRepository followRepository;
    private final PostService postService;

    public void fanoutOnWrite(final Long authorId) {
        List<Feed> feeds = feedRepository.findFeedsToAuthorId(authorId);

        feeds.forEach(this::getFeed);
    }

    @CachePut(
            cacheNames = "feed",
            key = "#feed.nickName()",
            cacheManager = "redisCacheManager"
    )
    public PostResponse getFeed(final Feed feed) {
        return postService.find(feed.postId());
    }

    @Cacheable(
            cacheNames = "feed",
            key = "#nickName",
            cacheManager = "redisCacheManager"
    )
    public List<PostResponse> fanoutOnRead(final String nickName) {
        List<Follow> followList = followRepository.findByInfluenceFollowee(nickName);

        return followList.stream()
                .map(follow -> postService.findByMemberId(follow.getFollowee().getId()))
                .toList();
    }
}
