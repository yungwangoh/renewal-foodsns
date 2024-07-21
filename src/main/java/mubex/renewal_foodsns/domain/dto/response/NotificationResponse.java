package mubex.renewal_foodsns.domain.dto.response;

import mubex.renewal_foodsns.domain.type.NotificationType;

public record NotificationResponse(
        String text,
        NotificationType type,
        boolean visible
) {
}
