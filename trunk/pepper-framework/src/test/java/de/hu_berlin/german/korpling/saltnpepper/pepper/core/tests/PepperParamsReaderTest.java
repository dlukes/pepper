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
package de.hu_berlin.german.korpling.saltnpepper.pepper.core.tests;

import static org.junit.Assert.*;

import java.io.File;

import org.eclipse.emf.common.util.URI;
import org.junit.Before;
import org.junit.Test;

import de.hu_berlin.german.korpling.saltnpepper.pepper.core.PepperParamsReader;
import de.hu_berlin.german.korpling.saltnpepper.pepper.tests.PepperTestUtil;

public class PepperParamsReaderTest {

	private PepperParamsReader fixture= null;
	
	public PepperParamsReader getFixture() {
		return fixture;
	}

	public void setFixture(PepperParamsReader fixture) {
		this.fixture = fixture;
	}
	@Before
	public void setUp(){
		setFixture(new PepperParamsReader());
		File tmpDir= new File(getTmPath());
		if (!tmpDir.exists())
			tmpDir.mkdirs();
	}
	
	public String getTmPath(){
		return(PepperTestUtil.getTmpStr()+"pepperParamsTest/");
	}
	
	@Test
	public void testResolveFile() {
		assertNull(getFixture().resolveFile(null));
		assertNull(getFixture().resolveFile(""));
		
		File retFile=null;
		
		retFile= getFixture().resolveFile("file:"+getTmPath()+"/some.file");
		assertNotNull(retFile);
		assertEquals(getTmPath()+"some.file", retFile.getAbsolutePath());
		
		retFile= getFixture().resolveFile("file:"+getTmPath()+"some.file");
		assertNotNull(retFile);
		assertEquals(getTmPath()+"some.file", retFile.getAbsolutePath());
		
		retFile= getFixture().resolveFile(getTmPath()+"/some.file");
		assertNotNull(retFile);
		assertEquals(getTmPath()+"some.file", retFile.getAbsolutePath());
		
		getFixture().setLocation(URI.createFileURI(getTmPath()));
		retFile= getFixture().resolveFile("./some.file");
		assertNotNull(retFile);
		assertEquals(getTmPath()+"some.file", retFile.getAbsolutePath());
	}
}
