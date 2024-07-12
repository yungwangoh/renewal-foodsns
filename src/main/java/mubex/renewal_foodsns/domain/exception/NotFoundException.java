package mubex.renewal_foodsns.domain.exception;

import lombok.Getter;
import mubex.renewal_foodsns.common.exception.ExceptionResolver;

@Getter
public class NotFoundException extends RuntimeException {

    private final ExceptionResolver exceptionResolver;

    public NotFoundException(ExceptionResolver exceptionResolver) {
        super(exceptionResolver.getMessage());
        this.exceptionResolver = exceptionResolver;
    }

    public NotFoundException(String message, ExceptionResolver exceptionResolver) {
        super(message);
        this.exceptionResolver = exceptionResolver;
    }

    public NotFoundException(String message, Throwable cause, ExceptionResolver exceptionResolver) {
        super(message, cause);
        this.exceptionResolver = exceptionResolver;
    }

    public NotFoundException(Throwable cause, ExceptionResolver exceptionResolver) {
        super(cause);
        this.exceptionResolver = exceptionResolver;
    }

    protected NotFoundException(String message, Throwable cause, boolean enableSuppression,
                                boolean writableStackTrace, ExceptionResolver exceptionResolver) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.exceptionResolver = exceptionResolver;
    }
}
