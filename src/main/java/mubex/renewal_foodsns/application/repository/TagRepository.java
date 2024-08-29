package mubex.renewal_foodsns.application.repository;

import java.util.List;
import mubex.renewal_foodsns.domain.entity.Post;
import mubex.renewal_foodsns.domain.entity.PostTag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface TagRepository {

    PostTag save(PostTag postTag);

    List<PostTag> findByTag(String tag);

    Slice<PostTag> findByTag(String tag, Pageable pageable);

    List<PostTag> findByPostId(Long postId);

    void deleteAllByPost(Post post);

    void saveAll(List<PostTag> postTags);
}
