package mubex.renewal_foodsns.infrastructure.persistance;

import java.util.List;
import lombok.RequiredArgsConstructor;
import mubex.renewal_foodsns.domain.entity.BlackList;
import mubex.renewal_foodsns.domain.repository.BlackListRepository;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BlackListRepositoryImpl implements BlackListRepository {

    @Override
    public BlackList save(BlackList blackList) {
        return null;
    }

    @Override
    public List<BlackList> findAll() {
        return List.of();
    }

    @Override
    public BlackList findById(Long id) {
        return null;
    }

    @Override
    public BlackList findByMemberId(Long memberId) {
        return null;
    }
}
