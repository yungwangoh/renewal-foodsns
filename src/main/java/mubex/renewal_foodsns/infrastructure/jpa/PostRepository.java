package mubex.renewal_foodsns.infrastructure.jpa;

import mubex.renewal_foodsns.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}