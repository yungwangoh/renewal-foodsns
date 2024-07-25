package mubex.renewal_foodsns.infrastructure.persistance;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mubex.renewal_foodsns.application.repository.EmitterRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Repository
@RequiredArgsConstructor
@Slf4j
public class EmitterRepositoryImpl implements EmitterRepository {

    private final Map<Long, SseEmitter> emitters;

    @Override
    public SseEmitter save(final Long memberId, final SseEmitter emitter) {
        log.info("Saving emitter {} {}", memberId, emitter);
        emitters.put(memberId, emitter);
        return emitter;
    }

    @Override
    public SseEmitter get(final Long memberId) {
        return emitters.get(memberId);
    }

    @Override
    public void remove(final Long memberId) {
        emitters.remove(memberId);
    }
}
