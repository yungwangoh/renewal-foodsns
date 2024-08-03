package mubex.renewal_foodsns.infrastructure.persistance.jpa;

import mubex.renewal_foodsns.domain.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostJpaRepository extends JpaRepository<Post, Long> {

    Page<Post> findAllByTitleStartsWith(String title, Pageable pageable);

    Page<Post> findAllByMemberNickName(String nickName, Pageable pageable);

    Page<Post> findAllByOrderByCreatedAtDesc(Pageable pageable);

    boolean existsByTitle(String title);

    Slice<Post> findAllByTitleOrText(String title, String text, Pageable pageable);
}