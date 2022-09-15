package org.warnotte.obj2gui2.ArrayGeneratorForsingleObject;


import java.lang.reflect.Method;
import java.util.List;

import org.warnotte.obj2gui2.JPanelMagique;
import org.warnotte.obj2gui2.Plugins.OBJ2GUIPlug;


/**
 * @author Warnotte Renaud
 *
 */
public class TableMagiquePlugin1D extends OBJ2GUIPlug<Object, TableMagique1D>
{
	TableMagiqueModel1D tbm;
	TableMagique1D component;
	
	@Override
	protected TableMagique1D build(Object values, Object parent, Method getter, Method setter, JPanelMagique panel_parent, boolean dummy)
	{
		tbm = new TableMagiqueModel1D((List<Object>)parent, getter, setter);
		component = new TableMagique1D(tbm);
		component.addFocusListener(panel_parent);
		return component;
	}

	@Override
	protected Object getValue(TableMagique1D component, boolean dummy)
	{
		return tbm.getValues();
	}

	@Override
	public void refresh(Object value, TableMagique1D component, boolean dummy) {
		tbm.fireTableDataChanged();
	}
}
