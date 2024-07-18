package mubex.renewal_foodsns.domain.repository;

import mubex.renewal_foodsns.domain.entity.CommentReport;

public interface CommentReportRepository {

    CommentReport save(CommentReport commentReport);

    boolean existsByMemberId(Long memberId);
}
