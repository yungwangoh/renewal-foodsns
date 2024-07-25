package mubex.renewal_foodsns.infrastructure.persistance;

import java.util.List;
import lombok.RequiredArgsConstructor;
import mubex.renewal_foodsns.application.repository.PostImageRepository;
import mubex.renewal_foodsns.common.exception.ExceptionResolver;
import mubex.renewal_foodsns.domain.entity.PostImage;
import mubex.renewal_foodsns.domain.exception.NotFoundException;
import mubex.renewal_foodsns.infrastructure.persistance.jpa.PostImageJpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostImageRepositoryImpl implements PostImageRepository {

    private final PostImageJpaRepository postImageJpaRepository;

    @Override
    @Transactional
    public PostImage save(final PostImage postImage) {
        return postImageJpaRepository.save(postImage);
    }

    @Override
    public PostImage findById(final Long id) {
        return postImageJpaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ExceptionResolver.NOT_FOUND_POST_IMAGE));
    }

    @Override
    public List<PostImage> findAllByPostId(final Long postId) {
        return postImageJpaRepository.findAllByPostId(postId);
    }
}
