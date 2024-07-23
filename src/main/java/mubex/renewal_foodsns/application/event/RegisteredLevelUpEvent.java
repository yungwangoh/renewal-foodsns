package mubex.renewal_foodsns.application.event;

import mubex.renewal_foodsns.domain.entity.Member;
import mubex.renewal_foodsns.domain.entity.Post;
import mubex.renewal_foodsns.domain.type.NotificationType;

public record RegisteredLevelUpEvent(Member member, NotificationType type, Post post) {
}
