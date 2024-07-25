package mubex.renewal_foodsns.presentation.adviser;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import mubex.renewal_foodsns.domain.exception.LoginException;
import mubex.renewal_foodsns.domain.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
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
    public ErrorResponse handleIllegalArgumentException(final IllegalArgumentException exception) {

        final ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, exception.getMessage());

        return new ErrorResponseException(HttpStatus.BAD_REQUEST, pd, exception);
    }

    @ExceptionHandler(LoginException.class)
    public ErrorResponse handleLoginException(final LoginException exception) {

        final ProblemDetail pd = exception.getExceptionResolver().getBody();

        return new ErrorResponseException(HttpStatus.UNAUTHORIZED, pd, exception);
    }

    @ExceptionHandler(NotFoundException.class)
    public ErrorResponse handleNotFoundException(final NotFoundException exception) {

        final ProblemDetail pd = exception.getExceptionResolver().getBody();

        return new ErrorResponseException(HttpStatus.NOT_FOUND, pd, exception);
    }

    @ExceptionHandler(RuntimeException.class)
    public ErrorResponse handleRuntimeException(final RuntimeException exception) {

        final ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR,
                exception.getMessage());

        return new ErrorResponseException(HttpStatus.INTERNAL_SERVER_ERROR, pd, exception);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse handleMethodArgumentNotValidException(
            final MethodArgumentNotValidException exception) {

        final ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, exception.getMessage());

        return new ErrorResponseException(HttpStatus.BAD_REQUEST, pd, exception);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ErrorResponse handleConstraintViolationException(
            final ConstraintViolationException exception) {

        final ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, exception.getMessage());

        return new ErrorResponseException(HttpStatus.BAD_REQUEST, pd, exception);
    }
}
