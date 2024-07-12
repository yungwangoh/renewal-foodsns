package mubex.renewal_foodsns.application;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import mubex.renewal_foodsns.application.login.LoginHandler;
import mubex.renewal_foodsns.domain.repository.MemberRepository;
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

        // when

        // then

    }
}