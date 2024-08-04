package mubex.renewal_foodsns.infrastructure.persistance.jpa;

import java.util.List;
import mubex.renewal_foodsns.domain.entity.PostHeart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostHeartJpaRepository extends JpaRepository<PostHeart, Long> {

    boolean existsByMemberId(Long memberId);

    boolean existsByMemberNickName(String nickName);

    List<PostHeart> findByMemberId(Long memberId);
}