package mubex.renewal_foodsns.domain.repository;

import mubex.renewal_foodsns.domain.post.entity.Post;

public interface PostRepository {

    Post save(Post post);
}