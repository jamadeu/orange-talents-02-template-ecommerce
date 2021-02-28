package br.com.zup.mercadolivre.email.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class SendEmailRequest {

    @NotBlank
    @Email
    @JsonProperty
    final String recipient;
    @NotBlank
    @JsonProperty
    final String from;
    @NotBlank
    @JsonProperty
    final String subject;
    @NotBlank
    @JsonProperty
    final String message;

    @JsonCreator
    public SendEmailRequest(@NotBlank @Email String recipient, @NotBlank String from, @NotBlank String subject, @NotBlank String message) {
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
