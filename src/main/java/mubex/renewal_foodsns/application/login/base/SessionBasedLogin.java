package mubex.renewal_foodsns.application.login.base;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import mubex.renewal_foodsns.application.login.LoginHandler;
import mubex.renewal_foodsns.common.mapper.Mappable;
import mubex.renewal_foodsns.domain.dto.response.MemberResponse;
import mubex.renewal_foodsns.domain.entity.Member;
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

        httpSession.setAttribute(email, member);

        return mappable.toResponse(member);
    }

    @Override
    public void signOut(String nickName) {
        httpSession.removeAttribute(nickName);
    }
}
