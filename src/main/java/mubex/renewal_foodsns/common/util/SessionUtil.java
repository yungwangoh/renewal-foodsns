package mubex.renewal_foodsns.common.util;

import lombok.Getter;

@Getter
public enum SessionUtil {

    SESSION_ID("session_id"),
    ;

    private final String value;

    SessionUtil(final String value) {
        this.value = value;
    }
}
