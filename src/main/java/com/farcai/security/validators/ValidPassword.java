package com.farcai.security.validators;

import javax.validation.Constraint;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = PasswordConstraintValidator.class)
@Target({ ElementType.TYPE, ElementType.FIELD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPassword {

    String message()

    default "La contraseña debe tener al menos 8 dígitos, una letra, un número y uno de los siguientes caracteres especiales @$!%*#?&";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
