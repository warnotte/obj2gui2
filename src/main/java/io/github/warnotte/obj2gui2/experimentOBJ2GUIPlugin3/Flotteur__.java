package io.github.warnotte.obj2gui2.experimentOBJ2GUIPlugin3;

import io.github.warnotte.obj2gui2.Tests.SimpleType.Material_;
import io.github.warnotte.waxlib3.core.TemplatePropertyMerger.property_mode;
import io.github.warnotte.waxlib3.core.TemplatePropertyMerger.Annotations.PROPERTY_interface;

public class Flotteur__ {

	private String ID_ObjetSurFlotteur;

	private Material_ material;
	private Material_ materialSupported;
	private Integer idMaterial;

	private double valeur;

	public Flotteur__(String iD_ObjetSurFlotteur, Material_ material, Material_ materialSupported, Integer idMaterial,
			double valeur) {
		super();
		ID_ObjetSurFlotteur = iD_ObjetSurFlotteur;
		this.material = material;
		this.materialSupported = materialSupported;
		this.idMaterial = idMaterial;
		this.valeur = valeur;
	}

	@PROPERTY_interface(Operation = property_mode.PROPERTY_MERGEABLE, orderDisplay = 10)
	public String getID_ObjetSurFlotteur() {
		return ID_ObjetSurFlotteur;
	}

	public void setID_ObjetSurFlotteur(String ID_ObjetSurFlotteur) {
		this.ID_ObjetSurFlotteur = ID_ObjetSurFlotteur;
	}

	@PROPERTY_interface(Operation = property_mode.PROPERTY_MERGEABLE, orderDisplay = 20)
	public Material_ getMaterial() {
		return material;
	}

	public void setMaterial(Material_ material) {
		this.material = material;
	}

	@PROPERTY_interface(Operation = property_mode.PROPERTY_MERGEABLE, orderDisplay = 30)
	public Material_ getMaterialSupported() {
		return materialSupported;
	}

	public void setMaterialSupported(Material_ materialSupported) {
		this.materialSupported = materialSupported;
	}

	@PROPERTY_interface(Operation = property_mode.PROPERTY_MERGEABLE, orderDisplay = 40)
	public Integer getIdMaterial() {
		return idMaterial;
	}

	public void setIdMaterial(Integer idMaterial) {
		this.idMaterial = idMaterial;
	}

	@PROPERTY_interface(Operation = property_mode.PROPERTY_MERGEABLE, orderDisplay = 50)
	public double getValeur() {
		return valeur;
	}

	public void setValeur(double valeur) {
		this.valeur = valeur;
	}

	@Override
	public String toString() {
		return "Flotteur [ID_ObjetSurFlotteur=" + ID_ObjetSurFlotteur + ", material=" + material
				+ ", materialSupported=" + materialSupported + ", idMaterial=" + idMaterial + ", valeur=" + valeur
				+ "]";
	}

}