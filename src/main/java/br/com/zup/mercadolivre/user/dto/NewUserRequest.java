package br.com.zup.mercadolivre.user.dto;

import br.com.zup.mercadolivre.shared.validator.annotation.FieldUnique;
import br.com.zup.mercadolivre.user.model.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

public class NewUserRequest {

    @NotBlank
    @JsonProperty
    private String name;
    @NotBlank
    @JsonProperty
    private String address;
    @NotBlank
    @CPF
    @FieldUnique(message = "CPF must be unique", fieldName = "cpf", domainClass = User.class)
    @JsonProperty
    private String cpf;
    @NotBlank
    @Email
    @FieldUnique(message = "Email must be unique", fieldName = "email", domainClass = User.class)
    @JsonProperty
    private String email;
    @NotBlank
    @Length(min = 6)
    @JsonProperty
    private String password;

    public NewUserRequest(@NotEmpty String name, @NotEmpty @NotBlank String address, @NotEmpty @CPF String cpf, @NotEmpty @Email String email, @NotEmpty @Min(6) String password) {
        this.name = name;
        this.address = address;
        this.cpf = cpf;
        this.email = email;
        this.password = password;
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
