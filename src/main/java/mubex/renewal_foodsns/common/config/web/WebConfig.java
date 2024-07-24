package mubex.renewal_foodsns.common.config.web;

import java.util.List;
import lombok.RequiredArgsConstructor;
import mubex.renewal_foodsns.presentation.interceptor.BlackListInterceptor;
import mubex.renewal_foodsns.presentation.interceptor.LoginInterceptor;
import mubex.renewal_foodsns.presentation.interceptor.RequestMatcherInterceptor;
import mubex.renewal_foodsns.presentation.resolver.LoginArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final LoginArgumentResolver loginArgumentResolver;
    private final LoginInterceptor loginInterceptor;
    private final BlackListInterceptor blackListInterceptor;

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {

        registry.addInterceptor(matchingInterceptor())
                .order(0);

        registry.addInterceptor(matchingBlackListInterceptor())
                .order(1);
    }

    @Override
    public void addArgumentResolvers(final List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(loginArgumentResolver);
    }

    private HandlerInterceptor matchingInterceptor() {
        return new RequestMatcherInterceptor(loginInterceptor)
                .addIncludingRequestPattern("/**")
                .addExcludingRequestPattern("/**", HttpMethod.GET)
                .addExcludingRequestPattern("/**/members", HttpMethod.POST)
                .addExcludingRequestPattern("/**/sign-in", HttpMethod.POST);
    }

    private HandlerInterceptor matchingBlackListInterceptor() {
        return new RequestMatcherInterceptor(blackListInterceptor)
                .addIncludingRequestPattern("/**")
                .addExcludingRequestPattern("/**", HttpMethod.GET);
    }
}
