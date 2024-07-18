package mubex.renewal_foodsns.domain.repository;

import mubex.renewal_foodsns.domain.entity.PostReport;

public interface PostReportRepository {

    PostReport save(PostReport postReport);

    boolean existsByMemberNickName(String nickName);

    boolean existsByMemberId(Long memberId);
}
