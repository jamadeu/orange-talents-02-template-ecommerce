package br.com.zup.mercadolivre.email;

import br.com.zup.mercadolivre.email.dto.SendEmailRequest;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.validation.Valid;

@Service
public class EmailService {

    final JavaMailSenderImpl emailSender;

    public EmailService(JavaMailSenderImpl emailSender) {
        this.emailSender = emailSender;
    }

    public void sendEmail(@Valid SendEmailRequest request) {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message);
        try {
            mimeMessageHelper.setTo(request.getRecipient());
            mimeMessageHelper.setFrom(request.getFrom());
            mimeMessageHelper.setSubject(request.getSubject());
            mimeMessageHelper.setText(request.getMessage());
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        emailSender.send(message);
    }

}
