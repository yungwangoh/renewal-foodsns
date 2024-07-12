package mubex.renewal_foodsns.infrastructure.persistance.jpa;

import mubex.renewal_foodsns.domain.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentJpaRepository extends JpaRepository<Comment, Long> {
}