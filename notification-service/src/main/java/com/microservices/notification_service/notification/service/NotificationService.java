package com.microservices.notification_service.notification.service;

import com.microservices.notification_service.order.event.OrderPlacedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private final JavaMailSender javaMailSender;
    private static final Logger log = LoggerFactory.getLogger(NotificationService.class);

    public NotificationService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @KafkaListener(topics = "order-placed" , groupId = "notificationService")
    public void listen(OrderPlacedEvent orderPlacedEvent){
        log.info("Got message from order-placed topic {}" , orderPlacedEvent);

        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);

            messageHelper.setFrom("suyeshazope@gmail.com");
            messageHelper.setTo(orderPlacedEvent.getEmail().toString());
            messageHelper.setSubject(String.format("Your Order with OrderNumber %s is placed successfully" , orderPlacedEvent.getOrderNumber()));
            messageHelper.setText(String.format("""
                    Hi 
                    
                    Your order with order number %s is now placed successfully.
                    
                    Best Regards,
                    Spring Shop
                    """ , orderPlacedEvent.getOrderNumber()));
        } ;

        try{
            javaMailSender.send(messagePreparator);
            log.info("Order Notification email sent!!");
        }catch (MailException e){
            log.error("Exception occurred when sending mail" , e);
            throw new RuntimeException("Exception occurred when sending mail : runtime exception " , e);
        }
    }
}
