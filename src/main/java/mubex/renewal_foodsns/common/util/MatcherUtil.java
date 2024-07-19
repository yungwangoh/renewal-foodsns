package mubex.renewal_foodsns.common.util;

import java.util.Objects;
import mubex.renewal_foodsns.presentation.interceptor.RequestPattern;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

public class MatcherUtil {

    private static final PathMatcher pathMatcher = new AntPathMatcher();

    public static boolean match(final RequestPattern requestPattern,
                                final RequestPattern compareRequestPattern) {

        return pathMatcher.match(requestPattern.uri(), compareRequestPattern.uri()) &&
                Objects.equals(requestPattern.httpMethod(), compareRequestPattern.httpMethod());
    }
}
