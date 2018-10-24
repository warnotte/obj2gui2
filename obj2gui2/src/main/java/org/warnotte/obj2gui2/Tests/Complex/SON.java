/**
 * 
 */
package org.warnotte.obj2gui2.Tests.Complex;

import org.warnotte.waxlib2.TemplatePropertyMerger.property_mode;
import org.warnotte.waxlib2.TemplatePropertyMerger.Annotations.PROPERTY_interface;

/**
 * @author Warnotte Renaud
 *
 */
public class SON
{
	A a;
	SON(A a)
	{
		this.a=a;
	}
	
	@PROPERTY_interface(Operation=property_mode.PROPERTY_MERGEABLE)
	public float getValueFromA()
	{
		return (float) a.aa;
		
	}
}
