package mubex.renewal_foodsns.infrastructure.persistance;

import lombok.RequiredArgsConstructor;
import mubex.renewal_foodsns.domain.entity.PostReport;
import mubex.renewal_foodsns.domain.repository.PostReportRepository;
import mubex.renewal_foodsns.infrastructure.persistance.jpa.PostReportJpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class PostRepostRepositoryImpl implements PostReportRepository {

    private final PostReportJpaRepository postReportJpaRepository;

    @Override
    @Transactional
    public PostReport save(PostReport postReport) {
        return postReportJpaRepository.save(postReport);
    }

    @Override
    public boolean existsByMemberNickName(String nickName) {
        return postReportJpaRepository.existsByMemberNickName(nickName);
    }
}