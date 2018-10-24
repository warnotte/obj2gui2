package org.warnotte.obj2gui2.Tests.Complex;
import java.io.File;

import org.warnotte.waxlib2.TemplatePropertyMerger.property_mode;

import org.warnotte.waxlib2.TemplatePropertyMerger.Annotations.PROPERTY_interface;

/**
 * 
 */

/**
 * @author Warnotte Renaud
 *
 */
public class E
{
	
	double f=(int)(Math.random()*4);
	
	double g=(int)(Math.random()*4);
	
	File file = new File("toto_"+(int)(Math.random()*655)+".xml");
	
	@PROPERTY_interface(Operation=property_mode.PROPERTY_MERGEABLE)
	public synchronized double getF()
	{
		return f;
	}

	public synchronized void setF(double f)
	{
		this.f = f;
	
	}
	
	@PROPERTY_interface(Operation=property_mode.PROPERTY_MERGEABLE)
	public synchronized double getG()
	{
		return g;
	}

	public synchronized void setG(double g)
	{
		this.g = g;
	
	}

	@PROPERTY_interface(Operation=property_mode.PROPERTY_MERGEABLE)
	public synchronized double getGplusF()
	{
		return g+f;
	}

	@PROPERTY_interface(Operation=property_mode.PROPERTY_MERGEABLE)
	public synchronized File getFile()
	{
		return file;
	}

	public synchronized void setFile(File file)
	{
		this.file = file;
	
	}

	@Override
	public String toString() {
		return "E [f=" + f + ", g=" + g + "]";
	}
	
	
}
