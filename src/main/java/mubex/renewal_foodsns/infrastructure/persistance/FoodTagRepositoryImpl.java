package mubex.renewal_foodsns.infrastructure.persistance;

import java.util.List;
import lombok.RequiredArgsConstructor;
import mubex.renewal_foodsns.domain.entity.FoodTag;
import mubex.renewal_foodsns.domain.repository.FoodTagRepository;
import mubex.renewal_foodsns.domain.type.Tag;
import mubex.renewal_foodsns.infrastructure.persistance.jpa.FoodTagJpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FoodTagRepositoryImpl implements FoodTagRepository {

    private final FoodTagJpaRepository foodTagJpaRepository;

    @Override
    @Transactional
    public FoodTag save(FoodTag foodTag) {
        return foodTagJpaRepository.save(foodTag);
    }

    @Override
    public List<FoodTag> findByTag(Tag tag) {
        return foodTagJpaRepository.findByTag(tag);
    }
}
