package org.warnotte.obj2gui2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.swing.CellRendererPane;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JViewport;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jdesktop.swingx.JXCollapsiblePane;
import org.jdesktop.swingx.JXDatePicker;
import org.jdesktop.swingx.JXTaskPane;
import org.jdesktop.swingx.JXTaskPaneContainer;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.warnotte.waxlibswingcomponents.FileChooser.FileChooser;
import org.warnotte.waxlibswingcomponents.Swing.Component.JColorChooserButton;
import org.warnotte.waxlibswingcomponents.Swing.Component.WaxSlider.WFlatSlider;
import org.warnotte.waxlibswingcomponents.Swing.Component.WaxSlider.WRoundSlider;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

// TODO : Penser a enventuelement faire un systeme de gestion des classes 
// TODO : Dans le sens ou 2 classes != peuvent avoir une variable au meme nom mais dont le label et le tooltips pourrait devoir �tre diff�rent.

/**
 * Classe permettant de sauvegarder/charger un XML � partir ou vers un GUI
 * genere par ParseurAnnotations afin de customiser les labels ainsi que les
 * tooltips gener� par le guiifieur et qui sont plutot codeur orient�
 * 
 * @author Warnotte Renaud Novembre 2007
 */
public class GUI2XMLLabel
{

	private static final Logger log = LogManager.getLogger("GUI2XMLLabel");
	

	private static GUI2XMLLabel	instance	= new GUI2XMLLabel();

	public static GUI2XMLLabel getInstance()
	{
		return instance;
	}

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception
	{

		/*
		 * final Test objet = new Test(); JFrame frame = new JFrame();
		 * frame.setSize(320,200); frame.setPreferredSize(new
		 * Dimension(320,200)); frame.setVisible(true); frame.setLayout(new
		 * BorderLayout()); // Cr�e ces 3 panels sans ajouter la variable change
		 * listener qui pompe les ressources si 10000 de changements final
		 * JWPanel panel = (JWPanel)
		 * ParseurAnnotations.CreatePanelFromObject("Main configuration",
		 * objet,false); panel.addMyEventListener(new MyEventListener() { public
		 * void myEventOccurred(MyChangedEvent e) { //
		 * System.err.println("*** Object has changed make the needed things ..."
		 * ); } }); //obj1.addVariableChangeListener( new
		 * ModificationListener(panObj,obj1)); // Ajoute du panel dans la frame
		 * //frame.add(new JScrollPane(panel),BorderLayout.CENTER);
		 * frame.add(new JScrollPane(panel),BorderLayout.CENTER); JButton button
		 * = new JButton(); button.setText("Debug");
		 * button.addActionListener(new java.awt.event.ActionListener() { public
		 * void actionPerformed(java.awt.event.ActionEvent e) { objet.debug();
		 * panel.fireMyEvent(new MyChangedEvent(this, null)); } }); JButton
		 * button2 = new JButton(); button2.setText("Refresh Panel");
		 * button2.addActionListener(new java.awt.event.ActionListener() {
		 * public void actionPerformed(java.awt.event.ActionEvent e) { try {
		 * ParseurAnnotations.Refresh_PanelEditor_For_Object("",
		 * panel,objet,panel,false); } catch (Exception e1) { // TODO
		 * Auto-generated catch block e1.printStackTrace(); } } }); JButton
		 * button3 = new JButton();
		 * button3.setText("Add Object to Vector of Object");
		 * button3.addActionListener(new java.awt.event.ActionListener() {
		 * public void actionPerformed(java.awt.event.ActionEvent e) {
		 * objet.addObjets(); try {
		 * ParseurAnnotations.Refresh_PanelEditor_For_Object("",
		 * panel,objet,panel,false); } catch (Exception e1) { // TODO
		 * Auto-generated catch block e1.printStackTrace(); } } });
		 * frame.add(button,BorderLayout.SOUTH);
		 * frame.add(button2,BorderLayout.NORTH);
		 * frame.add(button3,BorderLayout.EAST); frame.validate(); frame.pack();
		 */
		//GUI2XMLLabel xml = new GUI2XMLLabel();
		//xml.SaveLabel(panel, "f:\\test.xml");

		/*
		 * List<ParametreOptimiseur> selection = new ArrayList<>();
		 * ParametreOptimiseur po = new ParametreOptimiseur();
		 * selection.add(po); JPanelMagique panel =
		 * JPanelMagique.GenerateJPanelFromSelectionAndBindings(selection, null,
		 * null); GUI2XMLLabel.LoadLabel(panel,
		 * "e:\\Manager.IO.Optimiseur.DTOs.ParametreOptimiseur_labels.xml");
		 * GUI2XMLLabel.SaveLabel(panel,
		 * "e:\\Manager.IO.Optimiseur.DTOs.ParametreOptimiseur_labels2.xml");
		 * GUI2XMLLabel.LoadLabel(panel,
		 * "e:\\Manager.IO.Optimiseur.DTOs.ParametreOptimiseur_labels2.xml");
		 * GUI2XMLLabel.SaveLabel(panel,
		 * "e:\\Manager.IO.Optimiseur.DTOs.ParametreOptimiseur_labels3.xml");
		 */
	}

	HashMap<String, String>		labels_map		= new HashMap<String, String>();
	HashMap<String, String>		tooltips_map	= new HashMap<String, String>();
	private final boolean		VERBOSE			= false;
	static org.jdom2.Document	document;
	static Element				racine;

	public void debug()
	{
		Set<String> key = labels_map.keySet();
		for (int i = 0; i < key.size(); i++)
		{
			String cle = (String) key.toArray()[i];
			String value = labels_map.get(cle);
			log.info(cle + "=" + value);
			value = tooltips_map.get(cle);
			log.info(cle + "=" + value);
		}
	}

	public GUI2XMLLabel()
	{
	}

	public static boolean LoadLabel(JPanel panel, String filename) throws Exception
	{
		return getInstance().LoadLabelM(panel, filename);
	}

	public static boolean SaveLabel(JPanel panel, String filename) throws Exception
	{
		return getInstance().SaveLabelM(panel, filename);
	}

	/**
	 * Charge un XML et assigne les label et tooltips au panel generer par le
	 * generateur de gui
	 * 
	 * @param panel
	 *            Le panel dont il faut modifier les labels etc.
	 * @param filename
	 *            Le fichier XML contenant les Elements.
	 * @throws IOException
	 * @throws JDOMException
	 * @throws SAXException 
	 * @throws ParserConfigurationException 
	 */
	private boolean LoadLabelM(JPanel panel, String filename) throws IOException, JDOMException, SAXException
	{
		labels_map.clear();
		tooltips_map.clear();

		

		File file = new File(filename);
		if (file.exists() == false)
		{
			log.warn("Warning i cannot read " + filename + " so i don't load Labels");
			return false;
		}
		
		InputStream inputStream= new FileInputStream(filename);
		InputStreamReader inputReader = new InputStreamReader(inputStream,"UTF-8");
		InputSource inputSource = new InputSource(inputReader);
		inputSource.setEncoding("UTF-8");
		SAXBuilder sxb = new SAXBuilder();
		//document = sxb.build(file);
		
		document = sxb.build(inputSource);
		
		

		racine = document.getRootElement();

		List<?> listConn = racine.getChildren("element"); //$NON-NLS-1$
		Iterator<?> i = listConn.iterator();

		while (i.hasNext())
		{
			Element courant = (Element) i.next();
			String cle = courant.getAttribute("name").getValue();
			String tooltips = courant.getAttribute("tooltips").getValue();
			String label = courant.getAttribute("label").getValue();
			if (cle == null)
				log.warn("Attention une cle est null dans le fichier " + filename);
			labels_map.put(cle, label);
			tooltips_map.put(cle, tooltips);
			
		//	log.warn("GUI2XML : Read ["+label+"]");
			
		}
		recursToPanel(panel, 0);
		return true;
	}

	private boolean SaveLabelM(JPanel panel, String filename) throws IOException
	{
		if (VERBOSE == true)
			log.info("Saving panel named " + filename);
		File f = new File(filename);

		//PrintStream ps = new PrintStream(f);

		labels_map.clear();
		tooltips_map.clear();
		PanelTorecurs(panel, 0);
		Set<String> key = labels_map.keySet();

		ArrayList<String> list_key = new ArrayList<>();
		list_key.addAll(key);
		Collections.sort(list_key);

		Element elements = new Element("Elements");
		Document document = new Document(elements);

		for (int i = 0; i < list_key.size(); i++)
		{
			String cle = (String) list_key.toArray()[i];
			String tips_value = tooltips_map.get(cle);
			String label_value = labels_map.get(cle);
			if (tips_value == null)
				tips_value = "";
			if (tips_value.length() == 0)
				tips_value = "";
			if (tips_value.equals("null"))
				tips_value = "";

			Element element = new Element("element");
			element.setAttribute("name", cle);
			element.setAttribute("label", label_value);
			element.setAttribute("tooltips", tips_value);

			//log.warn("GUI2XML : Write ["+label_value+"]");
			
			elements.addContent(element);
		}

		XMLOutputter xmlOutput = new XMLOutputter();

		// passed fileWriter to write content in specified file  
		xmlOutput.setFormat(Format.getPrettyFormat());
		xmlOutput.getFormat().setEncoding("utf-8");
		OutputStreamWriter fstream = new OutputStreamWriter(new FileOutputStream(filename), StandardCharsets.UTF_8);
		xmlOutput.output(document,fstream/* new FileWriter(filename)*/);

		return true;
	}

	private void PanelTorecurs(JComponent cp2, int depth)
	{
		/*
		 * String tab=""; for (int i = 0 ;i < depth;i++) tab+="\t";
		 */
		int nbr_comps = cp2.getComponents().length;
		for (int i = 0; i < nbr_comps; i++)
		{
			if (cp2.getComponents()[i] instanceof CellRendererPane)
				continue;

			JComponent cp = (JComponent) cp2.getComponents()[i];

			if (cp instanceof FileChooser)
			{
				//log.warn("Nothing to do for FileChooser : "+cp.getClass().getName());
			} else if ((cp instanceof JXTaskPane) || (cp instanceof JXCollapsiblePane))
			{
				if (cp instanceof JXCollapsiblePane)
				{
					String label = ((JXCollapsiblePane) cp).getParent().getName();
					labels_map.put(label, label);
					label = ((JXCollapsiblePane) cp).getToolTipText();
					tooltips_map.put(label, label);
				} else
				{
					String label = ((JXTaskPane) cp).getTitle();
					labels_map.put(cp.getName(), label);
					label = ((JXTaskPane) cp).getToolTipText();
					tooltips_map.put(cp.getName(), label);
				}
				PanelTorecurs(cp, depth + 1);
			} else if ((cp instanceof JCheckBox) || (cp instanceof JLabel) || (cp instanceof JTextField) || (cp instanceof JTextArea) || (cp instanceof JTextPane) || (cp instanceof JList) || (cp instanceof JComboBox) || (cp instanceof JColorChooserButton) || (cp instanceof JXDatePicker) || (cp instanceof JSlider) || (cp instanceof WFlatSlider) || (cp instanceof WRoundSlider) || (cp instanceof JButton) || (cp instanceof JRadioButton))
			{
				String label = cp.getName();
				if (cp instanceof JCheckBox)
					label = ((JCheckBox) cp).getText();
				if (cp instanceof JLabel)
					label = ((JLabel) cp).getText();
				if (cp instanceof JTextField)
					label = ((JTextField) cp).getText();
				if (cp instanceof JTextArea)
					label = ((JTextArea) cp).getText();
				if (cp instanceof JTextPane)
					label = ((JTextPane) cp).getText();
				if (cp instanceof JRadioButton)
					label = ((JRadioButton) cp).getText();
				else if (cp instanceof JButton)
					label = ((JButton) cp).getText();
				if (label != null)
				{
					//System.out.println(tab+""+cp.getName()+" == "+label );
					labels_map.put(cp.getName(), label);

				}
				if (cp instanceof JLabel == false)
				{
					label = cp.getToolTipText();
					tooltips_map.put(cp.getName(), label);
				}
				//else
				//	System.err.println("No name for component "+cp);
			} else if (cp instanceof JViewport)
			{
				PanelTorecurs((JComponent) ((JViewport) cp).getView(), depth + 1);
			} else if (cp instanceof JScrollPane)
			{
				PanelTorecurs((JComponent) ((JScrollPane) cp).getViewport().getView(), depth + 1);
			} else if (cp instanceof JPanelMagique)
			{
				PanelTorecurs((cp), depth + 1);
			} else if (cp instanceof JTabbedPane)
			{
				for (int k = 0; k < ((JTabbedPane) cp).getTabCount(); k++)
				{

					//System.out.println(tab+""+((JTabbedPane)cp).getTitleAt(k)+" == "+((JTabbedPane)cp).getTitleAt(k));
					labels_map.put(((JTabbedPane) cp).getName(), ((JTabbedPane) cp).getTitleAt(k));
					//labels_map.put(((JTabbedPane)cp).getTitleAt(k),((JTabbedPane)cp).getTitleAt(k));

					String label = ((JTabbedPane) cp).getToolTipText();
					tooltips_map.put(((JTabbedPane) cp).getTitleAt(k), label);
					PanelTorecurs((JComponent) ((JTabbedPane) cp).getComponent(k), depth + 1);
				}
			} else if (cp instanceof JXTaskPaneContainer)
			{
				for (int k = 0; k < ((JXTaskPaneContainer) cp).getComponentCount(); k++)
				{
					//map.put(((JXTaskPaneContainer)cp).getComponent(k).getName(),((JXTaskPaneContainer)cp).getComponent(k).getName());
					PanelTorecurs((JComponent) ((JXTaskPaneContainer) cp).getComponent(k), depth + 1);
				}
			}

			else if (cp instanceof JPanel)
			{
				PanelTorecurs(cp, depth + 1);
			} else
			{
				if (VERBOSE == true)
					log.warn("Component non geré : " + cp.getClass().getName());
			}
		}
	}

	private void recursToPanel(JComponent cp2, int depth)
	{
		int nbr_comps = cp2.getComponents().length;
		for (int i = 0; i < nbr_comps; i++)
		{
			if ((cp2.getComponents()[i] instanceof CellRendererPane))
				continue;
			JComponent cp = (JComponent) cp2.getComponents()[i];
			if ((cp instanceof JXTaskPane) || (cp instanceof JXCollapsiblePane))
			{
				if (cp instanceof JXCollapsiblePane)
				{
					String label = ((JXCollapsiblePane) cp).getParent().getName();
					String valeur = labels_map.get(label);
					if (valeur == null)
						valeur = "UNDEFINEDELEMENT_" + cp.getParent().getName();
					((JXTaskPane) ((JXCollapsiblePane) cp).getParent()).setTitle(valeur);
				} else
				{
					String label = ((JXTaskPane) cp).getName();
					String valeur = labels_map.get(label);
					if (valeur == null)
						valeur = "UNDEFINEDELEMENT_" + cp.getName();
					((JXTaskPane) cp).setTitle(valeur);

					String value = tooltips_map.get(((JXTaskPane) cp).getName());
					cp.setToolTipText(value);

				}
				recursToPanel(cp, depth + 1);
			} else if ((cp instanceof JCheckBox) || (cp instanceof JLabel) || (cp instanceof JTextField) || (cp instanceof JTextArea) || (cp instanceof JTextPane) || (cp instanceof JList) || (cp instanceof JComboBox) || (cp instanceof JColorChooserButton) || (cp instanceof JXDatePicker) || (cp instanceof JSlider) || (cp instanceof WFlatSlider) || (cp instanceof WRoundSlider) || (cp instanceof JRadioButton) || (cp instanceof JButton))
			{
				String value = labels_map.get(cp.getName());
				if (value == null)
					value = "UNDEFINEDELEMENT_" + cp.getName();
				//	if (cp instanceof JTextArea)
				//		((JTextArea) cp).setText(value);
				//	if (cp instanceof JTextPane)
				//		((JTextPane) cp).setText(value);
				if (cp instanceof JCheckBox)
					((JCheckBox) cp).setText(value);
				if (cp instanceof JButton)
					((JButton) cp).setText(value);
				if (cp instanceof JLabel)
					((JLabel) cp).setText(value);
				if (cp instanceof JRadioButton)
					((JRadioButton) cp).setText(value);
				if (cp instanceof JLabel == false)
				{
					value = tooltips_map.get(cp.getName());
					if (value == null)
						value = "UNDEFINEDELEMENT_" + cp.getName();
					cp.setToolTipText(value);
				}
			} else if (cp instanceof JViewport)
			{
				recursToPanel((JComponent) ((JViewport) cp).getView(), depth + 1);
			} else if (cp instanceof JScrollPane)
			{
				recursToPanel((JComponent) ((JScrollPane) cp).getViewport().getView(), depth + 1);
			} else if (cp instanceof JTabbedPane)
			{
				for (int k = 0; k < ((JTabbedPane) cp).getTabCount(); k++)
				{
					//System.out.println(tab+""+((JTabbedPane)cp).getTitleAt(k)+" == "+((JTabbedPane)cp).getTitleAt(k));
					String cle = ((JTabbedPane) cp).getTitleAt(k); // ?????
					//		String cle = (String)((JTabbedPane)cp).getComponent(k).getName();
					String value = labels_map.get(cle);

					if (value != null)
						((JTabbedPane) cp).setTitleAt(k, value);
					else
					{
						value = "UNDEFINEDELEMENT_" + cle;
						((JTabbedPane) cp).setTitleAt(k, value);
					}
					value = tooltips_map.get(((JTabbedPane) cp).getTitleAt(k));
					cp.setToolTipText(value);
					recursToPanel((JComponent) ((JTabbedPane) cp).getComponent(k), depth + 1);
				}
			} else if (cp instanceof JXTaskPaneContainer)
			{
				for (int k = 0; k < ((JXTaskPaneContainer) cp).getComponentCount(); k++)
					recursToPanel((JComponent) ((JXTaskPaneContainer) cp).getComponent(k), depth + 1);
			} else if (cp instanceof FileChooser)
			{
				// Do nothing for now ...
			} else if (cp instanceof JPanel)
				recursToPanel(cp, depth + 1);
			else if (VERBOSE == true)
				log.warn("Component non geré : " + cp.getClass().getName());
		}
	}
}
