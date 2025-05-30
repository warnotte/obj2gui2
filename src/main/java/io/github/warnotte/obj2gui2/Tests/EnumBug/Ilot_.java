package io.github.warnotte.obj2gui2.Tests.EnumBug;

import io.github.warnotte.waxlib3.core.TemplatePropertyMerger.property_mode;
import io.github.warnotte.waxlib3.core.TemplatePropertyMerger.Annotations.PROPERTY_interface;
import io.github.warnotte.waxlib3.core.TemplatePropertyMerger.Annotations.PROPERTY_interface.gui_type;

public class Ilot_ {

	Axe_ axePanneauSolaire = Axe_.X;
	protected String name;
	
	public Ilot_(int id, Axe_ axePanneauSolaire) {
		name = "Ilot_"+id;
		this.axePanneauSolaire = axePanneauSolaire;
	}
	
	public void duplicateParameters(Ilot_ ii) {
		this.axePanneauSolaire = ii.axePanneauSolaire;
	}
	

	@PROPERTY_interface(Operation = property_mode.PROPERTY_MERGEABLE, gui_type=gui_type.COMBO,  orderDisplay = 20)
	public Axe_ getAxePanneauSolaire() {
		return axePanneauSolaire;
	}

	public void setAxePanneauSolaire(Axe_ axePanneauSolaire) {
		this.axePanneauSolaire = axePanneauSolaire;
	}

	@PROPERTY_interface(Operation = property_mode.PROPERTY_MERGEABLE, orderDisplay = 10)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Ilot [axePanneauSolaire=" + axePanneauSolaire + ", name=" + name + "]";
	}
	
	

}
