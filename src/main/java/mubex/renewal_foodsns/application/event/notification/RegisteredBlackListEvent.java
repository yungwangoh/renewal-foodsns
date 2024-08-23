package mubex.renewal_foodsns.application.event.notification;

import mubex.renewal_foodsns.domain.entity.Member;
import mubex.renewal_foodsns.domain.type.NotificationType;

public record RegisteredBlackListEvent(Member member, NotificationType type) {
}
