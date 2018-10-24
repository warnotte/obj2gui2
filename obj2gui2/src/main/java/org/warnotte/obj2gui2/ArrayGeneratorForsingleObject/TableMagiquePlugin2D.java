package org.warnotte.obj2gui2.ArrayGeneratorForsingleObject;


import java.lang.reflect.Method;
import java.util.List;

import org.warnotte.obj2gui2.JPanelMagique;
import org.warnotte.obj2gui2.Plugins.OBJ2GUIPlug;


/**
 * @author Warnotte Renaud
 *
 */
public class TableMagiquePlugin2D extends OBJ2GUIPlug<Object, TableMagique2D>
{
	TableMagiqueModel2D tbm;
	TableMagique2D component;
	
	@Override
	protected TableMagique2D build(Object values, Object parent, Method getter, Method setter, JPanelMagique panel_parent, boolean dummy)
	{
		tbm = new TableMagiqueModel2D((List<Object>)parent, getter, setter);
		component = new TableMagique2D(tbm);
		component.addFocusListener(panel_parent);
		return component;
	}

	@Override
	protected Object getValue(TableMagique2D component, boolean dummy)
	{
		return tbm.getValues();
	}

	@Override
	public void refresh(Object value, TableMagique2D component, boolean dummy) {
		tbm.fireTableDataChanged();
	}
}
