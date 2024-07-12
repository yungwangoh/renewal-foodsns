package mubex.renewal_foodsns.domain.repository;

import mubex.renewal_foodsns.domain.entity.CommentHeart;

public interface CommentHeartRepository {

    CommentHeart save(CommentHeart commentHeart);
    boolean existByMemberId(Long memberId);
}
