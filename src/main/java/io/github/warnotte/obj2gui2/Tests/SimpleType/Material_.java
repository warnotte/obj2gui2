package io.github.warnotte.obj2gui2.Tests.SimpleType;

import io.github.warnotte.waxlib3.core.Identifiable.Identifiable;

/**
 * 
 */

/**
 * @author Warnotte Renaud
 *
 */
public class Material_ implements Identifiable
{
	int id;
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 8529565396007095360L;
	String named;
	/**
	 * @param ID
	 * @param string 
	 */
	public Material_(int ID, String string)
	{
		this.id = ID;
		this.named = string;
	}
	public synchronized String getNamed()
	{
		return named;
	}
	public synchronized void setNamed(String named)
	{
		this.named = named;
	
	}
	@Override
	public String toString() {
		return "Material [named=" + named + "]";
	}
	
	@Override
	public int getId() {
		return id;
	}
	@Override
	public void setId(int id) {
		this.id= id;
		
	}
	
	
}
