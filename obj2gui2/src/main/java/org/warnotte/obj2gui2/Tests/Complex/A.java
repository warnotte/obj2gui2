package org.warnotte.obj2gui2.Tests.Complex;

import java.awt.Font;
import java.io.File;
import java.util.Calendar;
import java.util.Date;

import org.warnotte.obj2gui2.PROPERTY_LAYOUT;

import io.github.warnotte.waxlib3.waxlib2.TemplatePropertyMerger.property_mode;
import io.github.warnotte.waxlib3.waxlib2.TemplatePropertyMerger.Annotations.PROPERTY_FIELD_XXXABLE;
import io.github.warnotte.waxlib3.waxlib2.TemplatePropertyMerger.Annotations.PROPERTY_interface;
import io.github.warnotte.waxlib3.waxlib2.TemplatePropertyMerger.Annotations.PROPERTY_interface.gui_type;
import io.github.warnotte.waxlib3.waxlibswingcomponents.Swing.Component.JWColor;


/**
 * @author Warnotte Renaud.
 */
@PROPERTY_LAYOUT(type = PROPERTY_LAYOUT.Type.BoxLayout, BoxLayout_property = PROPERTY_LAYOUT.Type_BoxLayout.Y)
public class A
{
	// Divers test pour les type simple primitif
	double	a				= (int) (Math.random() * 10);
	String	b				= "JeSuisA";
	double	aa				= 16.5;
	double	aslider			= 10.0;
	

	int		bslidertest		= 5;

	boolean	checkBoxTrue	= true;
	boolean	checkBoxFalse	= false;
	boolean	checkBoxDepend	= (Math.random() > 0.5) ? true : false;
	String	readOnlyString	= "Toto";

	// Un test avec le type Date
	Date	birthDate		= Calendar.getInstance().getTime();

	Font 	unefonte = new Font("Verdana", Font.PLAIN,24);
	// Divers test pour les type File
	File	file_1			= new File("UnexistingFile");
	File	file_xml		= null;
	File	file_directory1	= new File(".");
	File	file_directory2	= new File("unknown");
	File	file_directory3	= null;

	// Divers test de sous classes.
	// Permet de voir si la communication va aussi du parent au fils (pas seulement fils->parent).
	@PROPERTY_FIELD_XXXABLE
	SON		classeSON		= new SON(this);

	@PROPERTY_FIELD_XXXABLE
	B		classeB			= new B();

	@PROPERTY_FIELD_XXXABLE
	Z		classeZ			= new Z();

	// Quelques test avec JWColor (equivalent du color mais avec un setter, oui c'est con je sais ... l'id�e est que je ne voulais recreer une nouvelle instance de color mais bien garder la meme pour conserver sa r�ference)
	JWColor	colorRed		= new JWColor(255, 0, 0);
	JWColor	colorGreen		= new JWColor(0, 255, 0);
	JWColor	colorBlue		= new JWColor(0, 0, 255);

	public A()
	{

	}

	@PROPERTY_interface(Operation = property_mode.PROPERTY_MERGEABLE, gui_type = gui_type.FLATSLIDER, min = 0, max = 100, divider = 100)
	public synchronized double getAslider()
	{
		return aslider;
	}

	public synchronized void setAslider(double aslider)
	{
		this.aslider = aslider;
	}

	@PROPERTY_interface(Operation = property_mode.PROPERTY_MERGEABLE, gui_type = gui_type.FLATSLIDER, min = 0, max = 100, divider = 1)
	public synchronized int getBslidertest()
	{
		return bslidertest;
	}

	public synchronized void setBslidertest(int bslidertest)
	{
		this.bslidertest = bslidertest;
	}

	@PROPERTY_interface(Operation = property_mode.PROPERTY_MERGEABLE, gui_type = gui_type.SLIDER, min = 0, max = 100, divider = 1)
	public synchronized int getBbisslidertest()
	{
		return bslidertest;
	}

	public synchronized void setBbisslidertest(int bslidertest)
	{
		this.bslidertest = bslidertest;
	}

	@PROPERTY_interface(Operation = property_mode.PROPERTY_MERGEABLE, editFormat = "", displayFormat = "4.2")
	public synchronized double getA()
	{
		return a;
	}

	@PROPERTY_interface(Operation = property_mode.PROPERTY_MERGEABLE)
	public synchronized double getSurfaceA_AA()
	{
		return a * aa;
	}

	@PROPERTY_interface(Operation = property_mode.PROPERTY_MERGEABLE)
	public synchronized double getSurfaceEF_G()
	{
		return classeB.getClasseE().getF() * classeB.getClasseE().getG();
	}

	public synchronized void setA(double a)
	{
		this.a = a;
	}

	@PROPERTY_interface(Operation = property_mode.PROPERTY_MERGEABLE)
	public synchronized String getB()
	{
		return b;
	}

	public synchronized void setB(String b)
	{
		this.b = b;
	}

	public synchronized B getClasseB()
	{
		return classeB;
	}

	public synchronized void setClasseB(B classeB)
	{
		this.classeB = classeB;
	}

	@PROPERTY_interface(Operation = property_mode.PROPERTY_MERGEABLE)
	public synchronized double getAa()
	{
		return aa;
	}

	public synchronized void setAa(double aa)
	{
		this.aa = aa;
	}

	@PROPERTY_interface(Operation = property_mode.PROPERTY_MERGEABLE)
	public synchronized Date getBirthDate()
	{
		return birthDate;
	}

	public synchronized void setBirthDate(Date birthDate)
	{
		this.birthDate = birthDate;
	}

	/**
	 * @return the unefonte
	 */
	@PROPERTY_interface(Operation = property_mode.PROPERTY_MERGEABLE)
	public synchronized Font getUnefonte()
	{
		return unefonte;
	}

	/**
	 * @param unefonte the unefonte to set
	 */
	public synchronized void setUnefonte(Font unefonte)
	{
		this.unefonte = unefonte;
	
	}

	@PROPERTY_interface(Operation = property_mode.PROPERTY_MERGEABLE)
	public synchronized boolean isCheckBoxTrue()
	{
		return checkBoxTrue;
	}

	public synchronized void setCheckBoxTrue(boolean checkBoxTrue)
	{
		this.checkBoxTrue = checkBoxTrue;
	}

	@PROPERTY_interface(Operation = property_mode.PROPERTY_MERGEABLE)
	public synchronized boolean isCheckBoxFalse()
	{
		return checkBoxFalse;
	}

	public synchronized void setCheckBoxFalse(boolean checkBoxFalse)
	{
		this.checkBoxFalse = checkBoxFalse;
	}

	@PROPERTY_interface(Operation = property_mode.PROPERTY_MERGEABLE)
	public synchronized boolean isCheckBoxDepend()
	{
		return checkBoxDepend;
	}

	public synchronized void setCheckBoxDepend(boolean checkBoxDepend)
	{
		this.checkBoxDepend = checkBoxDepend;
	}

	public synchronized Z getClasseZ()
	{
		return classeZ;
	}

	public synchronized void setClasseZ(Z classeZ)
	{
		this.classeZ = classeZ;
	}

	@PROPERTY_interface(Operation = property_mode.PROPERTY_MERGEABLE)
	public synchronized String getReadOnlyString()
	{
		return readOnlyString;
	}

	@PROPERTY_interface(Operation = property_mode.PROPERTY_MERGEABLE)
	public synchronized JWColor getColorRed()
	{
		return colorRed;
	}

	public synchronized void setColorRed(JWColor colorRed)
	{
		this.colorRed = colorRed;
	}

	@PROPERTY_interface(Operation = property_mode.PROPERTY_MERGEABLE)
	public synchronized JWColor getColorGreen()
	{
		return colorGreen;
	}

	public synchronized void setColorGreen(JWColor colorGreen)
	{
		this.colorGreen = colorGreen;
	}

	@PROPERTY_interface(Operation = property_mode.PROPERTY_MERGEABLE)
	public synchronized JWColor getColorBlue()
	{
		return colorBlue;
	}

	public synchronized void setColorBlue(JWColor colorBlue)
	{
		this.colorBlue = colorBlue;
	}

	@PROPERTY_interface(Operation = property_mode.PROPERTY_MERGEABLE)
	public synchronized File getFile_1()
	{
		return file_1;
	}

	public synchronized void setFile_1(File file_1)
	{
		this.file_1 = file_1;
	}

	@PROPERTY_interface(Operation = property_mode.PROPERTY_MERGEABLE, extension = "xml")
	public synchronized File getFile_xml()
	{
		return file_xml;
	}

	public synchronized void setFile_xml(File file_2)
	{
		this.file_xml = file_2;
	}

	@PROPERTY_interface(Operation = property_mode.PROPERTY_MERGEABLE, isDirectoryOnly = true)
	public synchronized File getFile_directory1()
	{
		return file_directory1;
	}

	public synchronized void setFile_directory1(File file_directory1)
	{
		this.file_directory1 = file_directory1;
	}

	@PROPERTY_interface(Operation = property_mode.PROPERTY_MERGEABLE, isDirectoryOnly = true)
	public synchronized File getFile_directory2()
	{
		return file_directory2;
	}

	public synchronized void setFile_directory2(File file_directory2)
	{
		this.file_directory2 = file_directory2;
	}

	@PROPERTY_interface(Operation = property_mode.PROPERTY_MERGEABLE, isDirectoryOnly = true)
	public synchronized File getFile_directory3()
	{
		return file_directory3;
	}

	public synchronized void setFile_directory3(File file_directory3)
	{
		this.file_directory3 = file_directory3;
	}
	
	

	@Override
	public String toString()
	{
		return "KOPKOK [aaaa=" + getAslider() + " a=" + a + ", b=" + b + ", aa=" + aa + ", checkBoxTrue=" + checkBoxTrue + ", checkBoxFalse=" + checkBoxFalse + ", checkBoxDepend=" + checkBoxDepend + ", classeB=" + classeB + ", classeZ=" + classeZ + "]\r\n" + "[File1 = " + file_1 + "]\r\n" + "[File2 = " + file_xml + "]\r\n" + "[Dir1 = " + file_directory1 + "]\r\n" + "[Dir2 = " + file_directory2 + "]\r\n" + "[Dir3 = " + file_directory3 + "]\r\n"+"Font = "+unefonte+"\r\n";

	}
	

}
