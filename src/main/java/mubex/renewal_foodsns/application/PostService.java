package mubex.renewal_foodsns.application;

import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import mubex.renewal_foodsns.application.event.RegisteredLevelUpEvent;
import mubex.renewal_foodsns.domain.dto.response.PostImageResponse;
import mubex.renewal_foodsns.domain.dto.response.PostPageResponse;
import mubex.renewal_foodsns.domain.dto.response.PostResponse;
import mubex.renewal_foodsns.domain.entity.Member;
import mubex.renewal_foodsns.domain.entity.Post;
import mubex.renewal_foodsns.domain.entity.PostHeart;
import mubex.renewal_foodsns.domain.entity.PostReport;
import mubex.renewal_foodsns.domain.mapper.map.PostMapper;
import mubex.renewal_foodsns.domain.mapper.map.PostPageMapper;
import mubex.renewal_foodsns.domain.repository.PostHeartRepository;
import mubex.renewal_foodsns.domain.repository.PostReportRepository;
import mubex.renewal_foodsns.domain.repository.PostRepository;
import mubex.renewal_foodsns.domain.type.Tag;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;
    private final MemberService memberService;
    private final PostImageService postImageService;
    private final PostHeartRepository postHeartRepository;
    private final PostReportRepository postReportRepository;
    private final FoodTagService foodTagService;
    private final ApplicationEventPublisher publisher;

    @Transactional
    public PostResponse create(final String title, final String text, final Long memberId,
                               final Set<Tag> tags, final List<MultipartFile> multipartFiles) {

        checkValidation(title, multipartFiles);

        Member member = memberService.findAfterCheckBlackList(memberId);

        Post post = Post.create(title, text, 0, 0, 0, false, member);

        post.addViews();

        Post savePost = postRepository.save(post);

        foodTagService.create(tags, savePost);

        if (!multipartFiles.isEmpty()) {
            return processImage(multipartFiles, post, savePost);
        } else {
            return PostMapper.INSTANCE.toResponse(post);
        }
    }

    @Transactional
    public PostResponse update(final Long postId, final String title, final String text,
                               final Long memberId, final Set<Tag> tags, final List<MultipartFile> multipartFiles) {

        checkValidation(title, multipartFiles);

        Member member = memberService.findAfterCheckBlackList(memberId);

        Post post = postRepository.findById(postId);

        post.checkMemberId(member.getId());

        post.updateTitle(title);

        post.updateText(text);

        foodTagService.update(tags, post);

        if (!multipartFiles.isEmpty()) {
            return processImage(multipartFiles, post);
        } else {
            return PostMapper.INSTANCE.toResponse(post);
        }
    }

    @Transactional
    public PostResponse increaseHeart(final Long memberId, final Long postId, final long heart) {

        if (postHeartRepository.existsByMemberId(memberId)) {
            throw new IllegalArgumentException("이미 좋아요를 눌렀습니다.");
        }

        Member member = memberService.findMember(memberId);

        Post post = postRepository.findById(postId);

        // post heart logic
        PostHeart postHeart = PostHeart.create(post, member);

        postHeartRepository.save(postHeart);

        post.addHeart(heart);

        publisher.publishEvent(new RegisteredLevelUpEvent(post.getMember(), post));

        return PostMapper.INSTANCE.toResponse(post);
    }

    @Transactional
    public PostResponse increaseReport(final Long memberId, final Long postId) {

        if (postReportRepository.existsByMemberId(memberId)) {
            throw new IllegalArgumentException("이미 신고를 눌렀습니다.");
        }

        Member member = memberService.findMember(memberId);

        Post post = postRepository.findById(postId);

        // post heart logic
        PostReport postReport = PostReport.create(post, member);

        postReportRepository.save(postReport);

        post.addReport();

        memberService.addToBlackList(post.getMemberId());

        return PostMapper.INSTANCE.toResponse(post);
    }

    @Transactional
    public void delete(final Long postId) {
        Post post = postRepository.findById(postId);

        post.checkDeletedPost();

        post.decideDeletedPost();
    }

    public PostResponse find(Long postId) {
        Post post = postRepository.findById(postId);

        return PostMapper.INSTANCE.toResponse(post);
    }

    public Slice<PostPageResponse> findByTag(final Tag tag, final Pageable pageable) {
        return foodTagService.findByTag(tag, pageable)
                .map(foodTag -> PostPageMapper.INSTANCE.toResponse(foodTag.getPost()));
    }

    public Slice<PostPageResponse> findAll(Pageable pageable) {
        return postRepository.findAll(pageable).map(PostPageMapper.INSTANCE::toResponse);
    }

    public Page<PostPageResponse> findPostsByTitle(final String title, final Pageable pageable) {
        return postRepository.findByTitle(title, pageable).map(PostPageMapper.INSTANCE::toResponse);
    }

    public Page<PostPageResponse> findPostsByNickName(final String nickName, final Pageable pageable) {
        return postRepository.findByNickName(nickName, pageable).map(PostPageMapper.INSTANCE::toResponse);
    }

    private PostResponse processImage(List<MultipartFile> multipartFiles, Post post, Post savePost) {
        String thumbnailFileName = postImageService.thumbnail(post, multipartFiles.getFirst()).originFileName();

        savePost.setThumbnail(thumbnailFileName);

        List<PostImageResponse> postImageResponses = postImageService.create(savePost, multipartFiles);

        return PostMapper.INSTANCE.toResponseWithImages(savePost, postImageResponses);
    }

    private PostResponse processImage(List<MultipartFile> multipartFiles, Post post) {
        List<PostImageResponse> postImageResponses = postImageService.update(post, multipartFiles);

        return PostMapper.INSTANCE.toResponseWithImages(post, postImageResponses);
    }

    private void checkValidation(String title, List<MultipartFile> multipartFiles) {
        if (multipartFiles.size() > 10) {
            throw new IllegalArgumentException("파일 저장은 10개 이하로만 가능합니다.");
        }

        if (postRepository.existsByTitle(title)) {
            throw new IllegalArgumentException("게시물이 이미 존재합니다.");
        }
    }
}
