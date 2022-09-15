package org.warnotte.obj2gui2.Plugins.tests;

import java.awt.Font;
import java.lang.reflect.Method;

import org.warnotte.obj2gui2.JPanelMagique;
import org.warnotte.obj2gui2.Plugins.OBJ2GUIPlug;

import io.github.warnotte.waxlib3.waxlibswingcomponents.Swing.Component.JFontChooserCombo;


/**
 * @author Warnotte Renaud
 *
 */
public class FontPlugin extends OBJ2GUIPlug<Font, JFontChooserCombo>
{
	
	@Override
	protected JFontChooserCombo build(Font value, Object parent, Method setter,
			Method method, JPanelMagique panel_parent, boolean dummy) {
		JFontChooserCombo component = new JFontChooserCombo(value);
		component.addFocusListener(panel_parent);
		component.addActionListener(panel_parent);
		return component;
	}
	
	@Override
	public void refresh(Font value, JFontChooserCombo component, boolean dummy)
	{
		component.setValue(value);
	}

	@Override
	protected Font getValue(JFontChooserCombo component, boolean dummy)
	{
		return component.getValue();
	}

	
}
