package br.com.zup.mercadolivre.shared.validator;

import br.com.zup.mercadolivre.shared.validator.annotation.IdExists;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IdExistsValidator implements ConstraintValidator<IdExists, Object> {
    private Class<?> klass;

    @PersistenceContext
    private EntityManager manager;

    @Override
    public void initialize(IdExists constraintAnnotation) {
        klass = constraintAnnotation.domainClass();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }
        Object o = manager.find(klass, value);
        return o != null;
    }
}
