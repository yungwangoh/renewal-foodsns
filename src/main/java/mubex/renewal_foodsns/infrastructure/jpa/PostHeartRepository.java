package mubex.renewal_foodsns.infrastructure.jpa;

import mubex.renewal_foodsns.domain.heart.entity.PostHeart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostHeartRepository extends JpaRepository<PostHeart, Long> {
}