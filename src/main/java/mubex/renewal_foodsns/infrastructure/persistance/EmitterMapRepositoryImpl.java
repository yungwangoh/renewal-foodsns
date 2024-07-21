package mubex.renewal_foodsns.infrastructure.persistance;

import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import mubex.renewal_foodsns.domain.repository.EmitterRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Repository
@RequiredArgsConstructor
public class EmitterMapRepositoryImpl implements EmitterRepository {

    private final Map<String, SseEmitter> emitterMap;
    private final Map<String, Object> eventMap;

    @Override
    public SseEmitter save(final String emitterId, final SseEmitter emitter) {
        emitterMap.put(emitterId, emitter);
        return emitter;
    }

    @Override
    public void save(final String eventId, final Object event) {
        eventMap.put(eventId, event);
    }

    @Override
    public Map<String, SseEmitter> findAllEmitterStartWithByMemberId(final Long memberId) {
        return emitterMap.entrySet().stream()
                .filter(entry -> entry.getKey().startsWith(String.valueOf(memberId)))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @Override
    public Map<String, Object> findAllEventCacheStartWithByMemberId(final Long memberId) {
        return emitterMap.entrySet().stream()
                .filter(entry -> entry.getKey().startsWith(String.valueOf(memberId)))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @Override
    public void deleteById(final String id) {
        emitterMap.remove(id);
    }

    @Override
    public void deleteAllEmitterStartWithId(final Long memberId) {
        emitterMap.forEach(
                (key, emitter) -> {
                    if (key.startsWith(String.valueOf(memberId))) {
                        emitterMap.remove(key);
                    }
                }
        );
    }

    @Override
    public void deleteAllEventCacheStartWithId(final Long memberId) {
        eventMap.forEach(
                (key, emitter) -> {
                    if (key.startsWith(String.valueOf(memberId))) {
                        eventMap.remove(key);
                    }
                }
        );
    }
}
