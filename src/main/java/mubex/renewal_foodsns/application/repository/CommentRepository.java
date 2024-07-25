package mubex.renewal_foodsns.application.repository;

import java.util.List;
import mubex.renewal_foodsns.domain.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface CommentRepository {

    Comment save(Comment comment);

    void saveAll(List<Comment> comments);

    Comment findById(Long id);

    List<Comment> findAll();

    Page<Comment> findAllByPostId(Long postId, Pageable pageable);

    Slice<Comment> findSliceAllByPostId(Long postId, Pageable pageable);

    Comment findByMemberIdAndId(Long memberId, Long id);

    Comment findByPostIdAndId(Long postId, Long id);

    Comment findByPostIdAndMemberIdAndId(Long postId, Long memberId, Long id);
}
