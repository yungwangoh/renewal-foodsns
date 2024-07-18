package mubex.renewal_foodsns.application;

import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import mubex.renewal_foodsns.common.mapper.map.PostMapper;
import mubex.renewal_foodsns.common.mapper.map.PostPageMapper;
import mubex.renewal_foodsns.domain.dto.response.PostImageResponse;
import mubex.renewal_foodsns.domain.dto.response.PostPageResponse;
import mubex.renewal_foodsns.domain.dto.response.PostResponse;
import mubex.renewal_foodsns.domain.entity.Member;
import mubex.renewal_foodsns.domain.entity.Post;
import mubex.renewal_foodsns.domain.entity.PostHeart;
import mubex.renewal_foodsns.domain.entity.PostReport;
import mubex.renewal_foodsns.domain.repository.MemberRepository;
import mubex.renewal_foodsns.domain.repository.PostHeartRepository;
import mubex.renewal_foodsns.domain.repository.PostReportRepository;
import mubex.renewal_foodsns.domain.repository.PostRepository;
import mubex.renewal_foodsns.domain.type.Tag;
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
    private final MemberRepository memberRepository;
    private final PostImageService postImageService;
    private final PostHeartRepository postHeartRepository;
    private final PostReportRepository postReportRepository;
    private final PostMapper postMapper;
    private final PostPageMapper postPageMapper;
    private final FoodTagService foodTagService;

    @Transactional
    public PostResponse create(final String title, final String text, final Long memberId,
                               final Set<Tag> tags, final List<MultipartFile> multipartFiles) {

        checkValidation(title, multipartFiles);

        Member member = memberRepository.findById(memberId);

        Post post = Post.create(title, text, 0, 0, 0, false, member);

        post.addViews();

        Post savePost = postRepository.save(post);

        foodTagService.create(tags, savePost);

        if (!multipartFiles.isEmpty()) {
            return processImages(multipartFiles, post, savePost);
        } else {
            return postMapper.toResponse(savePost);
        }
    }

    @Transactional
    public PostResponse update(final Long postId, final String title, final String text,
                               final Long memberId, final Set<Tag> tags, final List<MultipartFile> multipartFiles) {

        checkValidation(title, multipartFiles);

        Member member = memberRepository.findById(memberId);

        Post post = postRepository.findById(postId);

        post.checkMemberId(member.getId());

        post.updateTitle(title);

        post.updateText(text);

        foodTagService.update(tags, post);

        if (!multipartFiles.isEmpty()) {
            return processImage(multipartFiles, post);
        } else {
            return postMapper.toResponse(post);
        }
    }

    @Transactional
    public void increaseHeart(final Long memberId, final Long postId) {

        if (postHeartRepository.existsByMemberId(memberId)) {
            throw new IllegalArgumentException("이미 좋아요를 눌렀습니다.");
        }

        Member member = memberRepository.findById(memberId);

        member.addHeart(1);

        Post post = postRepository.findById(postId);

        // post heart logic
        PostHeart postHeart = PostHeart.create(post, member);

        postHeartRepository.save(postHeart);

        post.addHeart();
    }

    @Transactional
    public void increaseReport(final Long memberId, final Long postId) {

        if (postReportRepository.existsByMemberId(memberId)) {
            throw new IllegalArgumentException("이미 신고를 눌렀습니다.");
        }

        Member member = memberRepository.findById(memberId);

        member.addReport(1);

        Post post = postRepository.findById(postId);

        // post heart logic
        PostReport postReport = PostReport.create(post, member);

        postReportRepository.save(postReport);

        post.addReport();
    }

    @Transactional
    public void delete(final Long postId) {
        Post post = postRepository.findById(postId);

        if (post.isInDeleted()) {
            throw new IllegalArgumentException("이미 삭제된 게시물 입니다.");
        }

        post.markAsDeleted();
    }

    public PostResponse find(Long postId) {
        Post post = postRepository.findById(postId);

        return postMapper.toResponse(post);
    }

    public Slice<PostPageResponse> findByTag(final Tag tag, final Pageable pageable) {
        return foodTagService.findByTag(tag, pageable)
                .map(foodTag -> postPageMapper.toResponse(foodTag.getPost()));
    }

    public Page<PostPageResponse> findPostsByTitle(final String title, final Pageable pageable) {
        return postRepository.findByTitle(title, pageable).map(postPageMapper::toResponse);
    }

    public Page<PostPageResponse> findPostsByNickName(final String nickName, final Pageable pageable) {
        return postRepository.findByNickName(nickName, pageable).map(postPageMapper::toResponse);
    }

    private PostResponse processImages(List<MultipartFile> multipartFiles, Post post, Post savePost) {
        String thumbnailFileName = postImageService.thumbnail(post, multipartFiles.getFirst()).originFileName();

        savePost.setThumbnail(thumbnailFileName);

        List<PostImageResponse> postImageResponses = postImageService.create(savePost, multipartFiles);

        return postMapper.toResponseWithImages(savePost, postImageResponses);
    }

    private PostResponse processImage(List<MultipartFile> multipartFiles, Post post) {
        List<PostImageResponse> postImageResponses = postImageService.update(post, multipartFiles);

        return postMapper.toResponseWithImages(post, postImageResponses);
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
