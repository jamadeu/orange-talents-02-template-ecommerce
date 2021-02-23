package br.com.zup.mercadolivre.user.controller;

import br.com.zup.mercadolivre.user.dto.NewUserRequest;
import br.com.zup.mercadolivre.user.model.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UserCreator {

    public static User createUser() {
        return new User(
                "User",
                "Address",
                "949.053.040-91",
                "user@email.com",
                "123456"
        );
    }

    public static User createUserToAuthTests(String email, String password) {
        return new User(
                "User",
                "Address",
                "949.053.040-91",
                email,
                password
        );
    }

    public static User createUserPasswordEncrypted() {
        return new User(
                "User",
                "Address",
                "949.053.040-91",
                "user@email.com",
                new BCryptPasswordEncoder().encode("123456")
        );
    }

    public static NewUserRequest createNewUserRequest() {
        return new NewUserRequest(
                "User",
                "Address",
                "949.053.040-91",
                "user@email.com",
                "123456"
        );
    }

    public static NewUserRequest createNewUserRequest(String name, String address, String cpf, String email, String password) {
        return new NewUserRequest(
                name,
                address,
                cpf,
                email,
                password
        );
    }
}
