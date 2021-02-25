package br.com.zup.mercadolivre.user.dto;

import br.com.zup.mercadolivre.user.model.User;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class UserResponse {

    @JsonProperty
    private String name;
    @JsonProperty
    private String address;
    @JsonProperty
    private String cpf;
    @JsonProperty
    private String email;
    @JsonProperty
    private LocalDateTime createdAt;

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
