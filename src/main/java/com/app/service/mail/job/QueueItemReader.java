package com.app.service.mail.job;

import org.springframework.batch.item.ItemReader;

import java.util.concurrent.BlockingQueue;

public class QueueItemReader<T> implements ItemReader<T> {

    BlockingQueue<T> queue;

    public QueueItemReader(BlockingQueue<T> queue) {
        this.queue = queue;
    }

    @Override
    public T read() throws Exception {
        if (queue.peek() != null) {
            return queue.take();
        }
        return null;
    }
}
