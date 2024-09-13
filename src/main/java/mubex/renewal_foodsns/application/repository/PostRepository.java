package mubex.renewal_foodsns.application.repository;

import java.util.List;
import mubex.renewal_foodsns.domain.document.PostDocument;
import mubex.renewal_foodsns.domain.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.elasticsearch.core.SearchHits;

public interface PostRepository {

    Post save(Post post);

    void saveAll(List<Post> posts);

    Post findById(Long id);

    Post findByMemberId(Long memberId);

    Slice<Post> findByTitle(String title, Pageable pageable);

    Page<Post> findAll(Pageable pageable);

    Slice<Post> findSliceAll(Pageable pageable);

    Page<Post> findByMemberId(Long memberId, Pageable pageable);

    Page<Post> findByNickName(String userName, Pageable pageable);

    boolean existsByTitle(String title);

    SearchHits<PostDocument> findAllByTitleOrText(String searchText, Pageable pageable);
}
