package mubex.renewal_foodsns.infrastructure.persistance;

import lombok.RequiredArgsConstructor;
import mubex.renewal_foodsns.domain.entity.PostHeart;
import mubex.renewal_foodsns.domain.repository.PostHeartRepository;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PostHeartRepositoryImpl implements PostHeartRepository {

    @Override
    public PostHeart save(PostHeart postHeart) {
        return null;
    }

    @Override
    public boolean existsByMemberId(Long memberId) {
        return false;
    }
}
