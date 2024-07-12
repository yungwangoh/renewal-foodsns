package mubex.renewal_foodsns.infrastructure.persistance;

import java.util.List;
import lombok.RequiredArgsConstructor;
import mubex.renewal_foodsns.domain.entity.Comment;
import mubex.renewal_foodsns.domain.repository.CommentRepository;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepository {

    @Override
    public Comment save(Comment comment) {
        return null;
    }

    @Override
    public Comment findById(Long id) {
        return null;
    }

    @Override
    public List<Comment> findAll() {
        return List.of();
    }

    @Override
    public List<Comment> findAllByPostId(Long postId) {
        return List.of();
    }
}
