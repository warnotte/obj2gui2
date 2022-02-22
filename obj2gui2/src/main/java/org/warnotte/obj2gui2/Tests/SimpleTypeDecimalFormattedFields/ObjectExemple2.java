package org.warnotte.obj2gui2.Tests.SimpleTypeDecimalFormattedFields;

import io.github.warnotte.waxlib3.core.TemplatePropertyMerger.property_mode;
import io.github.warnotte.waxlib3.core.TemplatePropertyMerger.Annotations.PROPERTY_interface;
import io.github.warnotte.waxlib3.core.TemplatePropertyMerger.Annotations.PROPERTY_interface.gui_type;


/**
 * @author Warnotte Renaud.
 */
public class ObjectExemple2
{
	// Divers test pour les type simple primitif
	double	a				= (int) (Math.random() * 10)+Math.random();
	double	b				= (int) (Math.random() * 10)+Math.random();
	double	c				= (int) (Math.random() * 10)+Math.random();
	String  creditcard		= "123-98746-33";
	String  regexpField		= "ABCD_12";
	
	public ObjectExemple2()
	{

	}

	/**
	 * @return the a
	 */
	@PROPERTY_interface(Operation = property_mode.PROPERTY_MERGEABLE, orderDisplay=10, 
			displayFormat="Hauteur de 0.0000 Meters", editFormat="0.0000",
			gui_type = gui_type.DECIMALFORMATTEDTEXTFIELD)
	public synchronized double getA()
	{
		return a;
	}

	/**
	 * @param a the a to set
	 */
	public synchronized void setA(double a)
	{
		this.a = a;
	
	}

	/**
	 * @return the b
	 */
	@PROPERTY_interface(Operation = property_mode.PROPERTY_MERGEABLE, orderDisplay=20, 
			displayFormat="0.00E00 Meters", editFormat="0.0000",
			gui_type = gui_type.DECIMALFORMATTEDTEXTFIELD)
	public synchronized double getB()
	{
		return b;
	}

	/**
	 * @param b the b to set
	 */
	public synchronized void setB(double b)
	{
		this.b = b;
	
	}

	/**
	 * @return the c
	 */
	@PROPERTY_interface(Operation = property_mode.PROPERTY_MERGEABLE, orderDisplay=30, 
			displayFormat="0.0000 %", editFormat="0.0000",
			gui_type = gui_type.DECIMALFORMATTEDTEXTFIELD)
	public synchronized double getC()
	{
		return c;
	}

	/**
	 * @param c the c to set
	 */
	public synchronized void setC(double c)
	{
		this.c = c;
	
	}

	/**
	 * @return the creditcard
	 */
	@PROPERTY_interface(Operation = property_mode.PROPERTY_MERGEABLE, orderDisplay=40, 
			displayFormat="###-#####-## 'CCB",
			gui_type = gui_type.MASKFORMATTEDTEXTFIELD)
	public synchronized String getCreditcard()
	{
		return creditcard;
	}

	/**
	 * @param creditcard the creditcard to set
	 */
	public synchronized void setCreditcard(String creditcard)
	{
		this.creditcard = creditcard;
	
	}

	/**
	 * @return the regexpField
	 */
	@PROPERTY_interface(Operation = property_mode.PROPERTY_MERGEABLE, orderDisplay=50, 
			displayFormat="[A-Za-z]{4}[_]+[0-9]{2}",
			gui_type = gui_type.REGEXPFORMATTEDFIELD)
	public synchronized String getRegexpField()
	{
		return regexpField;
	}

	/**
	 * @param regexpField the regexpField to set
	 */
	public synchronized void setRegexpField(String regexpField)
	{
		this.regexpField = regexpField;
	
	}
	
	
	
}
