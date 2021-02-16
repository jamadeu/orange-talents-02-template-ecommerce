package br.com.zup.mercadolivre.user.dto;

import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

public class NewUserRequest {

    @NotEmpty
    private String name;
    @NotEmpty
    private String address;
    @NotEmpty
    @CPF
    private String cpf;
    @NotEmpty
    @Email
    private String email;
    @NotEmpty
    @Min(6)
    private String password;

    public String getAddress() {
        return address;
    }

    public String getCpf() {
        return cpf;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
