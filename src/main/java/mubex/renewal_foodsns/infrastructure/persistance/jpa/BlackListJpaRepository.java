package mubex.renewal_foodsns.infrastructure.persistance.jpa;

import mubex.renewal_foodsns.domain.entity.BlackList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlackListJpaRepository extends JpaRepository<BlackList, Long> {
}