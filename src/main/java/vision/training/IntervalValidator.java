package vision.training;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.*;

public class IntervalValidator implements ConstraintValidator<IsValidInterval, Interval> {


    @Override
    public boolean isValid(Interval interval, ConstraintValidatorContext constraintValidatorContext) {
        if (interval.getStartDate()==null || interval.getEndDate()==null){return true;}
        return (interval.getStartDate().isAfter(LocalDate.now()) || interval.getStartDate().isEqual(LocalDate.now()))
                && (interval.getEndDate().isAfter(interval.getStartDate()) || interval.getEndDate().isEqual(interval.getStartDate()));
    }
}