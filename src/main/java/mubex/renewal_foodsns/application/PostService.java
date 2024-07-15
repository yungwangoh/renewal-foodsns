package mubex.renewal_foodsns.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import mubex.renewal_foodsns.application.processor.MultiPartFileProcessor;
import mubex.renewal_foodsns.common.call.AsyncCall;
import mubex.renewal_foodsns.common.mapper.Mappable;
import mubex.renewal_foodsns.domain.dto.response.PostResponse;
import mubex.renewal_foodsns.domain.entity.Member;
import mubex.renewal_foodsns.domain.entity.Post;
import mubex.renewal_foodsns.domain.entity.PostHeart;
import mubex.renewal_foodsns.domain.entity.PostImage;
import mubex.renewal_foodsns.domain.entity.PostReport;
import mubex.renewal_foodsns.domain.repository.MemberRepository;
import mubex.renewal_foodsns.domain.repository.PostHeartRepository;
import mubex.renewal_foodsns.domain.repository.PostImageRepository;
import mubex.renewal_foodsns.domain.repository.PostReportRepository;
import mubex.renewal_foodsns.domain.repository.PostRepository;
import mubex.renewal_foodsns.domain.type.FoodTag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final Mappable<PostResponse, Post> mappable;
    private final MultiPartFileProcessor fileProcessor;
    private final PostImageRepository postImageRepository;
    private final PostHeartRepository postHeartRepository;
    private final PostReportRepository postReportRepository;

    @Transactional
    public PostResponse create(final String title, final String text, final String nickName,
                       final FoodTag foodTag, final List<MultipartFile> multipartFiles) {

        if(multipartFiles.size() > 10) {
            throw new IllegalArgumentException("파일 저장은 10개 이하로만 가능합니다.");
        }

        Member member = memberRepository.findByNickName(nickName);

        Post post = Post.create(title, text, 0, 0, foodTag, 0, false, member);

        Post savePost = postRepository.save(post);

        AsyncCall<MultipartFile, String> asyncCall = new AsyncCall<>(multipartFiles);

        List<String> fileNames = asyncCall.execute(10, fileProcessor::write);

        fileNames.forEach(fileName -> savePostImage(fileName, savePost));

        return mappable.toResponse(savePost);
    }

    @Transactional
    public void increaseHeart(final String nickName, final Long postId) {

        if(postHeartRepository.existsByMemberNickName(nickName)) {
            throw new IllegalArgumentException("이미 좋아요를 눌렀습니다.");
        }

        Member member = memberRepository.findByNickName(nickName);

        Post post = postRepository.findById(postId);

        // post heart logic
        PostHeart postHeart = PostHeart.create(post, member);

        postHeartRepository.save(postHeart);

        post.addHeart();
    }

    @Transactional
    public void increaseReport(final String nickName, final Long postId) {

        if(postReportRepository.existsByMemberNickName(nickName)) {
            throw new IllegalArgumentException("이미 신고를 눌렀습니다.");
        }

        Member member = memberRepository.findByNickName(nickName);

        Post post = postRepository.findById(postId);

        // post heart logic
        PostReport postReport = PostReport.create(post, member);

        postReportRepository.save(postReport);

        post.addReport();
    }

    @Transactional
    public void delete(final Long postId) {
        Post post = postRepository.findById(postId);

        post.markAsDeleted();
    }

    public Page<PostResponse> findPostsByTitle(final String title, final Pageable pageable) {
        return postRepository.findByTitle(title, pageable).map(mappable::toResponse);
    }

    public Page<PostResponse> findPostsByNickName(final String nickName, final Pageable pageable) {
        return postRepository.findByNickName(nickName, pageable).map(mappable::toResponse);
    }

    public Page<PostResponse> findPostsByFoodTag(final FoodTag foodTag, final Pageable pageable) {
        return postRepository.findByFoodTag(foodTag, pageable).map(mappable::toResponse);
    }

    private void savePostImage(String fileName, Post savePost) {
        PostImage postImage = PostImage.builder()
                .originFileName(fileName)
                .post(savePost)
                .build();

        postImageRepository.save(postImage);
    }
}
