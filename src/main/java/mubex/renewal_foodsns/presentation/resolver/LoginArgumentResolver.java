package mubex.renewal_foodsns.presentation.resolver;

import jakarta.servlet.http.HttpServletRequest;
import mubex.renewal_foodsns.common.annotation.Login;
import mubex.renewal_foodsns.common.util.SessionUtil;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class LoginArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(Login.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        HttpServletRequest httpServletRequest = (HttpServletRequest) webRequest.getNativeRequest();

        return httpServletRequest.getSession().getAttribute(SessionUtil.SESSION_ID.getValue());
    }
}
