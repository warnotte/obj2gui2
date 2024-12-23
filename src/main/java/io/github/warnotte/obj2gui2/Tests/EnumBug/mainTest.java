package io.github.warnotte.obj2gui2.Tests.EnumBug;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

import org.jdom2.JDOMException;

import io.github.warnotte.obj2gui2.Binding;
import io.github.warnotte.obj2gui2.BindingEnum;
import io.github.warnotte.obj2gui2.JPanelMagique;
import io.github.warnotte.obj2gui2.JTreeMagique;
import io.github.warnotte.obj2gui2.MyChangedEvent;
import io.github.warnotte.obj2gui2.MyEventListener;
import io.github.warnotte.waxlib3.core.TemplatePropertyMerger.TemplatePropertyMergerV2;

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
//		String file = JPanelMagique.class.getResource("log4j_OBJ2GUI2.xml").getFile();
		JPanelMagique.PRINT_DEBUG=true;
		JTreeMagique.PRINT_DEBUG=true;
		TemplatePropertyMergerV2.PRINT_DEBUG=false;
		
		final List<Ilot> selection = new ArrayList<>();
		selection.add(new Ilot(0, Axe.X));
		selection.add(new Ilot(1, Axe.X));
		selection.add(new Ilot(2, Axe.X));
		
		CreateExemple(selection);
		
		final List<Ilot> selection2 = new ArrayList<>();
		selection2.add(new Ilot(3, Axe.X));
		selection2.add(new Ilot(4, Axe.X));
		selection2.add(new Ilot(5, Axe.Y));
		
		CreateExemple(selection2);
	}

	/**
	 * !!! Ceci continue de tourner dans le vide...
	 * @throws Exception
	 * @throws IOException
	 * @throws JDOMException
	 */
	private static JFrame CreateExemple(List<Ilot> selection) throws Exception, IOException, JDOMException
	{
		
		//final JPanelMagique panel = JPanelMagique.GenerateJPanelFromSelectionAndBindings(selection, null, null);
		final JPanelMagique panel = JPanelMagique.GenerateJPanelFromSelectionAndBindings(selection, null, null);
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
					System.err.println("Item " + i+ " -> " + selection.get(i));
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
				JPanelMagique panelM = null;
				try
				{
					panelM = JPanelMagique.GenerateJPanelFromSelectionAndBindings(panel, selection,null, null);
					JFrame fr = new JFrame();
					fr.setSize(640, 480);
					fr.add(panelM, BorderLayout.CENTER);
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
