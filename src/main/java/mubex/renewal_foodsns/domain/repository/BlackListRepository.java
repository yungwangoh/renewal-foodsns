package mubex.renewal_foodsns.domain.repository;

import mubex.renewal_foodsns.domain.blacklist.entity.BlackList;

public interface BlackListRepository {

    BlackList save(BlackList blackList);
}
