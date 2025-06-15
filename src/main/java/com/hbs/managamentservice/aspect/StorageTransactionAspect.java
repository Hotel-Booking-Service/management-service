package com.hbs.managamentservice.aspect;

import com.hbs.managamentservice.exception.domain.storage.StorageException;
import com.hbs.managamentservice.service.storage.S3StorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class StorageTransactionAspect {

    private final S3StorageService s3StorageService;

    @SuppressWarnings("java:S112")
    @Around("execution(* com.hbs.managamentservice.service.storage.StorageService.upload(..))")
    public Object handleUploadWithRollbackSupport(ProceedingJoinPoint pjp) throws Throwable {
        Object result = proceedWithExceptionHandling(pjp);

        if (result instanceof String key && TransactionSynchronizationManager.isSynchronizationActive()) {
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
                @Override
                public void afterCompletion(int status) {
                    if (status == STATUS_ROLLED_BACK) {
                        try {
                            s3StorageService.delete(key);
                        } catch (Exception e) {
                            log.error("Failed to delete file from storage during rollback", e);
                        }
                    }
                }
            });
        }

        return result;
    }

    private Object proceedWithExceptionHandling(ProceedingJoinPoint pjp) {
        try {
            return pjp.proceed();
        } catch (Exception e) {
            throw new StorageException("Error during storage upload", e);
        } catch (Throwable t) {
            throw new StorageException("Unexpected error during storage upload", t);
        }
    }
}