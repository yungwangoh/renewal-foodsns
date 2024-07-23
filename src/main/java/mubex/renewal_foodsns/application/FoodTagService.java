package mubex.renewal_foodsns.application;

import java.util.List;
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

        checkValidation(tags);

        final List<FoodTag> foodTags = tags
                .stream()
                .map(tag -> FoodTag.create(tag, post))
                .toList();

        foodTagRepository.saveAll(foodTags);
    }

    @Transactional
    public void update(final Set<Tag> tags, final Post post) {

        checkValidation(tags);

        foodTagRepository.deleteAllByPost(post);

        final List<FoodTag> foodTags = tags
                .stream()
                .map(tag -> FoodTag.create(tag, post))
                .toList();

        foodTagRepository.saveAll(foodTags);
    }

    public Slice<FoodTag> findByTag(final Tag tag, final Pageable pageable) {
        return foodTagRepository.findByTag(tag, pageable);
    }

    private static void checkValidation(Set<Tag> tags) {
        if (tags.isEmpty()) {
            throw new IllegalArgumentException("태그를 넣어주세요!!.");
        }

        if (tags.size() >= 5) {
            throw new IllegalArgumentException("태그는 5개까지만 추가 가능합니다.");
        }
    }
}
