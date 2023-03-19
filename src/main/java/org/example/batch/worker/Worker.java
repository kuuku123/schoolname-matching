package org.example.batch.worker;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public class Worker {

    private final ExecutorService executorService;

    public Worker(ExecutorService executorService) {
        this.executorService = executorService;
    }

    public void work(Runnable runnable) {
        executorService.execute(runnable);

    }

    public void finish(){
        executorService.shutdown();

        try {
            executorService.awaitTermination(20, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
