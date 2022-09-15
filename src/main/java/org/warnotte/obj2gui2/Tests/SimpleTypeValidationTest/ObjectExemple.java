package org.warnotte.obj2gui2.Tests.SimpleTypeValidationTest;

import java.io.File;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import javax.vecmath.Vector2d;

import org.warnotte.obj2gui2.PROPERTY_LAYOUT;

import io.github.warnotte.waxlib3.core.TemplatePropertyMerger.property_mode;
import io.github.warnotte.waxlib3.core.TemplatePropertyMerger.Annotations.PROPERTY_FIELD_XXXABLE;
import io.github.warnotte.waxlib3.core.TemplatePropertyMerger.Annotations.PROPERTY_interface;
import io.github.warnotte.waxlib3.core.TemplatePropertyMerger.Annotations.PROPERTY_interface.gui_type;
import io.github.warnotte.waxlib3.waxlibswingcomponents.Swing.Component.JWColor;

/**
 * @author Warnotte Renaud.
 */
@PROPERTY_LAYOUT(type = PROPERTY_LAYOUT.Type.BoxLayout, BoxLayout_property = PROPERTY_LAYOUT.Type_BoxLayout.Y)
public class ObjectExemple
{
	
	@PROPERTY_FIELD_XXXABLE
	Time time1 = new Time();
	@PROPERTY_FIELD_XXXABLE
	Time time2 = new Time();
	
	// Divers test pour les type simple primitif
	double	a				= (int) (Math.random() * 10);
	
	Vector2d vector2d = new Vector2d(5.5,6.6);
	float dataset [] = new float[]{0,1,2,3,4,5,6};
	String dataset44 [] = new String[]{"Hello", "Salut", "Buenitos"};
	double dataset2 [] = new double[]{0.0,1.1,2.2,3.2,4.1,5.0,6.9};
	int dataset3 [] = new int[]{4,16,64,256};
	
	float arr2dFloat [][] = new float [][] {
			{1,2,3},
			{3,4,5},
			{6,7,8},
			{9,10,11},
	};
	String arr2dString [][] = new String [][] {
			{"1a","2e","3f"},
			{"3a","4e","5f"},
			{"6c","7d","8f"},
			{"9c","10d","11g"},
	};
	
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

	// Divers test pour les type File
	File	file_1			= new File("UnexistingFile");
	File	file_xml		= null;
	File	file_directory1	= new File(".");
	File	file_directory2	= new File("unknown");
	File	file_directory3	= null;

	long material_id = (int)(Math.random()*5);
	

	// Quelques test avec JWColor (equivalent du color mais avec un setter, oui c'est con je sais ... l'id�e est que je ne voulais recreer une nouvelle instance de color mais bien garder la meme pour conserver sa r�ference)
	JWColor	colorRed		= new JWColor(255, 0, 0);
	JWColor	colorGreen		= new JWColor(0, 255, 0);
	JWColor	colorBlue		= new JWColor(0, 0, 255);

	String textAreaTest = "Je suis un textArea";
	String textPaneTest = "Je suis un textPane";
	
	public ObjectExemple()
	{

	}

	
	
	/**
	 * @return the time1
	 */
	
	public synchronized Time getTime1()
	{
		return time1;
	}



	/**
	 * @param time1 the time1 to set
	 */
	public synchronized void setTime1(Time time1)
	{
		this.time1 = time1;
	
	}



	/**
	 * @return the time2
	 */
	
	public synchronized Time getTime2()
	{
		return time2;
	}



	/**
	 * @param time2 the time2 to set
	 */
	public synchronized void setTime2(Time time2)
	{
		this.time2 = time2;
	
	}



	@PROPERTY_interface(Operation = property_mode.PROPERTY_MERGEABLE, gui_type = gui_type.FLATSLIDER, min = 0, max = 100, divider = 100, orderDisplay=1)
	public synchronized double getAslider()
	{
		return aslider;
	}

	public synchronized void setAslider(double aslider)
	{
		this.aslider = aslider;
	}
	

	
	/**
	 * @return the vector2d
	 */
	@PROPERTY_interface(Operation = property_mode.PROPERTY_MERGEABLE)
	public synchronized Vector2d getVector2d()
	{
		return vector2d;
	}

	/**
	 * @param vector2d the vector2d to set
	 */
	public synchronized void setVector2d(Vector2d vector2d)
	{
		this.vector2d = vector2d;
	
	}
	
	
	@PROPERTY_interface(Operation = property_mode.PROPERTY_MERGEABLE)
	public float[] getDataset() {
		return dataset;
	}

	public void setDataset(float[] dataset) {
		this.dataset = dataset;
	}	
	
	@PROPERTY_interface(Operation = property_mode.PROPERTY_MERGEABLE)
	public String[] getDataset44() {
		return dataset44;
	}

	public void setDataset44(String[] dataset44) {
		this.dataset44 = dataset44;
	}
	
	

	@PROPERTY_interface(Operation = property_mode.PROPERTY_MERGEABLE)
	public double[] getDataset2() {
		return dataset2;
	}

	public void setDataset2(double[] dataset2) {
		this.dataset2 = dataset2;
	}

	
	@PROPERTY_interface(Operation = property_mode.PROPERTY_MERGEABLE)
	public int[] getDataset3() {
		return dataset3;
	}

	public void setDataset3(int[] dataset3) {
		this.dataset3 = dataset3;
	}

	

	/**
	 * @return the arr2dFloat
	 */
	@PROPERTY_interface(Operation = property_mode.PROPERTY_MERGEABLE)
	public synchronized float[][] getArr2dFloat()
	{
		return arr2dFloat;
	}

	/**
	 * @param arr2dFloat the arr2dFloat to set
	 */
	public synchronized void setArr2dFloat(float[][] arr2dFloat)
	{
		this.arr2dFloat = arr2dFloat;
	
	}

	/**
	 * @return the arr2dString
	 */
	@PROPERTY_interface(Operation = property_mode.PROPERTY_MERGEABLE)
	public synchronized String[][] getArr2dString()
	{
		return arr2dString;
	}

	/**
	 * @param arr2dString the arr2dString to set
	 */
	public synchronized void setArr2dString(String[][] arr2dString)
	{
		this.arr2dString = arr2dString;
	
	}

	@PROPERTY_interface(Operation = property_mode.PROPERTY_MERGEABLE, gui_type = gui_type.FLATSLIDER, min = 0, max = 100, divider = 1, orderDisplay=2)
	public synchronized int getBslidertest()
	{
		return bslidertest;
	}

	
	
	public synchronized void setBslidertest(int bslidertest)
	{
		this.bslidertest = bslidertest;
	}

	@PROPERTY_interface(Operation = property_mode.PROPERTY_MERGEABLE, gui_type = gui_type.SLIDER, min = 0, max = 100, divider = 1, orderDisplay=3)
	public synchronized int getBbisslidertest()
	{
		return bslidertest;
	}

	public synchronized void setBbisslidertest(int bslidertest)
	{
		this.bslidertest = bslidertest;
	}

	@PROPERTY_interface(Operation = property_mode.PROPERTY_MERGEABLE, editFormat = "", displayFormat = "4.2", orderDisplay=4)
	public synchronized double getA()
	{
		return a;
	}

	@PROPERTY_interface(Operation = property_mode.PROPERTY_MERGEABLE)
	public synchronized double getSurfaceA_AA()
	{
		return a * aa;
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
	
	
	@PROPERTY_interface(Operation=property_mode.PROPERTY_MERGEABLE, gui_type=gui_type.COMBO)
	public synchronized long getMaterial_id()
	{
		return material_id;
	}
	@PROPERTY_interface(Operation=property_mode.PROPERTY_MERGEABLE)
	public synchronized long getMaterial_id2()
	{
		return material_id;
	}
	public synchronized void setMaterial_id(long material_id)
	{
		this.material_id = material_id;
	}
	public synchronized void setMaterial_id2(long material_id)
	{
		this.material_id = material_id;
	}
	
	/**
	 * @return the textAreaTest
	 */
	@PROPERTY_interface(Operation=property_mode.PROPERTY_MERGEABLE, gui_type=gui_type.TEXTAREA)
	public synchronized String getTextAreaTest()
	{
		return textAreaTest;
	}

	/**
	 * @param textAreaTest the textAreaTest to set
	 */
	public synchronized void setTextAreaTest(String textAreaTest)
	{
		this.textAreaTest = textAreaTest;
	
	}

	/**
	 * @return the textPaneTest
	 */
	@PROPERTY_interface(Operation=property_mode.PROPERTY_MERGEABLE, gui_type=gui_type.TEXTPANE, readOnly=true)
	public synchronized String getTextPaneTest()
	{
		return textPaneTest;
	}

	/**
	 * @param textPaneTest the textPaneTest to set
	 */
	public synchronized void setTextPaneTest(String textPaneTest)
	{
		this.textPaneTest = textPaneTest;
	
	}

	@Override
	public String toString() {
		String ret = "ObjectExemple [a=" + a + ", vector2d=" + vector2d
				+ ", dataset=" + Arrays.toString(dataset) + ", dataset2="
				+ Arrays.toString(dataset44) + ", b=" + b + ", aa=" + aa
				+ ", aslider=" + aslider + ", bslidertest=" + bslidertest
				+ ", checkBoxTrue=" + checkBoxTrue + ", checkBoxFalse="
				+ checkBoxFalse + ", checkBoxDepend=" + checkBoxDepend
				+ ", readOnlyString=" + readOnlyString + ", birthDate="
				+ birthDate + ", file_1=" + file_1 + ", file_xml=" + file_xml
				+ ", file_directory1=" + file_directory1 + ", file_directory2="
				+ file_directory2 + ", file_directory3=" + file_directory3
				+ ", material_id=" + material_id + ", colorRed=" + colorRed
				+ ", colorGreen=" + colorGreen + ", colorBlue=" + colorBlue
				+ "]\r\n";

		ret+="DataSet=";
		for (int i = 0; i < dataset.length; i++) {
			ret+=dataset[i]+",";
		}
		ret+="\r\n";
		ret+="DataSet2=";
		for (int i = 0; i < dataset2.length; i++) {
			ret+=dataset2[i]+",";
		}
		ret+="\r\n";
		
		ret+="DataSet3=";
		for (int i = 0; i < dataset3.length; i++) {
			ret+=dataset3[i]+",";
		}
		ret+="\r\n";
		
		
		
		ret+="DataSet44=";
		for (int i = 0; i < dataset44.length; i++) {
			ret+=dataset44[i]+",";
		}
		ret+="\r\n";
		
		
		ret+="Arr2DFloat=\r\n";
		for (int i = 0; i < arr2dFloat.length; i++) {
			for (int j = 0; j < arr2dFloat[i].length; j++) {
				ret+=arr2dFloat[i][j]+",";
			}
			ret+="\r\n";
		}
		ret+="\r\n";
		
		ret+="Arr2DString=\r\n";
		for (int i = 0; i < arr2dString.length; i++) {
			for (int j = 0; j < arr2dString[i].length; j++) {
				ret+=arr2dString[i][j]+",";
			}
			ret+="\r\n";
		}
		ret+="\r\n";
		
	
		
		
		return ret;
	}
	
	
	

}
