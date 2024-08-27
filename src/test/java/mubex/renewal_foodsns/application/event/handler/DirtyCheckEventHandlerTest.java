package mubex.renewal_foodsns.application.event.handler;

import static org.assertj.core.api.Assertions.assertThat;

import mubex.renewal_foodsns.application.event.dirtycheck.DeleteDirtyCheck;
import mubex.renewal_foodsns.application.event.dirtycheck.InsertDirtyCheck;
import mubex.renewal_foodsns.application.event.dirtycheck.UpdateDirtyCheck;
import mubex.renewal_foodsns.common.config.cache.CacheConfig;
import mubex.renewal_foodsns.domain.dto.response.PostResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.context.ApplicationEventPublisher;

@SpringBootTest(classes = {
        DirtyCheckEventHandler.class,
        CacheConfig.class,
        ApplicationEventPublisher.class,
        CacheManager.class,
})
@Disabled
class DirtyCheckEventHandlerTest {

    @Autowired
    private DirtyCheckEventHandler dirtyCheckEventHandler;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    private CacheManager cacheManager;

    @BeforeEach
    void setUp() {
        cacheManager.getCache("post_dirty_check").clear();
    }

    @Test
    void dirtyCheckInsertCommand_캐시에_데이터를_추가한다() {
        // given
        PostResponse postResponse = PostResponse.builder()
                .id(1L)
                .title("Test Title")
                .text("Test Content")
                .build();

        InsertDirtyCheck insertDirtyCheck = new InsertDirtyCheck(postResponse);

        // when
        eventPublisher.publishEvent(insertDirtyCheck);

        // then
        PostResponse cachedResponse = cacheManager.getCache("post_dirty_check").get(1L, PostResponse.class);
        assertThat(cachedResponse).isNotNull();
        assertThat(cachedResponse.id()).isEqualTo(1L);
        assertThat(cachedResponse.title()).isEqualTo("Test Title");
        assertThat(cachedResponse.text()).isEqualTo("Test Content");
    }

    @Test
    void dirtyCheckUpdateCommand_캐시의_데이터를_업데이트한다() {
        // given
        PostResponse oldPostResponse = PostResponse.builder()
                .id(1L)
                .title("Old Title")
                .text("Old Content")
                .build();

        cacheManager.getCache("post_dirty_check").put(1L, oldPostResponse);

        PostResponse newPostResponse = PostResponse.builder()
                .id(1L)
                .title("New Title")
                .text("New Content")
                .build();

        UpdateDirtyCheck updateDirtyCheck = new UpdateDirtyCheck(newPostResponse);

        // when
        eventPublisher.publishEvent(updateDirtyCheck);

        // then
        PostResponse cachedResponse = cacheManager.getCache("post_dirty_check").get(1L, PostResponse.class);
        assertThat(cachedResponse).isNotNull();
        assertThat(cachedResponse.id()).isEqualTo(1L);
        assertThat(cachedResponse.title()).isEqualTo("New Title");
        assertThat(cachedResponse.text()).isEqualTo("New Content");
    }

    @Test
    void dirtyCheckDeleteCommand_캐시에서_데이터를_삭제한다() {
        // given
        PostResponse postResponse = PostResponse.builder()
                .id(1L)
                .title("Test Title")
                .text("Test Content")
                .build();
        cacheManager.getCache("post_dirty_check").put(1L, postResponse);

        DeleteDirtyCheck deleteDirtyCheck = new DeleteDirtyCheck(postResponse);

        // when
        eventPublisher.publishEvent(deleteDirtyCheck);

        // then
        PostResponse cachedResponse = cacheManager.getCache("post_dirty_check").get(1L, PostResponse.class);
        assertThat(cachedResponse).isNull();
    }
}