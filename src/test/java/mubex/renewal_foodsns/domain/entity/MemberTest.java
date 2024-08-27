package mubex.renewal_foodsns.domain.entity;

import static org.assertj.core.api.Assertions.assertThat;

import mubex.renewal_foodsns.domain.type.MemberRank;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(ReplaceUnderscores.class)
class MemberTest {

    @Test
    void 유저의_프로필_이미지를_변경한다() {
        // given
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

        // when
        member.updateProfileImage("update");

        // then
        assertThat(member.getProfileImage()).isEqualTo("update");
    }

    @Test
    void 유저의_랭크가_변한다() {
        // given
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

        // when
        member.updateMemberRank(MemberRank.INFLUENCE);

        // then
        assertThat(member.getMemberRank()).isEqualTo(MemberRank.INFLUENCE);
    }
}