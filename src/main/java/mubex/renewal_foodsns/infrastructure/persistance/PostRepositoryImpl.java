package mubex.renewal_foodsns.infrastructure.persistance;

import lombok.RequiredArgsConstructor;
import mubex.renewal_foodsns.common.exception.ExceptionResolver;
import mubex.renewal_foodsns.domain.entity.Post;
import mubex.renewal_foodsns.domain.exception.NotFoundException;
import mubex.renewal_foodsns.domain.repository.PostRepository;
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
    public Post save(Post post) {
        return postJpaRepository.save(post);
    }

    @Override
    public Post findById(Long id) {
        return postJpaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ExceptionResolver.NOT_FOUND_POST));
    }

    @Override
    public Page<Post> findByTitle(String title, Pageable pageable) {
        return postJpaRepository.findAllByTitle(title, pageable);
    }

    @Override
    public Slice<Post> findSliceAll(Pageable pageable) {
        return postJpaRepository.findAll(pageable);
    }

    @Override
    public Page<Post> findAll(Pageable pageable) {
        return postJpaRepository.findAll(pageable);
    }

    @Override
    public Page<Post> findByMemberId(Long memberId, Pageable pageable) {
        return null;
    }

    @Override
    public Page<Post> findByNickName(String nickName, Pageable pageable) {
        return postJpaRepository.findAllByMemberNickName(nickName, pageable);
    }

    @Override
    public boolean existsByTitle(String title) {
        return postJpaRepository.existsByTitle(title);
    }
}
