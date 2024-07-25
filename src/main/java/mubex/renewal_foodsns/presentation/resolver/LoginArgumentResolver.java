package mubex.renewal_foodsns.presentation.resolver;

import jakarta.servlet.http.HttpServletRequest;
import mubex.renewal_foodsns.common.util.SessionUtil;
import mubex.renewal_foodsns.presentation.annotation.Login;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class LoginArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(final MethodParameter parameter) {
        return parameter.hasParameterAnnotation(Login.class);
    }

    @Override
    public Object resolveArgument(final MethodParameter parameter, final ModelAndViewContainer mavContainer,
                                  final NativeWebRequest webRequest, final WebDataBinderFactory binderFactory)
            throws Exception {

        final HttpServletRequest httpServletRequest = (HttpServletRequest) webRequest.getNativeRequest();

        return httpServletRequest.getSession().getAttribute(SessionUtil.SESSION_ID.getValue());
    }
}
