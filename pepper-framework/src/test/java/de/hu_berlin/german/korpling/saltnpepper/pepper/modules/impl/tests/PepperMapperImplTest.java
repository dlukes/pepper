package de.hu_berlin.german.korpling.saltnpepper.pepper.modules.impl.tests;

import static org.junit.Assert.assertEquals;

import org.eclipse.emf.common.util.URI;
import org.junit.Before;
import org.junit.Test;

import de.hu_berlin.german.korpling.saltnpepper.pepper.common.DOCUMENT_STATUS;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.impl.PepperMapperImpl;
import de.hu_berlin.german.korpling.saltnpepper.salt.SaltFactory;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SCorpus;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SDocument;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SElementId;

public class PepperMapperImplTest {

	private PepperMapperImpl fixture= null;

	public PepperMapperImpl getFixture() {
		return fixture;
	}

	public void setFixture(PepperMapperImpl fixture) {
		this.fixture = fixture;
	}

	@Before
	public void setUp(){
		setFixture(new PepperMapperImpl());
	}
	
	@Test
	public void testSetGetSDocument(){
		SDocument sDocument= SaltFactory.eINSTANCE.createSDocument();
		SElementId sElementId= SaltFactory.eINSTANCE.createSElementId();
		sElementId.setSId("d1");
		sDocument.setSElementId(sElementId);
		getFixture().setSDocument(sDocument);
		
		assertEquals(sDocument, getFixture().getSDocument());
	}
	
	@Test
	public void testSetGetSCorpus(){
		SCorpus sCorpus= SaltFactory.eINSTANCE.createSCorpus();
		SElementId sElementId= SaltFactory.eINSTANCE.createSElementId();
		sElementId.setSId("c1");
		sCorpus.setSElementId(sElementId);
		getFixture().setSCorpus(sCorpus);
		
		assertEquals(sCorpus, getFixture().getSCorpus());
	}
	
	@Test
	public void testSetGetResourceURI(){
		URI resourceURI= URI.createFileURI("./");
		getFixture().setResourceURI(resourceURI);
		
		assertEquals(resourceURI, getFixture().getResourceURI());
	}
	
	@Test
	public void testSetGetMappingResult(){
		DOCUMENT_STATUS mappingResult= DOCUMENT_STATUS.COMPLETED;
		getFixture().setMappingResult(mappingResult);
		
		assertEquals(mappingResult, getFixture().getMappingResult());
	}
}