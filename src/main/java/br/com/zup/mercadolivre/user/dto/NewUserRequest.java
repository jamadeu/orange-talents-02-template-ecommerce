package br.com.zup.mercadolivre.user.dto;

import br.com.zup.mercadolivre.shared.validator.annotation.FieldUnique;
import br.com.zup.mercadolivre.user.model.User;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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
    @FieldUnique(message = "CPF must be unique", fieldName = "cpf", domainClass = User.class)
    private String cpf;
    @NotEmpty
    @Email
    @FieldUnique(message = "Email must be unique", fieldName = "email", domainClass = User.class)
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

    public User toModel() {
        String passwordEncoded = new BCryptPasswordEncoder().encode(password);
        return new User(name, address, cpf, email, passwordEncoded);
    }
}
