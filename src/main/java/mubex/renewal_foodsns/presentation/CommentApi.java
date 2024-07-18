package mubex.renewal_foodsns.presentation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mubex.renewal_foodsns.application.CommentService;
import mubex.renewal_foodsns.common.annotation.Login;
import mubex.renewal_foodsns.domain.dto.request.CommentParam;
import mubex.renewal_foodsns.domain.dto.response.CommentResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/api/v1/{postId}")
public class CommentApi {

    private final CommentService commentService;

    @PostMapping("/comments")
    public ResponseEntity<CommentResponse> create(@PathVariable("postId") final Long postId,
                                                  @RequestBody @Valid final CommentParam commentParam,
                                                  @Login final Long memberId) {

        CommentResponse commentResponse = commentService.create(
                memberId,
                postId,
                commentParam.text()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(commentResponse);
    }

    @PatchMapping("/comments/{commentId}")
    public ResponseEntity<CommentResponse> update(@PathVariable("postId") final Long postId,
                                                  @PathVariable("commentId") final Long commentId,
                                                  @RequestBody @Valid final CommentParam commentParam,
                                                  @Login final Long memberId) {

        CommentResponse commentResponse = commentService.update(
                postId,
                memberId,
                commentId,
                commentParam.text()
        );

        return ResponseEntity.ok(commentResponse);
    }

    @GetMapping("/comments/page")
    public ResponseEntity<Page<CommentResponse>> findByPostId(@PathVariable("postId") Long postId,
                                                              final Pageable pageable) {

        Page<CommentResponse> page = commentService.findPageByPostId(postId, pageable);

        return ResponseEntity.ok(page);
    }

    @GetMapping("/comments/slice")
    public ResponseEntity<Slice<CommentResponse>> findSliceByPostId(@PathVariable("postId") Long postId,
                                                                    final Pageable pageable) {

        Slice<CommentResponse> slice = commentService.findSliceByPostId(postId, pageable);

        return ResponseEntity.ok(slice);
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<Void> delete(@PathVariable("postId") final Long postId,
                                       @PathVariable("commentId") final Long commentId,
                                       @Login final Long memberId) {

        commentService.delete(postId, memberId, commentId);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/comments/{commentId}")
    public ResponseEntity<CommentResponse> increaseHeart(@PathVariable("postId") final Long postId,
                                                         @PathVariable("commentId") final Long commentId,
                                                         @Login final Long memberId) {

        CommentResponse commentResponse = commentService.increaseHeart(postId, memberId, commentId);

        return ResponseEntity.ok(commentResponse);
    }

    @PatchMapping("/comment/{commentId}")
    public ResponseEntity<CommentResponse> increaseReport(@PathVariable("postId") final Long postId,
                                                          @PathVariable("commentId") final Long commentId,
                                                          @Login final Long memberId) {

        CommentResponse commentResponse = commentService.increaseReport(postId, memberId, commentId);

        return ResponseEntity.ok(commentResponse);
    }
}
