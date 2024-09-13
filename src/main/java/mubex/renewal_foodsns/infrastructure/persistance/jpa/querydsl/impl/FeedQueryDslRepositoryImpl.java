package mubex.renewal_foodsns.infrastructure.persistance.jpa.querydsl.impl;

import static mubex.renewal_foodsns.domain.entity.QFollow.follow;
import static mubex.renewal_foodsns.domain.entity.QMember.member;
import static mubex.renewal_foodsns.domain.entity.QPost.post;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import mubex.renewal_foodsns.domain.dto.response.Feed;
import mubex.renewal_foodsns.infrastructure.persistance.jpa.querydsl.FeedQueryDslRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class FeedQueryDslRepositoryImpl implements FeedQueryDslRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Feed> findFeedsToAuthorId(final Long memberId) {

        return queryFactory.select(Projections.constructor(Feed.class, member.nickName, post.id))
                .from(post)
                .join(follow)
                .on(follow.followee.id.eq(post.member.id))
                .join(member)
                .on(follow.follower.id.eq(member.id))
                .where(follow.followee.id.eq(memberId))
                .fetch();
    }

    @Override
    public Slice<Feed> findFeedsToMemberId(final Long memberId, final Pageable pageable) {

        List<Feed> feeds = queryFactory.select(Projections.constructor(Feed.class, member.nickName, post.id))
                .from(post)
                .join(follow)
                .on(follow.followee.id.eq(post.member.id))
                .join(member)
                .on(follow.follower.id.eq(member.id))
                .where(follow.follower.id.eq(memberId).and(post.id.lt(pageable.getOffset())))
                .limit(pageable.getPageSize())
                .fetch();

        return new SliceImpl<>(feeds, pageable, this.isCheckNextPage(feeds));
    }

    private boolean isCheckNextPage(final List<Feed> feeds) {
        return !feeds.isEmpty();
    }
}
