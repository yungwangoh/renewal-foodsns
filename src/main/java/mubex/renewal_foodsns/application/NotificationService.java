package mubex.renewal_foodsns.application;

import java.io.IOException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import mubex.renewal_foodsns.domain.entity.Member;
import mubex.renewal_foodsns.domain.entity.Notification;
import mubex.renewal_foodsns.domain.mapper.map.NotificationMapper;
import mubex.renewal_foodsns.domain.repository.EmitterRepository;
import mubex.renewal_foodsns.domain.repository.NotificationRepository;
import mubex.renewal_foodsns.domain.type.NotificationType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final EmitterRepository emitterRepository;

    private static final Long DEFAULT_TIMEOUT = 60L * 1000L * 60L;

    public void subscribe(final Long memberId) {

        String convertMemberId = convertUUID(memberId);

        SseEmitter emitter = emitterRepository.save(convertMemberId, new SseEmitter(DEFAULT_TIMEOUT));

        emitter.onCompletion(() -> emitterRepository.deleteById(convertMemberId));

        emitter.onTimeout(() -> emitterRepository.deleteById(convertMemberId));

        String dummyMemberId = convertUUID(memberId);

        sendNotification(emitter, dummyMemberId, convertMemberId, "connection complete.");
    }

    public void send(final Member receiver, final NotificationType type, final String uri) {

        Notification notification = Notification.create(type.getText(), type, uri, false, receiver);

        Notification save = notificationRepository.save(notification);

        String convertMemberId = convertUUID(receiver.getId());

        emitterRepository.findAllEmitterStartWithByMemberId(receiver.getId())
                .forEach((key, emitter) -> {
                            emitterRepository.save(key, save);
                            sendNotification(emitter, convertMemberId, key, NotificationMapper.INSTANCE.toResponse(save));
                        }
                );
    }

    private void sendNotification(SseEmitter emitter, String eventId, String emitterId, Object data) {
        try {
            emitter.send(SseEmitter.event()
                    .id(eventId)
                    .name("sse")
                    .data(data)
            );
        } catch (IOException e) {
            emitterRepository.deleteById(emitterId);
            throw new RuntimeException(e);
        }
    }

    private String convertUUID(final Long memberId) {
        return memberId + " " + UUID.randomUUID();
    }
}
