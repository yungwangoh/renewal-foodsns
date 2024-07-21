package mubex.renewal_foodsns.domain.repository;

import mubex.renewal_foodsns.domain.entity.Notification;
import mubex.renewal_foodsns.domain.type.NotificationType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface NotificationRepository {

    Notification save(Notification notification);

    Slice<Notification> findAllByMemberId(Long memberId, Pageable pageable);

    Slice<Notification> findAllByTypeAndMemberId(NotificationType type, Long memberId, Pageable pageable);
}
