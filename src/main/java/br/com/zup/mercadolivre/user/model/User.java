package br.com.zup.mercadolivre.user.model;

import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty
    @Column(nullable = false)
    private String name;
    @NotEmpty
    @Column(nullable = false)
    private String address;
    @NotEmpty
    @CPF
    @Column(nullable = false, unique = true)
    private String cpf;
    @NotEmpty
    @Email
    @Column(nullable = false, unique = true)
    private String email;
    @NotEmpty
    @Column(nullable = false)
    private String password;
    @PastOrPresent
    private LocalDate createdAt = LocalDate.now();

    @Deprecated
    public User() {
    }

    public User(@NotEmpty String name, @NotEmpty String address, @NotEmpty @CPF String cpf, @NotEmpty @Email String email, @NotEmpty String password) {
        this.name = name;
        this.address = address;
        this.cpf = cpf;
        this.email = email;
        this.password = password;
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
