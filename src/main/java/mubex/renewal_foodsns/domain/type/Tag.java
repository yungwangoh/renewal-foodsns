package mubex.renewal_foodsns.domain.type;

import lombok.Getter;

@Getter
public enum Tag {
    NOODLE(1), // 면
    SOUP(2), // 국, 찌개
    SNACK(3), // 분식
    ETC(4); // 기타

    private final int value;

    Tag(final int value) {
        this.value = value;
    }
}
