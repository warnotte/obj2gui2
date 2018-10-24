package org.warnotte.obj2gui2;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Documented
@Retention(RUNTIME)
@Target(ElementType.TYPE)
public @interface PROPERTY_MIGLAYOUT {
	
	String LayoutConstraints() default "fill";
	String ColumnConstraints() default "fill, grow";
	String RowConstraints() default "fill, grow";
	
//	public static enum lblposition {LEFT, RIGHT, CENTER};
	
	lblposition labelPosition() default lblposition.RIGHT;
	
	
	
}

