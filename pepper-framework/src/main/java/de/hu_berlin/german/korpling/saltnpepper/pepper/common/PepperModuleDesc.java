package de.hu_berlin.german.korpling.saltnpepper.pepper.common;

import java.util.List;
import java.util.Vector;

import org.eclipse.emf.common.util.URI;

/**
 * This class is a kind of a fingerprint of a Pepper module and provides some information about a
 * module. 
 * 
 * @author Florian Zipser
 *
 */
public class PepperModuleDesc {

	/** name of the  Pepper module **/
	private String name= null;

	/**
	 * Returns the name of a Pepper module described by this {@link PepperModuleDesc} object.
	 * @return name of the Pepper module
	 */
	public String getName() {
		return name;
	}
	/**
	 * Sets the name of a Pepper module described by this {@link PepperModuleDesc} object.
	 * @param moduleName name of the Pepper module
	 */
	public void setName(String moduleName) {
		this.name = moduleName;
	}
	
	/** version of the Pepper module**/
	private String version= null;
	/**
	 * Returns the version of a Pepper module described by this {@link PepperModuleDesc} object.
	 * @return version of the Pepper module
	 */
	public String getVersion() {
		return version;
	}
	/**
	 * Sets the version of a Pepper module described by this {@link PepperModuleDesc} object.
	 * @param version of the Pepper module
	 */
	public void setVersion(String version) {
		this.version = version;
	}
	
	/** type of the pepper module **/
	private MODULE_TYPE moduleType= null;
	/**
	 * Returns the type of this module.
	 * @return type of module
	 */
	public MODULE_TYPE getModuleType(){
		return(moduleType);
	}
	/**
	 * Sets the type of this module.
	 * @param moduleType type of module
	 */
	public void setModuleType(MODULE_TYPE moduleType){
		this.moduleType= moduleType;
	}
	
	
	/** Some description of the function of this module**/
	private String desc= null;
	/**
	 * Returns a short description of this module. Please support some information, for the user, of
	 * what task this module does. 
	 * @return a short description of the task of this module
	 */
	public String getDesc() {
		return desc;
	}
	/**
	 * Sets a short description of this module. Please support some information, for the user, of
	 * what task this module does. 
	 * @param desc a short description of the task of this module
	 */
	public void setDesc(String desc) {
		this.desc= desc;	
	}

	protected URI supplierContact= null;
	/**
	 * Returns a uri where to find more information about this module and where to find some contact 
	 * information to contact the supllier.
	 * @return contact address like eMail address or homepage address
	 */
	public URI getSupplierContact() {
		return(supplierContact);
	}
	/**
	 * Sets a uri where to find more information about this module and where to find some contact 
	 * information to contact the supllier.
	 * @param uri contact address like eMail address or homepage address
	 */
	public void setSupplierContact(URI supplierContact) {
		this.supplierContact= supplierContact;
	}
	
	/**
	 * a list of all formats supported by the Pepper module, this object describes
	 */
	private List<FormatDesc> supportedFormats;
	/**
	 * Returns a list of {@link FormatDesc} objects describing all formats supported by the 
	 * Pepper module, this object describes.
	 * @return list of format descriptions
	 */
	public List<FormatDesc> getSupportedFormats() {
		if (supportedFormats == null) {
			supportedFormats = new Vector<FormatDesc>();
		}
		return supportedFormats;
	}
	/**
	 * Creates a new {@link FormatDesc} object containing the passed name, version and reference to
	 * the list of of {@link FormatDesc} objects describing all formats supported by the 
	 * Pepper module, this object describes.
	 * @param formatName name of the supported format
	 * @param formatVersion version of the supported format
	 * @param formatReference a reference for information about the format if exist
	 * @return a {@link FormatDesc} 
	 */	
	public FormatDesc addSupportedFormat(String formatName, String formatVersion, URI formatReference){
		FormatDesc retVal= null;
		retVal= new FormatDesc();
		retVal.setFormatName(formatName);
		retVal.setFormatVersion(formatVersion);
		retVal.setFormatReference(formatReference);
		getSupportedFormats().add(retVal);
		return(retVal);
	}
	/**
	 * Returns a String representation of this object.
	 * <strong>Please note, that this representation cannot be used for serialization/deserialization purposes</strong>
	 */
	public String toString(){
		StringBuilder str= new StringBuilder();
		str.append("(");
		str.append(getModuleType());
		str.append("::");
		str.append(getName());
		str.append(", ");
		str.append(getVersion());
		str.append(")");
		return(str.toString());
	}
}
