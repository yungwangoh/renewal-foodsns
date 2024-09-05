package mubex.renewal_foodsns.application;

import lombok.RequiredArgsConstructor;
import mubex.renewal_foodsns.application.repository.FollowRepository;
import mubex.renewal_foodsns.domain.entity.Follow;
import mubex.renewal_foodsns.domain.entity.Member;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FollowService {

    private final FollowRepository followRepository;

    @Transactional
    public void follow(final Member from, final Member to, final boolean inDeleted) {

        Follow follow = Follow.create(from, to, inDeleted);

        followRepository.save(follow);
    }

    @Transactional
    public void unfollow(final Member from, final Member to, final boolean inDeleted) {

        Follow follow = followRepository.findByFromAndTo(from, to);

        follow.delete();
    }

    public Slice<Long> findFollow(final Member member, final Pageable pageable) {
        return followRepository.findFollow(member, pageable);
    }

    public Slice<Long> findFollower(final Member member, final Pageable pageable) {
        return followRepository.findFollower(member, pageable);
    }
}
