package mubex.renewal_foodsns.domain.entity;

import static org.assertj.core.api.Assertions.*;

import mubex.renewal_foodsns.domain.entity.Member;
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

        Member member = Member.create(
                "name",
                "pwd",
                "email",
                heart,
                1,
                false,
                MemberRank.NORMAL,
                false);

        member.updateMemberRank(MemberRank.convert(heart));

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
                false,
                MemberRank.NORMAL,
                false);

        assertThatThrownBy(() -> member.updateMemberRank(MemberRank.convert(invalidHeart)))
                .isInstanceOf(IllegalArgumentException.class);
    }
}