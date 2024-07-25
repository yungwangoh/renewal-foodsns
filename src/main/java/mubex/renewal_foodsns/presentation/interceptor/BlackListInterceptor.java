package mubex.renewal_foodsns.presentation.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import mubex.renewal_foodsns.common.util.SessionUtil;
import mubex.renewal_foodsns.domain.entity.Member;
import mubex.renewal_foodsns.domain.repository.MemberRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class BlackListInterceptor implements HandlerInterceptor {

    private final MemberRepository memberRepository;

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler)
            throws Exception {

        HttpSession session = request.getSession();

        Long memberId = (Long) session.getAttribute(SessionUtil.SESSION_ID.getValue());

        Member member = memberRepository.findById(memberId);

        return !member.isInBlackList();
    }
}
