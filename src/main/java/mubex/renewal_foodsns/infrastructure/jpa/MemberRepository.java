package mubex.renewal_foodsns.infrastructure.jpa;

import mubex.renewal_foodsns.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}