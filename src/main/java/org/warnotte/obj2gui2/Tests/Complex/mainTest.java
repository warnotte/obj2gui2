package org.warnotte.obj2gui2.Tests.Complex;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

import org.jdom2.JDOMException;
import org.warnotte.obj2gui2.Binding;
import org.warnotte.obj2gui2.BindingEnum;
import org.warnotte.obj2gui2.JPanelMagique;
import org.warnotte.obj2gui2.JTreeMagique;
import org.warnotte.obj2gui2.MyChangedEvent;
import org.warnotte.obj2gui2.MyEventListener;
import org.warnotte.obj2gui2.Tests.SimpleType.Material;

/**
 * @author warnotte Renaud
 */
public class mainTest
{

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception
	{
	//	String file = JPanelMagique.class.getResource("log4j_OBJ2GUI2.xml").getFile();
	//	Logs logs = new Logs("mainTest", new File(file));
		
	//	JPanelMagique.PRINT_DEBUG=true;
	//	JTreeMagique.PRINT_DEBUG=true;
	//	TemplatePropertyMergerV2.PRINT_DEBUG=false;
		CreateExemple();
	}

	/**
	 * !!! Ceci continue de tourner dans le vide...
	 * @throws Exception
	 * @throws IOException
	 * @throws JDOMException
	 */
	private static JFrame CreateExemple() throws Exception, IOException, JDOMException
	{
	List<Material> list_mats = new ArrayList<Material>();
		
		list_mats.add(new Material(1, "SteelA"));
		list_mats.add(new Material(5, "SteelB"));
		list_mats.add(new Material(8, "SteelC"));
		list_mats.add(new Material(9, "SteelD"));
		list_mats.add(new Material(11, "SteelE"));
				
		final List<A> selection = new ArrayList<A>();
		selection.add(new A());
		selection.add(new A());
		selection.add(new A());
	//	Thread.sleep(1000);
	//	selection.add(new KOPKOK());
	//	selection.add(new KOPKOK());
				
		// This will help OBJ2GUI to assign an Object (from a list of Identifiable) to the value.
		final ArrayList<Binding> binds = new ArrayList<Binding>();
		binds.add(new Binding(list_mats, B.class, "material_id"));

				// This will help OBJ2GUI to assign an enumeration value to a "long" or "int" ordinal value setted in a variable.
				final ArrayList<BindingEnum> bindsEnum = new ArrayList<BindingEnum>();
				BindingEnum bindE = new BindingEnum(cards.values(), B.class, "Id_card_reference");
				bindE.setIndexoffset(0);
				bindsEnum.add(bindE);
		
				JPanelMagique panel = JPanelMagique.GenerateJPanelFromSelectionAndBindings(selection, binds, bindsEnum);
		//GUI2XMLLabel.SaveLabel(panel, "labels_exemple_complex.xml");
	//	panel.LoadLabel("labels_exemple_complex.xml");
		
		JTreeMagique paneltree = JTreeMagique.GenerateJTreeFromSelectionAndBindings(selection, binds, bindsEnum, "labels_exemple_complex.xml", true, true);
		JTreeMagique paneltree2 = JTreeMagique.GenerateJTreeFromSelectionAndBindings(selection, binds, bindsEnum, "labels_exemple_complex.xml", false, true);
		
		panel.addMyEventListener(new MyEventListener()
		{
			public void myEventOccurred(MyChangedEvent e)
			{
				System.err.println("*** PANELMAGIQUE-  Object has changed make the needed things ..."+e);
			}
		});
		paneltree.addMyEventListener(new MyEventListener()
		{
			public void myEventOccurred(MyChangedEvent e)
			{
				System.err.println("*** TREEMAGIQUE1 - Object has changed make the needed things ..."+e);
			}
		});
		

		paneltree2.addMyEventListener(new MyEventListener()
		{
			public void myEventOccurred(MyChangedEvent e)
			{
				System.err.println("*** TREEMAGIQUE2 - Object has changed make the needed things ..."+e);
			}
		});
		
		JFrame fr = new JFrame();
		fr.setSize(640, 480);
		fr.add(new JScrollPane(panel), BorderLayout.CENTER);
		fr.setVisible(true);
		fr.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		JFrame fr2 = new JFrame();
		fr2.setSize(640, 480);
		fr2.add(paneltree, BorderLayout.CENTER);
		fr2.setVisible(true);
		fr2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		JFrame fr3 = new JFrame();
		fr3.setSize(640, 480);
		fr3.add(paneltree2, BorderLayout.CENTER);
		fr3.setVisible(true);
		fr3.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		JButton button = new JButton();
		button.setText("Debug");
		button.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				for (int i = 0; i < selection.size(); i++)
				{
					System.err.println("Item " + i);
					System.err.println(selection.get(i));
				}
			}
		});
		fr.add(button, BorderLayout.SOUTH);
		
		JButton button2 = new JButton();
		button2.setText("AnotherView");
		button2.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				JPanelMagique panel;
				try
				{
					panel = JPanelMagique.GenerateJPanelFromSelectionAndBindings(selection,binds, bindsEnum);
					JFrame fr = new JFrame();
					fr.setSize(640, 480);
					fr.add(panel, BorderLayout.CENTER);
					fr.setVisible(true);
				} catch (IllegalArgumentException e1)
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IllegalAccessException e1)
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (InvocationTargetException e1)
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (Exception e1)
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		fr.add(button2, BorderLayout.EAST);
		return fr;
	}

	

	

}
