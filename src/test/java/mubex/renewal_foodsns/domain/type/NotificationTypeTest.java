package mubex.renewal_foodsns.domain.type;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(ReplaceUnderscores.class)
class NotificationTypeTest {

    @Test
    void 공지_문자열_변수_값에_값을_넣으면_문자열이_완성된다() {
        String answer = "yun님이 회원님의 게시물에 새로운 댓글이 추가되었습니다.";

        NotificationType comment = NotificationType.COMMENT;

        String generate = comment.generate("yun");

        assertThat(generate).isEqualTo(answer);
    }
}