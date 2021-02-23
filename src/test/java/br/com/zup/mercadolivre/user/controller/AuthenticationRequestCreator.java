package br.com.zup.mercadolivre.user.controller;

import br.com.zup.mercadolivre.user.dto.AuthenticationRequest;
import br.com.zup.mercadolivre.user.model.User;

public class AuthenticationRequestCreator {

    public static AuthenticationRequest createAuthenticationRequest(User user) {
        return new AuthenticationRequest(
                user.getEmail(),
                user.getPassword()
        );
    }
}
