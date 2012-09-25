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
package de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.tests;

import java.util.Properties;

import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperModuleProperties;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperModuleProperty;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.exceptions.PepperModulePropertyException;
import junit.framework.TestCase;

public class PepperModulePropertiesTest extends TestCase
{
	private PepperModuleProperties fixture= null;
	public void setFixture(PepperModuleProperties fixture) {
		this.fixture = fixture;
	}

	public PepperModuleProperties getFixture() {
		return fixture;
	}
	public void setUp()
	{
		this.setFixture(new PepperModuleProperties());
	}
	
	public void testAddProp()
	{
		String propName= "prop1";
		PepperModuleProperty<Integer> prop= new PepperModuleProperty<Integer>(propName, Integer.class, "some desc");
		prop.setValue(123);
		this.getFixture().addProperty(prop);
		assertEquals(prop, this.getFixture().getProperty(propName));
	}
	
	/**
	 * Checks adding a bunch of properties via {@link Properties} object. All properties are already described.
	 */
	public void testAdd_Properties()
	{
		String propName1= "prop1";
		String propName2= "prop2";
		String propName3= "prop3";
		
		PepperModuleProperty<Integer> prop1= new PepperModuleProperty<Integer>(propName1, Integer.class, "some desc");
		this.getFixture().addProperty(prop1);
		PepperModuleProperty<Integer> prop2= new PepperModuleProperty<Integer>(propName2, Integer.class, "some desc");
		this.getFixture().addProperty(prop2);
		PepperModuleProperty<Integer> prop3= new PepperModuleProperty<Integer>(propName3, Integer.class, "some desc");
		this.getFixture().addProperty(prop3);
		
		Properties properties= new Properties();
		properties.put(propName1, 123);
		properties.put(propName2, 123);
		properties.put(propName3, 123);
		
		this.getFixture().addProperties(properties);
		assertEquals(this.getFixture().getProperty(propName1), prop1);
		assertEquals(this.getFixture().getProperty(propName2), prop2);
		assertEquals(this.getFixture().getProperty(propName3), prop3);
	}
	/**
	 * Checks adding a bunch of properties via {@link Properties} object. Some of the properties are not desribed.
	 */
	public void testAdd_Properties2()
	{
		String propName1= "prop1";
		String propName2= "prop2";
		String propName3= "prop3";
		String propName4= "prop4";
		String propName5= "prop5";
		
		PepperModuleProperty<Integer> prop1= new PepperModuleProperty<Integer>(propName1, Integer.class, "some desc");
		this.getFixture().addProperty(prop1);
		PepperModuleProperty<Integer> prop2= new PepperModuleProperty<Integer>(propName2, Integer.class, "some desc");
		this.getFixture().addProperty(prop2);
		PepperModuleProperty<Integer> prop3= new PepperModuleProperty<Integer>(propName3, Integer.class, "some desc");
		this.getFixture().addProperty(prop3);
		
		Properties properties= new Properties();
		properties.put(propName1, 12);
		properties.put(propName2, 34);
		properties.put(propName3, 56);
		properties.put(propName4, 78);
		properties.put(propName5, 90);
		
		this.getFixture().addProperties(properties);
		assertEquals(this.getFixture().getProperty(propName1), prop1);
		assertEquals(this.getFixture().getProperty(propName2), prop2);
		assertEquals(this.getFixture().getProperty(propName3), prop3);
		assertEquals(this.getFixture().getProperty(propName4).getValue(), "78");
		assertFalse(this.getFixture().getProperty(propName4).getValue().equals(78));
		assertEquals(this.getFixture().getProperty(propName5).getValue(), "90");
		assertFalse(this.getFixture().getProperty(propName5).getValue().equals(90));		
	}

	/**
	 * Checks if check works correctly. Sets all required values, than 
	 */
	public void testAdd_CheckProperties()
	{
		String propName1= "prop1";
		String propName2= "prop2";
		String propName3= "prop3";
		String propName4= "prop4";
		String propName5= "prop5";
		
		PepperModuleProperty<Integer> prop1= new PepperModuleProperty<Integer>(propName1, Integer.class, "some desc", true);
		this.getFixture().addProperty(prop1);
		PepperModuleProperty<Integer> prop2= new PepperModuleProperty<Integer>(propName2, Integer.class, "some desc", true);
		this.getFixture().addProperty(prop2);
		PepperModuleProperty<Integer> prop3= new PepperModuleProperty<Integer>(propName3, Integer.class, "some desc", true);
		this.getFixture().addProperty(prop3);
		PepperModuleProperty<Integer> prop4= new PepperModuleProperty<Integer>(propName4, Integer.class, "some desc", false);
		this.getFixture().addProperty(prop4);
		PepperModuleProperty<Integer> prop5= new PepperModuleProperty<Integer>(propName5, Integer.class, "some desc", false);
		this.getFixture().addProperty(prop5);
		
		Properties properties= new Properties();
		properties.put(propName1, 12);
		properties.put(propName2, 34);
		properties.put(propName3, 56);
		
		this.getFixture().addProperties(properties);
		assertTrue(this.getFixture().checkProperties());
	}
	
	/**
	 * Checks if check works correctly. Does not set all required values, than 
	 */
	public void testAdd_CheckProperties2()
	{
		String propName1= "prop1";
		String propName2= "prop2";
		String propName3= "prop3";
		
		PepperModuleProperty<Integer> prop1= new PepperModuleProperty<Integer>(propName1, Integer.class, "some desc", true);
		this.getFixture().addProperty(prop1);
		PepperModuleProperty<Integer> prop2= new PepperModuleProperty<Integer>(propName2, Integer.class, "some desc", true);
		this.getFixture().addProperty(prop2);
		PepperModuleProperty<Integer> prop3= new PepperModuleProperty<Integer>(propName3, Integer.class, "some desc", true);
		this.getFixture().addProperty(prop3);
		
		Properties properties= new Properties();
		properties.put(propName1, 12);
		properties.put(propName2, 34);
		
		this.getFixture().addProperties(properties);
		try {
			this.getFixture().checkProperties();
			fail("Check should not return true");
		} catch (PepperModulePropertyException e) {
			// TODO: handle exception
		}
	}
}