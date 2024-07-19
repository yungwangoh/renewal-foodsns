package mubex.renewal_foodsns.common.interceptor;

import mubex.renewal_foodsns.common.util.MatcherUtil;
import org.springframework.http.HttpMethod;

public record RequestPattern(String uri, HttpMethod httpMethod) {

    public boolean match(final String uri, final HttpMethod httpMethod) {

        final RequestPattern requestPattern = new RequestPattern(uri, httpMethod);

        return MatcherUtil.match(this, requestPattern);
    }
}
