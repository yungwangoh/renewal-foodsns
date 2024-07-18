package mubex.renewal_foodsns.infrastructure.persistance.jpa;

import mubex.renewal_foodsns.domain.entity.CommentReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentReportJpaRepository extends JpaRepository<CommentReport, Long> {

    boolean existsByMemberId(Long memberId);
}