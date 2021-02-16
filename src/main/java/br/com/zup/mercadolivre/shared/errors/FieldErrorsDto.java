package br.com.zup.mercadolivre.shared.errors;

public class FieldErrorsDto {
    private String field;
    private String message;

    public FieldErrorsDto(String field, String message) {
        this.field = field;
        this.message = message;
    }

    public String getField() {
        return field;
    }

    public String getMessage() {
        return message;
    }
}
