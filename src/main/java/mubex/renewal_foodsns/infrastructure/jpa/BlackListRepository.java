package mubex.renewal_foodsns.infrastructure.jpa;

import mubex.renewal_foodsns.domain.entity.BlackList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlackListRepository extends JpaRepository<BlackList, Long> {
}