package mubex.renewal_foodsns.domain.repository;

import java.util.List;
import mubex.renewal_foodsns.domain.entity.FoodTag;
import mubex.renewal_foodsns.domain.type.Tag;

public interface FoodTagRepository {

    FoodTag save(FoodTag foodTag);
    List<FoodTag> findByTag(Tag tag);
}
