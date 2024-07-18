package mubex.renewal_foodsns.domain.repository;

import java.util.List;
import mubex.renewal_foodsns.domain.entity.FoodTag;
import mubex.renewal_foodsns.domain.entity.Post;
import mubex.renewal_foodsns.domain.type.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface FoodTagRepository {

    FoodTag save(FoodTag foodTag);

    List<FoodTag> findByTag(Tag tag);

    Slice<FoodTag> findByTag(Tag tag, Pageable pageable);

    List<FoodTag> findByPostId(Long postId);

    void deleteAllByPost(Post post);
}
