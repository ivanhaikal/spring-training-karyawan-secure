package com.idstar.karyawan.validation.anotation;




import com.idstar.karyawan.validation.utils.PasswordValueValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * <h2>PasswordValueMatch</h2>
 *
 * @author aek
 */
@Target({ TYPE, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = PasswordValueValidator.class)
@Documented
public @interface PasswordValueMatch {


    String message() default "Fields values don't match!";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    String field();

    String fieldMatch();

    @Target({ ElementType.TYPE })
    @Retention(RetentionPolicy.RUNTIME)
    @interface List {
        PasswordValueMatch[] value();
    }
}
