package mubex.renewal_foodsns.application.repository;

import mubex.renewal_foodsns.domain.entity.Follow;
import mubex.renewal_foodsns.domain.entity.Member;

public interface FollowRepository {

    Follow save(Follow follow);

    Follow findById(Long id);

    Follow findByFollowerAndFollowee(Member from, Member to);
}
