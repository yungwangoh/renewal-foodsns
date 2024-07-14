package mubex.renewal_foodsns.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mubex.renewal_foodsns.domain.type.MemberRank;
import mubex.renewal_foodsns.common.util.PasswordUtil;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(
        name = "member",
        indexes = {
                @Index(name = "nick_name_idx", columnList = "nick_name", unique = true),
                @Index(name = "login_idx", columnList = "email"),
                @Index(name = "member_rank_idx", columnList = "member_rank")
        }
)
public class Member extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nick_name", length = 20, nullable = false)
    private String nickName;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "profile_id", nullable = false)
    private int profileId;

    @Column(name = "heart", nullable = false)
    private long heart;

    @Column(name = "report", nullable = false)
    private int report;

    @Column(name = "in_black_list", nullable = false)
    private boolean inBlackList;

    @Enumerated(EnumType.STRING)
    @Column(name = "member_rank", nullable = false)
    private MemberRank memberRank;

    @Column(name = "in_deleted", nullable = false)
    private boolean inDeleted;

    @Builder
    private Member(String nickName, String password, String email, int profileId, long heart, int report, boolean inBlackList,
                  MemberRank memberRank, boolean inDeleted) {

        this.nickName = nickName;
        this.password = PasswordUtil.encryptPassword(password);
        this.email = email;
        this.profileId = profileId;
        this.heart = heart;
        this.report = report;
        this.inBlackList = inBlackList;
        this.memberRank = memberRank;
        this.inDeleted = inDeleted;
    }

    public static Member create(
            String nickName,
            String password,
            String email,
            int profileId,
            long heart,
            int report,
            boolean inBlackList,
            MemberRank memberRank,
            boolean inDeleted
    ) {
        return Member.builder()
                .nickName(nickName)
                .password(password)
                .email(email)
                .profileId(profileId)
                .heart(heart)
                .report(report)
                .inBlackList(inBlackList)
                .memberRank(memberRank)
                .inDeleted(inDeleted)
                .build();
    }

    public void updateProfileId(int profileId) {
        this.profileId = profileId;
    }

    public void updateNickName(String newNickName) {
        this.nickName = newNickName;
    }

    public void updatePassword(String password) {
        this.password = PasswordUtil.encryptPassword(password);
    }

    public void updateMemberRank(MemberRank memberRank) {
        this.memberRank = memberRank;
    }

    public void markAsDeleted() {
        this.inDeleted = true;
    }

    public void addToBlacklist() {
        this.inBlackList = true;
    }

    public void addHeart(long heart) {
        if(heart < 0) throw new IllegalArgumentException("유효하지 않은 값입니다.");
        this.heart += heart;
    }

    public void addReport(int report) {
        if(report < 0) throw new IllegalArgumentException("유효하지 않은 값입니다.");
        this.report += report;
    }

    public boolean checkMemberBlackList() {
        return !this.inBlackList && this.report >= 10;
    }
}
