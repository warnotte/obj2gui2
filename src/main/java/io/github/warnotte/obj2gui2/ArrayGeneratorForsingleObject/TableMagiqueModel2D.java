package io.github.warnotte.obj2gui2.ArrayGeneratorForsingleObject;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.github.warnotte.obj2gui2.converterandtools;

public class TableMagiqueModel2D extends TableMagiqueModelBase {

	private static final Logger LOGGER = LogManager.getLogger("TableMagiqueModel2D");
	
	/**
	 * 
	 */
	private static final long	serialVersionUID	= -1620444679852461747L;

	
	public TableMagiqueModel2D(List<Object> parent, Method get, Method set) {
		super(parent, get, set);
	}


	public int getRowCount() {
		return getValues().length;
	}
	
	public int getColumnCount() {
		return getValues()[0].length;
	}
	
	@Override
	public String getColumnName(int column) {	
	/*	String name = get.getName();	
		if (name.toLowerCase().startsWith("get"))
			name = name.substring(3);	
		if (name.toLowerCase().startsWith("is"))
			name = name.substring(2);	
	*/	return ""+column;
	}
	
	
	public Object getValueAt(int row, int col) {
		return getValues()[row][col];
		
	}

	
	@Override
	public boolean isCellEditable(int arg0, int arg1) {
		return true;
	}

	

	@Override
	public void setValueAt(Object val, int row, int column) {
		Object[][] vals = getValues();
		vals[row][column]=val;
		setValue(vals);
	}
	
	public void setValue(Object valueM) {
		setValue(valueM, false);
	}
	
	private void setValue(Object valueM, boolean propageToAll) {
		try {
			
			Object[][] value = converterandtools.convertObjectTo2DObjectArray(valueM);
			
			Class<?> compType = get.getReturnType().getComponentType();
			
			if (compType.isArray()==true)
				compType = compType.getComponentType();

			
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
	private void setValueForThatObject(Object[][] value, Class<?> compType, Object obj_to_set) throws IllegalAccessException, InvocationTargetException
	{
		if (compType==double.class)
		{
			double[][] array = converterandtools.Object2DArrTo2DDoubleArr(value);
			set.invoke(obj_to_set, array);
		}
		else
		if (compType==int.class)
		{
			int[][] array = converterandtools.Object2DArrTo2DIntegerArr(value);
			set.invoke(obj_to_set, array);
		}
		else
		if (compType==float.class)
		{
			float[][] array = converterandtools.Object2DArrTo2DFloatArr(value);
			set.invoke(obj_to_set, array);
		}
		else
		if (compType==String.class)
		{
			String[][] array = converterandtools.Object2DArrTo2DStringArr(value);
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

	public Object[][] getValues() {
		try {
			
			Object objetreference = getObjetReference();
			return converterandtools.convertObjectTo2DObjectArray(get.invoke(objetreference));
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
		
		// TODO : Refaire fonctionner ce code...
		
		Object objetreference = getObjetReference();
		
		Object val = get.invoke(objetreference);
		//Object vals [][] = getValues();
		
		Class<?> compType = get.getReturnType().getComponentType();

		System.err.println("Comp = "+compType);
		val = converterandtools.addArray2Drow(val);
		
		setValue(val);
		fireTableDataChanged();
	}


	/**
	 * @param rows
	 */
	public void removeRows(int[] rows)
	{
		Object[][] datas = getValues();
		
		for (int i = rows.length-1; i >=0; i--)
			datas = converterandtools.removerray2Drow(datas, rows[i]);
		
		setValue(datas);
		fireTableDataChanged();
	}
	


	/**
	 * @param cols
	 */
	public void removeColumns(int[] cols)
	{
		
		Object[][] datas = getValues();
		
		for (int i = cols.length-1; i >=0; i--)
			datas = converterandtools.removerray2Dcolumn(datas, cols[i]);
		
		setValue(datas);
		fireTableDataChanged();
		fireTableStructureChanged();
		
	}


	/**
	 * 
	 */
	public void addColumn()
	{
		Object[][] datas = getValues();
		
		datas = converterandtools.addrray2Dcolumn(datas);
		
		setValue(datas);
		fireTableDataChanged();
		fireTableStructureChanged();
		
	}
	
	



}
