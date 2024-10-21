package br.ufpb.dcx.dsc.paldex.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CapitalizedWordsValidator.class)
public @interface CapitalizedWords {
    String message() default "Cada palavra do nome deve começar com uma letra maiúscula";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
