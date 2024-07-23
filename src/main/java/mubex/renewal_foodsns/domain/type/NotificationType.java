package mubex.renewal_foodsns.domain.type;

import lombok.Getter;

@Getter
public enum NotificationType {
    COMMENT("%s님이 회원님의 게시물에 댓글을 추가하였습니다."),
    MEMBER_RANK("레벨 업~~"),
    BLACK_LIST("회원님이 블랙리스트로 선정되었습니다. 이제부터 서비스 이용이 불가합니다."),
    TAG(""),
    ;

    private final String text;

    NotificationType(String text) {
        this.text = text;
    }

    public String generate(String nickName) {
        return String.format(text, nickName);
    }
}
