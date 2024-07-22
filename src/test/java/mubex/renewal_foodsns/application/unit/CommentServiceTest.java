package mubex.renewal_foodsns.application.unit;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import mubex.renewal_foodsns.application.CommentService;
import mubex.renewal_foodsns.domain.mapper.Mappable;
import mubex.renewal_foodsns.domain.dto.response.CommentResponse;
import mubex.renewal_foodsns.domain.entity.Comment;
import mubex.renewal_foodsns.domain.exception.NotFoundException;
import mubex.renewal_foodsns.domain.repository.CommentHeartRepository;
import mubex.renewal_foodsns.domain.repository.CommentReportRepository;
import mubex.renewal_foodsns.domain.repository.CommentRepository;
import mubex.renewal_foodsns.domain.repository.MemberRepository;
import mubex.renewal_foodsns.domain.repository.PostRepository;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(ReplaceUnderscores.class)
public class CommentServiceTest {

    @InjectMocks
    private CommentService commentService;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private CommentHeartRepository commentHeartRepository;

    @Mock
    private CommentReportRepository commentReportRepository;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private PostRepository postRepository;

    @Mock
    private Mappable<CommentResponse, Comment> mappable;

    @Test
    void 유저가_댓글을_등록할_때_유저가_없는_경우_예외_발생() {

        Long memberId = 1L;
        Long postId = 2L;
        String text = "으아";

        given(memberRepository.findById(anyLong())).willThrow(NotFoundException.class);

        assertThatThrownBy(() -> commentService.create(memberId, postId, text))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    void 유저가_댓글을_등록할_때_게시물이_존재_하지_않는_경우_예외() {

        Long memberId = 1L;
        Long postId = 2L;
        String text = "으아";

        given(postRepository.findById(anyLong())).willThrow(NotFoundException.class);

        assertThatThrownBy(() -> commentService.create(memberId, postId, text))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    void 댓글을_수정하는데_유저가_다른_경우_예외() {

        Long memberId = 1L;
        Long postId = 2L;
        Long commentId = 3L;
        String text = "으아";

        Comment comment = mock(Comment.class);

        given(commentRepository.findByPostIdAndMemberIdAndId(anyLong(), anyLong(), anyLong())).willReturn(comment);
        given(comment.isCommentAuthor(memberId)).willReturn(false);

        assertThatThrownBy(() -> commentService.update(postId, memberId, commentId, text))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 댓글을_삭제하려고_하는데_이미_댓글이_삭제_된_경우_예외() {

        Long memberId = 1L;
        Long postId = 2L;
        Long commentId = 3L;

        Comment comment = mock(Comment.class);

        given(commentRepository.findByPostIdAndMemberIdAndId(anyLong(), anyLong(), anyLong())).willReturn(comment);
        given(comment.isDeleted()).willReturn(true);

        assertThatThrownBy(() -> commentService.delete(postId, memberId, commentId))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 유저가_좋아요를_누르려는데_이미_좋아요를_누른_경우_예외() {

        Long memberId = 1L;
        Long postId = 2L;
        Long commentId = 3L;

        given(commentHeartRepository.existByMemberId(anyLong())).willReturn(true);

        assertThatThrownBy(() -> commentService.increaseHeart(postId, memberId, commentId))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 유저가_신고를_누르려는데_이미_신고를_누른_경우_예외() {

        Long memberId = 1L;
        Long postId = 2L;
        Long commentId = 3L;

        given(commentReportRepository.existsByMemberId(anyLong())).willReturn(true);

        assertThatThrownBy(() -> commentService.increaseReport(postId, memberId, commentId))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
