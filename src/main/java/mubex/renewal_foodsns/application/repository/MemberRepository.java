package mubex.renewal_foodsns.application.repository;

import java.util.List;
import mubex.renewal_foodsns.domain.entity.Member;
import mubex.renewal_foodsns.domain.type.MemberRank;

public interface MemberRepository {

    Member save(Member member);

    Member findById(Long id);

    Member findByNickName(String nickName);

    List<Member> findAllByMemberRank(MemberRank memberRank);

    boolean existsByEmailAndPassword(String email, String password);

    Member findByEmailAndPassword(String email, String password);

    List<Member> findAll();

    boolean existsByNickName(String nickName);
}
