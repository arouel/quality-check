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
package net.sf.qualitytest.blueprint;

import net.sf.qualitytest.blueprint.strategy.matching.BuilderMethodMatchingStrategy;
import net.sf.qualitytest.blueprint.strategy.matching.SetterMethodMatchingStrategy;

/**
 * A utility interface to have a more readable API. This class holds possible method matching strategies.
 * 
 * @author Dominik Seichter
 * 
 */
public interface Match {

	/**
	 * Match all public methods of classes with a name ending in "Builder".
	 */
	MatchingStrategy BUILDER_METHODS = new BuilderMethodMatchingStrategy();

	/**
	 * Match all methods which are setter-methods.
	 */
	MatchingStrategy SETTER_METHODS = new SetterMethodMatchingStrategy();

}
