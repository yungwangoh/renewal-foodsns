package mubex.renewal_foodsns.application;

import static mubex.renewal_foodsns.common.util.UriUtil.COMMENT_URI;

import lombok.RequiredArgsConstructor;
import mubex.renewal_foodsns.application.event.RegisteredSendEvent;
import mubex.renewal_foodsns.common.mapper.Mappable;
import mubex.renewal_foodsns.domain.dto.response.CommentResponse;
import mubex.renewal_foodsns.domain.entity.Comment;
import mubex.renewal_foodsns.domain.entity.CommentHeart;
import mubex.renewal_foodsns.domain.entity.CommentReport;
import mubex.renewal_foodsns.domain.entity.Member;
import mubex.renewal_foodsns.domain.entity.Post;
import mubex.renewal_foodsns.domain.repository.CommentHeartRepository;
import mubex.renewal_foodsns.domain.repository.CommentReportRepository;
import mubex.renewal_foodsns.domain.repository.CommentRepository;
import mubex.renewal_foodsns.domain.repository.PostRepository;
import mubex.renewal_foodsns.domain.type.NotificationType;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final CommentRepository commentRepository;
    private final MemberService memberService;
    private final Mappable<CommentResponse, Comment> mapper;
    private final PostRepository postRepository;
    private final CommentHeartRepository commentHeartRepository;
    private final CommentReportRepository commentReportRepository;
    private final ApplicationEventPublisher publisher;

    @Transactional
    public CommentResponse create(final Long memberId, final Long postId, final String text) {

        Member member = memberService.findAfterCheckBlackList(memberId);

        Post post = postRepository.findById(postId);

        Comment comment = Comment.create(text, 0, 0, false, post, member);

        Comment save = commentRepository.save(comment);

        publisher.publishEvent(new RegisteredSendEvent(
                member,
                NotificationType.COMMENT,
                COMMENT_URI.generate(postId, comment.getId())
        ));

        return mapper.toResponse(save);
    }

    @Transactional
    public CommentResponse update(final Long postId, final Long memberId, final Long commentId, final String text) {

        Comment comment = commentRepository.findByPostIdAndMemberIdAndId(postId, memberId, commentId);

        if (!comment.isCommentAuthor(memberId)) {
            throw new IllegalArgumentException("유저가 다릅니다.");
        }

        comment.updateText(text);

        return mapper.toResponse(comment);
    }

    public Page<CommentResponse> findPageByPostId(final Long postId, final Pageable pageable) {
        Page<Comment> page = commentRepository.findAllByPostId(postId, pageable);

        return page.map(mapper::toResponse);
    }

    public Slice<CommentResponse> findSliceByPostId(final Long postId, final Pageable pageable) {
        Slice<Comment> page = commentRepository.findSliceAllByPostId(postId, pageable);

        return page.map(mapper::toResponse);
    }

    @Transactional
    public void delete(final Long memberId, final Long commentId) {

        Comment comment = commentRepository.findByMemberIdAndId(memberId, commentId);

        if (comment.isDeleted()) {
            throw new IllegalArgumentException("이미 삭제한 댓글입니다.");
        }

        comment.markAsDeleted();
    }

    @Transactional
    public void delete(final Long postId, final Long memberId, final Long commentId) {

        Comment comment = commentRepository.findByPostIdAndMemberIdAndId(postId, memberId, commentId);

        if (comment.isDeleted()) {
            throw new IllegalArgumentException("이미 삭제한 댓글입니다.");
        }

        comment.markAsDeleted();
    }

    @Transactional
    public CommentResponse increaseHeart(final Long postId, final Long memberId, final Long commentId) {

        if (commentHeartRepository.existByMemberId(memberId)) {
            throw new IllegalArgumentException("이미 좋아요를 눌렀습니다.");
        }

        Comment comment = commentRepository.findByPostIdAndId(postId, commentId);

        Member member = memberService.findMember(memberId);

        comment.addHeart();

        CommentHeart commentHeart = CommentHeart.create(comment, member);

        commentHeartRepository.save(commentHeart);

        return mapper.toResponse(comment);
    }

    @Transactional
    public CommentResponse increaseReport(final Long postId, final Long memberId, final Long commentId) {

        if (commentReportRepository.existsByMemberId(memberId)) {
            throw new IllegalArgumentException("이미 신고를 눌렀습니다.");
        }

        Comment comment = commentRepository.findByPostIdAndMemberIdAndId(postId, memberId, commentId);

        Member member = memberService.findMember(memberId);

        comment.addReport();

        CommentReport commentReport = CommentReport.create(member, comment);

        commentReportRepository.save(commentReport);

        return mapper.toResponse(comment);
    }
}
