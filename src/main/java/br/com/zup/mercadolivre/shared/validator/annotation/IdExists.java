package br.com.zup.mercadolivre.shared.validator.annotation;

import br.com.zup.mercadolivre.shared.validator.IdExistsValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;

@Retention(RetentionPolicy.RUNTIME)
@Target({FIELD})
@Constraint(validatedBy = {IdExistsValidator.class})
@Documented
public @interface IdExists {
    String message() default "IdExists.message";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    Class<?> domainClass();
}
