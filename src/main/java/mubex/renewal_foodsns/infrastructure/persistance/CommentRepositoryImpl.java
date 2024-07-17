package mubex.renewal_foodsns.infrastructure.persistance;

import java.util.List;
import lombok.RequiredArgsConstructor;
import mubex.renewal_foodsns.common.exception.ExceptionResolver;
import mubex.renewal_foodsns.domain.entity.Comment;
import mubex.renewal_foodsns.domain.exception.NotFoundException;
import mubex.renewal_foodsns.domain.repository.CommentRepository;
import mubex.renewal_foodsns.infrastructure.persistance.jpa.CommentJpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepository {

    private final CommentJpaRepository commentJpaRepository;

    @Override
    @Transactional
    public Comment save(Comment comment) {
        return commentJpaRepository.save(comment);
    }

    @Override
    public Comment findById(Long id) {
        return commentJpaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ExceptionResolver.NOT_FOUND_COMMENT));
    }

    @Override
    public List<Comment> findAll() {
        return commentJpaRepository.findAll();
    }

    @Override
    public Page<Comment> findAllByPostId(Long postId, Pageable pageable) {
        return commentJpaRepository.findAllByPostId(postId, pageable);
    }

    @Override
    public Comment findByMemberIdAndId(Long memberId, Long id) {
        return commentJpaRepository.findByMemberIdAndId(memberId, id)
                .orElseThrow(() -> new NotFoundException(ExceptionResolver.NOT_FOUND_COMMENT));
    }
}
