package me.anchore.ioc;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;

/**
 * @author anchore
 * @date 2019/2/13
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({TYPE, FIELD, METHOD, CONSTRUCTOR})
public @interface Bean {

    String value() default "";

    Scope scope() default Scope.SINGLETON;

    enum Scope {
        //
        SINGLETON, PROTOTYPE
    }
}