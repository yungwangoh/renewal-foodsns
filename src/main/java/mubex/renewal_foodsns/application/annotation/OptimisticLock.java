package mubex.renewal_foodsns.application.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.hibernate.StaleObjectStateException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Retryable(
        retryFor = {
                ObjectOptimisticLockingFailureException.class,
                StaleObjectStateException.class,
        },
        maxAttempts = 10,
        backoff = @Backoff(delay = 50, multiplier = 2)
)
public @interface OptimisticLock {
}
