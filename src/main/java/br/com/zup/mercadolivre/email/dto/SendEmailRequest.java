package br.com.zup.mercadolivre.email.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class SendEmailRequest {

    @NotEmpty
    @Email
    private String recipient;
    @NotEmpty
    private String from;
    @NotEmpty
    private String subject;
    @NotEmpty
    private String message;

    public SendEmailRequest(@NotEmpty @Email String recipient, @NotEmpty String from, @NotEmpty String subject, @NotEmpty String message) {
        this.recipient = recipient;
        this.from = from;
        this.subject = subject;
        this.message = message;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getFrom() {
        return from;
    }

    public String getSubject() {
        return subject;
    }

    public String getMessage() {
        return message;
    }
}
