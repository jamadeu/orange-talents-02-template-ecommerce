package br.com.zup.mercadolivre.email;

import br.com.zup.mercadolivre.email.dto.SendEmailRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.validation.Valid;

@Service
public class EmailService {

    @Autowired
    private JavaMailSenderImpl emailSender;

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
