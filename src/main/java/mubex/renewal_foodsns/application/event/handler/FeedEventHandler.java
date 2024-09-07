package mubex.renewal_foodsns.application.event.handler;

import java.util.List;
import lombok.RequiredArgsConstructor;
import mubex.renewal_foodsns.application.event.feed.RegisteredFeedEvent;
import mubex.renewal_foodsns.application.repository.FeedRepository;
import mubex.renewal_foodsns.domain.entity.Feed;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FeedEventHandler {

    private final FeedRepository feedRepository;

    @EventListener
    public void propagateFeedEvent(final RegisteredFeedEvent event) {
        List<Feed> list = feedRepository.findFanoutByPostId(event.postId());

        
    }
}
