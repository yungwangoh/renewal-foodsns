package mubex.renewal_foodsns.infrastructure.persistance;

import lombok.RequiredArgsConstructor;
import mubex.renewal_foodsns.domain.entity.CommentReport;
import mubex.renewal_foodsns.domain.repository.CommentReportRepository;
import mubex.renewal_foodsns.infrastructure.persistance.jpa.CommentReportJpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class CommentReportRepositoryImpl implements CommentReportRepository {

    private final CommentReportJpaRepository commentReportJpaRepository;

    @Override
    @Transactional
    public CommentReport save(CommentReport commentReport) {
        return commentReportJpaRepository.save(commentReport);
    }

    @Override
    public boolean existsByMemberId(Long memberId) {
        return commentReportJpaRepository.existsByMemberId(memberId);
    }
}
