/**
 * 
 */
package io.github.warnotte.obj2gui2.Tests.mainEmbbeder;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

import org.jdom2.JDOMException;

import io.github.warnotte.obj2gui2.JPanelMagique;
import io.github.warnotte.obj2gui2.MyChangedEvent;
import io.github.warnotte.obj2gui2.MyEventListener;

/**
 * @author Warnotte Renaud
 *
 */
public class mainTesteur
{

	

	/**
	 * !!! Ceci continue de tourner dans le vide...
	 * @throws Exception
	 * @throws IOException
	 * @throws JDOMException
	 */
	public static JFrame CreateExemple(final List selection) throws Exception, IOException, JDOMException
	{
		//final List<DTOMig> selection = new ArrayList<DTOMig>();
		//selection.add(new DTOMig());
				
		final JPanelMagique panel = JPanelMagique.GenerateJPanelFromSelectionAndBindings(selection, null, null);
		
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
		//fr.add(panel, BorderLayout.CENTER);
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
