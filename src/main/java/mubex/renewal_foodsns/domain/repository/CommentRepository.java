package mubex.renewal_foodsns.domain.repository;

import java.util.List;
import mubex.renewal_foodsns.domain.entity.Comment;

public interface CommentRepository {

    Comment save(Comment comment);
    Comment findById(Long id);
    List<Comment> findAll();
    List<Comment> findAllByPostId(Long postId);
}
