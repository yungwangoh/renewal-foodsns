package mubex.renewal_foodsns.infrastructure.persistance.jpa;

import mubex.renewal_foodsns.domain.entity.CommentHeart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentHeartJpaRepository extends JpaRepository<CommentHeart, Long> {
}