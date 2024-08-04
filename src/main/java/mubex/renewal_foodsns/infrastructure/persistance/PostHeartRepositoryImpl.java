package mubex.renewal_foodsns.infrastructure.persistance;

import java.util.List;
import lombok.RequiredArgsConstructor;
import mubex.renewal_foodsns.application.repository.PostHeartRepository;
import mubex.renewal_foodsns.domain.entity.PostHeart;
import mubex.renewal_foodsns.infrastructure.persistance.jpa.PostHeartJpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class PostHeartRepositoryImpl implements PostHeartRepository {

    private final PostHeartJpaRepository postHeartJpaRepository;

    @Override
    @Transactional
    public PostHeart save(final PostHeart postHeart) {
        return postHeartJpaRepository.save(postHeart);
    }

    @Override
    public boolean existsByMemberId(final Long memberId) {
        return postHeartJpaRepository.existsByMemberId(memberId);
    }

    @Override
    public boolean existsByMemberNickName(final String nickName) {
        return postHeartJpaRepository.existsByMemberNickName(nickName);
    }

    @Override
    public List<PostHeart> findByMemberId(final Long memberId) {
        return postHeartJpaRepository.findByMemberId(memberId);
    }
}
