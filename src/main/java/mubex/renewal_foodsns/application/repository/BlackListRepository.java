package mubex.renewal_foodsns.application.repository;

import java.util.List;
import mubex.renewal_foodsns.domain.entity.BlackList;

public interface BlackListRepository {

    BlackList save(BlackList blackList);

    List<BlackList> findAll();

    BlackList findById(Long id);

    BlackList findByMemberId(Long memberId);
}
