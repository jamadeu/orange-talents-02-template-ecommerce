package br.com.zup.mercadolivre.user.dto;

public class TokenResponse {
    private final String tokenType;
    private final String token;

    public TokenResponse(String token, String tokenType) {
        this.token = token;
        this.tokenType = tokenType;
    }

    public String getTokenType() {
        return tokenType;
    }

    public String getToken() {
        return token;
    }
}
