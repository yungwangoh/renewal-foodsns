package mubex.renewal_foodsns.domain.repository;

import java.util.List;
import mubex.renewal_foodsns.domain.entity.Post;
import mubex.renewal_foodsns.domain.type.FoodTag;

public interface PostRepository {

    Post save(Post post);
    Post findById(Long id);
    List<Post> findAllByTitle(String title);
    List<Post> findAll();
    List<Post> findAllByUserId(Long memberId);
    List<Post> findAllByNickName(String userName);
    List<Post> findAllByFoodTag(FoodTag foodTag);
}
