/**
 * 
 */
package org.warnotte.obj2gui2.ArrayGeneratorForsingleObject;

import java.lang.reflect.Method;
import java.util.List;

import javax.swing.table.AbstractTableModel;

/**
 * @author Warnotte Renaud
 *
 */
public abstract class TableMagiqueModelBase extends AbstractTableModel
{
	/**
	 * 
	 */
	private static final long	serialVersionUID	= -8309280351590201345L;

	List<Object> parent;
	
	Method get;
	Method set;
	
	public enum SELECTION_MODE{CHOOSE_FIRST_SELECTED, CHOOSE_LAST_SELECTED}
	public SELECTION_MODE mode_selection = SELECTION_MODE.CHOOSE_LAST_SELECTED;

	public TableMagiqueModelBase(List<Object> parent, Method get, Method set) {
		this.parent = parent;
		this.get=get;
		this.set=set;
		
	}


	/**
	 * @return the mode_selection
	 */
	public synchronized SELECTION_MODE getMode_selection()
	{
		return mode_selection;
	}


	/**
	 * @param mode_selection the mode_selection to set
	 */
	public synchronized void setMode_selection(SELECTION_MODE mode_selection)
	{
		this.mode_selection = mode_selection;
	}

	

	public Object getObjetReference() {
		Object objetreference=parent.get(0);
		if (mode_selection==SELECTION_MODE.CHOOSE_LAST_SELECTED)
			objetreference=parent.get(parent.size()-1);
		return objetreference;
	}
	
}
