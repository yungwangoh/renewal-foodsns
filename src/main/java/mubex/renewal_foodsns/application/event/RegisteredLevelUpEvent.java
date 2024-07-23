package mubex.renewal_foodsns.application.event;

import mubex.renewal_foodsns.domain.entity.Member;
import mubex.renewal_foodsns.domain.entity.Post;

public record RegisteredLevelUpEvent(Member member, Post post) {
}
