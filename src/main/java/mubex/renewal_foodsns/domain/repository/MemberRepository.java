package mubex.renewal_foodsns.domain.repository;

import mubex.renewal_foodsns.domain.member.entity.Member;

public interface MemberRepository {

    Member save(Member member);
}
