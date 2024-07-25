package mubex.renewal_foodsns.application.repository;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface EmitterRepository {

    SseEmitter save(Long memberId, SseEmitter emitter);

    SseEmitter get(Long memberId);

    void remove(Long memberId);
}
