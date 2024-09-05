package mubex.renewal_foodsns.infrastructure.persistance.jpa.querydsl.impl;

import static mubex.renewal_foodsns.domain.entity.QFollow.follow;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import mubex.renewal_foodsns.domain.entity.Member;
import mubex.renewal_foodsns.infrastructure.persistance.jpa.querydsl.FollowQueryDslRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class FollowQueryDslRepositoryImpl implements FollowQueryDslRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Slice<Long> findByGroupByFrom(final Member from, final Pageable pageable) {

        List<Long> fetch = queryFactory.select(follow.to.id)
                .from(follow)
                .where(follow.from.id.eq(from.getId()).and(follow.id.goe(pageable.getOffset())))
                .groupBy(follow.from)
                .offset(0)
                .limit(pageable.getPageSize())
                .fetch();

        return new SliceImpl<>(fetch);
    }

    @Override
    public Slice<Long> findByGroupByTo(final Member to, final Pageable pageable) {

        List<Long> fetch = queryFactory.select(follow.from.id)
                .from(follow)
                .where(follow.to.id.eq(to.getId()).and(follow.id.goe(pageable.getOffset())))
                .groupBy(follow.from)
                .offset(0)
                .limit(pageable.getPageSize())
                .fetch();

        return new SliceImpl<>(fetch);
    }
}
