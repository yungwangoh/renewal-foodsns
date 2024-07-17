package mubex.renewal_foodsns.domain.repository;

import java.util.List;
import mubex.renewal_foodsns.domain.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentRepository {

    Comment save(Comment comment);

    Comment findById(Long id);

    List<Comment> findAll();

    Page<Comment> findAllByPostId(Long postId, Pageable pageable);

    Comment findByMemberIdAndId(Long memberId, Long id);
}
