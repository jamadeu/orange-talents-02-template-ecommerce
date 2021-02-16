package br.com.zup.mercadolivre.user.dto;

import br.com.zup.mercadolivre.user.model.User;

import java.time.LocalDate;

public class UserResponse {

    private String name;
    private String address;
    private String cpf;
    private String email;
    private LocalDate createdAt;

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

    public String getAddress() {
        return address;
    }

    public String getCpf() {
        return cpf;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }
}
