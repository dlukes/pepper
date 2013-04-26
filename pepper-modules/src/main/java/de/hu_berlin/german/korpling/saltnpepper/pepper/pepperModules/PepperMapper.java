package de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules;

import org.eclipse.emf.common.util.URI;
import org.osgi.service.log.LogService;

import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SCorpus;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SDocument;

/**
 * This interface can be used in a threaded environment, where the {@link PepperModule} is used for delegating thread jobs,
 * and a set of PepperMappers are mapping one {@link SDocument} or {@link SCorpus} object.
 * 
 * @author Florian Zipser
 *
 */
public interface PepperMapper {
	/**
	 * Sets the OSGi logger for this mapper. To be removed, when abstract logging via slf4j is used.
	 * @deprecated
	 * @param logService
	 */
	public void setLogService(LogService logService);
	/**
	 * Returns the OSGi logger for this mapper. To be removed, when abstract logging via slf4j is used.
	 * @deprecated
	 * @return
	 */
	public LogService getLogService();
	
//	/** Returns the connector between calling {@link PepperModule} and this {@link PepperMapper}**/
//	public PepperMapperConnector getMapperConnector();
//	
//	/** Sets the connector between calling {@link PepperModule} and this {@link PepperMapper}**/
//	public void setMapperConnector(PepperMapperConnector mapperConnector);
	
	/**
	 * Sets {@link URI} of resource. The URI could refer a directory or a file, which can be a corpus or a document.
	 * @param resourceURI uri of resource
	 */
	public void setResourceURI(URI resourceURI);
	/**
	 * Returns {@link URI} of resource. The URI could refer a directory or a file, which can be a corpus or a document.
	 * @return uri of resource
	 */
	public URI getResourceURI();
	/**
	 * Returns the {@link SDocument} object to be mapped by this mapper. 
	 * @return {@link SDocument} object to be mapped
	 */
	public SDocument getSDocument();
	
	/**
	 * Sets the {@link SDocument} object to be mapped by this mapper. 
	 * @param sDocument {@link SDocument} object to be mapped
	 */
	public void setSDocument(SDocument sDocument);	
	/**
	 * Returns the {@link SCorpus} object to be mapped by this mapper.
	 * @return {@link SCorpus} object to be mapped
	 */
	public SCorpus getSCorpus();
	/**
	 * Sets the {@link SCorpus} object to be mapped by this mapper.
	 * @param sCorpus {@link SCorpus} object to be mapped
	 */
	public void setSCorpus(SCorpus sCorpus);
	/**
	 * Returns the {@link PepperModuleProperties} object containing user customizations to be observed during the mapping.
	 * @return {@link PepperModuleProperties} object to be used
	 */
	public PepperModuleProperties getProps();
	/**
	 * Sets the {@link PepperModuleProperties} object containing user customizations to be observed during the mapping.
	 * @param props {@link PepperModuleProperties} object to be used
	 */
	public void setProps(PepperModuleProperties props);
	/**
	 * Sets the result of the current mapping, when it is finished. 
	 * @param status of mapping
	 */
	public void setMappingResult(MAPPING_RESULT mappingResult);
	/**
	 * Returns the result of the mapping, when finished.
	 * @return mapping result
	 */
	public MAPPING_RESULT getMappingResult();
	/**
	 * This method starts the {@link PepperMapper} object. If {@link #getSCorpus()} is not null,
	 * {@link #mapSCorpus()} is called, if {@link #getSDocument()} is not null, {@link #mapSDocument()} is called.
	 */
	public void map();
	/**
	 * Starts to map a given {@link SDocument} object, if one is given.
	 */
	public MAPPING_RESULT mapSDocument();
	/**
	 * Starts to map a given {@link SCorpus} object, if one is given.
	 */
	public MAPPING_RESULT mapSCorpus();
	
//	/**
//	 * Delegation of {@link Thread#getName()}.
//	 * Returns this thread's name.
//	 * @return this thread's name.
//	 */
//	public String getName();
}
