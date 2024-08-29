package mubex.renewal_foodsns.infrastructure.persistance.jpa;

import java.util.List;
import mubex.renewal_foodsns.domain.entity.Post;
import mubex.renewal_foodsns.domain.entity.PostTag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodTagJpaRepository extends JpaRepository<PostTag, Long> {

    List<PostTag> findByTag(String tag);

    Slice<PostTag> findByTag(String tag, Pageable pageable);

    List<PostTag> findByPostId(Long postId);

    void deleteAllByPost(Post post);
}