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
package de.hu_berlin.german.korpling.saltnpepper.pepper.modules.impl.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperMapperController;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperModuleProperties;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.impl.PepperMapperControllerImpl;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.impl.PepperMapperImpl;
import de.hu_berlin.german.korpling.saltnpepper.salt.SaltFactory;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SDocument;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SLayer;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SNode;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SRelation;
import de.hu_berlin.german.korpling.saltnpepper.salt.samples.SampleGenerator;

public class PepperMapperControllerImplTest {

	private PepperMapperController fixture= null; 
	
	public PepperMapperController getFixture() {
		return fixture;
	}

	public void setFixture(PepperMapperController fixture) {
		this.fixture = fixture;
	}

	@Before
	public void setUp() throws Exception {
		setFixture(new PepperMapperControllerImpl(null, "testThread"));
		getFixture().setPepperMapper(new PepperMapperImpl());
		getFixture().getPepperMapper().setProperties(new PepperModuleProperties());
	}

	@Test
	public void test_PropAddSLayer() {
		SDocument sDoc= SaltFactory.eINSTANCE.createSDocument();
		SampleGenerator.createSDocumentStructure(sDoc);
		int layersBefore = sDoc.getSDocumentGraph().getSLayers().size();
		getFixture().getPepperMapper().getProperties().setPropertyValue(PepperModuleProperties.PROP_AFTER_ADD_SLAYER, "layer1; layer2");
		sDoc.setSElementId(SaltFactory.eINSTANCE.createSElementId());
		getFixture().after(sDoc.getSElementId());
		
		assertEquals(layersBefore+2, sDoc.getSDocumentGraph().getSLayers().size());
		SLayer layer1= sDoc.getSDocumentGraph().getSLayers().get(0);
		SLayer layer2= sDoc.getSDocumentGraph().getSLayers().get(1);
		for (SNode sNode: sDoc.getSDocumentGraph().getSNodes()){
			assertTrue(sNode.getSLayers().contains(layer1));
			assertTrue(sNode.getSLayers().contains(layer2));
		}
		for (SRelation sRel: sDoc.getSDocumentGraph().getSRelations()){
			assertTrue(sRel.getSLayers().contains(layer1));
			assertTrue(sRel.getSLayers().contains(layer2));
		}
	}

}
