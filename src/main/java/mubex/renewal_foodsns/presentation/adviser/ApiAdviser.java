package mubex.renewal_foodsns.presentation.adviser;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import mubex.renewal_foodsns.domain.exception.LoginException;
import mubex.renewal_foodsns.domain.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(annotations = RestController.class)
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

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException exception) {

        ErrorResponse errorResponse = new ErrorResponseException(HttpStatus.INTERNAL_SERVER_ERROR, exception);

        return ResponseEntity.internalServerError().body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException exception) {
        ErrorResponse errorResponse = new ErrorResponseException(HttpStatus.BAD_REQUEST, exception);

        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException exception) {
        ErrorResponse errorResponse = new ErrorResponseException(HttpStatus.BAD_REQUEST, exception);
        return ResponseEntity.badRequest().body(errorResponse);
    }
}
