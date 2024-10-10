/**
 * 
 */
package io.github.warnotte.obj2gui2.Plugins.tests;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;

import io.github.warnotte.obj2gui2.Binding;
import io.github.warnotte.obj2gui2.BindingEnum;
import io.github.warnotte.obj2gui2.JPanelMagique;
import io.github.warnotte.obj2gui2.Tests.Complex.A;
import io.github.warnotte.obj2gui2.Tests.Complex.B;
import io.github.warnotte.obj2gui2.Tests.Complex.cards;
import io.github.warnotte.obj2gui2.Tests.SimpleType.Material;

/**
 * @author Warnotte Renaud
 *
 */
public class mainPluginTest
{

	static List<Material> list_mats2 = new ArrayList<Material>();
	static
	{
		list_mats2.add(new Material(1, "SteelA"));
		list_mats2.add(new Material(5, "SteelB"));
		list_mats2.add(new Material(8, "SteelC"));
		list_mats2.add(new Material(9, "SteelD"));
		list_mats2.add(new Material(11, "SteelE"));
		
	}
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception
	{
		final A a = new A();
		final A b = new A();
		b.setRef_MaterialCombo(9);
		List<A> tps = new ArrayList<>();
		tps.add(a);
		tps.add(b);
		
		// Pour la classe A on a besoin d'avoir une liste de reference...
		List<Material> list_mats = new ArrayList<Material>();
		
		list_mats.add(new Material(1, "SteelA"));
		list_mats.add(new Material(5, "SteelB"));
		list_mats.add(new Material(8, "SteelC"));
		list_mats.add(new Material(9, "SteelD"));
		list_mats.add(new Material(11, "SteelE"));
		
		//JideDateExcomboboxPlug plugin1 = new JideDateExcomboboxPlug(manager);
		//JideDateChooserPlug2 plugin2 = new JideDateChooserPlug2(manager);
		io.github.warnotte.obj2gui2.Plugins.tests.JTextAreaFieldPlug plugin3 = new io.github.warnotte.obj2gui2.Plugins.tests.JTextAreaFieldPlug();
		//GuiGenerator.Plugins.tests.Vector2DPlugin plugin4 = new GuiGenerator.Plugins.tests.Vector2DPlugin();
		
		
		//JPanelMagique.registerPlugin(plugin2);
		JPanelMagique.registerPlugin(plugin3);
		//JPanelMagique.registerPlugin(plugin4);
		//JPanelMagique.registerPlugin(plugin1);
		
		MaterialCombobox pluginN2 = new MaterialCombobox(A.class, "ref_MaterialCombo");
		JPanelMagique.registerPlugin2(pluginN2);
		
		
		// This will help OBJ2GUI to assign an Object (from a list of Identifiable) to the value.
		final ArrayList<Binding> binds = new ArrayList<Binding>();
		binds.add(new Binding(list_mats, B.class, "material_id"));

		// This will help OBJ2GUI to assign an enumeration value to a "long" or "int" ordinal value setted in a variable.
		final ArrayList<BindingEnum> bindsEnum = new ArrayList<BindingEnum>();
		BindingEnum bindE = new BindingEnum(cards.values(), B.class, "Id_card_reference");
		bindE.setIndexoffset(0);
		bindsEnum.add(bindE);
		
		
		JPanelMagique jp = JPanelMagique.GenerateJPanelFromSelectionAndBindings(tps, binds, bindsEnum);
		
		JFrame frame = JPanelMagique.embbedInFrame(jp);
		frame.setVisible(true);
		JButton button = new JButton("DEBUG");
		button.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				System.err.println(""+a);
				
				
			}
			
		});
		
		frame.add(button, BorderLayout.SOUTH);
	}

}
