/**
 * 
 */
package io.github.warnotte.obj2gui2.experimentOBJ2GUIPlugin3;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
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

	public static List<Material> list_mats2 = new ArrayList<Material>();
	static
	{
		list_mats2.add(new Material(1, "SteelA"));
		list_mats2.add(new Material(20, "SteelB"));
		list_mats2.add(new Material(30, "SteelC"));
		list_mats2.add(new Material(40, "SteelD"));
		list_mats2.add(new Material(50, "SteelE"));
		
	}
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception
	{
		List<Flotteur> tps = new ArrayList<>();
		
		//public Flotteur(String iD_ObjetSurFlotteur, Material material, Material materialSupported, Integer idMaterial) {
		
		tps.add(new Flotteur("1", list_mats2.get(0), list_mats2.get(1), 20, 0.35));
		tps.add(new Flotteur("1", list_mats2.get(0), list_mats2.get(1), 20, 0.35));
		tps.add(new Flotteur("1", list_mats2.get(0), list_mats2.get(1), 30, 0.35));
		tps.add(new Flotteur("8", list_mats2.get(0), list_mats2.get(1), 20, 0.35));
		tps.add(new Flotteur("1", list_mats2.get(0), list_mats2.get(1), 20, 0.35));
		
		
		JPanelMagique.registerPlugin3(new TextFieldID());
		JPanelMagique.registerPlugin3(new MaterialCombobox()); // TODO : Doesn't work
		JPanelMagique.registerPlugin3(new MaterialIDCombobox()); // TODO : Solve that problem
		JPanelMagique.registerPlugin3(new RenderedTextFieldValue());
		
		JPanelMagique jp = JPanelMagique.GenerateJPanelFromSelectionAndBindings(tps, null, null);
		
		JFrame frame = JPanelMagique.embbedInFrame(jp);
		frame.setVisible(true);
		JButton button = new JButton("DEBUG");
		button.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				int cpt = 0;
				for (Iterator iterator = tps.iterator(); iterator.hasNext();) {
					Flotteur flotteur = (Flotteur) iterator.next();
					System.err.println(cpt+ ") "+ flotteur);
				}
				
				
			}
			
		});
		
		frame.add(button, BorderLayout.SOUTH);
	}

}
