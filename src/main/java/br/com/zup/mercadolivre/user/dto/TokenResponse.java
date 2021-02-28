package br.com.zup.mercadolivre.user.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TokenResponse {

    @JsonProperty
    final String tokenType;
    @JsonProperty
    final String token;

    @JsonCreator
    public TokenResponse(String token, String tokenType) {
        this.token = token;
        this.tokenType = tokenType;
    }
}
