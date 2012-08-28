/*******************************************************************************
 * Copyright 2012 André Rouél
 * Copyright 2012 Dominik Seichter
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

package com.github.quality.check;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates that a method is intended to check all its arguments using
 * Quality-Check. If a method is annotated with this annotation the method is
 * responsible to check all arguments against basic conditions like not null,
 * not empty etc.
 * <p>
 * In turn the caller has to be aware that exceptions like
 * IllegalNullArgumentException etc. will be thrown by the method if input
 * arguments are not valid.
 * 
 * <p>Example
 * {@code
 * @ArgumentsChecked
 * public void validate(Object o) {
 * 	  Check.notNull(o);
 * }}
 * 
 * 
 * @author André Rouél
 * @author Dominik Seichter
 */
@Target(value = ElementType.METHOD)
@Retention(value = RetentionPolicy.SOURCE)
public @interface ArgumentsChecked {

}
