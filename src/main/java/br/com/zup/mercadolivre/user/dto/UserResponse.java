package br.com.zup.mercadolivre.user.dto;

import br.com.zup.mercadolivre.user.model.User;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class UserResponse {

    @JsonProperty
    final String name;
    @JsonProperty
    final String address;
    @JsonProperty
    final String cpf;
    @JsonProperty
    final String email;
    @JsonProperty
    final LocalDateTime createdAt;

    @JsonCreator
    public UserResponse(User user) {
        this.name = user.getName();
        this.address = user.getAddress();
        this.cpf = user.getCpf();
        this.email = user.getEmail();
        this.createdAt = user.getCreatedAt();
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
