package mubex.renewal_foodsns.domain.repository;

import java.util.Map;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface EmitterRepository {

    SseEmitter save(String emitterId, SseEmitter emitter);

    void save(String eventId, Object event);

    Map<String, SseEmitter> findAllEmitterStartWithByMemberId(Long memberId);

    Map<String, Object> findAllEventCacheStartWithByMemberId(Long memberId);

    void deleteById(String id);

    void deleteAllEmitterStartWithId(Long memberId);

    void deleteAllEventCacheStartWithId(Long memberId);
}
