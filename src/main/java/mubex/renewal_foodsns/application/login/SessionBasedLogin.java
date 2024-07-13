package mubex.renewal_foodsns.application.login;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import mubex.renewal_foodsns.common.mapper.MemberMapper;
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

    @Override
    public MemberResponse signIn(String email, String password) {
        Member member = memberRepository.findByEmailAndPassword(email, password);

        httpSession.setAttribute(member.getNickName(), member);

        return MemberMapper.INSTANCE.toResponse(member);
    }

    @Override
    public void signOut(String nickName) {
        httpSession.removeAttribute(nickName);
    }
}
