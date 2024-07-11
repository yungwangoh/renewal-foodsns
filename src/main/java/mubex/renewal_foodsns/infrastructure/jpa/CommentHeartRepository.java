package mubex.renewal_foodsns.infrastructure.jpa;

import mubex.renewal_foodsns.domain.entity.CommentHeart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentHeartRepository extends JpaRepository<CommentHeart, Long> {
}