package org.corpus_tools.pepper.gui.components;

import org.corpus_tools.pepper.common.MODULE_TYPE;
import org.corpus_tools.pepper.gui.controller.PepperGUIController;

import com.vaadin.annotations.DesignRoot;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Component;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;

@DesignRoot
public class PepperGuiManipulatorsView extends PepperGuiView {
	private boolean isInit = false;
	private ListSelect manipulatorsList;
	private Table propertiesTable;
	private Table descriptionTable;
	private AbsoluteLayout details;
	
	public PepperGuiManipulatorsView(){
		super();
		setModuleType(MODULE_TYPE.MANIPULATOR);
	}
	
	@Override
	public void attach(){
		super.attach();
		if (!isInit){
			PepperGUIController controller = (PepperGUIController)getUI();
			isInit = true;
		}
	}

	/**
	 * Returns null for this class.
	 */
	@Override
	public TextField getPathField() {				
		return null;
	}

	@Override
	public Table getDescriptionTable() {
		return descriptionTable;
	}

	@Override
	public Table getPropertiesTable() {
		return propertiesTable;
	}

	@Override
	public Component getDetailsComponent() {
		return details;
	}

	@Override
	public ListSelect getModuleSelector() {
		return manipulatorsList;
	}
}