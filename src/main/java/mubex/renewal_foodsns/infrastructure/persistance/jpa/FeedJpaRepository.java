package mubex.renewal_foodsns.infrastructure.persistance.jpa;

import mubex.renewal_foodsns.domain.entity.Feed;
import mubex.renewal_foodsns.infrastructure.persistance.jpa.querydsl.FeedQueryDslRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedJpaRepository extends JpaRepository<Feed, Long>, FeedQueryDslRepository {
}