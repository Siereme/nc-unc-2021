# NC-UNC-2021 - Movie Library

## Introduction
This module extends the existing Spring Boot web application by adding a mail sending service.

## Overview
The message sending service is implemented using two technologies:
- Spring Batch framework is used to create and send emails to users. Emails are stored in a thread-safe ArrayBlockingQueue, sending occurs according to the specified schedule.
- Emails are sent via the Kafka streaming platform. Kafka Producer passes emails to Kafka Consumer, which calls the method of sending an email to the user in MailService.

Sending messages occurs in two scenarios:
- After registration, an email with a confirmation link is sent to the user.
- AOP tools intercept method calls to add new entities, then emails are created notifying about new available films, actors, directors or genres, which are transmitted to the Spring Batch or Kafka service for sending to users.