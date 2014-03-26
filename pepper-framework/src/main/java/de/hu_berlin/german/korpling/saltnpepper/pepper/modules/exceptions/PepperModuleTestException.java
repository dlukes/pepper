/**
 * Copyright 2009 Humboldt University of Berlin, INRIA.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *
 */
package de.hu_berlin.german.korpling.saltnpepper.pepper.modules.exceptions;



/**
 * This Exception class can be used for Tests (i.e. JUnit tests) of {@link de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperModule} classes.
 *  
 * @author Florian Zipser
 *
 */
public class PepperModuleTestException extends PepperModuleException
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5431375961043975520L;

	public PepperModuleTestException()
	{ super(); }
	
    public PepperModuleTestException(String s)
    { super(s); }
    
	public PepperModuleTestException(String s, Throwable ex)
	{super(s, ex); }
}