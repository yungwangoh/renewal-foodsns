package mubex.renewal_foodsns.application.integration;

import static org.assertj.core.api.Assertions.*;

import jakarta.servlet.http.HttpSession;
import mubex.renewal_foodsns.application.MemberService;
import mubex.renewal_foodsns.application.login.LoginHandler;
import mubex.renewal_foodsns.common.TestContainer;
import mubex.renewal_foodsns.domain.dto.response.MemberResponse;
import mubex.renewal_foodsns.domain.entity.Member;
import mubex.renewal_foodsns.domain.repository.MemberRepository;
import mubex.renewal_foodsns.domain.type.MemberRank;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest
@Sql("/data.sql")
public class MemberServiceTest extends TestContainer {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private LoginHandler loginHandler;

    @Autowired
    private HttpSession httpSession;

    @Test
    void 로그인_유저_세션이_등록_되었다() {
        // given
        String email = "qwer1234@na.com";
        String password = "qwer1234@A";
        String nickName = "name";

        memberService.signUp(email, nickName, password, 1);

        // when
        MemberResponse memberResponse = memberService.signIn(email, password);
        Member sessionMember = (Member) httpSession.getAttribute(email);

        // then
        assertThat(memberResponse).isNotNull();
        assertThat(sessionMember.getNickName()).isEqualTo(nickName);
        assertThat(memberResponse.memberRank()).isEqualTo(MemberRank.NORMAL);
        assertThat(memberResponse.nickName()).isEqualTo(nickName);
    }

    @Test
    void 로그아웃이_되면_세션이_없어진다() {
        // given
        String email = "qwer1234@na.com";
        String password = "qwer1234@A";
        String nickName = "name";

        memberService.signUp(email, nickName, password, 1);
        memberService.signIn(email, password);

        // when
        memberService.signOut(nickName);
        Member member = (Member) httpSession.getAttribute(nickName);

        // then
        assertThat(member).isNull();
    }
}
