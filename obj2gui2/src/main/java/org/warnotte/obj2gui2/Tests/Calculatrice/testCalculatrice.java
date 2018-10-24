package org.warnotte.obj2gui2.Tests.Calculatrice;


import org.warnotte.obj2gui2.PROPERTY_button;
import org.warnotte.waxlib2.TemplatePropertyMerger.property_mode;
import org.warnotte.waxlib2.TemplatePropertyMerger.Annotations.PROPERTY_interface;

public class testCalculatrice extends CalculatriceBase {
		
	@PROPERTY_button(method_name="setOperateurAdd", text="+")
	private final Object Bouton_ADD = null; // Px importe son type en fait.
	@PROPERTY_button(method_name="setOperateurMoins", text="-")
	private final Object Bouton_MOINS = null; // Px importe son type en fait.
	@PROPERTY_button(method_name="setOperateurFois", text="*")
	private final Object Bouton_FOIS = null; // Px importe son type en fait.
	@PROPERTY_button(method_name="setOperateurDivide", text="/")
	private final Object Bouton_DIVIDE = null; // Px importe son type en fait.
	
	@PROPERTY_interface(Operation=property_mode.PROPERTY_MERGEABLE)
	public double OperationModulaire()
	{
		if (operateur==Operation.PLUS) return inputA+inputB;
		if (operateur==Operation.MOINS) return inputA-inputB;
		if (operateur==Operation.MULTIPLY) return inputA*inputB;
		if (operateur==Operation.DIVISER) return inputA/inputB;
		return 0;
	}
	

	@PROPERTY_interface(Operation=property_mode.PROPERTY_MERGEABLE)
	public double AfoisB()
	{
		return inputA*inputB;
	}	

	@PROPERTY_interface(Operation=property_mode.PROPERTY_MERGEABLE)
	public double getNouveauTerme()
	{
		return inputA*inputB;
	}
	
	public void setOperateurAdd()
	{
		operateur = Operation.PLUS;
	}
	public void setOperateurMoins()
	{
		operateur = Operation.MOINS;
	}
	public void setOperateurFois()
	{
		operateur = Operation.MULTIPLY;
	}
	public void setOperateurDivide()
	{
		operateur = Operation.DIVISER;
	}
	
	
}
