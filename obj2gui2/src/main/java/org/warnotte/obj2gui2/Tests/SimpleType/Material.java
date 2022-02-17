package org.warnotte.obj2gui2.Tests.SimpleType;
import io.github.warnotte.waxlib3.waxlib2.Identifiable.Identifiable;

/**
 * 
 */

/**
 * @author Warnotte Renaud
 *
 */
public class Material extends Identifiable
{
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 8529565396007095360L;
	String named;
	/**
	 * @param ID
	 * @param string 
	 */
	public Material(long ID, String string)
	{
		super(ID);
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
	
	
}
