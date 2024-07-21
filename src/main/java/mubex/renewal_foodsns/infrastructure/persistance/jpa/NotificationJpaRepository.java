package mubex.renewal_foodsns.infrastructure.persistance.jpa;

import mubex.renewal_foodsns.domain.entity.Notification;
import mubex.renewal_foodsns.domain.type.NotificationType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationJpaRepository extends JpaRepository<Notification, Long> {

    Slice<Notification> findAllByMemberId(Long memberId, Pageable pageable);

    Slice<Notification> findAllByTypeAndMemberId(NotificationType type, Long memberId, Pageable pageable);
}