package mubex.renewal_foodsns.domain.dto.response;

public record CommentResponse(
        long postId,
        long id,
        String nickName,
        String text,
        long heart,
        int report
) {
}
