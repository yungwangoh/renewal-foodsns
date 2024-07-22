package mubex.renewal_foodsns.infrastructure.persistance;

import java.util.Map;
import java.util.stream.Collectors;
import mubex.renewal_foodsns.domain.repository.EmitterRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Repository
public class EmitterRepositoryImpl implements EmitterRepository {

    private final Map<String, SseEmitter> emitters;
    private final Map<String, Object> eventCaches;

    public EmitterRepositoryImpl(@Qualifier("emitters") Map<String, SseEmitter> emitters,
                                 @Qualifier("eventCaches") Map<String, Object> eventCaches) {

        this.emitters = emitters;
        this.eventCaches = eventCaches;
    }

    @Override
    public SseEmitter save(final String emitterId, final SseEmitter emitter) {
        emitters.put(emitterId, emitter);
        return emitter;
    }

    @Override
    public void save(final String eventId, final Object event) {
        eventCaches.put(eventId, event);
    }

    @Override
    public Map<String, SseEmitter> findAllEmitterStartWithByMemberId(final Long memberId) {
        return emitters.entrySet().stream()
                .filter(entry -> entry.getKey().startsWith(String.valueOf(memberId)))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @Override
    public Map<String, Object> findAllEventCacheStartWithByMemberId(final Long memberId) {
        return emitters.entrySet().stream()
                .filter(entry -> entry.getKey().startsWith(String.valueOf(memberId)))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @Override
    public void deleteById(final String id) {
        emitters.remove(id);
    }

    @Override
    public void deleteAllEmitterStartWithId(final Long memberId) {
        emitters.forEach(
                (key, emitter) -> {
                    if (key.startsWith(String.valueOf(memberId))) {
                        emitters.remove(key);
                    }
                }
        );
    }

    @Override
    public void deleteAllEventCacheStartWithId(final Long memberId) {
        eventCaches.forEach(
                (key, emitter) -> {
                    if (key.startsWith(String.valueOf(memberId))) {
                        eventCaches.remove(key);
                    }
                }
        );
    }
}
