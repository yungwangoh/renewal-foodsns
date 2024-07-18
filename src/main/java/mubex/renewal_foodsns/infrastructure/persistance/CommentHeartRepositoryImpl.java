package mubex.renewal_foodsns.infrastructure.persistance;

import lombok.RequiredArgsConstructor;
import mubex.renewal_foodsns.domain.entity.CommentHeart;
import mubex.renewal_foodsns.domain.repository.CommentHeartRepository;
import mubex.renewal_foodsns.infrastructure.persistance.jpa.CommentHeartJpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class CommentHeartRepositoryImpl implements CommentHeartRepository {

    private final CommentHeartJpaRepository commentHeartJpaRepository;

    @Override
    @Transactional
    public CommentHeart save(CommentHeart commentHeart) {
        return commentHeartJpaRepository.save(commentHeart);
    }

    @Override
    public boolean existByMemberId(Long memberId) {
        return commentHeartJpaRepository.existsByMemberId(memberId);
    }
}
