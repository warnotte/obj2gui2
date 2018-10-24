package org.warnotte.obj2gui2.Tests.SimpleTypeDecimalFormattedFields;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

import org.jdom2.JDOMException;
import org.warnotte.obj2gui2.JPanelMagique;
import org.warnotte.obj2gui2.JTreeMagique;
import org.warnotte.obj2gui2.MyChangedEvent;
import org.warnotte.obj2gui2.MyEventListener;
import org.warnotte.waxlib2.Logs.Logs;
import org.warnotte.waxlib2.TemplatePropertyMerger.TemplatePropertyMergerV2;

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
		String file = JPanelMagique.class.getResource("log4j_OBJ2GUI2.xml").getFile();
		Logs logs = new Logs("mainTest", new File(file));
		JPanelMagique.PRINT_DEBUG=true;
		JTreeMagique.PRINT_DEBUG=true;
		TemplatePropertyMergerV2.PRINT_DEBUG=false;
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
		JPanelMagique.newSystem=false; // Mettre ceci pour ne avoir le texte "Different values"
				
		final List<ObjectExemple2> selection = new ArrayList<ObjectExemple2>();
		ObjectExemple2 o1 = new ObjectExemple2();
		ObjectExemple2 o2 = new ObjectExemple2();
		ObjectExemple2 o3 = new ObjectExemple2();
		selection.add(o1);
		selection.add(o2);
		selection.add(o3);
		o3.setCreditcard("144-98746-33");

		final JPanelMagique panel = JPanelMagique.GenerateJPanelFromSelectionAndBindings(selection, null, null);
		
		//GUI2XMLLabel.SaveLabel(panel, "labels_exemple_simple.xml");
		//panel.LoadLabel("labels_exemple_simple.xml");
		
		panel.addMyEventListener(new MyEventListener()
		{
			public void myEventOccurred(MyChangedEvent e)
			{
				System.err.println("MAIN - From PANELMAGIQUE-  Object has changed make the needed things ..."+e);
			}
		});
		
		
		
		JFrame fr = new JFrame();
		fr.setSize(640, 480);
		fr.add(new JScrollPane(panel), BorderLayout.CENTER);
		fr.setVisible(true);
		fr.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		fr.addMouseMotionListener(new MouseMotionListener(){

			@Override
			public void mouseDragged(MouseEvent e)
			{
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseMoved(MouseEvent e)
			{
				System.err.println(" Mouse = "+e.getX());
				
			}

			
			
		});
		
		
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
		
		
		return fr;
	}

	

	

}
