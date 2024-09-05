package mubex.renewal_foodsns.infrastructure.persistance.jpa;

import java.util.Optional;
import mubex.renewal_foodsns.domain.entity.Follow;
import mubex.renewal_foodsns.domain.entity.Member;
import mubex.renewal_foodsns.infrastructure.persistance.jpa.querydsl.FollowQueryDslRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowJpaRepository extends JpaRepository<Follow, Long>, FollowQueryDslRepository {

    Optional<Follow> findByFromAndTo(Member from, Member to);

    Slice<Follow> findByFromAndTo(Member from, Pageable pageable);

    Slice<Follow> findByToAndFrom(Member to, Member from, Pageable pageable);
}