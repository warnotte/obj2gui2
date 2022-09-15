package io.github.warnotte.obj2gui2.Plugins.tests;

import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JTextArea;

import io.github.warnotte.obj2gui2.JPanelMagique;
import io.github.warnotte.obj2gui2.Plugins.OBJ2GUIPlug;


/**
 * @author Warnotte Renaud
 *
 */
public class JTextAreaFieldPlug extends OBJ2GUIPlug<String, JTextArea>
{
	@Override
	protected JTextArea build(String value, Object parent, Method getter, Method setter, JPanelMagique panel_parent, boolean dummy)
	{
		JTextArea component = new JTextArea();
		component.setText(value);
		component.addFocusListener(panel_parent);
		return component;
	}

	@Override
	public void refresh(String value, JTextArea component, boolean dummy)
	{
		component.setText(value);
	}

	@Override
	protected String getValue(JTextArea component, boolean dummy)
	{
		return component.getText();
	}
}
