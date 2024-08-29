package mubex.renewal_foodsns.application;

import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import mubex.renewal_foodsns.application.repository.TagRepository;
import mubex.renewal_foodsns.domain.dto.response.PostTagResponse;
import mubex.renewal_foodsns.domain.entity.Post;
import mubex.renewal_foodsns.domain.entity.PostTag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TagService {

    private final TagRepository tagRepository;

    @Transactional
    public void create(final Set<String> tags, final Post post) {

        checkValidation(tags);

        final List<PostTag> postTags = tags
                .stream()
                .map(tag -> PostTag.create(tag, post))
                .toList();

        tagRepository.saveAll(postTags);
    }

    @Transactional
    public void update(final Set<String> tags, final Post post) {

        checkValidation(tags);

        tagRepository.deleteAllByPost(post);

        final List<PostTag> postTags = tags
                .stream()
                .map(tag -> PostTag.create(tag, post))
                .toList();

        tagRepository.saveAll(postTags);
    }

    public Slice<PostTagResponse> findByTag(final String tag, final Pageable pageable) {
        return tagRepository.findByTag(tag, pageable).map(PostTagResponse::of);
    }

    private static void checkValidation(Set<String> tags) {
        if (tags.isEmpty()) {
            throw new IllegalArgumentException("태그를 넣어주세요!!.");
        }

        if (tags.size() >= 5) {
            throw new IllegalArgumentException("태그는 5개까지만 추가 가능합니다.");
        }
    }
}
