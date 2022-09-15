/**
 * 
 */
package org.warnotte.obj2gui2;

/**
 * @author Warnotte Renaud
 *
 */
public class TreeNodeWax
{
	private Object objClass;
	String varName;

	/**
	 * @param objectClass
	 * @param varName2
	 */
	public TreeNodeWax(Class<?> objectClass, String varName2) {
		this.setObjClass(objectClass);
		this.varName=varName2;
	}

	public String toString()
	{
		//return objClass+"_"+varName;
		return varName;//+"__"+objClass.toString();
	}

	public Object getObjClass()
	{
		return objClass;
	}

	public void setObjClass(Object objClass)
	{
		this.objClass = objClass;
		
		
	}
}
