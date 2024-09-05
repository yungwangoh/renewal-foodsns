package mubex.renewal_foodsns.application.repository;

import mubex.renewal_foodsns.domain.entity.Follow;
import mubex.renewal_foodsns.domain.entity.Member;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface FollowRepository {

    Follow save(Follow follow);

    Follow findById(Long id);

    Follow findByFromAndTo(Member from, Member to);

    Slice<Long> findFollow(Member from, Pageable pageable);

    Slice<Long> findFollower(Member to, Pageable pageable);
}
