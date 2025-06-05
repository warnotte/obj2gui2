package io.github.warnotte.obj2gui2;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.github.warnotte.waxlib3.core.TemplatePropertyMerger.Annotations.PROPERTY_FIELD_XXXABLE;
import net.miginfocom.swing.MigLayout;


// TODO : Utiliser Classe c'est bien mais pour LBR5 par exemple le scantling a 2 variable Frame et 2 variable Stiffener et le truc ici
// confondra donc les 2 :( --> KOKOK tester je pensais que c'est fait.
// 

// TODO : Verifier un peu pourquoi je recr�e a chaque fois les panel et l'utilit� de ca ...
/**
 * @author Warnotte Renaud
 * @date 2011-2013
 *
 */
public class JTreeMagique extends JPanel implements TreeSelectionListener, MyEventListener
{

	private static final Logger LOGGER = LogManager.getLogger(JTreeMagique.class);

	
	public static boolean PRINT_DEBUG = false;
	
	public static int CompteurInstance = 0;
	public int id;
	
	boolean showTreeViewButtons;
	
	@Override
	public int hashCode()
	{
		return id;
	}
	
	@Override
	public String toString()
	{
		return getClass().getName()+":"+hashCode();
	}
	
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1369589669482967290L;

	// La list des objets a "afficher/editer".
	List<?> selection;
	
	private HashMap<TreeNodeWax, List<?>> mapSons = new HashMap<TreeNodeWax, List<?>>();
	
	// Lien pour relier un "entier" ou "long" servant d'id dans une classe vers Arraylist d'identifiable
	List<Binding> bindList_identifiables = new ArrayList<Binding>();
	// Lien pour relier un "entier" ou "long" servant d'id dans une classe vers une enumeration
	List<BindingEnum> bindList = new ArrayList<BindingEnum>();
	
	List<JPanelMagique> SonsList = new ArrayList<JPanelMagique>(); 
	
	// Pass� par son parent pour savoir quel fichier XML IL faut a JPanelMagique
	String XMLLabelFile=null; 

	
	// Element du GUI.
	JPanel pan_tree_cmd;
	JPanel tree_bouton_cmd;
	public JTree jtree;
	JComponent editor;
	JCheckBox recursive_box;
	JCheckBox OnlyAnnotatedMethods_box;
	
	
	/**
	 * Create the panel.
	 * @param selection 
	 * @wbp.parser.constructor
	 */
	public JTreeMagique(List<?> selection, List<Binding> bindings_identifiable, List<BindingEnum> bindings_enum)
	{
		this(selection, bindings_identifiable, bindings_enum, null, true);
	}
	/**
	 * @param selection2
	 * @param binds
	 * @param bindsEnum
	 * @param xMLLabelFile
	 * @return
	 */
	public JTreeMagique(List<?> selection, List<Binding> bindings_identifiable, List<BindingEnum> bindings_enum, String xMLLabelFile, boolean Showtreeviewbutton)
	{
		synchronized(this)
		{
			CompteurInstance++;
			id=CompteurInstance;
		}
		
		showTreeViewButtons=Showtreeviewbutton;
		this.selection = selection;
		this.bindList_identifiables = bindings_identifiable;
		this.bindList = bindings_enum;
		this.XMLLabelFile=xMLLabelFile;
	//	setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		setLayout(new MigLayout("ins 4", "fill,grow", "fill,grow"));
		
	}

	/**
	 * @param selection
	 * @return
	 */
	public static JTreeMagique GenerateJTreeFromSelectionAndBindings(List<?> selection, List<Binding> bindings_identifiable, List<BindingEnum> bindings_enum, String xMLLabelFile, boolean UseTabbedPane, boolean showTreeViewbuttons)
	{
		// Si on donne une liste vide alors on retourne un panneau avec un label dedans indiquant que y'a pas de selection.
		if (selection.size()==0) { JTreeMagique panel = new JTreeMagique(selection, bindings_identifiable, bindings_enum); panel.add(new JLabel("No selection")); return panel;};
		// On prends un objet de reference. (on espere que la liste ne contients que des objets du m�me type).
		
		final JTreeMagique panel = new JTreeMagique(selection,  bindings_identifiable,bindings_enum, xMLLabelFile, showTreeViewbuttons);

		// R�cupere le nom de la classe
		//String VarName = selection.get(0).getClass().getName();
		String VarName = selection.get(0).toString();
		// Refiltre un peu le nom de la variable
		// TODO : Ce truc ne fonctionne pas comme je voudrais mais pourquoi ais-je fait ça ?
		if (VarName.contains("."))
			VarName = VarName.substring(VarName.lastIndexOf(".")+1);
		
				TreeNode arbre = getClassTree(selection,VarName, panel.getMapSons());
		panel.jtree = new JTree(arbre);
		
		panel.jtree.addTreeSelectionListener(panel);
		
		if (UseTabbedPane==false)
		{
			panel.editor = new JPanel();
			panel.editor.setLayout(new BoxLayout(panel.editor, BoxLayout.Y_AXIS));
		}
		else
		{
			panel.editor = new JTabbedPane();
		}
		
		
		
		panel.pan_tree_cmd = new JPanel();
		panel.pan_tree_cmd.setLayout(new BorderLayout());
		panel.pan_tree_cmd.add(new JScrollPane(panel.jtree), BorderLayout.CENTER);
		
		
		panel.tree_bouton_cmd = new JPanel();
		panel.tree_bouton_cmd.setLayout(new BoxLayout(panel.tree_bouton_cmd, BoxLayout.X_AXIS));
		
		panel.recursive_box = new JCheckBox();
		panel.recursive_box.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				panel.refresh();
			}
			
		});
		panel.recursive_box.setText("Recursive");
		panel.tree_bouton_cmd.add(panel.recursive_box);
		
		
		panel.OnlyAnnotatedMethods_box = new JCheckBox();
		panel.OnlyAnnotatedMethods_box.setSelected(true);
		panel.OnlyAnnotatedMethods_box.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				panel.refresh();
			}
		});
		panel.OnlyAnnotatedMethods_box.setText("Only annotated");
		panel.tree_bouton_cmd.add(panel.OnlyAnnotatedMethods_box);
		
		panel.pan_tree_cmd.add(panel.tree_bouton_cmd, BorderLayout.SOUTH);
		
		panel.add(panel.editor, "cell 0 0, grow");
		panel.add(panel.pan_tree_cmd,  "cell 1 0, growy, alignx right");
		
		panel.jtree.setSelectionInterval(0, 0);
		
		
		expandAll(panel.jtree, true);
		
		
		return panel;
	}

	/**
	 * Recupere une arboresence des classes filles qui sont PROPERTY_FIELD_XXXABLE
	 * @param obj
	 * @return
	 */
	private static MutableTreeNode getClassTree(List<?> sel, String varName, HashMap<TreeNodeWax, List<?>> mapSons)
	{
		if (sel.size()==0) return new  DefaultMutableTreeNode("Empty", true);
		Class<?> objectClass = sel.get(0).getClass();
		
		//System.err.println("ObjectClass == "+objectClass);
			
		TreeNodeWax treen = new TreeNodeWax(objectClass, varName);
			mapSons.put(treen, sel);
		
		// Test si la class 
			DefaultMutableTreeNode parent = new DefaultMutableTreeNode(treen, true);

		if (containsEditablecontents(objectClass))
		{
			Field[] fields = JPanelMagique.getAllFields(objectClass);
			for (int i = 0; i < fields.length; i++)
			{
				// Pour les "FILS"
				Annotation anot = fields[i].getAnnotation(PROPERTY_FIELD_XXXABLE.class);
				if (anot != null)
				{
					try
					{
						
						fields[i].setAccessible(true);
						
						List<Object> selFils = new ArrayList<Object>();
						for (int j = 0; j < sel.size(); j++)
						{
							Object objFils= fields[i].get(sel.get(j));
							if (objFils!=null)
							selFils.add(objFils);
						}
						
						fields[i].setAccessible(false);
						parent.add(getClassTree(selFils,fields[i].getName(), mapSons));
					} catch (IllegalArgumentException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalAccessException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
				
				// Pour les "FILS"
				anot = fields[i].getAnnotation(PROPERTY_FIELD_LISTABLE.class);
				if (anot != null)
				{

					
					try
					{
						fields[i].setAccessible(true);
						
						DefaultMutableTreeNode fils = new DefaultMutableTreeNode(fields[i].getName(), true);
						
						
						for (int j = 0; j < sel.size(); j++)
						{
							List<?> objFils= (List<?>) fields[i].get(sel.get(j));
							for (int k = 0; k < objFils.size(); k++)
							{
								List<Object> selFils = new ArrayList<Object>();
								if (objFils.get(k)!=null)
								selFils.add(objFils.get(k));
								String VarName = objFils.get(k).toString();
								// TODO : Ce truc ne fonctionne pas comme je voudrais mais pourquoi ais-je fait ça ?
								if (VarName.contains("."))
									VarName = VarName.substring(VarName.lastIndexOf(".")+1);

								fils.add(getClassTree(selFils,VarName, mapSons));
							}
							
						}
						
						
						parent.add(fils);
						fields[i].setAccessible(false);
						
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
				}
			}
		}
		else
			LOGGER.info("Class ("+objectClass+") is not editable content");
		
		
		return parent;
	
	}
	
	/**
	 * Me demande encore si c'est utilisable ???
	 * @param obj
	 * @return
	 */
	private static boolean containsEditablecontents(Object obj)
	{
		return true;
	}
	

	/* (non-Javadoc)
	 * @see javax.swing.event.TreeSelectionListener#valueChanged(javax.swing.event.TreeSelectionEvent)
	 */
	@Override
	public void valueChanged(TreeSelectionEvent arg0)
	{
		refresh();
	}

	
	/**
	 * Effectue un refresh du panel magique contenu dans la fenetre avec l'arbre.
	 */
	public void refresh()
	{
		if (PRINT_DEBUG)
			LOGGER.info("TREEMAGIQUE("+this.hashCode()+")::REFRESH::"+jtree.getLastSelectedPathComponent().toString());
	
		// Va retenir des lists d'object du meme type selectionn�s.
		HashMap<Class<?>, List<Object>> map = new HashMap<Class<?>, List<Object>>();
		
		// Parcours tout les noeuds selectionn�.
		
		TreePath tp [] = jtree.getSelectionPaths();
		if (tp!=null)
		for (int i = 0; i < tp.length; i++)
		{
			DefaultMutableTreeNode objetclicked1 = (DefaultMutableTreeNode) tp[i].getLastPathComponent();
			Object objetclicked = objetclicked1.getUserObject();
			
			
			if (objetclicked instanceof String)
			{
				LOGGER.info("Il manque un petit bazar ici... tu as cliqu� sur un string dans l'arbre.");
			}
			
			if ((objetclicked instanceof TreeNodeWax)==false)
			{
				//Logs.getLogger().warn(objectClass+" is not TreeNodeWax");
				continue;
			}
			
			Class<?> objectClass = (Class<?>) ((TreeNodeWax)objetclicked).getObjClass();
			if (map.get(objectClass)==null)
				map.put(objectClass, new ArrayList<Object>());
			
			TreeNodeWax obj = (TreeNodeWax) objetclicked;
			map.get(objectClass).addAll(getMapSons().get(obj));
		}
		
		// Enleve les listener des ou du fils d'avant cette reselection dans l'arbre.
		for (int i = 0; i < SonsList.size(); i++) {
			SonsList.get(i).removeMyEventListener(this);
		}
		SonsList.clear();
		
		// Supprime tout les editeur.
		editor.removeAll();
		
		// Doit filtrer pour n'avoir que le type demand�.
		try
		{
			Set<Class<?>> classesToPanelise = map.keySet();
			for (Iterator<?> iterator = classesToPanelise.iterator(); iterator.hasNext();)
			{
				Class<?> class1 = (Class<?>) iterator.next();
				ArrayList<Object> sel = (ArrayList<Object>) map.get(class1);
				//this.removeMyEventListener(pan);
				JPanelMagique pan = JPanelMagique.GenerateJPanelFromSelectionAndBindings(null, sel, recursive_box.isSelected(), bindList_identifiables, bindList, OnlyAnnotatedMethods_box.isSelected(), showTreeViewButtons);
	
				pan.addMyEventListener(this);
				if (editor instanceof JTabbedPane)
					((JTabbedPane)editor).addTab(class1.getName(), new JScrollPane(pan));
				else
					editor.add(new JScrollPane(pan));
				
				SonsList.add(pan);
			}
		//	
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/*
		if (compFocusOwner!=null)
		{
		System.err.println("Focus owner : "+compFocusOwner);
		System.err.println("Focus hash : "+compFocusOwner.hashCode());
		System.err.println("Focus owner named : "+compFocusOwner.getName());
		
		// Remets le focus sur l'ancien component qui a fait la modification ?
		JComponent c = getComponentByName(editor, compFocusOwner.getName());
		if (c!=null)
		{
			System.err.println("Refocus");
			c.requestFocus();
		}
		}
		*/
		
		revalidate();
		repaint();
		doLayout();
	}
	
	
	/**
	 * @param editor2 
	 * @param name
	 * @return
	 */
/*	private JComponent getComponentByName(JComponent editor2, String name)
	{
		for (int i = 0; i < editor2.getComponentCount(); i++)
		{
			JComponent c = (JComponent) editor2.getComponent(i);
			if (c.getName()!=null)
			if (c.getName().equals(name))
					return c; 
		}
		return null;
	}*/
	// This methods allows classes to register for MyEvents
    public void addMyEventListener(MyEventListener listener) {
    	//System.err.println(this.getName()+" Adding a listener "+listener); 
        listenerList.add(MyEventListener.class, listener);
    }

    // This methods allows classes to unregister for MyEvents
    public void removeMyEventListener(MyEventListener listener) {
        listenerList.remove(MyEventListener.class, listener);
    }

    // This private class is used to fire MyEvents
    public void fireMyEvent(MyChangedEvent evt) {
        Object[] listeners = listenerList.getListenerList();
        // Each listener occupies two elements - the first is the listener class
        // and the second is the listener instance
        if (PRINT_DEBUG)
        	LOGGER.info("Sending Event Event ");
        for (int i=0; i<listeners.length; i+=2) {
            if (listeners[i]==MyEventListener.class) {
            	//System.err.println("Transmit the event" +((MyEventListener)listeners[i+1]));
            	((MyEventListener)listeners[i+1]).myEventOccurred(evt);
            }
        }
    }

    @Override
	public void myEventOccurred(MyChangedEvent evt) {
    	if (PRINT_DEBUG)
    		LOGGER.info("TREEMAGIQUE("+this.hashCode()+")::EVENT OCCURED : "+evt);
    	// Propage au parent qu'il y'a eu changement.
		
    	int sel = 0;
    	if (editor instanceof JTabbedPane)
    		sel = ((JTabbedPane)editor).getSelectedIndex();
    	try {
			refresh();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fireMyEvent(evt);
		if (editor instanceof JTabbedPane)
			((JTabbedPane)editor).setSelectedIndex(sel);
	}

	public synchronized JTree getJtree()
	{
		return jtree;
	}

	public synchronized void setJtree(JTree jtree)
	{
		this.jtree = jtree;
	
	}

	// If expand is true, expands all nodes in the tree.
	// Otherwise, collapses all nodes in the tree.
	public static void expandAll(JTree tree, boolean expand) {
	    TreeNode root = (TreeNode)tree.getModel().getRoot();

	    // Traverse tree from root
	    expandAll(tree, new TreePath(root), expand);
	}
	private static void expandAll(JTree tree, TreePath parent, boolean expand) {
	    // Traverse children
	    TreeNode node = (TreeNode)parent.getLastPathComponent();
	    if (node.getChildCount() >= 0) {
	        for (Enumeration<? extends TreeNode> e=node.children(); e.hasMoreElements(); ) {
	            TreeNode n = e.nextElement();
	            TreePath path = parent.pathByAddingChild(n);
	            expandAll(tree, path, expand);
	        }
	    }

	    // Expansion or collapse must be done bottom-up
	    if (expand) {
	        tree.expandPath(parent);
	    } else {
	        tree.collapsePath(parent);
	    }
	}

	public HashMap<TreeNodeWax, List<?>> getMapSons()
	{
		return mapSons;
	}

	public void setMapSons(HashMap<TreeNodeWax, List<?>> mapSons)
	{
		this.mapSons = mapSons;
		
		
	}
	

	
}
