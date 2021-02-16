package br.com.zup.mercadolivre.shared.validator;

import br.com.zup.mercadolivre.shared.validator.annotation.FieldUnique;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FieldUniqueValidator implements ConstraintValidator<FieldUnique, Object> {
    private String domainAttribute;
    private Class<?> klass;

    @PersistenceContext
    private EntityManager manager;

    @Override
    public void initialize(FieldUnique constraintAnnotation) {
        domainAttribute = constraintAnnotation.fieldName();
        klass = constraintAnnotation.domainClass();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        Query query = manager.createQuery("select 1 from "+ klass.getName()+ " where "+ domainAttribute + " = :value");
        query.setParameter("value", value);
        return query.getResultList().isEmpty();
    }
}
