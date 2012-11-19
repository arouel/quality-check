package net.sf.qualitycheck;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The <code>Throws</code> annotation specifies the classes that will be thrown when one ore more arguments are not
 * valid.
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Throws {

	/**
	 * @return the classes that can be thrown
	 */
	Class<? extends Throwable>[] value();

}
