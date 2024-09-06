package mubex.renewal_foodsns.application;

import lombok.RequiredArgsConstructor;
import mubex.renewal_foodsns.application.repository.FollowRepository;
import mubex.renewal_foodsns.domain.entity.Follow;
import mubex.renewal_foodsns.domain.entity.Member;
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
    public void unfollow(final Member follower, final Member followee) {

        Follow follow = followRepository.findByFollowerAndFollowee(follower, followee);

        follow.delete();
    }
}
