package mubex.renewal_foodsns.common.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.HandlerInterceptor;

public class RequestMatcherInterceptor implements HandlerInterceptor {

    private final HandlerInterceptor handlerInterceptor;
    private final List<RequestPattern> includingRequestPattern = new ArrayList<>();
    private final List<RequestPattern> excludingRequestPattern = new ArrayList<>();

    public RequestMatcherInterceptor(HandlerInterceptor handlerInterceptor) {
        this.handlerInterceptor = handlerInterceptor;
    }

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler)
            throws Exception {

        if (isMatchingRequestUri(request)) {
            return handlerInterceptor.preHandle(request, response, handler);
        }

        return true;
    }

    private boolean isMatchingRequestUri(final HttpServletRequest request) {
        final String requestUri = request.getRequestURI();
        final HttpMethod httpMethod = HttpMethod.valueOf(request.getMethod().toUpperCase());

        final boolean isIncludingUri = includingRequestPattern.stream()
                .anyMatch(requestPattern -> requestPattern.match(requestUri, httpMethod));

        final boolean isExcludingUri = excludingRequestPattern.stream()
                .noneMatch(requestPattern -> requestPattern.match(requestUri, httpMethod));

        return isIncludingUri && isExcludingUri;
    }

    public RequestMatcherInterceptor addIncludingRequestPattern(final String uri,
                                                                final HttpMethod... httpMethods) {

        final List<HttpMethod> httpMethodList = Arrays.stream(httpMethods).toList();

        httpMethodList.forEach(httpMethod -> this.includingRequestPattern.add(new RequestPattern(uri, httpMethod)));

        return this;
    }

    public RequestMatcherInterceptor addExcludingRequestPattern(final String uri, final HttpMethod... httpMethods) {

        final List<HttpMethod> httpMethodList = Arrays.stream(httpMethods).toList();

        httpMethodList.forEach(httpMethod -> this.excludingRequestPattern.add(new RequestPattern(uri, httpMethod)));

        return this;
    }
}
