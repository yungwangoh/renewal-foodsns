package mubex.renewal_foodsns.application;

import java.util.Objects;
import lombok.RequiredArgsConstructor;
import mubex.renewal_foodsns.common.mapper.Mappable;
import mubex.renewal_foodsns.domain.dto.response.CommentResponse;
import mubex.renewal_foodsns.domain.entity.Comment;
import mubex.renewal_foodsns.domain.entity.Member;
import mubex.renewal_foodsns.domain.entity.Post;
import mubex.renewal_foodsns.domain.repository.CommentRepository;
import mubex.renewal_foodsns.domain.repository.MemberRepository;
import mubex.renewal_foodsns.domain.repository.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final CommentRepository commentRepository;
    private final Mappable<CommentResponse, Comment> mapper;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

    @Transactional
    public CommentResponse create(final Long memberId, final Long postId, final String text) {

        Member member = memberRepository.findById(memberId);

        Post post = postRepository.findById(postId);

        Comment comment = Comment.create(text, 0, 0, false, post, member);

        Comment save = commentRepository.save(comment);

        return mapper.toResponse(save);
    }

    @Transactional
    public CommentResponse update(final Long memberId, final Long commentId, final String text) {

        Comment comment = commentRepository.findById(commentId);

        if (!Objects.equals(memberId, comment.getMember().getId())) {
            throw new IllegalArgumentException("유저가 다릅니다.");
        }

        comment.updateText(text);

        return mapper.toResponse(comment);
    }

    public Page<CommentResponse> findPageByPostId(final Long postId, final Pageable pageable) {
        Page<Comment> page = commentRepository.findAllByPostId(postId, pageable);

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
}
