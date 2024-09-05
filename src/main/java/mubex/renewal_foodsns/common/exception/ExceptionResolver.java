package mubex.renewal_foodsns.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponse;

@Getter
public enum ExceptionResolver implements ErrorResponse {

    // not_found 404
    NOT_FOUND_MEMBER(HttpStatus.NOT_FOUND, "NOT_FOUND_000", "유저를 찾을 수 없습니다."),
    NOT_FOUND_POST(HttpStatus.NOT_FOUND, "NOT_FOUND_001", "게시물을 찾을 수 없습니다."),
    NOT_FOUND_COMMENT(HttpStatus.NOT_FOUND, "NOT_FOUND_002", "댓글을 찾을 수 없습니다."),
    NOT_FOUND_COMMENT_HEART(HttpStatus.NOT_FOUND, "NOT_FOUND_003", "댓글 좋아요를 찾을 수 없습니다."),
    NOT_FOUND_POST_HEART(HttpStatus.NOT_FOUND, "NOT_FOUND_004", "게시물 좋아요를 찾을 수 없습니다."),
    NOT_FOUND_BLACK_LIST(HttpStatus.NOT_FOUND, "NOT_FOUND_005", "블랙 리스트를 찾을 수 없습니다."),
    NOT_FOUND_POST_IMAGE(HttpStatus.NOT_FOUND, "NOT_FOUND_006", "게시물 이미지를 찾을 수 없습니다."),
    NOT_FOUND_FOLLOW(HttpStatus.NOT_FOUND, "NOT_FOUND_007", "팔로우를 찾을 수 없습니다."),

    // un_authorized 401
    LOGIN_FAILED(HttpStatus.UNAUTHORIZED, "UN_AUTHORIZED_000", "로그인에 실패하였습니다."),
    UNAUTHORIZED_MEMBER(HttpStatus.UNAUTHORIZED, "UN_AUTHORIZED_001", "인증되지 않은 사용자입니다."),

    // bad_request 400
    ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    ExceptionResolver(HttpStatus httpStatus, String code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }

    @Override
    public HttpStatusCode getStatusCode() {
        return this.httpStatus;
    }

    @Override
    public ProblemDetail getBody() {
        final ProblemDetail pd = ProblemDetail.forStatusAndDetail(this.httpStatus, this.message);
        pd.setTitle(this.code);

        return pd;
    }
}
