package com.hbs.managamentservice.listener;

import com.hbs.managamentservice.listener.event.PhotoUploadedEvent;
import com.hbs.managamentservice.service.storage.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class PhotoUploadRollbackListener {

    private final StorageService storageService;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_ROLLBACK)
    public void onUploadRollback(PhotoUploadedEvent event) {
        storageService.delete(event.fileKey());
    }
}
