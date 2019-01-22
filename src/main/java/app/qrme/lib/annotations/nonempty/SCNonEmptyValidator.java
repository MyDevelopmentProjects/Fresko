package app.qrme.lib.annotations.nonempty;

import org.apache.commons.beanutils.BeanUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.InvocationTargetException;

import static app.qrme.lib.utils.BaseConstants.Common.empty_string;
import static app.qrme.lib.utils.CoreLibUtils.isNullOrEmpty;


public class SCNonEmptyValidator implements ConstraintValidator<SCPasswordNonEmpty, Object> {

    private String fieldName;
    private String google;
    private String twitter;
    private String facebook;

    @Override
    public void initialize(SCPasswordNonEmpty annotation) {
        fieldName = annotation.fieldName();
        google = annotation.google();
        twitter = annotation.twitter();
        facebook = annotation.facebook();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext ctx) {
        if (value == null) {
            return true;
        }
        try {
            String fieldValue = BeanUtils.getProperty(value, fieldName);
            String google = BeanUtils.getProperty(value, this.google);
            String twitter = BeanUtils.getProperty(value, this.twitter);
            String facebook = BeanUtils.getProperty(value, this.facebook);
            if (isNullOrEmpty(google) && isNullOrEmpty(twitter) && isNullOrEmpty(facebook)) {
                if (fieldValue == null || fieldValue.equals(empty_string)) {
                    ctx.disableDefaultConstraintViolation();
                    ctx.buildConstraintViolationWithTemplate(ctx.getDefaultConstraintMessageTemplate())
                            .addConstraintViolation();
                    return false;
                }
            }
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException ex) {
            throw new RuntimeException(ex);
        }

        return true;
    }
}
