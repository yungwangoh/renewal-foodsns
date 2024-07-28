package mubex.renewal_foodsns.application.repository;

import mubex.renewal_foodsns.domain.entity.PostHeart;

public interface PostHeartRepository {

    PostHeart save(PostHeart postHeart);

    boolean existsByMemberId(Long memberId);

    boolean existsByMemberNickName(String nickName);
}