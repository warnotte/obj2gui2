package io.github.warnotte.obj2gui2.Tests.Calculatrice;

import java.awt.BorderLayout;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import io.github.warnotte.obj2gui2.JPanelMagique;
import io.github.warnotte.obj2gui2.MyChangedEvent;
import io.github.warnotte.obj2gui2.MyEventListener;

public class test_main_calculatrice {

	public static void main(String args[]) throws Exception
	{
			
		List<testCalculatrice> selection = new ArrayList<testCalculatrice>();
		selection.add(new testCalculatrice());
		JPanelMagique panel = JPanelMagique.GenerateJPanelFromSelectionAndBindings(selection, null, null);
		panel.addMyEventListener(new MyEventListener()
		{
			public void myEventOccurred(MyChangedEvent e)
			{
				System.err.println("*** Object has changed make the needed things ..."+e);			
			}
		});
		
		JFrame fr = new JFrame();
		fr.setSize(640, 480);
		fr.add(new JScrollPane(panel), BorderLayout.CENTER);
		fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fr.setVisible(true);

	}
}
