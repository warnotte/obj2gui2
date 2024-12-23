package io.github.warnotte.obj2gui2.Tests.EnumBug;

import io.github.warnotte.waxlib3.core.TemplatePropertyMerger.property_mode;
import io.github.warnotte.waxlib3.core.TemplatePropertyMerger.Annotations.PROPERTY_interface;
import io.github.warnotte.waxlib3.core.TemplatePropertyMerger.Annotations.PROPERTY_interface.gui_type;

public class Ilot {

	Axe axePanneauSolaire = Axe.X;
	protected String name;
	
	public Ilot(int id, Axe axePanneauSolaire) {
		name = "Ilot_"+id;
		this.axePanneauSolaire = axePanneauSolaire;
	}
	
	public void duplicateParameters(Ilot ii) {
		this.axePanneauSolaire = ii.axePanneauSolaire;
	}
	

	@PROPERTY_interface(Operation = property_mode.PROPERTY_MERGEABLE, gui_type=gui_type.COMBO,  orderDisplay = 20)
	public Axe getAxePanneauSolaire() {
		return axePanneauSolaire;
	}

	public void setAxePanneauSolaire(Axe axePanneauSolaire) {
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
