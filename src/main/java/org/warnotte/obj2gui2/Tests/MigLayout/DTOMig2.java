/**
 * 
 */
package org.warnotte.obj2gui2.Tests.MigLayout;

import org.warnotte.obj2gui2.PROPERTY_MIGLAYOUT;
import org.warnotte.obj2gui2.lblposition;

import io.github.warnotte.waxlib3.core.TemplatePropertyMerger.property_mode;
import io.github.warnotte.waxlib3.core.TemplatePropertyMerger.Annotations.PROPERTY_interface;

/**
 * @author Warnotte Renaud
 *
 */
@PROPERTY_MIGLAYOUT(LayoutConstraints="fill, debug", labelPosition=lblposition.LEFT)
//@PROPERTY_MIGLAYOUT(LayoutConstraints="", ColumnConstraints="[fill,grow][fill,grow][fill,grow][fill,grow]", RowConstraints="[fill,grow][fill,grow][fill,grow]")
public class DTOMig2
{

	public String valeur1 = "123564";
	public Double valeur2 = 123.564;
	public String valeur3 = "Je suis un grand bidule";
	public String valeur4 = "poko";
	public String valeur5 = "654897";
	public String valeur6 = "combobox";
	public String valeur7 = "Jebidule";
	public boolean checkValue1 = false;
	public boolean checkValue2 = true;
	
	/**
	 * @return the valeur1
	 */
	@PROPERTY_interface(Operation = property_mode.PROPERTY_MERGEABLE, orderDisplay=10)
	
	public synchronized String getValeur1()
	{
		return valeur1;
	}
	/**
	 * @param valeur1 the valeur1 to set
	 */
	public synchronized void setValeur1(String valeur1)
	{
		this.valeur1 = valeur1;
	
	}
	/**
	 * @return the valeur2
	 */
	@PROPERTY_interface(Operation = property_mode.PROPERTY_MERGEABLE, orderDisplay=20)
	
	public synchronized Double getValeur2()
	{
		return valeur2;
	}
	/**
	 * @param valeur2 the valeur2 to set
	 */
	public synchronized void setValeur2(Double valeur2)
	{
		this.valeur2 = valeur2;
	
	}
	/**
	 * @return the valeur3
	 */
	@PROPERTY_interface(Operation = property_mode.PROPERTY_MERGEABLE, orderDisplay=30)
	
	public synchronized String getValeur3()
	{
		return valeur3;
	}
	/**
	 * @param valeur3 the valeur3 to set
	 */
	public synchronized void setValeur3(String valeur3)
	{
		this.valeur3 = valeur3;
	
	}
	/**
	 * @return the valeur4
	 */
	@PROPERTY_interface(Operation = property_mode.PROPERTY_MERGEABLE, orderDisplay=40)
	
	public synchronized String getValeur4()
	{
		return valeur4;
	}
	/**
	 * @param valeur4 the valeur4 to set
	 */
	public synchronized void setValeur4(String valeur4)
	{
		this.valeur4 = valeur4;
	
	}
	/**
	 * @return the valeur5
	 */
	@PROPERTY_interface(Operation = property_mode.PROPERTY_MERGEABLE, orderDisplay=50, isDisplayLabel=false)
	
	public synchronized String getValeur5()
	{
		return valeur5;
	}
	/**
	 * @param valeur5 the valeur5 to set
	 */
	public synchronized void setValeur5(String valeur5)
	{
		this.valeur5 = valeur5;
	
	}
	/**
	 * @return the valeur6
	 */
	@PROPERTY_interface(Operation = property_mode.PROPERTY_MERGEABLE, orderDisplay=60)
	
	public synchronized String getValeur6()
	{
		return valeur6;
	}
	/**
	 * @param valeur6 the valeur6 to set
	 */
	public synchronized void setValeur6(String valeur6)
	{
		this.valeur6 = valeur6;
	
	}
	/**
	 * @return the valeur7
	 */
	@PROPERTY_interface(Operation = property_mode.PROPERTY_MERGEABLE, orderDisplay=70)
	
	public synchronized String getValeur7()
	{
		return valeur7;
	}
	/**
	 * @param valeur7 the valeur7 to set
	 */
	public synchronized void setValeur7(String valeur7)
	{
		this.valeur7 = valeur7;
	
	}
	/**
	 * @return the checkValue1
	 */
	@PROPERTY_interface(Operation = property_mode.PROPERTY_MERGEABLE, orderDisplay=80, isDisplayLabel=false)
	public synchronized boolean isCheckValue1()
	{
		return checkValue1;
	}
	/**
	 * @param checkValue1 the checkValue1 to set
	 */
	public synchronized void setCheckValue1(boolean checkValue1)
	{
		this.checkValue1 = checkValue1;
	
	}
	/**
	 * @return the checkValue2
	 */
	@PROPERTY_interface(Operation = property_mode.PROPERTY_MERGEABLE, orderDisplay=80)
	public synchronized boolean isCheckValue2()
	{
		return checkValue2;
	}
	/**
	 * @param checkValue2 the checkValue2 to set
	 */
	public synchronized void setCheckValue2(boolean checkValue2)
	{
		this.checkValue2 = checkValue2;
	
	}
	
	
	

	
	
}
