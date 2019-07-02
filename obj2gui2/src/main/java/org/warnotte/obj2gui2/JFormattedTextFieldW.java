/**
 * 
 */
package org.warnotte.obj2gui2;

import javax.swing.JFormattedTextField;
import javax.swing.text.DefaultFormatterFactory;

/**
 * @author Warnotte Renaud
 *
 */
public class JFormattedTextFieldW extends JFormattedTextField
{

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 2301114612860951393L;

	/**
	 * @param defaultFormatterFactory
	 */
	public JFormattedTextFieldW(DefaultFormatterFactory defaultFormatterFactory)
	{
		super(defaultFormatterFactory);
	}

	@Override
	public void setValue(Object value)
	{
	//	System.err.println("Value is = "+value);
		super.setValue(value);
	}
	
	@Override
	public void setText(String value)
	{
		if (value.length()==0){
			System.err.println("TOTO");
			return;
		}
			
	//	System.err.println("Text is = "+value);
		super.setText(value);
	}

}
