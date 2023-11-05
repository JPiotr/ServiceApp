package com.oblitus.serviceApp.Quartz.System.Notification;

import com.oblitus.serviceApp.Common.Email.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
abstract class EmailNotification {
    protected final EmailService emailService;
}
