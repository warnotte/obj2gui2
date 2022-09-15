package io.github.warnotte.obj2gui2;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class converterandtools {

	public static Object resizeArray1D(Object array, int newSize)
	{
		int oldSize = java.lang.reflect.Array.getLength(array);
		Class<?> elementType = array.getClass().getComponentType();
		Object newArray = java.lang.reflect.Array.newInstance(elementType, newSize);
		int preserveLength = Math.min(oldSize, newSize);
		if (preserveLength > 0)
			System.arraycopy(array, 0, newArray, 0, preserveLength);
		return newArray;
	}
	
	/**
	 * @param val
	 * @param i
	 * @return
	 */
	// TODO : Pourquoi ne pas prendre directement des U[][] ?
		public static Object addArray2Drow(Object array)
		{
			Object datas [][] = convertObjectTo2DObjectArray(array);
			
			int Cols = datas[0].length;
			
			Class<?> elementType = array.getClass().getComponentType();
			if (elementType.isArray())
				elementType=elementType.getComponentType();
		
			Object dudultoreplace = java.lang.reflect.Array.newInstance(elementType, Cols);
			Object dudultoreplace2[] = convertObjectTo1DObjectArray(dudultoreplace);
			
			String ret [] = Object1DArrTo1DStringArr(dudultoreplace2);
			
			if (elementType==String.class)
				for (int i = 0; i < ret.length; i++)
					ret[i]="Dummy";
			
			datas = append(datas, ret);
			return datas;
		}
		
	/**
	 * @param datas
	 */
	public static<U> U[][] removerray2Drow(U[][] datas, int idx)
	{
		U[][] neo = (U[][]) new Object[datas.length-1][datas[0].length];
		int cpt = 0;
		for (int i = 0; i < datas.length; i++)
		{
			if (i!=idx)
			{
				for (int j = 0; j < neo[0].length; j++)
					neo[cpt][j]=datas[i][j];
				cpt++;
			}
		}
		return neo;
	}
	
	
	/**
	 * @param datas
	 * @return
	 */
	public static<U> U[][] addrray2Dcolumn(U[][] datas)
	{
		int idxNewlyCol = datas[0].length;
		U[][] neo = (U[][]) new Object[datas.length][datas[0].length+1];
		int cpt = 0;
		for (int i = 0; i < datas.length; i++)
		{
			for (int j = 0; j < datas[0].length; j++)
				neo[i][j]=datas[i][j];
			neo[i][idxNewlyCol] = (U) "0";
		}
		return neo;
	}

	
	
	/**
	 * @param datas
	 * @param i
	 * @return
	 */
	public static<U> U[][] removerray2Dcolumn(U[][] datas, int idx)
	{
		U[][] neo = (U[][]) new Object[datas.length][datas[0].length-1];
		int cpt = 0;
		for (int i = 0; i < datas.length; i++)
		{
			cpt=0;
			for (int j = 0; j < datas[0].length; j++)
			{
				if (j!=idx)
				{
					neo[i][cpt]=datas[i][j];
					cpt++;
				}
			}
				
		}
		return neo;
	}
	
	protected static <T> T[] append(T[] arr, T element) {
	    final int N = arr.length;
	    arr = Arrays.copyOf(arr, N + 1);
	    arr[N] = element;
	    return arr;
	}

	public static double[] Object1DArrTo1DDoubleArr(Object[] value2) {
		double [] ret = new double [value2.length];
		for (int i = 0; i < ret.length; i++) {
			ret[i]=Double.parseDouble(""+value2[i]);
		}
		return ret;
	}
	public static float[] Object1DArrTo1DFloatArr(Object[] value2) {
		float [] ret = new float [value2.length];
		for (int i = 0; i < ret.length; i++) {
			ret[i]=Float.parseFloat(""+value2[i]);
		}
		return ret;
	}
	public static String[] Object1DArrTo1DStringArr(Object[] value2) {
		String [] ret = new String [value2.length];
		for (int i = 0; i < ret.length; i++) {
			ret[i]=""+value2[i];
		}
		return ret;
	}
	public static int[] Object1DArrTo1DIntegerArr(Object[] value2) {
		int [] ret = new int [value2.length];
		for (int i = 0; i < ret.length; i++) {
			ret[i]=Integer.parseInt(""+value2[i]);
		}
		return ret;
	}
	
	
	
	public static double[][] Object2DArrTo2DDoubleArr(Object[][] value2) {
		double [][] ret = new double [value2.length][value2[0].length];
		for (int i = 0; i < ret.length; i++) {
			for (int j = 0; j < ret[i].length; j++) {
			ret[i][j]=Double.parseDouble(""+value2[i][j]);
			}
		}
		return ret;
	}
	public static float[][] Object2DArrTo2DFloatArr(Object[][] value2) {
		float [][] ret = new float [value2.length][value2[0].length];
		for (int i = 0; i < ret.length; i++) {
			for (int j = 0; j < ret[i].length; j++) {
			ret[i][j]=Float.parseFloat(""+value2[i][j]);
			}
		}
		return ret;
	}
	public static String[][] Object2DArrTo2DStringArr(Object[][] value2) {
		String [][] ret = new String [value2.length][value2[0].length];
		for (int i = 0; i < ret.length; i++) {
			for (int j = 0; j < ret[i].length; j++) {
			ret[i][j]=""+value2[i][j];
			}
		}
		return ret;
	}
	public static int[][] Object2DArrTo2DIntegerArr(Object[][] value2) {
		int [][]ret = new int [value2.length][value2[0].length];
		for (int i = 0; i < ret.length; i++) {
			for (int j = 0; j < ret[i].length; j++) {
			ret[i][j]=Integer.parseInt(""+value2[i][j]);
			}
		}
		return ret;
	}
	

	public static Object[] convertObjectTo1DObjectArray(Object array) {
		Class ofArray = array.getClass().getComponentType();
		if (ofArray.isPrimitive()) {
			List ar = new ArrayList();
			int length = Array.getLength(array);
	        for (int i = 0; i < length; i++) {
	            ar.add(Array.get(array, i));
	        }
	        return ar.toArray();
	    }
	    else {
	        return (Object[]) array;
	    }
	}

	/**
	 * @param invoke
	 * @return
	 */
	public static Object[][] convertObjectTo2DObjectArray(Object array)
	{
		Class<?> ofArray2 = Array.get(array, 0).getClass().getComponentType();
		
		if (ofArray2.isPrimitive()) {
			int rows = Array.getLength(array);
			int cols = Array.getLength(Array.get(array, 0));
			
			Object[][] retour = new Object[rows][];
			for (int i = 0; i < rows; i++)
			{
				Object objRow = Array.get(array, i);
				retour[i]=convertObjectTo1DObjectArray(objRow);
			}
			
			return retour;
	    }
	    else {
	        return (Object[][]) array;
	    }
	//	return null;
		
		
	}

	
	


}
