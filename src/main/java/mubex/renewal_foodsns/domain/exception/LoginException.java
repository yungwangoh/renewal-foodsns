package mubex.renewal_foodsns.domain.exception;

import lombok.Getter;
import mubex.renewal_foodsns.common.exception.ExceptionResolver;

@Getter
public class LoginException extends RuntimeException {

    private final ExceptionResolver exceptionResolver;

    public LoginException(ExceptionResolver exceptionResolver) {
        super(exceptionResolver.getMessage());
        this.exceptionResolver = exceptionResolver;
    }

    public LoginException(String message, ExceptionResolver exceptionResolver) {
        super(message);
        this.exceptionResolver = exceptionResolver;
    }

    public LoginException(String message, Throwable cause, ExceptionResolver exceptionResolver) {
        super(message, cause);
        this.exceptionResolver = exceptionResolver;
    }

    public LoginException(Throwable cause, ExceptionResolver exceptionResolver) {
        super(cause);
        this.exceptionResolver = exceptionResolver;
    }

    public LoginException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace,
                          ExceptionResolver exceptionResolver) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.exceptionResolver = exceptionResolver;
    }
}
