package br.com.zup.mercadolivre.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TokenResponse {

    @JsonProperty
    private final String tokenType;
    @JsonProperty
    private final String token;

    public TokenResponse(String token, String tokenType) {
        this.token = token;
        this.tokenType = tokenType;
    }
}
