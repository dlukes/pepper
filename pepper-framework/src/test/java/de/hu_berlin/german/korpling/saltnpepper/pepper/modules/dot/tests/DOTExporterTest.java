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
package de.hu_berlin.german.korpling.saltnpepper.pepper.modules.dot.tests;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Properties;

import org.eclipse.emf.common.util.URI;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hu_berlin.german.korpling.saltnpepper.pepper.common.CorpusDesc;
import de.hu_berlin.german.korpling.saltnpepper.pepper.common.FormatDesc;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.dot.DOTExporter;
import de.hu_berlin.german.korpling.saltnpepper.pepper.testFramework.PepperExporterTest;
import de.hu_berlin.german.korpling.saltnpepper.salt.SaltFactory;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.SaltCommonFactory;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.SaltProject;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SCorpus;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SCorpusDocumentRelation;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SCorpusGraph;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SDocument;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SElementId;

@RunWith(JUnit4.class)
public class DOTExporterTest extends PepperExporterTest
{
	Logger logger= LoggerFactory.getLogger(DOTExporter.class);
	
	URI resourceURI= URI.createFileURI("src/test/resources/resources");
	URI inputURI= URI.createFileURI("src/test/resources/test/test1.saltCommon");
	URI outputURI= URI.createFileURI("_TMP/ExporterTest/");
	
	//SaltSample saltSample = new SaltSample();
	SaltProject saltProject = SaltFactory.eINSTANCE.createSaltProject();
	SCorpusGraph sCorpusGraph= null;
	
	@Before
	public void setUp() throws Exception 
	{
		super.setFixture(new DOTExporter());
		super.getFixture().setSaltProject(SaltCommonFactory.eINSTANCE.createSaltProject());
		super.setResourcesURI(resourceURI);
		//setting temproraries and resources
			
			File resourceDir= new File(resourceURI.toFileString());
			if (!resourceDir.exists())
				resourceDir.mkdirs();
			this.getFixture().setResources(resourceURI);
		//setting temproraries and resources
		
		//set formats to support
		FormatDesc formatDef= new FormatDesc(); 
		formatDef.setFormatName("dot");
		formatDef.setFormatVersion("1.0");
		this.supportedFormatsCheck.add(formatDef);
}
	
//	TODO incomment this test in next version
	public void testCreateCorpusStructure() throws IOException{
		
		File corpusPathFile= new File("./_TMP/de.hu_berlin.german.korpling.saltnpepper.pepperModules.DotModules/Exporter/testcase1");
		File currentFile = new File("./_TMP/de.hu_berlin.german.korpling.saltnpepper.pepperModules.DotModules/Exporter/testcase1/corp1.dot");
		File expectedFile= new File("./src/test/resources/expected/Exporter/testcase1/corp1.dot");
		
		URI corpusPath= URI.createFileURI(corpusPathFile.getCanonicalPath());
		URI currentURI = URI.createFileURI(currentFile.getCanonicalPath());
		URI expectedURI= URI.createFileURI(expectedFile.getCanonicalPath());
				
		this.removeDirRec(new File(corpusPath.toFileString()));
		
		{//creating and setting corpus definition
			CorpusDesc corpDef= new CorpusDesc();
			FormatDesc formatDef= new FormatDesc();
			formatDef.setFormatName("dot");
			formatDef.setFormatVersion("1.0");
			corpDef.setFormatDesc(formatDef);
			corpDef.setCorpusPath(corpusPath);
			this.getFixture().setCorpusDesc(corpDef);
			
		}
		logger.debug("created Corpus Def for testcase 1");
		
		//start: create sample
			//start:create corpus structure
				
			SDocument sDoc = this.createCorpusStructure(sCorpusGraph);
			sDoc.setSDocumentGraph(null);
		//logger.debug("Created corpus structure for sDocument");	
		//end: create sample
		
		//start: exporting document
			this.start();
		//end: exporting document
		//logger.debug("Finished Export");
		
		{//checking if export was correct
			assertTrue("The Corpus Structure was not created", currentFile.isFile());
			assertTrue("The Corpus Structure is wrong (" + currentFile + ")", this.compareFiles(expectedURI, currentURI));
		}
	}
	
	//TODO incomment this test in next version
//	public void testCreatePrimaryData() throws IOException{
//		File corpusPathFile= new File("./_TMP/de.hu_berlin.german.korpling.saltnpepper.pepperModules.DotModules/Exporter/testcase2");
//		File currentFile = new File("./_TMP/de.hu_berlin.german.korpling.saltnpepper.pepperModules.DotModules/Exporter/testcase2/corp1/doc1.dot");
//		File expectedFile= new File("./src/test/resources/expected/Exporter/testcase2/corp1/doc1.dot");
//		
//		URI corpusPath= URI.createFileURI(corpusPathFile.getCanonicalPath());
//		URI currentURI = URI.createFileURI(currentFile.getCanonicalPath());
//		URI expectedURI= URI.createFileURI(expectedFile.getCanonicalPath());
//				
//		this.removeDirRec(new File(corpusPath.toFileString()));
//		
//		{//creating and setting corpus definition
//			CorpusDefinition corpDef= PepperModulesFactory.eINSTANCE.createCorpusDefinition();
//			FormatDefinition formatDef= PepperModulesFactory.eINSTANCE.createFormatDefinition();
//			formatDef.setFormatName("dot");
//			formatDef.setFormatVersion("1.0");
//			corpDef.setFormatDefinition(formatDef);
//			corpDef.setCorpusPath(corpusPath);
//			this.getFixture().setCorpusDefinition(corpDef);
//			
//		}
////		logger.debug("created Corpus Def for testcase 1");
//		
//		//start: create sample
//			//start:create corpus structure
//				
//			SDocument sDoc = this.createCorpusStructure(sCorpusGraph);
//			SaltSample.createPrimaryData(sDoc);
//
////		logger.debug("Created corpus structure for sDocument");	
//		//end: create sample
//		
//		//start: exporting document
//			this.start();
//		//end: exporting document
////		logger.debug("Finished Export");
//		
//		{//checking if export was correct
//			assertTrue("The Document Structure was not created", currentFile.isFile());
//			assertTrue("The Document Structure is wrong (" + currentURI + ")", this.compareFiles(expectedURI, currentURI));
//		}
//	
//	}

	//TODO incomment this test in next version
//	public void testCreateToken() throws IOException{
//		File corpusPathFile= new File("./_TMP/de.hu_berlin.german.korpling.saltnpepper.pepperModules.DotModules/Exporter/testcase3");
//		File currentFile = new File("./_TMP/de.hu_berlin.german.korpling.saltnpepper.pepperModules.DotModules/Exporter/testcase3/corp1/doc1.dot");
//		File expectedFile= new File("./src/test/resources/expected/Exporter/testcase3/corp1/doc1.dot");
//		
//		URI corpusPath= URI.createFileURI(corpusPathFile.getCanonicalPath());
//		URI currentURI = URI.createFileURI(currentFile.getCanonicalPath());
//		URI expectedURI= URI.createFileURI(expectedFile.getCanonicalPath());
//				
//		this.removeDirRec(new File(corpusPath.toFileString()));
//		
//		{//creating and setting corpus definition
//			CorpusDefinition corpDef= PepperModulesFactory.eINSTANCE.createCorpusDefinition();
//			FormatDefinition formatDef= PepperModulesFactory.eINSTANCE.createFormatDefinition();
//			formatDef.setFormatName("dot");
//			formatDef.setFormatVersion("1.0");
//			corpDef.setFormatDefinition(formatDef);
//			corpDef.setCorpusPath(corpusPath);
//			this.getFixture().setCorpusDefinition(corpDef);
//			
//		}
////		logger.debug("created Corpus Def for testcase 1");
//		
//		//start: create sample
//			//start:create corpus structure
//				
//			SDocument sDoc = this.createCorpusStructure(sCorpusGraph);
//			//sDoc.setSDocumentGraph(null);
//			SaltSample.createPrimaryData(sDoc);
//			SaltSample.createTokens(sDoc);
////		logger.debug("Created corpus structure for sDocument");	
//		//end: create sample
//		
//		//start: exporting document
//			this.start();
//		//end: exporting document
////		logger.debug("Finished Export");
//		
//		{//checking if export was correct
//			assertTrue("The Document Structure was not created", currentFile.isFile());
//			assertTrue("The Document Structure is wrong (" + currentURI + ")", this.compareFiles(expectedURI, currentURI));
//		}
//	
//	}

	//TODO incomment this test in next version
//	public void testCreateMorphAnnotation() throws IOException{
//		File corpusPathFile= new File("./_TMP/de.hu_berlin.german.korpling.saltnpepper.pepperModules.DotModules/Exporter/testcase4");
//		File currentFile = new File("./_TMP/de.hu_berlin.german.korpling.saltnpepper.pepperModules.DotModules/Exporter/testcase4/corp1/doc1.dot");
//		File expectedFile= new File("./src/test/resources/expected/Exporter/testcase4/corp1/doc1.dot");
//		
//		URI corpusPath= URI.createFileURI(corpusPathFile.getCanonicalPath());
//		URI currentURI = URI.createFileURI(currentFile.getCanonicalPath());
//		URI expectedURI= URI.createFileURI(expectedFile.getCanonicalPath());
//				
//		this.removeDirRec(new File(corpusPath.toFileString()));
//		
//		{//creating and setting corpus definition
//			CorpusDefinition corpDef= PepperModulesFactory.eINSTANCE.createCorpusDefinition();
//			FormatDefinition formatDef= PepperModulesFactory.eINSTANCE.createFormatDefinition();
//			formatDef.setFormatName("dot");
//			formatDef.setFormatVersion("1.0");
//			corpDef.setFormatDefinition(formatDef);
//			corpDef.setCorpusPath(corpusPath);
//			this.getFixture().setCorpusDefinition(corpDef);
//			
//		}
////		logger.debug("created Corpus Def for testcase 1");
//		
//		//start: create sample
//			//start:create corpus structure
//				
//			SDocument sDoc = this.createCorpusStructure(sCorpusGraph);
//			SaltSample.createPrimaryData(sDoc);
//			SaltSample.createTokens(sDoc);
//			SaltSample.createMorphologyAnnotations(sDoc);
////		logger.debug("Created corpus structure for sDocument");	
//		//end: create sample
//		
//		//start: exporting document
//			this.start();
//		//end: exporting document
////		logger.debug("Finished Export");
//		
//		{//checking if export was correct
////			char [] cbuf = new char [1024];
////			new FileReader(currentFile).read(cbuf);
////			System.out.println(cbuf);
//			assertTrue("The Document structure was not created", currentFile.isFile());
//			assertTrue("The Document structure is wrong (" + currentURI + ")", this.compareFiles(expectedURI, currentURI));
//		}
//	
//	}
	
	//TODO incomment this test in next version
//	public void testCreateSpans() throws IOException{
//		File corpusPathFile= new File("./_TMP/de.hu_berlin.german.korpling.saltnpepper.pepperModules.DotModules/Exporter/testcase5");
//		File currentFile = new File("./_TMP/de.hu_berlin.german.korpling.saltnpepper.pepperModules.DotModules/Exporter/testcase5/corp1/doc1.dot");
//		File expectedFile= new File("./src/test/resources/expected/Exporter/testcase5/corp1/doc1.dot");
//		
//		URI corpusPath= URI.createFileURI(corpusPathFile.getCanonicalPath());
//		URI currentURI = URI.createFileURI(currentFile.getCanonicalPath());
//		URI expectedURI= URI.createFileURI(expectedFile.getCanonicalPath());
//				
//		this.removeDirRec(new File(corpusPath.toFileString()));
//		
//		{//creating and setting corpus definition
//			CorpusDefinition corpDef= PepperModulesFactory.eINSTANCE.createCorpusDefinition();
//			FormatDefinition formatDef= PepperModulesFactory.eINSTANCE.createFormatDefinition();
//			formatDef.setFormatName("dot");
//			formatDef.setFormatVersion("1.0");
//			corpDef.setFormatDefinition(formatDef);
//			corpDef.setCorpusPath(corpusPath);
//			this.getFixture().setCorpusDefinition(corpDef);
//			
//		}
////		logger.debug("created Corpus Def for testcase 1");
//		
//		//start: create sample
//			//start:create corpus structure
//				
//			SDocument sDoc = this.createCorpusStructure(sCorpusGraph);
//			SaltSample.createPrimaryData(sDoc);
//			SaltSample.createTokens(sDoc);
//			SaltSample.createMorphologyAnnotations(sDoc);
//			SaltSample.createInformationStructureSpan(sDoc);
////		logger.debug("Created corpus structure for sDocument");	
//		//end: create sample
//		
//		//start: exporting document
//			this.start();
//		//end: exporting document
////		logger.debug("Finished Export");
//		
//		{//checking if export was correct
////			char [] cbuf = new char [1024];
////			new FileReader(currentFile).read(cbuf);
////			System.out.println(cbuf);
//			assertTrue("The Document Structure was not created", currentFile.isFile());
//			assertTrue("The Document Structure is wrong", this.compareFiles(expectedURI, currentURI));
//		}
//	
//	}
	
	//TODO incomment this test in next version
//	public void testCreateSpanAnnotation() throws IOException{
//		File corpusPathFile= new File("./_TMP/de.hu_berlin.german.korpling.saltnpepper.pepperModules.DotModules/Exporter/testcase6");
//		File currentFile = new File("./_TMP/de.hu_berlin.german.korpling.saltnpepper.pepperModules.DotModules/Exporter/testcase6/corp1/doc1.dot");
//		File expectedFile= new File("./src/test/resources/expected/Exporter/testcase6/corp1/doc1.dot");
//		
//		URI corpusPath= URI.createFileURI(corpusPathFile.getCanonicalPath());
//		URI currentURI = URI.createFileURI(currentFile.getCanonicalPath());
//		URI expectedURI= URI.createFileURI(expectedFile.getCanonicalPath());
//				
//		this.removeDirRec(new File(corpusPath.toFileString()));
//		
//		{//creating and setting corpus definition
//			CorpusDefinition corpDef= PepperModulesFactory.eINSTANCE.createCorpusDefinition();
//			FormatDefinition formatDef= PepperModulesFactory.eINSTANCE.createFormatDefinition();
//			formatDef.setFormatName("dot");
//			formatDef.setFormatVersion("1.0");
//			corpDef.setFormatDefinition(formatDef);
//			corpDef.setCorpusPath(corpusPath);
//			this.getFixture().setCorpusDefinition(corpDef);
//			
//		}
////		logger.debug("created Corpus Def for testcase 1");
//		
//		//start: create sample
//			//start:create corpus structure
//				
//			SDocument sDoc = this.createCorpusStructure(sCorpusGraph);
//			//sDoc.setSDocumentGraph(null);
//			SaltSample.createPrimaryData(sDoc);
//			SaltSample.createTokens(sDoc);
//			SaltSample.createMorphologyAnnotations(sDoc);
//			SaltSample.createInformationStructureSpan(sDoc);
//			SaltSample.createInformationStructureAnnotations(sDoc);
////		logger.debug("Created corpus structure for sDocument");	
//		//end: create sample
//		
//		//start: exporting document
//			this.start();
//		//end: exporting document
////		logger.debug("Finished Export");
//		
//		{//checking if export was correct
////			char [] cbuf = new char [1024];
////			new FileReader(currentFile).read(cbuf);
////			System.out.println(cbuf);
//			assertTrue("The Document Structure was not created", currentFile.isFile());
//			assertTrue("The Document Structure is wrong (" + currentURI + ")", this.compareFiles(expectedURI, currentURI));
//		}
//	
//	}
	
//	@Test
//	public void SetGetCorpusDefinition()
//	{
//		//TODO somethong to test???
//		CorpusDesc corpDef= new CorpusDesc();
//		FormatDesc formatDef= new FormatDesc();
//		formatDef.setFormatName("dot");
//		formatDef.setFormatVersion("1.0");
//		corpDef.setFormatDesc(formatDef);
//	}
	
	
	/**
	 * Creates a corpus structure with one corpus and one document. It returns the created document.
	 * 		corp1
	 *		|
	 *		doc1
	 * @param corpGraph 
	 * @return
	 */
	private SDocument createCorpusStructure(SCorpusGraph corpGraph)
	{
		{//creating corpus structure
			corpGraph= SaltFactory.eINSTANCE.createSCorpusGraph();
			this.getFixture().getSaltProject().getSCorpusGraphs().add(corpGraph);
			//		corp1
			//		|
			//		doc1
			
			//corp1
			SElementId sElementId= SaltFactory.eINSTANCE.createSElementId();
			sElementId.setSId("corp1");
			SCorpus corp1= SaltFactory.eINSTANCE.createSCorpus();
			corp1.setSName("corp1");
			corp1.setId("corp1");
			corp1.setSElementId(sElementId);
			corpGraph.addSNode(corp1);
			
			
			//doc1
			SDocument doc1= SaltFactory.eINSTANCE.createSDocument();
			sElementId= SaltFactory.eINSTANCE.createSElementId();
			sElementId.setSId("corp1/doc1");
			doc1.setSElementId(sElementId);
			doc1.setSName("doc1");
			corpGraph.addSNode(doc1);
			doc1.setSDocumentGraph(SaltFactory.eINSTANCE.createSDocumentGraph());
			//CorpDocRel
			SCorpusDocumentRelation corpDocRel1= SaltFactory.eINSTANCE.createSCorpusDocumentRelation();
			sElementId= SaltFactory.eINSTANCE.createSElementId();
			sElementId.setSId("rel1");
			corpDocRel1.setSElementId(sElementId);
			corpDocRel1.setSName("rel1");
			corpDocRel1.setSCorpus(corp1);
			corpDocRel1.setSDocument(doc1);
			corpGraph.addSRelation(corpDocRel1);
			return(doc1);
		}
	}
	
	private void removeDirRec(File dir)
	{
		if (dir != null)
		{
			if (dir.listFiles()!= null && dir.listFiles().length!= 0)
			{	
				for (File subDir: dir.listFiles())
				{
					this.removeDirRec(subDir);
				}
			}
			dir.delete();
		}
	}
}