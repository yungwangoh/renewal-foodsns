package mubex.renewal_foodsns.infrastructure.persistance.jpa;

import mubex.renewal_foodsns.domain.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostJpaRepository extends JpaRepository<Post, Long> {
}