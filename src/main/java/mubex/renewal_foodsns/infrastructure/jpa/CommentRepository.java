package mubex.renewal_foodsns.infrastructure.jpa;

import mubex.renewal_foodsns.domain.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}