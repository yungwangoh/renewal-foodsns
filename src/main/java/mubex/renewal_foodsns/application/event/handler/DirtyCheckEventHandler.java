package mubex.renewal_foodsns.application.event.handler;

import mubex.renewal_foodsns.application.event.dirtycheck.DeleteDirtyCheck;
import mubex.renewal_foodsns.application.event.dirtycheck.InsertDirtyCheck;
import mubex.renewal_foodsns.application.event.dirtycheck.UpdateDirtyCheck;
import mubex.renewal_foodsns.domain.dto.response.PostResponse;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class DirtyCheckEventHandler {

    @TransactionalEventListener
    @Cacheable(
            cacheNames = "post_dirty_check",
            key = "#insertDirtyCheck.postResponse().id()"
    )
    public PostResponse dirtyCheckInsertCommand(final InsertDirtyCheck insertDirtyCheck) {
        return insertDirtyCheck.postResponse();
    }

    @TransactionalEventListener
    @CachePut(
            cacheNames = "post_dirty_check",
            key = "#updateDirtyCheck.postResponse().id()"
    )
    public PostResponse dirtyCheckUpdateCommand(final UpdateDirtyCheck updateDirtyCheck) {
        return updateDirtyCheck.postResponse();
    }

    @TransactionalEventListener
    @CacheEvict(
            cacheNames = "post_dirty_check",
            key = "#deleteDirtyCheck.postResponse().id()",
            beforeInvocation = true
    )
    public PostResponse dirtyCheckDeleteCommand(final DeleteDirtyCheck deleteDirtyCheck) {
        return deleteDirtyCheck.postResponse();
    }
}
