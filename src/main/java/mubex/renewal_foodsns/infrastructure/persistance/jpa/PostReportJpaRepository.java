package mubex.renewal_foodsns.infrastructure.persistance.jpa;

import mubex.renewal_foodsns.domain.entity.PostReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostReportJpaRepository extends JpaRepository<PostReport, Long> {

    boolean existsByMemberNickName(String nickName);
}
