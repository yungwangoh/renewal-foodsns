package mubex.renewal_foodsns.infrastructure.persistance.jpa;

import java.util.Optional;
import mubex.renewal_foodsns.domain.entity.Follow;
import mubex.renewal_foodsns.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowJpaRepository extends JpaRepository<Follow, Long> {

    Optional<Follow> findByFollowerAndFollowee(Member follower, Member followee);
}