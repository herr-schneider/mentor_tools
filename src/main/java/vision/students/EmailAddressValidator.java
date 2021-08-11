package vision.students;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.apache.commons.validator.routines.EmailValidator;

public class EmailAddressValidator implements ConstraintValidator<Email, String> {

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
            // create the EmailValidator instance
            EmailValidator validator = EmailValidator.getInstance();

            // check for valid email addresses using isValid method
            return validator.isValid(email);
    }
}
