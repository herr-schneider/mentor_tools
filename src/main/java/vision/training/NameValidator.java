package vision.training;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NameValidator implements ConstraintValidator<Name, String> {

    @Override
    public boolean isValid(String name, ConstraintValidatorContext constraintValidatorContext) {
        if (name==null){return true;}
        return name.trim().length()>2 && name.length()<255; // && !name.isBlank() && !name.isEmpty();
        }
}
