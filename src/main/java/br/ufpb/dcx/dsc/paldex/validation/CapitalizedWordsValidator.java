package br.ufpb.dcx.dsc.paldex.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CapitalizedWordsValidator implements ConstraintValidator<CapitalizedWords, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) {
            return true; // Deixe o @NotBlank cuidar disso
        }

        String[] words = value.split(" ");
        for (String word : words) {
            if (!Character.isUpperCase(word.charAt(0))) {
                return false;
            }
        }
        return true;
    }
}
