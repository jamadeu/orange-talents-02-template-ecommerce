package br.com.zup.mercadolivre.user.model;

import br.com.zup.mercadolivre.purchase.model.Purchase;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty
    private Long id;
    @NotBlank
    @Column(nullable = false)
    @JsonProperty
    private String name;
    @NotBlank
    @Column(nullable = false)
    @JsonProperty
    private String address;
    @NotBlank
    @CPF
    @Column(nullable = false, unique = true)
    @JsonProperty
    private String cpf;
    @NotBlank
    @Email
    @Column(nullable = false, unique = true)
    @JsonProperty
    private String email;
    @NotBlank
    @Column(nullable = false)
    @JsonProperty
    private String password;
    @PastOrPresent
    @JsonProperty
    private LocalDateTime createdAt = LocalDateTime.now();
    @OneToMany(mappedBy = "buyer", cascade = CascadeType.MERGE)
    @JsonProperty
    private List<Purchase> purchases = new ArrayList<>();

    @Deprecated
    public User() {
    }

    public User(@NotBlank String name, @NotBlank String address, @NotBlank @CPF String cpf, @NotBlank @Email String email, @NotBlank String password) {
        this.name = name;
        this.address = address;
        this.cpf = cpf;
        this.email = email;
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Long getId() {
        return id;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
