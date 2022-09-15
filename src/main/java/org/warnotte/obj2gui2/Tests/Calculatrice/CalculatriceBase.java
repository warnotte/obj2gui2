package org.warnotte.obj2gui2.Tests.Calculatrice;

import io.github.warnotte.waxlib3.core.TemplatePropertyMerger.property_mode;
import io.github.warnotte.waxlib3.core.TemplatePropertyMerger.Annotations.PROPERTY_interface;
import io.github.warnotte.waxlib3.core.TemplatePropertyMerger.Annotations.PROPERTY_interface.gui_type;

public class CalculatriceBase {

	public enum Operation {PLUS, MOINS, MULTIPLY, DIVISER};
	double inputA;
	double inputB;
	Operation operateur = Operation.PLUS;

	@PROPERTY_interface(Operation=property_mode.PROPERTY_MERGEABLE)
	public synchronized double getInputA() {
		return inputA;
	}

	public synchronized void setInputA(double inputA) {
		this.inputA = inputA;
	}

	@PROPERTY_interface(Operation=property_mode.PROPERTY_MERGEABLE)
	public synchronized double getInputB() {
		return inputB;
	}

	public synchronized void setInputB(double inputB) {
		this.inputB = inputB;
	}

	@PROPERTY_interface(Operation=property_mode.PROPERTY_MERGEABLE, gui_type=gui_type.COMBO)
	public synchronized Operation getOperateur() {
		return operateur;
	}
	
	@PROPERTY_interface(Operation=property_mode.PROPERTY_MERGEABLE, gui_type=gui_type.COMBO)
	public synchronized Operation getAOperationBEgale0() {
		if (inputA*inputB==0) return Operation.MULTIPLY;
		if (inputA/inputB==0) return Operation.DIVISER;
		if (inputA+inputB==0) return Operation.PLUS;
		if (inputA-inputB==0) return Operation.MOINS;
			
		return null;
	}

	
	
	public synchronized void setOperateur(Operation operateur) {
		this.operateur = operateur;
	}

	@Override
	public String toString() {
		return "testCalculatrice [inputA=" + inputA + ", inputB=" + inputB + ", operateur=" + operateur + "]";
	}
}
