package com.app.service.mail.kafka;

import com.app.model.emailInfo.NewEmail;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.time.Duration;
import java.util.concurrent.ConcurrentLinkedQueue;

@Service
@EnableScheduling
public class KafkaProducer {

    private static final Duration d = java.time.Duration.parse("PT1M");

    private ConcurrentLinkedQueue<NewEmail> concurrentLinkedQueue = new ConcurrentLinkedQueue<>();

    public void addEmailToQueue(NewEmail newEmail) {
        concurrentLinkedQueue.add(newEmail);
    }

    private static final Logger logger = Logger.getLogger(KafkaProducer.class);
    @Value(value = "${spring.kafka.topic-name}")
    private String topicName;

    @Autowired
    private KafkaTemplate<String, NewEmail> kafkaTemplate;

    @Scheduled(initialDelay = 60000, fixedRate = 60000)
    public void sendMessage() {

        if (concurrentLinkedQueue.size() != 0) {
            NewEmail email = concurrentLinkedQueue.remove();

            ListenableFuture<SendResult<String, NewEmail>> future = kafkaTemplate.send(topicName, email);

            future.addCallback(new ListenableFutureCallback<SendResult<String, NewEmail>>() {

                @Override
                public void onSuccess(SendResult<String, NewEmail> result) {
                    logger.info(
                            "Sent message=[" + email + "] with offset=[" + result.getRecordMetadata().offset() + "]");
                }

                @Override
                public void onFailure(Throwable ex) {
                    logger.warn("Unable to send message=[" + email + "] due to : " + ex.getMessage());
                }
            });
        }

    }

}
