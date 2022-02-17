package org.warnotte.obj2gui2.Tests.Complex;
import io.github.warnotte.waxlib3.waxlib2.TemplatePropertyMerger.property_mode;
import io.github.warnotte.waxlib3.waxlib2.TemplatePropertyMerger.Annotations.PROPERTY_interface;

/**
 * @author Warnotte Renaud
 *
 */
public class ZZ
{
	double dudulZ = 1.23;

	@PROPERTY_interface(Operation=property_mode.PROPERTY_MERGEABLE)
	public synchronized double getDudulZ()
	{
		return dudulZ;
	}

	public synchronized void setDudulZ(double dudulZ)
	{
		this.dudulZ = dudulZ;
	
	}
	
}
