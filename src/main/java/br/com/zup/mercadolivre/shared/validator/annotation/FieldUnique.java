package br.com.zup.mercadolivre.shared.validator.annotation;

import br.com.zup.mercadolivre.shared.validator.FieldUniqueValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = {FieldUniqueValidator.class})
@Documented
public @interface FieldUnique {
    String message() default "FieldUnique.message";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String fieldName();

    Class<?> domainClass();
}
