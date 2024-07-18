package mubex.renewal_foodsns.application;

import java.util.Set;
import lombok.RequiredArgsConstructor;
import mubex.renewal_foodsns.domain.entity.FoodTag;
import mubex.renewal_foodsns.domain.entity.Post;
import mubex.renewal_foodsns.domain.repository.FoodTagRepository;
import mubex.renewal_foodsns.domain.type.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FoodTagService {

    private final FoodTagRepository foodTagRepository;

    @Transactional
    public void create(final Set<Tag> tags, final Post post) {

        if (tags.isEmpty()) {
            throw new IllegalArgumentException("태그를 넣어주세요!!.");
        }

        if (tags.size() >= 5) {
            throw new IllegalArgumentException("태그는 5개까지만 추가 가능합니다.");
        }

        tags.forEach(tag -> {
            FoodTag foodTag = FoodTag.create(tag, post);

            foodTagRepository.save(foodTag);
        });
    }

    @Transactional
    public void update(Set<Tag> tags, Post post) {

        foodTagRepository.deleteAllByPost(post);

        tags.forEach(tag -> {
            FoodTag foodTag = FoodTag.create(tag, post);

            foodTagRepository.save(foodTag);
        });
    }

    public Slice<FoodTag> findByTag(final Tag tag, final Pageable pageable) {
        return foodTagRepository.findByTag(tag, pageable);
    }
}