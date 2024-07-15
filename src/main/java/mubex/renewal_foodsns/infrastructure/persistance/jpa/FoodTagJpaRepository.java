package mubex.renewal_foodsns.infrastructure.persistance.jpa;

import java.util.List;
import mubex.renewal_foodsns.domain.entity.FoodTag;
import mubex.renewal_foodsns.domain.type.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodTagJpaRepository extends JpaRepository<FoodTag, Long> {

    List<FoodTag> findByTag(Tag tag);
}