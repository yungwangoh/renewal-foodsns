package mubex.renewal_foodsns.infrastructure.persistance.jpa;

import java.util.List;
import java.util.Optional;
import mubex.renewal_foodsns.domain.entity.Member;
import mubex.renewal_foodsns.domain.type.MemberRank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberJpaRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByNickName(String nickName);
    List<Member> findAllByMemberRank(MemberRank memberRank);
    boolean existsByEmailAndPassword(String email, String password);
    Optional<Member> findByEmailAndPassword(String email, String password);
}