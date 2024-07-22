package mubex.renewal_foodsns.common.config.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Configuration
public class EmitterConfig {

    @Bean
    public Map<String, SseEmitter> emitters() {
        return new ConcurrentHashMap<>();
    }

    @Bean
    public Map<String, Object> eventCaches() {
        return new ConcurrentHashMap<>();
    }
}
