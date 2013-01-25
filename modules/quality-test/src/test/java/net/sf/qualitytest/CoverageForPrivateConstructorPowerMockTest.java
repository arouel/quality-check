/*******************************************************************************
 * Copyright 2013 André Rouél
 * Copyright 2013 Dominik Seichter
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
package net.sf.qualitytest;

import java.lang.reflect.InvocationTargetException;

import net.sf.qualitytest.exception.CoverageForPrivateConstructorException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest(CoverageForPrivateConstructor.class)
public class CoverageForPrivateConstructorPowerMockTest {
	
	private final class HasPrivateContructor {
		private HasPrivateContructor() {
			// Do not call
		}
	}
	
	@Test(expected=CoverageForPrivateConstructorException.class)
	public void testSecurityException() throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
		testWithException(new SecurityException());
	}

	@Test(expected=CoverageForPrivateConstructorException.class)
	public void testNoSuchMethodException() throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
		testWithException(new NoSuchMethodException());
	}
	
	@Test(expected=CoverageForPrivateConstructorException.class)
	public void testIllegalArgumentException() throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
		testWithException(new IllegalArgumentException());
	}
	
	@Test(expected=CoverageForPrivateConstructorException.class)
	public void testInstantiationException() throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
		testWithException(new InstantiationException());
	}	
	
	@Test(expected=CoverageForPrivateConstructorException.class)
	public void testIllegalAccessException() throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
		testWithException(new IllegalAccessException());
	}	
	
	@Test(expected=CoverageForPrivateConstructorException.class)
	public void testInvocationTargetException() throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
		testWithException(new InvocationTargetException(new NullPointerException()));
	}		
	
	private void testWithException(final Exception e) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
		PowerMock.mockStaticPartial(CoverageForPrivateConstructor.class, "giveMeCoverageInteral");
		CoverageForPrivateConstructor.giveMeCoverageInteral(HasPrivateContructor.class);
		PowerMock.expectLastCall().andThrow(e);
		PowerMock.replay(CoverageForPrivateConstructor.class);
		
		CoverageForPrivateConstructor.giveMeCoverage(HasPrivateContructor.class);
	}
}
