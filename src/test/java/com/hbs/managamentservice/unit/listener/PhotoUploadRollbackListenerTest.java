package com.hbs.managamentservice.unit.listener;

import com.hbs.managamentservice.listener.PhotoUploadRollbackListener;
import com.hbs.managamentservice.listener.event.PhotoUploadedEvent;
import com.hbs.managamentservice.service.storage.StorageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
class PhotoUploadRollbackListenerTest {

    @Mock
    private StorageService storageService;

    private PhotoUploadRollbackListener photoUploadRollbackListener;

    @BeforeEach
    void setUp() {
        photoUploadRollbackListener = new PhotoUploadRollbackListener(storageService);
    }

    @Test
    void handlePhotoUploadRollback() {
        String s3Key = "dima/sin/shluhi.png";
        PhotoUploadedEvent photoUploadedEvent = new PhotoUploadedEvent(s3Key);

        photoUploadRollbackListener.onUploadRollback(photoUploadedEvent);

        verify(storageService).delete(s3Key);
        verifyNoMoreInteractions(storageService);
    }
}
