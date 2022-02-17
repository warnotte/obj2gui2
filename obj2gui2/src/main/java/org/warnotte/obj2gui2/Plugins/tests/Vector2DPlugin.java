package org.warnotte.obj2gui2.Plugins.tests;

import java.lang.reflect.Method;

import javax.vecmath.Vector2d;

import org.warnotte.obj2gui2.JPanelMagique;
import org.warnotte.obj2gui2.Plugins.OBJ2GUIPlug;

import io.github.warnotte.waxlib3.waxlibswingcomponents.Swing.Component.GUI_Vector2d;


/**
 * @author Warnotte Renaud
 *
 */
public class Vector2DPlugin extends OBJ2GUIPlug<Vector2d, GUI_Vector2d>
{
	
	@Override
	protected GUI_Vector2d build(Vector2d value, Object parent, Method setter,
			Method method, JPanelMagique panel_parent, boolean dummy) {
		GUI_Vector2d component = new GUI_Vector2d(value);
		component.setValue(value);
		component.addFocusListener(panel_parent);
		return component;
	}
	
	

	@Override
	public void refresh(Vector2d value, GUI_Vector2d component, boolean dummy)
	{
		component.setValue(value);
	}

	@Override
	protected Vector2d getValue(GUI_Vector2d component, boolean dummy)
	{
		return component.getValue();
	}

	
}
