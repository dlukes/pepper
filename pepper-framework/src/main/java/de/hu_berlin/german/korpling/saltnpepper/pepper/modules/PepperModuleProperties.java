/**
 * Copyright 2009 Humboldt-Universität zu Berlin, INRIA.
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
package de.hu_berlin.german.korpling.saltnpepper.pepper.modules;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.Vector;

import org.eclipse.emf.common.util.URI;
import org.osgi.service.component.annotations.Property;

import de.hu_berlin.german.korpling.saltnpepper.pepper.exceptions.PepperException;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.exceptions.PepperModulePropertyException;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.SDocumentGraph;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SLayer;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SNode;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SRelation;

/**
 * This class is a container for a set of {@link PepperModuleProperty} objects.
 * This class also offers some methods for accessing and maintaining the
 * objects.
 * 
 * @author Florian Zipser
 * 
 */
public class PepperModuleProperties implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7802558539252580678L;
	/**
	 * Prefixes all customization properties directly provided by Pepper (
	 * {@link PepperModule}).
	 **/
	public static final String PREFIX_PEPPER = "pepper";
	/**
	 * Prefixes all pre processing customization properties directly provided by
	 * Pepper ({@link PepperModule}). A pre processing property is handled by
	 * method
	 * {@link PepperModule#before(de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SElementId)}
	 * before
	 * {@link PepperModule#start(de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SElementId)}
	 * is called.
	 **/
	public static final String PREFIX_PEPPER_BEFORE = PREFIX_PEPPER + ".before.";
	/**
	 * Prefixes all post processing customization properties directly provided
	 * by Pepper ({@link PepperModule}). A post processing property is handled
	 * by method
	 * {@link PepperModule#after(de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SElementId)}
	 * after
	 * {@link PepperModule#start(de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SElementId)}
	 * is called.
	 **/
	public static final String PREFIX_PEPPER_AFTER = PREFIX_PEPPER + ".after.";
	/**
	 * Consumes a semicolon separated list of names for {@link SLayer} objects.
	 * For each list element, one {@link SLayer} is created and added to all
	 * {@link SNode} and {@link SRelation} objects of a {@link SDocumentGraph}
	 * object.
	 */
	public static final String PROP_AFTER_ADD_SLAYER = PREFIX_PEPPER_AFTER + "addSLayer";

	/**
	 * Copies one or more source files to one or more target files after
	 * processing. This is very helpful, in case of customizations should be
	 * done in target format. If you use relative paths, the are anchored to
	 * either the location of the workflow description file or where Pepper was
	 * started. Syntax is: SOURCE_FILE -> TARGET_FILE (; SOURCE_FILE ->
	 * TARGET_FILE)*
	 * 
	 */
	public static final String PROP_AFTER_COPY_RES = PREFIX_PEPPER_AFTER + "copyRes";
	/**
	 * Consumes a semicolon separated list of names for {@link SLayer} objects.
	 * For each list element, one {@link SLayer} is created and added to all
	 * {@link SNode} and {@link SRelation} objects of a {@link SDocumentGraph}
	 * object.
	 */
	public static final String PROP_BEFORE_ADD_SLAYER = PREFIX_PEPPER_BEFORE + "addSLayer";
	/**
	 * Reads meta data for corpora and subcorpora in a very simple
	 * attribute-value format like:<br/>
	 * a=b<br/>
	 * c=d<br/>
	 * To enable the reading of meta data set this property to the file ending
	 * of the metadata file. For instance in case of the file is named
	 * data.meta: {@value #PROP_BEFORE_READ_META}=meta
	 */
	public static final String PROP_BEFORE_READ_META = PREFIX_PEPPER_BEFORE + "readMeta";

	/**
	 * Prints the corpus graph to standard out after a module has processed it.
	 * This property is mainly used for importers, to visualize the created
	 * corpus structure.
	 */
	public static final String PROP_AFTER_REPORT_CORPUSGRAPH = PREFIX_PEPPER_AFTER + "reportCorpusGraph";

	/**
	 * Creates instance of {@link PepperModuleProperties} and initializes it
	 * with a set of customization properties. These properties are:
	 * <ul>
	 * <li>{@link #PROP_AFTER_ADD_SLAYER}</li>
	 * </ul>
	 */
	public PepperModuleProperties() {
		addProperty(new PepperModuleProperty<String>(PROP_BEFORE_ADD_SLAYER, String.class, "Consumes a semicolon separated list of names for {@link SLayer} objects. For each list element, one layer is created and added to all nodes and relations of a document-structure before the mapping was processed."));
		addProperty(new PepperModuleProperty<String>(PROP_BEFORE_READ_META, String.class, "Reads meta data for corpora and subcorpora in a very simple attribute-value format like: a=b. To enable the reading of meta data set this property to the file ending of the metadata file.  For instance in case of the file is named data.meta: {@value #PROP_BEFORE_READ_META}=meta"));
		addProperty(new PepperModuleProperty<String>(PROP_AFTER_ADD_SLAYER, String.class, "Consumes a semicolon separated list of names for {@link SLayer} objects. For each list element, one layer is created and added to all nodes and relations of a document-structure after the mapping was processed."));
		addProperty(new PepperModuleProperty<String>(PROP_AFTER_COPY_RES, String.class, "Copies one or more source files to one or more target files after processing. This is very helpful, in case of customizations should be done in target format. If you use relative paths, the are anchored to either the location of the workflow description file or where Pepper was started. The syntax is as follows: SOURCE_FILE -> TARGET_FILE (; SOURCE_FILE -> TARGET_FILE)*."));
		addProperty(new PepperModuleProperty<Boolean>(PROP_AFTER_REPORT_CORPUSGRAPH, Boolean.class, "When set to true, prints the corpus graph to standard out after a module has processed it. This property is mainly used for importers, to visualize the created corpus structure. The default value is 'false'.", false, false));
	}

	/**
	 * Loads the given file via {@link Properties#load(java.io.InputStream)} and
	 * adds all properties given in the passed {@link Property} object. That
	 * means, the corresponding {@link PepperModuleProperty} will be searched
	 * and its value will be set to the one found in the passed
	 * {@link Properties} object. If no corresponding
	 * {@link PepperModuleProperties} object corresponds to one of the
	 * properties contained in the passed {@link Property} object, a new one
	 * will be created.
	 */
	public void addProperties(URI propURI) {
		if (propURI != null) {
			this.setPropertyValues(new File(propURI.toFileString()));
		}
	}

	/**
	 * Returns a new {@link Properties} object containing all property names and
	 * their values.
	 * 
	 * @return new {@link Properties} object
	 */
	public Properties getProperties() {
		Properties retVal = new Properties();
		Collection<String> names = this.getPropertyNames();
		if (names != null) {
			for (String name : names) {
				if (name != null) {
					PepperModuleProperty<?> prop = getProperty(name);
					if (prop.getValue() != null) {
						retVal.put(name, prop.getValue());
					}
				}
			}
		}
		return (retVal);
	}

	/**
	 * Loads the given file via {@link Properties#load(java.io.InputStream)} and
	 * adds all properties given in the passed {@link Property} object. That
	 * means, the corresponding {@link PepperModuleProperty} will be searched
	 * and its value will be set to the one found in the passed
	 * {@link Properties} object. If no corresponding
	 * {@link PepperModuleProperties} object corresponds to one of the
	 * properties contained in the passed {@link Property} object, a new one
	 * will be created.
	 */
	public void setPropertyValues(File propFile) {
		if ((propFile != null) && (propFile.exists())) {
			Properties props = new Properties();
			try {
				props.load(new FileInputStream(propFile));
			} catch (FileNotFoundException e) {
				throw new PepperModulePropertyException("Cannot load property file.", e);
			} catch (IOException e) {
				throw new PepperModulePropertyException("Cannot load property file.", e);
			}
			this.setPropertyValues(props);
		}
	}

	/**
	 * Adds all properties given in the passed {@link Property} object. That
	 * means, the corresponding {@link PepperModuleProperty} will be searched
	 * and its value will be set to the one found in the passed
	 * {@link Properties} object. If no corresponding
	 * {@link PepperModuleProperties} object corresponds to one of the
	 * properties contained in the passed {@link Property} object, a new one
	 * will be created.
	 */
	public void setPropertyValues(Properties properties) {
		if (properties != null) {
			Set<Object> keys = properties.keySet();
			for (Object key : keys) {
				this.setPropertyValue(key.toString(), properties.get(key));
			}
		}
	}

	/**
	 * Searches for a {@link PepperModuleProperty} object in registered
	 * {@link PepperModuleProperty} objects and sets its value attribute, if a
	 * {@link PepperModuleProperty} object was found.
	 * 
	 * @param propName
	 *            name of property to search for
	 * @param propValue
	 *            value to which {@link PepperModuleProperty}s value attribute
	 *            is set to
	 */
	public <T> void setPropertyValue(String propName, T propValue) {
		PepperModuleProperty<?> prop = this.getProperty(propName);
		if (prop != null) {
			prop.setValueString(propValue.toString());
		} else {
			prop = new PepperModuleProperty<String>(propName, String.class, "this entry is automatically created by pepper and no description exists.");
			prop.setValueString(propValue.toString());
			this.addProperty(prop);
		}
		this.checkProperty(prop);
	}

	/**
	 * Checks if all properties marked as required are really set. Throws a
	 * {@link PepperModulePropertyException} if a required value is not set.
	 */
	public boolean checkProperties() {
		Collection<PepperModuleProperty<?>> properties = this.getPropertyDesctriptions();
		for (PepperModuleProperty<?> prop : properties) {
			this.checkProperty(prop);
		}
		return (true);
	}

	/**
	 * Checks if the value of given property, when marked as required is really
	 * set. Throws a {@link PepperModulePropertyException} if a required value
	 * is not set.
	 */
	public boolean checkProperty(PepperModuleProperty<?> prop) {
		if ((prop.isRequired()) && (prop.getValue() == null))
			throw new PepperModulePropertyException("The following property is required, but its value was not set: " + prop);
		return (true);
	}

	/**
	 * Internal map to map all {@link PepperModuleProperty} objects to their
	 * name.
	 */
	protected Map<String, PepperModuleProperty<?>> pepperModuleProperties = null;

	/**
	 * Adds the given {@link PepperModuleProperty} object to the internal list.
	 * 
	 * @param property
	 */
	public void addProperty(PepperModuleProperty<?> property) {
		if ((property.getName() == null) || (property.getName().isEmpty()))
			throw new PepperException("Cannot add a property description without a name.");
		if (pepperModuleProperties == null) {
			pepperModuleProperties = new HashMap<String, PepperModuleProperty<?>>();
		}
		pepperModuleProperties.put(property.getName(), property);
	}

	/**
	 * Returns a {@link PepperModuleProperty} object corresponding to the given
	 * property name.
	 * 
	 * @param propName
	 *            name of the property
	 * @return {@link PepperModuleProperty} object
	 */
	public PepperModuleProperty<?> getProperty(String propName) {
		PepperModuleProperty<?> retVal = null;
		if (this.pepperModuleProperties != null) {
			retVal = this.pepperModuleProperties.get(propName);
		}
		return (retVal);
	}

	/**
	 * Returns all property names registered in that object, and therefore
	 * usable for the corresponding {@link PepperModule}.
	 * 
	 * @return
	 */
	public Collection<String> getPropertyNames() {
		Collection<String> names = new Vector<String>();
		if (pepperModuleProperties != null) {
			Set<String> keys = pepperModuleProperties.keySet();
			for (String key : keys) {
				names.add(key);
			}
		}
		return (names);
	}

	/**
	 * Returns all registered {@link PepperModuleProperty} objects, which are
	 * usable for the corresponding {@link PepperModule}.
	 * 
	 * @return
	 */
	public Collection<PepperModuleProperty<?>> getPropertyDesctriptions() {
		List<PepperModuleProperty<?>> retVal = new ArrayList<PepperModuleProperty<?>>(pepperModuleProperties.values());
		Collections.sort(retVal);
		return (retVal);
	}

	/**
	 * Removes the value of the property with the passed property name from the
	 * properties.
	 * 
	 * @param propName
	 *            name of the property to be removed
	 */
	public void removePropertyValue(String propName) {
		PepperModuleProperty<?> prop = pepperModuleProperties.get(propName);
		prop.setValue(null);
	}

	/**
	 * Expects a list of characters encoded as a String. The String is split and
	 * returned as a list of characters. The list must be build like this: <br/>
	 * LIST: ITEM (,ITEM)* <br/>
	 * ITEM: 'CHARACTER' <br/>
	 * <br/>
	 * For instance the passed String "'a', 'b', 'c'" is returned as a list
	 * containing 'a', 'b' and 'c'. In case of the characters "'" or
	 * "\" are used as items, they must be escaped as "\'" or "\\".
	 * 
	 * @return the isToTokenize
	 */
	public List<Character> stringToCharList(String input) {
		List<Character> simpleTokSeparators = new ArrayList<Character>();
		boolean isOpen = false;
		boolean isEscaped = false;
		for (char chr : input.toCharArray()) {
			if ((chr == '\\') && (!isEscaped)) {
				// if current character is an escape character
				isEscaped = true;
			} else if ((chr == '\'') && (!isEscaped)) {
				// if current character is an not escaped apos, flip isOpen
				isOpen = !isOpen;
			} else if ((isOpen)) {
				simpleTokSeparators.add(chr);
				if (chr == '\\') {
					// disable isEscaped in case of character was an escaped
					// backslash (see //)
					isEscaped = false;
				}
			}

			if (chr != '\\') {
				isEscaped = false;
			}
		}
		return simpleTokSeparators;
	}

	public String toString() {
		StringBuffer buf = new StringBuffer();
		buf.append("[");
		for (String name : getPropertyNames()) {
			PepperModuleProperty<?> prop = getProperty(name);
			buf.append(prop + ", ");
		}
		buf.append("]");
		return (buf.toString());
	}
}
