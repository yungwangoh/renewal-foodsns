package mubex.renewal_foodsns.domain.dto.response;

public record CommentResponse(
        String nickName,
        String text,
        long heart,
        int report
) {
}