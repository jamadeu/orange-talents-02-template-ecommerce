package br.com.zup.mercadolivre.user.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class AuthenticationRequest {
    @NotBlank
    @Email
    @JsonProperty
    final String email;
    @NotBlank
    @JsonProperty
    final String password;

    @JsonCreator
    public AuthenticationRequest(@NotBlank @Email String email, @NotBlank String password) {
        this.email = email;
        this.password = password;
    }

    public UsernamePasswordAuthenticationToken convert() {
        return new UsernamePasswordAuthenticationToken(this.email, this.password);
    }
}
