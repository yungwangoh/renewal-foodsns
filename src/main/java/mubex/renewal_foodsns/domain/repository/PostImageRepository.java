package mubex.renewal_foodsns.domain.repository;

import java.util.List;
import mubex.renewal_foodsns.domain.entity.PostImage;

public interface PostImageRepository {

    PostImage save(PostImage postImage);
    PostImage findById(Long id);
    List<PostImage> findAllByPostId(Long postId);
}
