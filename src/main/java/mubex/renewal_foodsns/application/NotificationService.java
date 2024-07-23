package mubex.renewal_foodsns.application;

import java.io.IOException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mubex.renewal_foodsns.domain.dto.response.MemberResponse;
import mubex.renewal_foodsns.domain.dto.response.NotificationResponse;
import mubex.renewal_foodsns.domain.entity.Member;
import mubex.renewal_foodsns.domain.entity.Notification;
import mubex.renewal_foodsns.domain.entity.Post;
import mubex.renewal_foodsns.domain.mapper.map.MemberMapper;
import mubex.renewal_foodsns.domain.mapper.map.NotificationMapper;
import mubex.renewal_foodsns.domain.repository.EmitterRepository;
import mubex.renewal_foodsns.domain.repository.NotificationRepository;
import mubex.renewal_foodsns.domain.type.NotificationType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter.SseEventBuilder;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final EmitterRepository emitterRepository;

    private static final String SSE_NAME = "sse";

    private static final Long DEFAULT_TIMEOUT = 60L * 1000L * 60L;

    public SseEmitter subscribe(final Long memberId) {

        final SseEmitter emitter = new SseEmitter(DEFAULT_TIMEOUT);

        final SseEventBuilder data = SseEmitter.event()
                .id(uuid(memberId))
                .name(SSE_NAME)
                .data("connection!");

        final SseEmitter save = emitterRepository.save(memberId, emitter);

        emitter.onCompletion(() -> emitterRepository.remove(memberId));

        emitter.onTimeout(() -> emitterRepository.remove(memberId));

        send(data, save);

        return save;
    }

    public void sendTo(final Member receiver,
                       final Member sender,
                       final NotificationType type,
                       final String uri) {

        final Notification notification = Notification.create(type.generate(sender.getNickName()), type, uri, false,
                receiver);

        notificationRepository.save(notification);

        final SseEmitter sseEmitter = emitterRepository.get(receiver.getId());

        log.info("sseEmitter: {}", sseEmitter);

        final SseEventBuilder data = SseEmitter.event()
                .id(uuid(receiver.getId()))
                .name(SSE_NAME)
                .data(NotificationMapper.INSTANCE.toResponse(notification));

        send(data, sseEmitter);
    }

    public void sendTo(final Member receiver, final NotificationType type, final Post post) {
        receiver.levelUp(post.getHeart());

        final SseEmitter sseEmitter = emitterRepository.get(receiver.getId());

        final Notification notification = Notification.create(type.getText(), type, null, false, receiver);

        final Notification saveNotification = notificationRepository.save(notification);

        final SseEventBuilder data = SseEmitter.event()
                .id(uuid(receiver.getId()))
                .name(SSE_NAME)
                .data(LevelUpResponse.of(
                        NotificationMapper.INSTANCE.toResponse(saveNotification),
                        MemberMapper.INSTANCE.toResponse(receiver)
                ));

        send(data, sseEmitter);
    }

    private void send(final SseEventBuilder builder, final SseEmitter sseEmitter) {

        try {
            sseEmitter.send(builder);
        } catch (IOException e) {
            sseEmitter.complete();
        }
    }

    private String uuid(final Long id) {
        return id + "_" + UUID.randomUUID();
    }

    private record LevelUpResponse(NotificationResponse notificationResponse, MemberResponse memberResponse) {

        private static LevelUpResponse of(final NotificationResponse notificationResponse,
                                          final MemberResponse memberResponse) {

            return new LevelUpResponse(notificationResponse, memberResponse);
        }
    }
}
