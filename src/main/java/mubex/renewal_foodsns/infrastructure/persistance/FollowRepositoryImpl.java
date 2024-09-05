package mubex.renewal_foodsns.infrastructure.persistance;

import lombok.RequiredArgsConstructor;
import mubex.renewal_foodsns.application.repository.FollowRepository;
import mubex.renewal_foodsns.common.exception.ExceptionResolver;
import mubex.renewal_foodsns.common.exception.NotFoundException;
import mubex.renewal_foodsns.domain.entity.Follow;
import mubex.renewal_foodsns.domain.entity.Member;
import mubex.renewal_foodsns.infrastructure.persistance.jpa.FollowJpaRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class FollowRepositoryImpl implements FollowRepository {

    private final FollowJpaRepository followJpaRepository;

    @Override
    @Transactional
    public Follow save(final Follow follow) {
        return followJpaRepository.save(follow);
    }

    @Override
    public Follow findById(final Long id) {
        return followJpaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ExceptionResolver.NOT_FOUND_FOLLOW));
    }

    @Override
    public Follow findByFromAndTo(final Member from, final Member to) {
        return followJpaRepository.findByFromAndTo(from, to)
                .orElseThrow(() -> new NotFoundException(ExceptionResolver.NOT_FOUND_MEMBER));
    }

    @Override
    public Slice<Long> findFollow(final Member from, final Pageable pageable) {
        return followJpaRepository.findByGroupByFrom(from, pageable);
    }

    @Override
    public Slice<Long> findFollower(final Member to, final Pageable pageable) {
        return followJpaRepository.findByGroupByTo(to, pageable);
    }
}
