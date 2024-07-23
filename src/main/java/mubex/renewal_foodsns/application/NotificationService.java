package mubex.renewal_foodsns.application;

import java.io.IOException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

        SseEmitter emitter = new SseEmitter(DEFAULT_TIMEOUT);

        SseEventBuilder data = SseEmitter.event()
                .id(uuid(memberId))
                .name(SSE_NAME)
                .data("connection!");

        SseEmitter save = emitterRepository.save(memberId, emitter);

        emitter.onCompletion(() -> emitterRepository.remove(memberId));

        emitter.onTimeout(() -> emitterRepository.remove(memberId));

        send(data, save);

        return save;
    }

    public void sendTo(Member receiver, Member sender, NotificationType type, String uri) {

        Notification notification = Notification.create(type.generate(sender.getNickName()), type, uri, false,
                receiver);

        notificationRepository.save(notification);

        SseEmitter sseEmitter = emitterRepository.get(receiver.getId());

        log.info("sseEmitter: {}", sseEmitter);

        SseEventBuilder data = SseEmitter.event()
                .id(uuid(receiver.getId()))
                .name(SSE_NAME)
                .data(NotificationMapper.INSTANCE.toResponse(notification));

        send(data, sseEmitter);
    }

    public void sendTo(Member receiver, Post post) {
        receiver.levelUp(post.getHeart());

        SseEmitter sseEmitter = emitterRepository.get(receiver.getId());

        SseEventBuilder data = SseEmitter.event()
                .id(uuid(receiver.getId()))
                .name(SSE_NAME)
                .data(MemberMapper.INSTANCE.toResponse(receiver));

        send(data, sseEmitter);
    }

    private void send(SseEventBuilder builder, SseEmitter sseEmitter) {

        try {
            sseEmitter.send(builder);
        } catch (IOException e) {
            sseEmitter.complete();
        }
    }

    private String uuid(Long id) {
        return id + "_" + UUID.randomUUID();
    }
}
