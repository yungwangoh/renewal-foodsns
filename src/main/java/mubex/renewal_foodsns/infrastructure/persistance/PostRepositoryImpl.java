package mubex.renewal_foodsns.infrastructure.persistance;

import java.util.List;
import lombok.RequiredArgsConstructor;
import mubex.renewal_foodsns.application.repository.PostRepository;
import mubex.renewal_foodsns.common.exception.ExceptionResolver;
import mubex.renewal_foodsns.domain.entity.Post;
import mubex.renewal_foodsns.domain.exception.NotFoundException;
import mubex.renewal_foodsns.infrastructure.persistance.jpa.PostJpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
@Transactional
public class PostRepositoryImpl implements PostRepository {

    private final PostJpaRepository postJpaRepository;

    @Override
    @Transactional
    public Post save(final Post post) {
        return postJpaRepository.save(post);
    }

    @Override
    @Transactional
    public void saveAll(final List<Post> posts) {
        postJpaRepository.saveAll(posts);
    }

    @Override
    public Post findById(final Long id) {
        return postJpaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ExceptionResolver.NOT_FOUND_POST));
    }

    @Override
    public Page<Post> findByTitle(final String title, final Pageable pageable) {
        return postJpaRepository.findAllByTitle(title, pageable);
    }

    @Override
    public Slice<Post> findSliceAll(final Pageable pageable) {
        return postJpaRepository.findAll(pageable);
    }

    @Override
    public Page<Post> findAll(final Pageable pageable) {
        return postJpaRepository.findAll(pageable);
    }

    @Override
    public Page<Post> findByMemberId(final Long memberId, final Pageable pageable) {
        return null;
    }

    @Override
    public Page<Post> findByNickName(final String nickName, final Pageable pageable) {
        return postJpaRepository.findAllByMemberNickName(nickName, pageable);
    }

    @Override
    public boolean existsByTitle(final String title) {
        return postJpaRepository.existsByTitle(title);
    }
}
