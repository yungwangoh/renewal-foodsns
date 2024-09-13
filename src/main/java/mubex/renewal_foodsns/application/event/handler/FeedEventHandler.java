package mubex.renewal_foodsns.application.event.handler;

import lombok.RequiredArgsConstructor;
import mubex.renewal_foodsns.application.FeedService;
import mubex.renewal_foodsns.application.event.feed.RegisteredFeedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FeedEventHandler {

    private final FeedService feedService;

    @EventListener
    public void propagateFeedEvent(final RegisteredFeedEvent event) {
        feedService.fanoutOnWrite(event.memberId());
    }
}
