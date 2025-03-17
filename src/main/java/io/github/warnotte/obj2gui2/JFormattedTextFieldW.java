/**
 * 
 */
package io.github.warnotte.obj2gui2;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

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
		super.setValue(value);
	}
	
	@Override
	public void setText(String value)
	{
		if (value.length()==0){
			return;
		}
		super.setText(value);
	}
	
}
