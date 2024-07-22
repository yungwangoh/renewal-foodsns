package mubex.renewal_foodsns.application.login.base;

import jakarta.servlet.http.HttpSession;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import mubex.renewal_foodsns.application.login.LoginHandler;
import mubex.renewal_foodsns.common.exception.ExceptionResolver;
import mubex.renewal_foodsns.common.mapper.Mappable;
import mubex.renewal_foodsns.common.util.SessionUtil;
import mubex.renewal_foodsns.domain.dto.response.MemberResponse;
import mubex.renewal_foodsns.domain.entity.Member;
import mubex.renewal_foodsns.domain.exception.LoginException;
import mubex.renewal_foodsns.domain.repository.MemberRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Primary
@Component
@RequiredArgsConstructor
public class SessionBasedLogin implements LoginHandler {

    private final HttpSession httpSession;
    private final MemberRepository memberRepository;
    private final Mappable<MemberResponse, Member> mappable;

    @Override
    public MemberResponse signIn(String email, String password) {
        Member member = memberRepository.findByEmailAndPassword(email, password);

        httpSession.setAttribute(SessionUtil.SESSION_ID.getValue(), member.getId());

        return mappable.toResponse(member);
    }

    @Override
    public void signOut() {
        httpSession.removeAttribute(SessionUtil.SESSION_ID.getValue());
    }

    @Override
    public MemberResponse getMember() {
        Long memberId = Optional.ofNullable((Long) httpSession.getAttribute(SessionUtil.SESSION_ID.getValue()))
                .orElseThrow(() -> new LoginException(ExceptionResolver.LOGIN_FAILED));

        Member member = memberRepository.findById(memberId);

        return mappable.toResponse(member);
    }

    @Override
    public Long getMemberId() {
        return Optional.ofNullable((Long) httpSession.getAttribute(SessionUtil.SESSION_ID.getValue()))
                .orElseThrow(() -> new LoginException(ExceptionResolver.LOGIN_FAILED));
    }
}
