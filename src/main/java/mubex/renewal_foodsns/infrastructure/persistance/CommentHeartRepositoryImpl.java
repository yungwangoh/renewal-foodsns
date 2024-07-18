package mubex.renewal_foodsns.infrastructure.persistance;

import lombok.RequiredArgsConstructor;
import mubex.renewal_foodsns.domain.entity.CommentHeart;
import mubex.renewal_foodsns.domain.repository.CommentHeartRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class CommentHeartRepositoryImpl implements CommentHeartRepository {

    private final CommentHeartRepository commentHeartRepository;

    @Override
    @Transactional
    public CommentHeart save(CommentHeart commentHeart) {
        return commentHeartRepository.save(commentHeart);
    }

    @Override
    public boolean existByMemberId(Long memberId) {
        return commentHeartRepository.existByMemberId(memberId);
    }
}
