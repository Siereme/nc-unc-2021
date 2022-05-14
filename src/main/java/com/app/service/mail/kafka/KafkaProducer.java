package com.app.service.mail.kafka;

import com.app.model.emailInfo.NewEmail;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.concurrent.ArrayBlockingQueue;

import static com.app.ConstantVariables.INT_EMAIL_QUEUE_CAPACITY;

@Service
public class KafkaProducer {

    private static final Logger logger = Logger.getLogger(KafkaProducer.class);
    @Value(value = "${spring.kafka.topic-name}")
    private String topicName;

    @Autowired
    private KafkaTemplate<String, NewEmail> kafkaTemplate;

    private final ArrayBlockingQueue<NewEmail> queue = new ArrayBlockingQueue<NewEmail>(INT_EMAIL_QUEUE_CAPACITY);

    @Scheduled(cron = "0 * * * * *")
    public void sendMessage() throws InterruptedException {

        if (queue.size() != 0) {
            NewEmail email = queue.take();
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

    public void addEmail(NewEmail email) {
        queue.add(email);
    }
}
