package mubex.renewal_foodsns.infrastructure.migration.batch.item.reader;

import com.github.benmanes.caffeine.cache.Cache;
import java.util.Iterator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mubex.renewal_foodsns.domain.dto.response.PostResponse;
import org.springframework.batch.item.ItemReader;

@Slf4j
@RequiredArgsConstructor
public class DirtyCheckItemReader implements ItemReader<PostResponse> {

    private final Cache<Object, Object> cache;
    private Iterator<Object> iterator;

    @Override
    public PostResponse read() {

        if (iterator == null) {
            iterator = cache.asMap().keySet().iterator();
        }

        if (iterator.hasNext()) {
            final Object key = iterator.next();

            return (PostResponse) cache.asMap().get(key);
        }

        return null;
    }
}
