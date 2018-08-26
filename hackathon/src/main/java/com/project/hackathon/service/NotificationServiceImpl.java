package com.project.hackathon.service;

import com.project.hackathon.dto.ProgressDto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class NotificationServiceImpl implements NotificationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationServiceImpl.class);

    private final SimpMessagingTemplate simpMessagingTemplate;

    @Value("${websocket.notification.address}")
    private String notificationUrl;

    @Autowired
    public NotificationServiceImpl(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @Override
    public void notifyToAllSession(ProgressDto notification) {
        LOGGER.info("Sending notification:{}",notification);
        notification.setTime(new Date());
        simpMessagingTemplate.convertAndSend(notificationUrl,notification);
        LOGGER.info("Notification Sent");
    }
}
