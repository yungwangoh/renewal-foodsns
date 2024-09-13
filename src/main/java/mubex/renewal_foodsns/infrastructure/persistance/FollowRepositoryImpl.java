package mubex.renewal_foodsns.infrastructure.persistance;

import java.util.List;
import lombok.RequiredArgsConstructor;
import mubex.renewal_foodsns.application.repository.FollowRepository;
import mubex.renewal_foodsns.common.exception.ExceptionResolver;
import mubex.renewal_foodsns.common.exception.NotFoundException;
import mubex.renewal_foodsns.domain.entity.Follow;
import mubex.renewal_foodsns.domain.entity.Member;
import mubex.renewal_foodsns.domain.type.MemberRank;
import mubex.renewal_foodsns.infrastructure.persistance.jpa.FollowJpaRepository;
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
    public Follow findByFollowerAndFollowee(final Member follower, final Member followee) {
        return followJpaRepository.findByFollowerAndFollowee(follower, followee)
                .orElseThrow(() -> new NotFoundException(ExceptionResolver.NOT_FOUND_MEMBER));
    }

    @Override
    public List<Follow> findByFollowee(final Long followerId) {
        return followJpaRepository.findByFollowerId(followerId);
    }

    @Override
    public List<Follow> findByInfluenceFollowee(final String nickName) {
        return followJpaRepository.findByMemberRankFlower(nickName, MemberRank.INFLUENCE);
    }
}
