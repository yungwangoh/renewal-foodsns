package mubex.renewal_foodsns.infrastructure.persistance;

import java.util.List;
import lombok.RequiredArgsConstructor;
import mubex.renewal_foodsns.domain.entity.Post;
import mubex.renewal_foodsns.domain.repository.PostRepository;
import mubex.renewal_foodsns.domain.type.FoodTag;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepository {

    @Override
    public Post save(Post post) {
        return null;
    }

    @Override
    public Post findById(Long id) {
        return null;
    }

    @Override
    public List<Post> findAllByTitle(String title) {
        return List.of();
    }

    @Override
    public List<Post> findAll() {
        return List.of();
    }

    @Override
    public List<Post> findAllByUserId(Long memberId) {
        return List.of();
    }

    @Override
    public List<Post> findAllByNickName(String userName) {
        return List.of();
    }

    @Override
    public List<Post> findAllByFoodTag(FoodTag foodTag) {
        return List.of();
    }
}
