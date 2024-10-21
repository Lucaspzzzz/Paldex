package br.ufpb.dcx.dsc.paldex.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import br.ufpb.dcx.dsc.paldex.repository.UserRepository;

public class UniqueValidator implements ConstraintValidator<Unique, Object> {

    @Autowired
    private UserRepository userRepository;

    private String fieldName;
    private Class<?> domainClass;

    @Override
    public void initialize(Unique constraintAnnotation) {
        fieldName = constraintAnnotation.fieldName();
        domainClass = constraintAnnotation.domainClass();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        String email = (String) value;

        return !userRepository.existsByEmail(email);
    }
}
