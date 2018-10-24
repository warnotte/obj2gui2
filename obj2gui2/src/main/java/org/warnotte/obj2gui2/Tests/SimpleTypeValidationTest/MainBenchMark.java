/**
 * 
 */
package org.warnotte.obj2gui2.Tests.SimpleTypeValidationTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.JDOMException;
import org.warnotte.obj2gui2.Binding;
import org.warnotte.obj2gui2.BindingEnum;
import org.warnotte.obj2gui2.JPanelMagique;
import org.warnotte.obj2gui2.MyChangedEvent;
import org.warnotte.obj2gui2.MyEventListener;

/**
 * @author Warnotte Renaud
 *
 */
public class MainBenchMark
{

	static List<Material> list_mats;
	static List<ObjectExemple> selection;
	static ArrayList<Binding> binds;
	static ArrayList<BindingEnum> bindsEnum ;
	
	/**
	 * @param args
	 * @throws Exception 
	 * @throws JDOMException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException, JDOMException, Exception
	{
		list_mats = new ArrayList<Material>();
		
		list_mats.add(new Material(1, "SteelA"));
		list_mats.add(new Material(5, "SteelB"));
		list_mats.add(new Material(8, "SteelC"));
		list_mats.add(new Material(9, "SteelD"));
		list_mats.add(new Material(11, "SteelE"));
				
		selection = new ArrayList<ObjectExemple>();
		selection.add(new ObjectExemple());
		for (int i = 0; i < 300; i++)
		{
			selection.add(new ObjectExemple());
		}
		
		selection.add(new ObjectExemple());
		
		// This will help OBJ2GUI to assign an Object (from a list of Identifiable) to the value.
		binds = new ArrayList<Binding>();
		binds.add(new Binding(list_mats, ObjectExemple.class, "material_id"));

		// This will help OBJ2GUI to assign an enumeration value to a "long" or "int" ordinal value setted in a variable.
		bindsEnum = new ArrayList<BindingEnum>();
		BindingEnum bindE = new BindingEnum(cards.values(), ObjectExemple.class, "Id_card_reference");
		bindE.setIndexoffset(0);
		bindsEnum.add(bindE);
		
		MainBenchMark mbm = new MainBenchMark();
		for (int i = 0; i < 50000; i++)
		{
			mbm.CreateExemple();
		}
	}

	/**
	 * @throws Exception
	 * @throws IOException
	 * @throws JDOMException
	 */
	private void CreateExemple() throws Exception, IOException, JDOMException
	{
		JPanelMagique panel = JPanelMagique.GenerateJPanelFromSelectionAndBindings(selection, binds, bindsEnum);
		panel.LoadLabel("labels.xml");
		panel.addMyEventListener(new MyEventListener()
		{
			public void myEventOccurred(MyChangedEvent e)
			{
				System.err.println("*** PANELMAGIQUE-  Object has changed make the needed things ..."+e);
			}
		});
		panel=null;
	}
}
