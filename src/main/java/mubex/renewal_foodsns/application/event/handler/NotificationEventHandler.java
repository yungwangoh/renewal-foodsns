package mubex.renewal_foodsns.application.event.handler;

import lombok.RequiredArgsConstructor;
import mubex.renewal_foodsns.application.NotificationService;
import mubex.renewal_foodsns.application.event.notification.RegisteredBlackListEvent;
import mubex.renewal_foodsns.application.event.notification.RegisteredLevelUpEvent;
import mubex.renewal_foodsns.application.event.notification.RegisteredSendEvent;
import mubex.renewal_foodsns.application.event.notification.RegisteredSubscribeEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class NotificationEventHandler {

    private final NotificationService notificationService;

    @TransactionalEventListener
    public void handleSubscribe(final RegisteredSubscribeEvent event) {
        notificationService.subscribe(event.memberId());
    }

    @TransactionalEventListener
    public void handleSend(final RegisteredSendEvent event) {
        notificationService.sendTo(event.receiver(), event.sender(), event.type(), event.uri());
    }

    @TransactionalEventListener
    public void handleLevelUp(final RegisteredLevelUpEvent event) {
        notificationService.sendTo(event.member(), event.type());
    }

    @TransactionalEventListener
    public void handleBlackList(final RegisteredBlackListEvent event) {
        notificationService.sendTo(event.member(), event.type());
    }
}
