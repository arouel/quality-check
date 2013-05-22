/*******************************************************************************
 * Copyright 2013 André Rouél and Dominik Seichter
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package net.sf.qualitycheck;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates that a method is intended to check all its arguments using <em>Quality-Check</em>. If a method is annotated
 * with this annotation the method is responsible to check all arguments against basic conditions like not null, not
 * empty etc.
 * 
 * <p>
 * In turn the caller has to be aware that exceptions like {@code IllegalNullArgumentException} will be thrown by the
 * method if input arguments are not valid.
 * 
 * <p>
 * The following example describes how to use it.
 * 
 * <pre>
 * &#064;ArgumentsChecked
 * public void validate(Object o) {
 * 	Check.notNull(o);
 * }
 * </pre>
 * 
 * <p>
 * Additionally it can be documented which exceptions may occur if given arguments are invalid using
 * the additional annotation {@code @Throws}.
 * 
 * <pre>
 * &#064;ArgumentsChecked
 * &#064;Throws(IllegalNullArgumentException.class)
 * public void validate(Object o) {
 * 	Check.notNull(o);
 * }
 * </pre>
 * 
 * @author André Rouél
 * @author Dominik Seichter
 */
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = { ElementType.CONSTRUCTOR, ElementType.METHOD })
public @interface ArgumentsChecked {

}
