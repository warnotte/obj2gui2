package io.github.warnotte.obj2gui2.experimentOBJ2GUIPlugin3;

import io.github.warnotte.obj2gui2.Tests.SimpleType.Material;
import io.github.warnotte.waxlib3.core.TemplatePropertyMerger.property_mode;
import io.github.warnotte.waxlib3.core.TemplatePropertyMerger.Annotations.PROPERTY_interface;


public class Flotteur {
	
    private String ID_ObjetSurFlotteur;
    
    private Material material;
    private Material materialSupported;
    private Integer idMaterial;
    
    public Flotteur(String iD_ObjetSurFlotteur, Material material, Material materialSupported, Integer idMaterial) {
		super();
		ID_ObjetSurFlotteur = iD_ObjetSurFlotteur;
		this.material = material;
		this.materialSupported = materialSupported;
		this.idMaterial = idMaterial;
	}

	@PROPERTY_interface(Operation = property_mode.PROPERTY_MERGEABLE, orderDisplay = 10)
    public String getID_ObjetSurFlotteur() {
        return ID_ObjetSurFlotteur;
    }

    public void setID_ObjetSurFlotteur(String ID_ObjetSurFlotteur) {
        this.ID_ObjetSurFlotteur = ID_ObjetSurFlotteur;
    }

    
    @PROPERTY_interface(Operation = property_mode.PROPERTY_MERGEABLE, orderDisplay = 20)
	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}

	@PROPERTY_interface(Operation = property_mode.PROPERTY_MERGEABLE, orderDisplay = 30)
	public Material getMaterialSupported() {
		return materialSupported;
	}

	public void setMaterialSupported(Material materialSupported) {
		this.materialSupported = materialSupported;
	}

	@PROPERTY_interface(Operation = property_mode.PROPERTY_MERGEABLE, orderDisplay = 40)
	public Integer getIdMaterial() {
		return idMaterial;
	}

	public void setIdMaterial(Integer idMaterial) {
		this.idMaterial = idMaterial;
	}

	@Override
	public String toString() {
		return "Flotteur [ID_ObjetSurFlotteur=" + ID_ObjetSurFlotteur + "]";
	}
    
    
}