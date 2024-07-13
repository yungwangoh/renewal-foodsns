package mubex.renewal_foodsns.application.unit;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import mubex.renewal_foodsns.application.MemberService;
import mubex.renewal_foodsns.application.login.LoginHandler;
import mubex.renewal_foodsns.domain.dto.response.MemberResponse;
import mubex.renewal_foodsns.domain.repository.MemberRepository;
import mubex.renewal_foodsns.domain.type.MemberRank;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(ReplaceUnderscores.class)
class MemberServiceTest {

    @InjectMocks
    private MemberService memberService;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private LoginHandler loginHandler;

    @Test
    void 로그인() {
        // given
        String email = "qwer1234@na.com";
        String password = "qwer1234@A";

        MemberResponse mr = MemberResponse.builder()
                .profileId(1)
                .heart(1)
                .inBlackList(false)
                .report(1)
                .memberRank(MemberRank.NORMAL)
                .nickName("name")
                .build();

        given(loginHandler.signIn(anyString(), anyString())).willReturn(mr);

        // when
        MemberResponse memberResponse = memberService.signIn(email, password);

        // then
        assertThat(memberResponse).isNotNull();
        assertThat(memberResponse.nickName()).isEqualTo("name");
        assertThat(memberResponse.memberRank()).isEqualTo(MemberRank.NORMAL);
        assertThat(memberResponse.inBlackList()).isEqualTo(false);
    }
}