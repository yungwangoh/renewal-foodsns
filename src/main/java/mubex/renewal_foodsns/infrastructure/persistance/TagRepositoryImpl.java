package mubex.renewal_foodsns.infrastructure.persistance;

import java.util.List;
import lombok.RequiredArgsConstructor;
import mubex.renewal_foodsns.application.repository.TagRepository;
import mubex.renewal_foodsns.domain.entity.Post;
import mubex.renewal_foodsns.domain.entity.PostTag;
import mubex.renewal_foodsns.infrastructure.persistance.jpa.FoodTagJpaRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TagRepositoryImpl implements TagRepository {

    private final FoodTagJpaRepository foodTagJpaRepository;

    @Override
    @Transactional
    public PostTag save(final PostTag postTag) {
        return foodTagJpaRepository.save(postTag);
    }

    @Override
    public List<PostTag> findByTag(final String tag) {
        return foodTagJpaRepository.findByTag(tag);
    }

    @Override
    public Slice<PostTag> findByTag(final String tag, final Pageable pageable) {
        return foodTagJpaRepository.findByTag(tag, pageable);
    }

    @Override
    public List<PostTag> findByPostId(final Long postId) {
        return foodTagJpaRepository.findByPostId(postId);
    }

    @Override
    @Transactional
    public void deleteAllByPost(final Post post) {
        foodTagJpaRepository.deleteAllByPost(post);
    }

    @Override
    @Transactional
    public void saveAll(List<PostTag> postTags) {
        foodTagJpaRepository.saveAll(postTags);
    }
}
