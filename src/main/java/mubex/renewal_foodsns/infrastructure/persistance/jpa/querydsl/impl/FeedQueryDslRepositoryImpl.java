package mubex.renewal_foodsns.infrastructure.persistance.jpa.querydsl.impl;

import static mubex.renewal_foodsns.domain.entity.QFollow.follow;
import static mubex.renewal_foodsns.domain.entity.QMember.member;
import static mubex.renewal_foodsns.domain.entity.QPost.post;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import mubex.renewal_foodsns.domain.entity.Feed;
import mubex.renewal_foodsns.infrastructure.persistance.jpa.querydsl.FeedQueryDslRepository;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class FeedQueryDslRepositoryImpl implements FeedQueryDslRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Feed> findByFollower(final Long postId) {

        return queryFactory.select(Projections.constructor(Feed.class, member, post))
                .from(post)
                .join(follow)
                .on(follow.followee.id.eq(post.member.id))
                .join(member)
                .on(follow.follower.id.eq(member.id))
                .where(post.id.eq(postId))
                .fetch();
    }
}
