/**
 * 
 */
package io.github.warnotte.obj2gui2;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * @author Warnotte Renaud.
 * KOPKOK mettre devant une variable de type liste pour la voire apparaitre
 */
@Documented
@Retention(RUNTIME)
@Target(ElementType.FIELD)
public @interface PROPERTY_FIELD_LISTABLE
{
	boolean showAddorRemoveButton() default true;
}
