package org.warnotte.obj2gui2.Tests.Complex;
import org.warnotte.waxlib2.TemplatePropertyMerger.Annotations.PROPERTY_FIELD_XXXABLE;

/**
 * 
 */

/**
 * @author Warnotte Renaud
 *
 */
public class Z
{
	@PROPERTY_FIELD_XXXABLE
	ZZ classeZZ = new ZZ();

	public synchronized ZZ getClasseZZ()
	{
		return classeZZ;
	}

	public synchronized void setClasseZZ(ZZ classeZZ)
	{
		this.classeZZ = classeZZ;
	
	}
	
	
}
