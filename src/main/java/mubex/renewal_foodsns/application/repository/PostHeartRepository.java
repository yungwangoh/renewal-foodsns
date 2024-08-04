package mubex.renewal_foodsns.application.repository;

import java.util.List;
import mubex.renewal_foodsns.domain.entity.PostHeart;

public interface PostHeartRepository {

    PostHeart save(PostHeart postHeart);

    boolean existsByMemberId(Long memberId);

    boolean existsByMemberNickName(String nickName);

    List<PostHeart> findByMemberId(Long memberId);
}
