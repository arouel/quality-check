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
package net.sf.qualitycheck.exception;

/**
 * This is a marker interface indicating that an exception also holds the value which caused the exception to be thrown.
 * 
 * @author André Rouél
 * @author Dominik Seichter
 */
public interface IllegalArgumentHolder<T> {

	/**
	 * Get access to the illegal argument causing the exception.
	 * 
	 * @return the illegal argument which caused the exception to be thrown.
	 */
	T getIllegalArgument();
}
