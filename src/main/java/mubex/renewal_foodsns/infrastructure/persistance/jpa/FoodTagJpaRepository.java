package mubex.renewal_foodsns.infrastructure.persistance.jpa;

import java.util.List;
import mubex.renewal_foodsns.domain.entity.FoodTag;
import mubex.renewal_foodsns.domain.entity.Post;
import mubex.renewal_foodsns.domain.type.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodTagJpaRepository extends JpaRepository<FoodTag, Long> {

    List<FoodTag> findByTag(Tag tag);

    Slice<FoodTag> findByTag(Tag tag, Pageable pageable);

    List<FoodTag> findByPostId(Long postId);

    void deleteAllByPost(Post post);
}