package mubex.renewal_foodsns.presentation.adviser;

import lombok.extern.slf4j.Slf4j;
import mubex.renewal_foodsns.domain.exception.LoginException;
import mubex.renewal_foodsns.domain.exception.NotFoundException;
import mubex.renewal_foodsns.presentation.annotation.Api;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(annotations = Api.class)
public class ApiAdviser {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException exception) {

        ErrorResponse errorResponse = new ErrorResponseException(HttpStatus.BAD_REQUEST, exception);

        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(LoginException.class)
    public ResponseEntity<ErrorResponse> handleLoginException(LoginException exception) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(exception.getExceptionResolver());
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getExceptionResolver());
    }
}
