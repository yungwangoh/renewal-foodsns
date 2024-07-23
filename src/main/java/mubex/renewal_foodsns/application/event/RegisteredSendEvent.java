package mubex.renewal_foodsns.application.event;

import mubex.renewal_foodsns.domain.entity.Member;
import mubex.renewal_foodsns.domain.type.NotificationType;

public record RegisteredSendEvent(
        Member receiver,
        Member sender,
        NotificationType type,
        String uri
) {
}
