package br.com.zup.mercadolivre.shared.validator.annotation;

import br.com.zup.mercadolivre.shared.validator.IdExistsValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Constraint(validatedBy = {IdExistsValidator.class})
@Documented
public @interface IdExists {
    String message() default "IdExists.message";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    Class<?> domainClass();
}
