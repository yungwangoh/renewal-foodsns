package mubex.renewal_foodsns.common.call;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SingleTypeAsyncCall<T> {

    private final List<T> lists;

    public SingleTypeAsyncCall(List<T> lists) {
        this.lists = lists;
    }

    public void execute(int nThread, Consumer<T> consumer) {
        try (ExecutorService executorService = Executors.newFixedThreadPool(nThread)) {
            List<CompletableFuture<Void>> futures = lists.stream()
                    .map(item -> CompletableFuture.runAsync(() -> consumer.accept(item), executorService))
                    .toList();

            futures.forEach(CompletableFuture::join);
        } catch (Exception e) {
            log.error("call error = {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
