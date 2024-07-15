package mubex.renewal_foodsns.domain.repository;

import java.util.List;
import mubex.renewal_foodsns.domain.entity.Post;
import mubex.renewal_foodsns.domain.type.FoodTag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostRepository {

    Post save(Post post);
    Post findById(Long id);
    Page<Post> findByTitle(String title, Pageable pageable);
    Page<Post> findAll(Pageable pageable);
    Page<Post> findByMemberId(Long memberId, Pageable pageable);
    Page<Post> findByNickName(String userName, Pageable pageable);
    Page<Post> findByFoodTag(FoodTag foodTag, Pageable pageable);
}
