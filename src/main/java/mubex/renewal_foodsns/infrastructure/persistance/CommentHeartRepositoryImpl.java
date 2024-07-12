package mubex.renewal_foodsns.infrastructure.persistance;

import lombok.RequiredArgsConstructor;
import mubex.renewal_foodsns.domain.entity.CommentHeart;
import mubex.renewal_foodsns.domain.repository.CommentHeartRepository;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CommentHeartRepositoryImpl implements CommentHeartRepository {

    @Override
    public CommentHeart save(CommentHeart commentHeart) {
        return null;
    }

    @Override
    public boolean existByMemberId(Long memberId) {
        return false;
    }
}
