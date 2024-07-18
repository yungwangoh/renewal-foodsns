package mubex.renewal_foodsns.domain.repository;

import mubex.renewal_foodsns.domain.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface PostRepository {

    Post save(Post post);

    Post findById(Long id);

    Page<Post> findByTitle(String title, Pageable pageable);

    Page<Post> findAll(Pageable pageable);

    Slice<Post> findSliceAll(Pageable pageable);

    Page<Post> findByMemberId(Long memberId, Pageable pageable);

    Page<Post> findByNickName(String userName, Pageable pageable);

    boolean existsByTitle(String title);
}
