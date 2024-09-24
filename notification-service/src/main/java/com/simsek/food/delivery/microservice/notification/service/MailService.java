package com.simsek.food.delivery.microservice.notification.service;

import com.simsek.food.delivery.microservice.order.event.OrderEvent;
import com.simsek.food.delivery.microservice.notification.model.Mail;
import com.simsek.food.delivery.microservice.notification.model.enums.OrderStatus;
import com.simsek.food.delivery.microservice.notification.repository.MailRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.MimeMessagePreparator;

import java.util.Date;

@Service
@RequiredArgsConstructor
@Slf4j
public class MailService {
    private final MailRepository mailRepository;
    private final JavaMailSender javaMailSender;


    private void processSendMailToUser(OrderEvent orderEvent)
    {
        String content = getMailContentForOrderEvent(orderEvent);
        String header = getMailHeaderForOrderEvent(orderEvent);

        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("springshop@email.com");
            messageHelper.setTo(orderEvent.getEmail());
            messageHelper.setSubject(header);
            messageHelper.setText(content);
        };

        try {
            javaMailSender.send(messagePreparator);
            log.info("Order Notification email sent!!");

            Mail mail = new Mail();
            mail.setSender("springshop@email.com");
            mail.setReceiver(orderEvent.getEmail());
            mail.setHeader(header);
            mail.setContent(content);
            mail.setCreationDate(new Date());

            mailRepository.save(mail);

        } catch (MailException e) {
            log.error("Exception occurred when sending mail", e);
            throw new RuntimeException("Exception occurred when sending mail to springshop@email.com", e);
        }
    }

    private String getMailHeaderForOrderEvent(OrderEvent orderEvent)
    {
        String mailHeader;
        if(OrderStatus.valueOf(orderEvent.getStatus()).equals(OrderStatus.PAYMENT_COMPLETED))
        {
            mailHeader = String.format("Your Order with OrderNumber %s is placed successfully", orderEvent.getOrderNumber());
        }
        else if(OrderStatus.valueOf(orderEvent.getStatus()).equals(OrderStatus.ORDER_FAILED))
        {
            mailHeader = String.format("Your order with order number %s has currently failed.", orderEvent.getOrderNumber());
        }
        else
        {
            mailHeader = String.format("Your order with order number %s is now successfully delivered.", orderEvent.getOrderNumber());
        }
        return mailHeader;
    }

    private String getMailContentForOrderEvent(OrderEvent orderEvent)
    {
     String mailContent;
     if(OrderStatus.valueOf(orderEvent.getStatus()).equals(OrderStatus.PAYMENT_COMPLETED))
     {
         mailContent = String.format("""
                            Hi customer,

                            Your order with order number %s is now placed successfully.
                            
                            Price:%s
                            Quantity:%s
                            Date: %s
                            
                            
                            Best Regards
                            Spring Shop
                            
                            
                            """,
                 orderEvent.getOrderNumber(),
                 orderEvent.getPrice().toString(),
                 orderEvent.getQuantity(),
                 orderEvent.getCreationDate());
     }
     else if(OrderStatus.valueOf(orderEvent.getStatus()).equals(OrderStatus.ORDER_FAILED))
     {
         mailContent = String.format("""
                            Hi customer,

                            Your order with order number %s has currently failed.
                            We refund your payment.
                            
                            Price:%s
                            Quantity:%s
                            Date: %s
                            
                            
                            Best Regards
                            Spring Shop
                            
                            
                            """,
                 orderEvent.getOrderNumber(),
                 orderEvent.getPrice().toString(),
                 orderEvent.getQuantity(),
                 orderEvent.getCreationDate());
     }
     else
     {
         mailContent = String.format("""
                            Hi customer,

                            Your order with order number %s is now successfully delivered.
                            Please send us your ideas about the restaurant.
                            
                            Thank you for choosing us.
                            
                            Price:%s
                            Quantity:%s
                            Date: %s
                            
                            
                            Best Regards
                            Spring Shop
                            
                            
                            """,
                 orderEvent.getOrderNumber(),
                 orderEvent.getPrice().toString(),
                 orderEvent.getQuantity(),
                 orderEvent.getCreationDate());
     }
     return mailContent;
    }

    @KafkaListener(topics = {"payment-completed","order-failed","order-completed"})
    public void consumeOrderEvent(OrderEvent orderEvent)
    {
        log.info("Kafka event taken from listener. orderNumber:{}",
                orderEvent.getOrderNumber()
                );

        processSendMailToUser(orderEvent);
    }
}
