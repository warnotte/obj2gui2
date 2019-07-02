package org.warnotte.obj2gui2.ArrayGeneratorForsingleObject;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.warnotte.obj2gui2.converterandtools;

public class TableMagiqueModel1D extends TableMagiqueModelBase {

	private static final Logger LOGGER = LogManager.getLogger("TableMagiqueModel1D");
	
	/**
	 * 
	 */
	private static final long	serialVersionUID	= -1620444679852461747L;

	
	public TableMagiqueModel1D(List<Object> parent, Method get, Method set) {
		super(parent, get, set);
	}


	public int getRowCount() {
		return getValues().length;
	}
	
	public int getColumnCount() {
		return 1;
	}
	
	@Override
	public String getColumnName(int arg0) {	
		String name = get.getName();	
		if (name.toLowerCase().startsWith("get"))
			name = name.substring(3);	
		if (name.toLowerCase().startsWith("is"))
			name = name.substring(2);	
		return name;
	}
	
	
	public Object getValueAt(int arg0, int arg1) {
		return getValues()[arg0];
		
	}

	
	@Override
	public boolean isCellEditable(int arg0, int arg1) {
		return true;
	}

	

	@Override
	public void setValueAt(Object val, int arg0, int arg1) {
		Object[] vals = getValues();
		vals[arg0]=val;
		setValue(vals);
	}
	
	public void setValue(Object valueM) {
		setValue(valueM, false);
	}
	
	private void setValue(Object valueM, boolean propageToAll) {
		try {
			
			Object[] value = converterandtools.convertObjectTo1DObjectArray(valueM);
			
			Class compType = get.getReturnType().getComponentType();
			
			System.err.println("Component : "+compType);
			
			if (propageToAll==true)
			{
				for (int i = 0; i < parent.size(); i++)
				{
					Object objetreference=parent.get(i);
					setValueForThatObject(value, compType, objetreference);
				}
			}
			else
			{
				Object objetreference = getObjetReference();
				
				setValueForThatObject(value, compType, objetreference);
			}
			
		
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}




	/**
	 * @param value
	 * @param compType
	 * @param obj_to_set
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	private void setValueForThatObject(Object[] value, Class<?> compType, Object obj_to_set) throws IllegalAccessException, InvocationTargetException
	{
		if (compType==double.class)
		{
			double[] array = converterandtools.Object1DArrTo1DDoubleArr(value);
			set.invoke(obj_to_set, array);
		}
		else
		if (compType==int.class)
		{
			int[] array = converterandtools.Object1DArrTo1DIntegerArr(value);
			set.invoke(obj_to_set, array);
		}
		else
		if (compType==float.class)
		{
			float[] array = converterandtools.Object1DArrTo1DFloatArr(value);
			set.invoke(obj_to_set, array);
		}
		else
		if (compType==String.class)
		{
			String[] array = converterandtools.Object1DArrTo1DStringArr(value);
			set.invoke(obj_to_set, new Object[]{array});
		}
		else
		{
			LOGGER.fatal("Cannot converttt thats");
		}
	}
/*
	private Field findFieldNamed(String varName) {
		Field field[] = parent.get(0).getClass().getDeclaredFields();
		
		for (int i = 0; i < field.length; i++) {
			String name = field[i].getName();
			if (name.startsWith("get")==true) name = name.substring(3);
			else
			if (name.startsWith("is")==true) name = name.substring(2);
			else
				LOGGER.fatal("FUCKING DAMN corrige ca wax... fout une exception...");
				
			if (varName.toLowerCase().equals(name.toLowerCase()))
				return field[i];
		}
		
		return null;
	}
*/

	public Object[] getValues() {
		try {
			
			Object objetreference = getObjetReference();
			
			return converterandtools.convertObjectTo1DObjectArray(get.invoke(objetreference));
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}


	/**
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 * 
	 */
	public void propagateAll() 
	{
		try
		{
			
			Object objetreference = getObjetReference();
			
			Object valeurs = get.invoke(objetreference);
			
			setValue(valeurs, true);
			
			fireTableDataChanged();
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}



	/**
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 * 
	 */
	public void addRow() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException
	{
		Object objetreference = getObjetReference();
		
		Object val = get.invoke(objetreference);
		Object vals [] = getValues();
		
		
		Class<?> retType = get.getReturnType();
		
		val = converterandtools.resizeArray1D(val, vals.length+1);
		
		if (retType== String[].class)
			((String[])val)[vals.length]="NewString_"+(int)(Math.random()*65536);
		
		setValue(val);
		fireTableDataChanged();
	}


	/**
	 * @param rows
	 */
	public void removeRows(int[] rows)
	{
		List list = new ArrayList<>(Arrays.asList(getValues()));
		for (int i = rows.length-1; i >= 0; i--)
		{
			int idx = rows[i];
			System.err.println(i+"=="+idx);
			list.remove(idx);
		}
		
		Object [] ret = list.toArray();
		setValue(ret);
		fireTableDataChanged();
	}
	
	



}
