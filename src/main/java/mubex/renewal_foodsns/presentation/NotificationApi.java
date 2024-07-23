package mubex.renewal_foodsns.presentation;

import lombok.RequiredArgsConstructor;
import mubex.renewal_foodsns.application.NotificationService;
import mubex.renewal_foodsns.common.annotation.Login;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/notification")
public class NotificationApi {

    private final NotificationService notificationService;

    @GetMapping
    public ResponseEntity<SseEmitter> notification(@Login final Long memberId) {
        
        final SseEmitter sseEmitter = notificationService.subscribe(memberId);

        return ResponseEntity.ok(sseEmitter);
    }
}
