package com.app.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Constraint(validatedBy=UserNameValidator.class)
@Documented
public @interface CheckUserName {
    String message() default "User name should have contain only Latin alphabet characters or 0 - 9";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
