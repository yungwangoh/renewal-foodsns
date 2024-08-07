package mubex.renewal_foodsns.infrastructure.persistance.jpa;

import jakarta.persistence.LockModeType;
import java.util.Optional;
import mubex.renewal_foodsns.domain.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostJpaRepository extends JpaRepository<Post, Long> {

    @Lock(LockModeType.OPTIMISTIC)
    @Query("select p from Post p where p.id = :postId")
    Optional<Post> findByPostId(@Param("postId") Long postId);

    Slice<Post> findAllByTitleStartsWith(String title, Pageable pageable);

    Page<Post> findAllByMemberNickName(String nickName, Pageable pageable);

    Page<Post> findAllByOrderByCreatedAtDesc(Pageable pageable);

    boolean existsByTitle(String title);

    Slice<Post> findSliceBy(Pageable pageable);

    Slice<Post> findAllByTitleOrText(String title, String text, Pageable pageable);
}