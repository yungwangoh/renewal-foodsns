package mubex.renewal_foodsns.infrastructure.persistance.jpa;

import java.util.List;
import java.util.Optional;
import mubex.renewal_foodsns.domain.entity.Follow;
import mubex.renewal_foodsns.domain.entity.Member;
import mubex.renewal_foodsns.domain.type.MemberRank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FollowJpaRepository extends JpaRepository<Follow, Long> {

    Optional<Follow> findByFollowerAndFollowee(Member follower, Member followee);

    List<Follow> findByFollowerId(Long followerId);

    @Query("select f from Follow f join Member m on m.memberRank = :memberRank where f.follower.nickName = :nickName")
    List<Follow> findByMemberRankFlower(@Param("nickName") String nickName, @Param("memberRank") MemberRank memberRank);
}