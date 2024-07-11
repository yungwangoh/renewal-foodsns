package mubex.renewal_foodsns.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mubex.renewal_foodsns.domain.type.MemberRank;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Member extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nick_name", length = 20, nullable = false)
    private String nickName;

    @Column(name = "password", length = 50, nullable = false)
    private String password;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "heart", nullable = false)
    private long heart;

    @Column(name = "report", nullable = false)
    private int report;

    @Column(name = "is_black_list", nullable = false)
    private boolean isBlackList;

    @Enumerated(EnumType.STRING)
    @Column(name = "member_rank", nullable = false)
    private MemberRank memberRank;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted;

    @Builder
    private Member(String nickName, String password, String email, long heart, int report, boolean isBlackList,
                  MemberRank memberRank, boolean isDeleted) {
        this.nickName = nickName;
        this.password = password;
        this.email = email;
        this.heart = heart;
        this.report = report;
        this.isBlackList = isBlackList;
        this.memberRank = memberRank;
        this.isDeleted = isDeleted;
    }

    public static Member create(
            String nickName,
            String password,
            String email,
            long heart,
            int report,
            boolean isBlackList,
            MemberRank memberRank,
            boolean isDeleted
    ) {
        return Member.builder()
                .nickName(nickName)
                .password(password)
                .email(email)
                .heart(heart)
                .report(report)
                .isBlackList(isBlackList)
                .memberRank(memberRank)
                .isDeleted(isDeleted)
                .build();
    }

    public void updateNickName(String newNickName) {
        this.nickName = newNickName;
    }

    public void updatePassword(String password) {
        this.password = password;
    }

    public void updateMemberRank(MemberRank memberRank) {
        this.memberRank = memberRank;
    }

    public void markAsDeleted() {
        this.isDeleted = true;
    }

    public void addToBlacklist() {
        this.isBlackList = true;
    }

    public void addHeart() {
        this.heart++;
    }

    public void addReport() {
        this.report++;
    }
}
