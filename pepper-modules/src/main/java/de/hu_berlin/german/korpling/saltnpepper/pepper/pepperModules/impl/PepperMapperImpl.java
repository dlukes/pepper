package de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.impl;

import org.eclipse.emf.common.util.URI;
import org.osgi.service.log.LogService;

import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.MAPPING_RESULT;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperMapper;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperMapperController;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperModule;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperModuleProperties;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.exceptions.NotInitializedException;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SCorpus;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SDocument;

/**
 * An abstract implementation of {@link PepperMapper} to be used for further derivations for specific mapping
 * purposes.
 * 
 * @author Florian Zipser
 *
 */
public class PepperMapperImpl implements PepperMapper {
	
	public PepperMapperImpl()
	{
		this.initialize();
	}
	
//	/**
//	 * Initializes this object and sets its {@link ThreadGroup} and the name of the thread.
//	 * @param threadGroup
//	 * @param threadName
//	 */
//	public PepperMapperImpl(PepperMapperConnector connector, ThreadGroup threadGroup, String threadName)
//	{
//		super(threadGroup,threadName);
//		System.out.println("---------------------------> after super super: ");
//		this.setMapperConnector(connector);
//		this.initialize();
//		System.out.println("---------------------------> end of constructor PepperMapper: ");
//	}
	
	/**
	 * OSGi logger for this mapper. To be removed, when abstract logging via slf4j is used.
	 * @deprecated
	 */
	private LogService logService;

	public void setLogService(LogService logService) 
	{
		this.logService = logService;
	}
	
	public LogService getLogService() 
	{
		return(this.logService);
	}
	
//	/** connector class between calling {@link PepperModule} and this {@link PepperMapper}**/
//	protected PepperMapperConnector mapperConnector= null;
//	
//	/** {@inheritDoc PepperMapper#getMapperConnector()} **/
//	public PepperMapperConnector getMapperConnector() {
//		return mapperConnector;
//	}
//	/** {@inheritDoc PepperMapper#setMapperConnector(PepperMapperConnector)} **/
//	public void setMapperConnector(PepperMapperConnector mapperConnector) {
//		this.mapperConnector = mapperConnector;
//	}

	/**
	 * {@link URI} of resource. The URI could refer a directory or a file, which can be a corpus or a document.
	 */
	protected URI resourceURI= null;
	/**
	 * {@inheritDoc PepperMapper#getResourceURI()}
	 */
	public URI getResourceURI() {
		return(resourceURI);
	}
	/**
	 * {@inheritDoc PepperMapper#setResourceURI(URI)}
	 */
	public void setResourceURI(URI resourceURI) {
		this.resourceURI= resourceURI;
	}
	/**
	 * {@link SDocument} object to be created/ fullfilled during the mapping.
	 */
	protected SDocument sDocument= null;
	/**
	 * {@inheritDoc PepperMapper#getSDocument()}
	 */
	@Override
	public SDocument getSDocument() {
		return(sDocument);
	}
	/**
	 * {@inheritDoc PepperMapper#setSDocument(SDocument)}
	 */
	@Override
	public void setSDocument(SDocument sDocument) {
		this.sDocument= sDocument;
	}
	/**
	 * {@link SCorpus} object to be created/ fullfilled during the mapping.
	 */
	protected SCorpus sCorpus= null;
	
	public SCorpus getSCorpus() {
		return sCorpus;
	}

	/**
	 * {@inheritDoc PepperMapper#setSCorpus(SCorpus)} 
	 */
	public void setSCorpus(SCorpus sCorpus) {
		this.sCorpus = sCorpus;
	}
	/**
	 * {@link PepperModuleProperties} object containing user customizations to be observed during the mapping.
	 */
	protected PepperModuleProperties props= null;
	/**
	 * {@inheritDoc PepperMapper#getProps()} 
	 */
	public PepperModuleProperties getProps() {
		return props;
	}
	/**
	 * {@inheritDoc PepperMapper#setProps(PepperModuleProperties)} 
	 */
	public void setProps(PepperModuleProperties props) {
		this.props = props;
	}

	protected volatile MAPPING_RESULT mappingResult= null;
	/** {@inheritDoc PepperMapperConnector#setMappingResult(MAPPING_RESULT)} **/
	@Override
	public synchronized void setMappingResult(MAPPING_RESULT mappingResult) {
		this.mappingResult= mappingResult;
		
	}
	/** {@inheritDoc PepperMapperConnector#getMappingResult()} **/
	@Override
	public MAPPING_RESULT getMappingResult() {
		return(this.mappingResult);
	}
	
	
	/**
	 * {@inheritDoc PepperMapper#map()}
	 */
	@Override
	public void map() 
	{
		MAPPING_RESULT mappingResult= null;
	
		if (this.getSCorpus()!= null)
			mappingResult= this.mapSCorpus();
		else if (this.getSDocument()!= null)
			mappingResult= this.mapSDocument();
		else
			throw new NotInitializedException("Cannot start mapper, because neither the SDocument nor the SCorpus value is set.");
		
		this.setMappingResult(mappingResult);
	}
	
	/**
	 * This method initializes this object and is called by the constructor.
	 * 
	 * OVERRIDE THIS METHOD FOR CUSTOMIZED MAPPING.
	 */
	protected void initialize()
	{
		
	}
	
	/**
	 * {@inheritDoc PepperMapper#setSDocument(SDocument)}
	 * 
	 * OVERRIDE THIS METHOD FOR CUSTOMIZED MAPPING.
	 */
	@Override
	public MAPPING_RESULT mapSDocument() {
		throw new UnsupportedOperationException("OVERRIDE THIS METHOD FOR CUSTOMIZED MAPPING.");
	}
	/**
	 * {@inheritDoc PepperMapper#setSCorpus(SCorpus)}
	 * 
	 * OVERRIDE THIS METHOD FOR CUSTOMIZED MAPPING.
	 */
	@Override
	public MAPPING_RESULT mapSCorpus() {
		return(MAPPING_RESULT.FINISHED);
	}
}
