package mubex.renewal_foodsns.infrastructure.persistance.jpa;

import java.util.List;
import mubex.renewal_foodsns.domain.entity.PostImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostImageJpaRepository extends JpaRepository<PostImage, Long> {

    List<PostImage> findAllByPostId(Long postId);
}
