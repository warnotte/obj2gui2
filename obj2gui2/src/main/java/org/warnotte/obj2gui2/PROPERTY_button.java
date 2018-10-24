package org.warnotte.obj2gui2;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 
 * @author Warnotte Renaud
 *
 */
@Documented
@Retention(RUNTIME)
@Target(ElementType.FIELD)
public @interface PROPERTY_button {

	String method_name() default "toString";

	String text() default "";
	String toolTipText() default "";
	
	boolean threadedMethod() default false;

}
