package mubex.renewal_foodsns.application.unit;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.BDDMockito.anyString;
import static org.mockito.BDDMockito.given;

import mubex.renewal_foodsns.application.MemberService;
import mubex.renewal_foodsns.application.login.LoginHandler;
import mubex.renewal_foodsns.application.repository.MemberRepository;
import mubex.renewal_foodsns.domain.entity.Member;
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
    void 유저가_회원_가입을_하는데_이미_존재하는_경우_예외_발생() {

        given(memberRepository.existsByNickName(anyString())).willReturn(true);

        assertThatThrownBy(() -> memberService.signUp(
                "qwer1234@na.com",
                "name",
                "qwer1234",
                null)
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 유저가_이미_탈퇴_하였는데_또_다시_탈퇴하는_경우_예외_발생() {
        Member member = Member.create(
                "name",
                "pwd",
                "email",
                "",
                1,
                1,
                false,
                MemberRank.NORMAL,
                true);

        given(memberRepository.findByNickName(anyString()))
                .willReturn(member);

        assertThatThrownBy(() -> memberService.markAsDeleted("name"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 유저의_블랙_리스트_기준이_넘지않는다_이미_블랙리스트로_등록되어있는_경우() {
        Member member = Member.create(
                "name",
                "pwd",
                "email",
                "",
                1,
                1,
                true,
                MemberRank.NORMAL,
                true);

        given(memberRepository.findByNickName(anyString()))
                .willReturn(member);

        assertThatThrownBy(() -> memberService.addToBlacklist("name"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 유저의_블랙_리스트_기준이_넘지않는다_신고_수가_기준에_달성_하지_못한다() {
        Member member = Member.create(
                "name",
                "pwd",
                "email",
                "",
                1,
                1,
                false,
                MemberRank.NORMAL,
                false);

        given(memberRepository.findByNickName(anyString()))
                .willReturn(member);

        boolean check = memberService.addToBlacklist("name");

        assertFalse(check);
    }

    @Test
    void 유저_닉네임_변경_닉네임이_이미_존재하는_경우_예외_발생() {
        Member member = Member.create(
                "name",
                "pwd",
                "email",
                "",
                1,
                1,
                true,
                MemberRank.NORMAL,
                false);

        given(memberRepository.findByNickName(anyString())).willReturn(member);
        given(memberRepository.existsByNickName(anyString())).willReturn(true);

        assertThatThrownBy(() -> memberService.updateNickName("name", "updateName"))
                .isInstanceOf(IllegalArgumentException.class);
    }
}