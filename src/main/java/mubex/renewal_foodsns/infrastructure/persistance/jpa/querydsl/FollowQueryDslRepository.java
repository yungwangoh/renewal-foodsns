package mubex.renewal_foodsns.infrastructure.persistance.jpa.querydsl;

import mubex.renewal_foodsns.domain.entity.Member;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface FollowQueryDslRepository {

    Slice<Long> findByGroupByFrom(Member from, Pageable pageable);

    Slice<Long> findByGroupByTo(Member to, Pageable pageable);
}
