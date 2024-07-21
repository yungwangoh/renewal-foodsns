package mubex.renewal_foodsns.infrastructure.persistance;

import lombok.RequiredArgsConstructor;
import mubex.renewal_foodsns.domain.entity.Notification;
import mubex.renewal_foodsns.domain.repository.NotificationRepository;
import mubex.renewal_foodsns.domain.type.NotificationType;
import mubex.renewal_foodsns.infrastructure.persistance.jpa.NotificationJpaRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class NotificationRepositoryImpl implements NotificationRepository {

    private final NotificationJpaRepository notificationJpaRepository;

    @Override
    public Notification save(Notification notification) {
        return notificationJpaRepository.save(notification);
    }

    @Override
    public Slice<Notification> findAllByMemberId(Long memberId, Pageable pageable) {
        return notificationJpaRepository.findAllByMemberId(memberId, pageable);
    }

    @Override
    public Slice<Notification> findAllByTypeAndMemberId(NotificationType type, Long memberId, Pageable pageable) {
        return notificationJpaRepository.findAllByTypeAndMemberId(type, memberId, pageable);
    }
}
