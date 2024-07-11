package mubex.renewal_foodsns.domain.repository;

import mubex.renewal_foodsns.domain.entity.Comment;

public interface CommentRepository {

    Comment save(Comment comment);
}
