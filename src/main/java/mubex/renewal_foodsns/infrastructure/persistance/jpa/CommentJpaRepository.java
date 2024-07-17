package mubex.renewal_foodsns.infrastructure.persistance.jpa;

import java.util.Optional;
import mubex.renewal_foodsns.domain.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentJpaRepository extends JpaRepository<Comment, Long> {

    Page<Comment> findAllByPostId(Long postId, Pageable pageable);

    Optional<Comment> findByMemberIdAndId(Long memberId, Long id);
}