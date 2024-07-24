package mubex.renewal_foodsns.domain.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertTrue;

import mubex.renewal_foodsns.common.util.PasswordUtil;
import mubex.renewal_foodsns.domain.type.MemberRank;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayNameGeneration(ReplaceUnderscores.class)
class MemberTest {

    @ParameterizedTest
    @ValueSource(longs = {150, 250, 250})
    void 유저의_좋아요의_개수에_따라_등급이_변한다(long heart) {
        // given
        Member member = Member.create(
                "name",
                "pwd",
                "email",
                1,
                heart,
                1,
                false,
                MemberRank.NORMAL,
                false);

        // when
        member.updateMemberRank(MemberRank.convert(heart));

        // then
        assertThat(member.getMemberRank()).isEqualTo(MemberRank.convert(heart));
    }

    @Test
    void 유저의_좋아요_개수가_올바른_입력이_아닐_경우() {

        long invalidHeart = -1;

        Member member = Member.create(
                "name",
                "pwd",
                "email",
                0,
                1,
                1,
                false,
                MemberRank.NORMAL,
                false);

        assertThatThrownBy(() -> member.updateMemberRank(MemberRank.convert(invalidHeart)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 유저_비밀번호_암호화_() {
        // given
        String password = "qwer1234@A";
        Member member = Member.create(
                "name",
                password,
                "qwer1234@na.com",
                1,
                0,
                1,
                false,
                MemberRank.NORMAL,
                false);

        // when
        String encryptedPassword = member.getPassword();

        // then
        assertTrue(PasswordUtil.match(encryptedPassword, password));
    }

    @Test
    void 유저의_등급이_바뀌면_true를_반환한다() {
        // given
        Member member = Member.create(
                "test",
                "test",
                "test",
                1,
                10,
                0,
                false,
                MemberRank.NORMAL,
                false);

        // when
        boolean levelUp = member.levelUp(1);

        // then
        assertTrue(levelUp);
        assertThat(member.getMemberRank()).isEqualTo(MemberRank.MIDDLE);
    }
}