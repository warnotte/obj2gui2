package io.github.warnotte.obj2gui2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.EventListenerList;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import javax.swing.text.NumberFormatter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jdesktop.swingx.JXDatePicker;
import org.jdom2.JDOMException;

import io.github.warnotte.obj2gui2.ArrayGeneratorForsingleObject.TableMagiquePlugin1D;
import io.github.warnotte.obj2gui2.ArrayGeneratorForsingleObject.TableMagiquePlugin2D;
import io.github.warnotte.obj2gui2.Plugins.OBJ2GUIPlug;
import io.github.warnotte.obj2gui2.Plugins.OBJ2GUIPlugExtended;
import io.github.warnotte.obj2gui2.Validators.ValidationException;
import io.github.warnotte.obj2gui2.Validators.Validator;
//import io.github.warnotte.waxlib3.core.Identifiable.Identifiable;
import io.github.warnotte.waxlib3.core.TemplatePropertyMerger.ResultatMerge;
import io.github.warnotte.waxlib3.core.TemplatePropertyMerger.TemplatePropertyMergerV2;
import io.github.warnotte.waxlib3.core.TemplatePropertyMerger.Annotations.PROPERTY_FIELD_XXXABLE;
import io.github.warnotte.waxlib3.core.TemplatePropertyMerger.Annotations.PROPERTY_interface;
import io.github.warnotte.waxlib3.core.TemplatePropertyMerger.Annotations.PROPERTY_interface.gui_type;
import io.github.warnotte.waxlib3.waxlibswingcomponents.Dialog.DialogDivers;
import io.github.warnotte.waxlib3.waxlibswingcomponents.FileChooser.FileChooser;
import io.github.warnotte.waxlib3.waxlibswingcomponents.Swing.Component.JColorChooserButton;
import io.github.warnotte.waxlib3.waxlibswingcomponents.Swing.Component.JWColor;
import io.github.warnotte.waxlib3.waxlibswingcomponents.Swing.Component.GlassPanes.InfiniteProgressGlassPane;
import io.github.warnotte.waxlib3.waxlibswingcomponents.Swing.Component.WaxSlider.WFlatSlider;
import net.miginfocom.swing.MigLayout;


// TODO : Y'a pas un leak avec les listener???? me semble qu'il y'a un truc louche ...
// TODO : (Pas vraiment un truc qu'on a déjà bcp utilisé) IL manque la communication parent->fils pour les maj d'interface (si un parent change une valeur et qu'un fils doit le savoir... cas de la class SON qui prends un KOPKOK en parametre dans le package Test )
// TODO : Le boolean newSystem qui gére le fait d'affiche la derniere valeur du dernier objet selectionné
//      : Ne fonctionne pas avec les lists ou table, en effet on dirait que le code utilisateur envoye les objets, mais dans l'ordre visuel de la table ou quoi

/**
 * @author Warnotte Renaud 2011-2024
 * @date 2011-2024
 */
public class JPanelMagique extends JPanel implements ActionListener, MyEventListener, ChangeListener, ItemListener, FocusListener, DocumentListener
{

	private static final Logger log = LogManager.getLogger("JPanelMagique");

	static Map<Class<?>, OBJ2GUIPlug<?, ?>>	map_plugins					= null;
	
	// Pour les classe speciale genre les Identifiable... voire MaterialComboBox
	static List<OBJ2GUIPlugExtended<?, ?>>	map_plugins2					= null;

	// TODO : Ceci devrai disparaitre un jour (a moins que...)
	// Si true, alors on ne mets plus valeurs differentes dans les textfield quand c'est le cas.
	// Mais la valeur pour le dernier objet.
	public static boolean					newSystem					= true;

	// Pour les textfield avec des flottant (par exemple), on affiche tjrs a l'ancienne avec uniquement un POINT
	// comme séparateur, sinon on formatte avec la Locale.
	public static boolean					formatTextFieldWithDotOnly	= true;

	static String							valeur_differentes			= "Differents values";
	static
	{
		map_plugins = new HashMap<>();
		// Rajout de quelques plugins maison
		io.github.warnotte.obj2gui2.Plugins.tests.Vector2DPlugin plugin4 = new io.github.warnotte.obj2gui2.Plugins.tests.Vector2DPlugin();
		registerPlugin(plugin4);
		io.github.warnotte.obj2gui2.Plugins.tests.FontPlugin plugin5 = new io.github.warnotte.obj2gui2.Plugins.tests.FontPlugin();
		registerPlugin(plugin5);

		if (Locale.getDefault().getLanguage().toLowerCase().equals("fr"))
		{
			valeur_differentes = "Valeurs différentes";
		}
		
		map_plugins2 = new ArrayList<>();

	}

	static String								TEXT_BOUTON_VIEW_EDIT				= "View/Edit";
	static String								TEXT_BOUTON_ADD_ELEMENT				= "+";
	static String								TEXT_BOUTON_REMOVE_ELEMENT			= "-";
	static String								TEXT_TOOLTIP_BOUTON_VIEW_EDIT		= "View/Edit";
	static String								TEXT_TOOLTIP_BOUTON_ADD_ELEMENT		= "+";
	static String								TEXT_TOOLTIP_BOUTON_REMOVE_ELEMENT	= "-";
	static Icon									IMG_BOUTON_VIEW_EDIT				= null;
	static Icon									IMG_BOUTON_ADD_ELEMENT				= null;
	static Icon									IMG_BOUTON_REMOVE_ELEMENT			= null;

	public static boolean						PRINT_DEBUG							= false;
	// Directory where labels files will be stored.
	public static String						LABEL_XML_DIRECTORY					= "Obj2GuiRessources";
	public static boolean						activateAutoSaveAutoUpdate			= true;
	public static int							CompteurInstance					= 0;
	public int									id;

	/**
	 * 
	 */
	private static final long					serialVersionUID					= 5026589469585370696L;

	/**
	 * La selection que l'on a passé au panneau. Elle va reservir au moment de
	 * retransmettre un changement de valeur a tout les elements de cette liste.
	 */
	private List<?>								selection;

	/**
	 * Lien pour relier un component a un resultatMerge
	 */
	private Map<JComponent, ResultatMerge>		map_component_to_class				= new HashMap<JComponent, ResultatMerge>();

	/**
	 * Lien pour relier un bouton a une methode de la classe visée.
	 */
	private final Map<JButton, PROPERTY_button>	map_button_to_method				= new HashMap<JButton, PROPERTY_button>();

	private final JPanel						panelFields							= new JPanel();
	private final JPanel						panelSons							= new JPanel();
	private final JPanel						panelLists							= new JPanel();

	private final List<JFrameTreeView>			FrameTreeViews						= new ArrayList<JFrameTreeView>();

	/**
	 * Lien pour relier un "entier" ou "long" servant d'id dans une classe vers
	 * Arraylist d'identifiable.
	 */
	private List<Binding>						bindList_identifiables				= new ArrayList<Binding>();
	/**
	 * Lien pour relier un "entier" ou "long" servant d'id dans une classe vers
	 * une enumeration.
	 */
	private List<BindingEnum>					bindList							= new ArrayList<BindingEnum>();

	/**
	 * Si on utilise la fonction ChargeLabel alors on retient le nom du fichier
	 * pour les eventuel jpanelmagique fils (treeview).
	 */
	private String								XMLLabelFile						= null;

	/**
	 * Retiens que l'on ne veux que les methods annotatée et pas le systeme
	 * "magique".
	 */
	private boolean								OnlyAnnotatedMethods				= true;

	/**
	 * Retiens que l'on veux voir les boutons magique pour faire apparaitre
	 * l'arbre.
	 */
	private boolean								showTreeViewButtons					= false;

	/**
	 * To notify the parent, that i'm destroyed and need to be removed from
	 * notification (possible memory leak eventuel je crois sinon)
	 */
	JPanelMagique								parent;

	/**
	 * @param selection
	 *            L'arrayList avec des elements (du meme type) a afficher/editer
	 * @param bindings_identifiable
	 *            Si certaines variable de l'objet sont des identifiant (int ou
	 *            long) permet de relier a une List<Identifiable> servant de
	 *            modele
	 * @param bindings_enum
	 *            Si certains variable de l'objet sont des identifiant (int ou
	 *            long) permet de relier a une enumeration.
	 */
	protected JPanelMagique(JPanelMagique parent, List<?> selection, List<Binding> bindings_identifiable, List<BindingEnum> bindings_enum, boolean nlyAnnotatedMethods, boolean showTreeViewButtons)
	{
		this.parent = parent;
		this.selection = selection;
		this.bindList_identifiables = bindings_identifiable;
		this.bindList = bindings_enum;
		this.OnlyAnnotatedMethods = nlyAnnotatedMethods;
		this.showTreeViewButtons = showTreeViewButtons;

		synchronized (this)
		{
			CompteurInstance++;
			id = CompteurInstance;
			if (PRINT_DEBUG == true)
				log.info("instance : " + CompteurInstance);
		}
		if (parent != null)
			addMyEventListener(parent);

	}

	@Override
	protected void finalize() throws Throwable
	{
		//log.info("JpanelMagique::Finalize("+this+")");
		super.finalize();
		if (parent != null)
			parent.removeMyEventListener(this);
		// Pas sure que ca soit necessaire, normalement un parent est passé a chaque fils (en théorie) et le fils demande au pere de supprimer l'event listener.
		for (int i = 0; i < panelSons.getComponentCount(); i++)
			((JPanelMagique) panelSons.getComponent(i)).removeMyEventListener(this);
	}

	public synchronized List<?> getSelection()
	{
		return selection;
	}

	public synchronized Map<JComponent, ResultatMerge> getMap_component_to_class()
	{
		return map_component_to_class;
	}

	public synchronized List<Binding> getBindList()
	{
		return bindList_identifiables;
	}

	/**
	 * Reset the selection (! don't forget to refresh the gui (see toDo))
	 * 
	 * @param selection
	 */
	public synchronized void setSelection(List<?> selection)
	{
		this.selection = selection;
		// TODO : Et la selection pour les panneaux magique enfant ????!
		//refresh(); // TODO : refresh or not ?
	}

	Thread thread_callableMethod = null;

	/*
	 * (non-Javadoc)
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getID()==ActionEvent.ACTION_PERFORMED) {
			//System.err.println("****");
		}
		if (PRINT_DEBUG == true)
			log.info("JPanelMagique::actionPerformed(" + e + ")");

		JComponent component = (JComponent) e.getSource();
		//System.err.println("Toto == "+component.getName());
		if (selection != null)
			for (int i = 0; i < selection.size(); i++)
			{
				final Object o = selection.get(i);
				if (o == null)
				{
					log.fatal("La selection contient un objet==null");
					continue;
				}
				Class<?> Objectcls = o.getClass();

				if ((component instanceof JButton) && (component instanceof JColorChooserButton) == false)
				{
					execute_CallableMethod(component, o);
					continue;
				}

				ResultatMerge rm = map_component_to_class.get(component);
				if (rm == null)
				{
					log.fatal("I cannot find in the HashMap something for the ComponentNamed : " + component.getName());
					log.fatal("I cannot find in the HashMap something for the Component : " + component);
					//DialogDivers.Show_dialog("I cannot find in the HashMap something for the Component : "+component);
					continue;
				}
				Class<?> returnType = rm.getNom().getReturnType();
				int offset = 3;
				if ((returnType == boolean.class) || (returnType == Boolean.class))
					offset = 2;
				String name = rm.getNom().getName().substring(offset);

				Method setMeth = rm.getHasSet();
				

				if (setMeth != null)
				{
					Class<?> valueClass = setMeth.getParameterTypes()[0];
					try
					{
						Object value = null;
						OBJ2GUIPlug<?, ?> plugin = getPlugin(valueClass);
						OBJ2GUIPlugExtended<?, ?> plugin2 = getPlugin2(valueClass, Objectcls, name);
						
						if (plugin2 != null)
						{
							value = plugin2.getValue(e.getSource());
						} else
						if (plugin != null)
						{
							value = plugin.getValue(e.getSource());
						} else if (component instanceof FileChooser)
						{
							FileChooser componentM = (FileChooser) e.getSource();
							value = componentM.getFile();
						} else if (component instanceof JColorChooserButton)
						{
							JColorChooserButton componentM = (JColorChooserButton) e.getSource();
							value = componentM.getCol();
						} else if (component instanceof JTextArea)
						{
							JTextArea componentM = (JTextArea) e.getSource();
							value = componentM.getText();
						} else if (component instanceof JTextPane)
						{
							JTextPane componentM = (JTextPane) e.getSource();
							value = componentM.getText();
						} else if (component instanceof JTextField)
						{
							JTextField componentM = (JTextField) e.getSource();
							value = componentM.getText();

							if ((newSystem == true) || (value.equals(valeur_differentes) == false))
							{
								if ((valueClass == float.class) || (valueClass == Float.class))
									value = Float.parseFloat("" + value.toString().replace(",", "."));
								if ((valueClass == double.class) || (valueClass == Double.class))
									value = Double.parseDouble("" + value.toString().replace(",", "."));
								if ((valueClass == long.class) || (valueClass == Long.class))
									value = Long.parseLong("" + value.toString().replace(",", "."));
								if ((valueClass == int.class) || (valueClass == Integer.class))
									value = Integer.parseInt("" + value.toString().replace(",", "."));
							}
							//value = new Integer(""+new Float(""+value));
						} else if (component instanceof JCheckBox)
						{
							JCheckBox componentM = (JCheckBox) e.getSource();
							value = componentM.isSelected();
						} else if (component instanceof JList)
						{
							JList<?> componentM = (JList<?>) e.getSource();
							value = componentM.getSelectedValue();
						} else if (component instanceof WFlatSlider)
						{
							WFlatSlider componentM = (WFlatSlider) e.getSource();
							value = componentM.getValue();

							if ((valueClass == float.class) || (valueClass == Float.class))
								value = Float.parseFloat("" + value);
							if ((valueClass == double.class) || (valueClass == Double.class))
								value = Double.parseDouble("" + value);
							if ((valueClass == long.class) || (valueClass == Long.class))
								value = Long.parseLong("" + value);
							if ((valueClass == int.class) || (valueClass == Integer.class))
								value = Integer.parseInt("" + new Float("" + value).intValue());

						} else if (component instanceof JSlider)
						{
							JSlider componentM = (JSlider) e.getSource();
							value = componentM.getValue();

							if ((valueClass == float.class) || (valueClass == Float.class))
								value = Float.parseFloat("" + value);
							if ((valueClass == double.class) || (valueClass == Double.class))
								value = Double.parseDouble("" + value);
							if ((valueClass == long.class) || (valueClass == Long.class))
								value = Long.parseLong("" + value);
							if ((valueClass == int.class) || (valueClass == Integer.class))
								value = Integer.parseInt("" + new Float("" + value).intValue());

						} else if (component instanceof JXDatePicker)
						{
							JXDatePicker componentM = (JXDatePicker) e.getSource();
							value = componentM.getDate();
						} else if (component instanceof JComboBox)
						{
							JComboBox<?> componentM = (JComboBox<?>) e.getSource();
							// Identifialbe or enum ?
							if ((componentM.getSelectedItem()) instanceof Identifiable)
								value = ((Identifiable) componentM.getSelectedItem()).getId();
							else
							{

								BindingEnum bindingenum = getBindingEnum(Objectcls, name);
								// Si y'a pas de binding alors on transfert la valeur direct
								if (bindingenum == null)
								{
									value = componentM.getSelectedItem();
								} else
								{
									// TODO : Rajouter le code correspondant qui parcours l'enum et trouve la bonne valuer
									for (int j = 0; j < bindingenum.list_mats.length; j++)
									{
										Object z = bindingenum.list_mats[j];
										if (z.toString().equalsIgnoreCase(componentM.getSelectedItem().toString()))
										{
											value = j - bindingenum.getIndexoffset();
											break;
										}
									}
								}
							}
						}
						if (value != null)
						{
							// Apparement avec un TextField qui contient un simple string, si on va dans la case et que c'est en orange et marqué "Valeurs différentes" alors on sette éa pour tout les objets ce qui est une erreur monumentale...
							if ((newSystem == true) || (value.equals(valeur_differentes) == false))
							{
								try
								{
									
									Object oldValue = rm.getNom().invoke(selection.get(selection.size()-1), null);
									/**
									 * ADDITION MAURICIO 20-09-2023
									 */
									// Si c'est la même valeur qu'avant (dans le cas du lost focus et qu'on a rien changé) ET
									// Qu'on a pas appuyé sur ENTER (cas d'une multiselection si on veux appliquer la valeur du dernier objet selectionné a tout les autres objets).
									if ((value.equals(oldValue)) && ((e.getID()!=ActionEvent.ACTION_PERFORMED)))
									{
										if (PRINT_DEBUG==true)
											log.debug("Value hasn't changed");
										return;
									}
									/**
									 * FIN ADDITION
									 */
									// TODO : Je me demande s'il ne faudrait pas avoir aussi la OldValue au cas ou ... ça pourrait être interessant.
									Object fixedValue = validateValue(o, setMeth, oldValue, value);
									setMeth.invoke(o, fixedValue);
								} catch (ValidationException e1)
								{
									DialogDivers.Show_dialog(e1, e1.getMessage());
									e1.printStackTrace();
									break;
								}
							} else
								if (PRINT_DEBUG==true)
									log.info("Valeurs différentes alors je ne sette pas les valeurs");
						}
					} catch (IllegalArgumentException e1)
					{
						log.fatal(e1, e1);
						log.fatal(e1.getCause());
						e1.printStackTrace();
					} catch (IllegalAccessException e1)
					{
						log.fatal(e1, e1);
						log.fatal(e1.getCause());
						e1.printStackTrace();
					} catch (InvocationTargetException e1)
					{
						log.fatal(e1, e1);
						log.fatal(e1.getCause());
						e1.printStackTrace();
					}
				} else
					log.info("No set method " + rm + " or readOnly");
			}
		// Envoyer un message au parent pour qu'ils sachent qu'il doivent s'updater.
		fireMyEvent(new MyChangedEvent(this, null));

		// Rafraichir aussi les fils
		for (int i = 0; i < panelSons.getComponentCount(); i++)
		{
			JComponent fils = (JComponent) panelSons.getComponent(i);
			if (fils instanceof JPanelMagique)
			{
				try
				{
					((JPanelMagique) fils).refresh();
				} catch (Exception e1)
				{
					log.fatal(e1, e1);
					log.fatal(e1.getCause());
					e1.printStackTrace();
				}
			}
		}

		// Rafraichir les eventuelle fenetre Tree View aussi
		for (int i = 0; i < FrameTreeViews.size(); i++)
		{
			JFrameTreeView jtv = FrameTreeViews.get(i);
			jtv.paneltree.refresh();
		}

		try
		{
			refresh();
		} catch (Exception e1)
		{
			log.fatal(e1, e1);
			log.fatal(e1.getCause());
			e1.printStackTrace();
		} // ?!
	}

	/**
	 * Call a method when a JButton is pressed.
	 * 
	 * @param component
	 * @param objectBinded
	 */
	private void execute_CallableMethod(JComponent component, final Object objectBinded)
	{
		Class<?> Objectcls = objectBinded.getClass();
		PROPERTY_button annot_pb = map_button_to_method.get(component);
		String methodname = annot_pb.method_name();
		try
		{
			final Method meth = Objectcls.getMethod(methodname);
			
			//boolean acc = meth.isAccessible();
			boolean acc = meth.canAccess(objectBinded);
			if (annot_pb.threadedMethod() == true)
			{
				if (thread_callableMethod != null)
					if (thread_callableMethod.isAlive() == true)
						log.fatal("You've tried to relaunch the CallableMethod Thread a " + "second time, and a previous is still running");

				thread_callableMethod = new Thread()
				{
					@Override
					public void run()
					{
						final InfiniteProgressGlassPane glasspane = new InfiniteProgressGlassPane();
						final JFrame frameParent = getParentFrame();
						final Component old_glass_pane = frameParent.getGlassPane();

						try
						{
							meth.setAccessible(true);
							SwingUtilities.invokeLater(new Runnable()
							{
								public void run()
								{
									frameParent.setGlassPane(glasspane);
									glasspane.setVisible(true);
								}
							});
							meth.invoke(objectBinded);
						} catch (IllegalArgumentException e)
						{
							log.fatal(e, e);
							log.fatal(e.getCause());
							e.printStackTrace();
							DialogDivers.Show_dialog(e, "Error invoking method : " + e);
						} catch (IllegalAccessException e)
						{
							log.fatal(e, e);
							log.fatal(e.getCause());
							e.printStackTrace();
							DialogDivers.Show_dialog(e, "Error invoking method : " + e);
						} catch (InvocationTargetException e)
						{
							log.fatal(e, e);
							log.fatal(e.getCause());
							e.printStackTrace();
							DialogDivers.Show_dialog(e, "Error invoking method : " + e.getTargetException());
						}

						SwingUtilities.invokeLater(new Runnable()
						{
							public void run()
							{
								glasspane.setVisible(false);
								frameParent.setGlassPane(old_glass_pane);
							}
						});
					}
				};
				thread_callableMethod.setName("JPanelMagiqueMethodCall_" + methodname);
				thread_callableMethod.start();

			} else
			{
				meth.setAccessible(true);
				meth.invoke(objectBinded);
			}

			meth.setAccessible(acc);
		} catch (SecurityException e1)
		{
			log.fatal(e1, e1);
			log.fatal(e1.getCause());
			DialogDivers.Show_dialog(e1, "Error invoking method : " + e1);
			e1.printStackTrace();
		} catch (NoSuchMethodException e1)
		{
			log.fatal(e1, e1);
			log.fatal(e1.getCause());
			DialogDivers.Show_dialog(e1, "Error invoking method : " + e1);
			e1.printStackTrace();
		} catch (IllegalArgumentException e1)
		{
			log.fatal(e1, e1);
			log.fatal(e1.getCause());
			DialogDivers.Show_dialog(e1, "Error invoking method : " + e1);
			e1.printStackTrace();
		} catch (IllegalAccessException e1)
		{
			log.fatal(e1, e1);
			log.fatal(e1.getCause());
			DialogDivers.Show_dialog(e1, "Error invoking method : " + e1);
			e1.printStackTrace();
		} catch (InvocationTargetException e1)
		{
			log.fatal(e1, e1);
			log.fatal(e1.getCause());
			DialogDivers.Show_dialog(e1, "Error invoking method : " + e1);
			e1.printStackTrace();
		}
	}

	/**
	 * Recupere la JFrame qui contient ce jpanel...
	 * 
	 * @return
	 */
	protected JFrame getParentFrame()
	{
		return (JFrame) SwingUtilities.getRoot(this);
	}

	/**
	 * Permet d'assigner a une variable de type int Material = 1; une [List]
	 * d'[Identifiable] (les Material doit extends Identifiable)
	 * 
	 * @param list_mats
	 *            La liste de référence.
	 * @param class1
	 * @param string
	 */
	public void register(Binding bind)
	{
		bindList_identifiables.add(bind);
	}

	public void unregisterAll()
	{
		bindList_identifiables.clear();
	}

	/**
	 * Ceci cherche le binding pour une classe donnée pour une variable nommée de cette classe
	 * Ajout 17-12-2024 : Verifie que la classe peut être une classe héritée (Flotteur et Flotteur_Prismatique).
	 * @param class1 class de l'objet conteneur (material.class)
	 * @param string
	 *            La nom de la variable.
	 * @return
	 */
	public Binding getBinding(Class<?> class1, String string)
	{
		if (bindList_identifiables != null)
		{
			for (int i = 0; i < bindList_identifiables.size(); i++)
			{
				Binding bind = bindList_identifiables.get(i);
				// if (bind.class1 == class1) Modification du 17-12-2024
				if (bind.class1.isAssignableFrom(class1)) // Vérifie si class1 est une sous-classe ou la même classe					
					if (bind.variable.equalsIgnoreCase(string))
						return bind;
			}
		}
		return null;
	}
	
	
	/*
	 * TODO : Supprimer ceci si je vois que tout va bien.
	public Binding getBinding(Class<?> class1, String string)
	{
		if (bindList_identifiables != null)
		{
			for (int i = 0; i < bindList_identifiables.size(); i++)
			{
				Binding bind = bindList_identifiables.get(i);
				if (bind.class1 == class1)
					if (bind.variable.equalsIgnoreCase(string))
						return bind;
			}
		}
		return null;
	}*/

	/**
	 * @param class1
	 *            class de l'objet conteneur (material.class)
	 * @param string
	 *            La nom de la variable.
	 * @return
	 */
	public BindingEnum getBindingEnum(Class<?> class1, String string)
	{
		if (bindList != null)
		{
			for (int i = 0; i < bindList.size(); i++)
			{
				BindingEnum bind = bindList.get(i);
				// if (bind.class1 == class1) Modification 17-12-2024
				if (bind.class1.isAssignableFrom(class1)) // Vérifie si class1 est une sous-classe ou la même classe
					if (bind.variable.equalsIgnoreCase(string))
						return bind;
			}
		}
		return null;
	}
	/*
	 * Modification du 4 17-12-2024
	public BindingEnum getBindingEnum(Class<?> class1, String string)
	{
		if (bindList != null)
		{
			for (int i = 0; i < bindList.size(); i++)
			{
				BindingEnum bind = bindList.get(i);
				if (bind.class1 == class1)
					if (bind.variable.equalsIgnoreCase(string))
						return bind;
			}
		}
		return null;
	}*/

	/**
	 * Refresh the GUI (not recreating a new panel, refreshing all component
	 * inside).
	 * 
	 * @throws Exception
	 */
	public void refresh() throws Exception
	{
		if ((selection != null) && (selection.size() != 0))
		{
			final Object obj = selection.get(0);
			// Récupere le nom de la classe
			String VarName = obj.getClass().getName();
			// Refiltre un peu le nom de la variable
			if (VarName.contains("."))
				VarName = VarName.substring(VarName.lastIndexOf(".") + 1);

			if (getBorder() == null)
			{
				String titledBorder = getRegisteredTitleBorder(obj.getClass());

				if (selection.size() > 1)
					setBorder(BorderFactory.createTitledBorder(titledBorder + " (" + selection.size() + ")"));
				else
					setBorder(BorderFactory.createTitledBorder(titledBorder));

			}
		}

		// TODO : Y'a pas un leak avec les listener????
		List<?> merged = TemplatePropertyMergerV2.MergeCollection(selection, OnlyAnnotatedMethods);
		Map<JComponent, ResultatMerge> neo_map_component_to_class = new HashMap<JComponent, ResultatMerge>();
		for (int i = 0; i < merged.size(); i++)
		{
			ResultatMerge rm = (ResultatMerge) merged.get(i);
			if (rm == null)
				log.info("Probleme lors du mergeage probablement");

			Class<?> returnType = rm.getNom().getReturnType();
			int offset = 3;
			if ((returnType == boolean.class) || (returnType == Boolean.class))
				offset = 2;
			String name = rm.getNom().getName().substring(offset);

			JComponent comp = getComponentNamed(name);

			if (comp == null)
			{
				log.fatal("Quelques chose ne se passe pas comme prévu, le composant nommé : " + name + " n'est pas trouvé");
			}

			refreshComponent(comp, rm, selection);

			//if (comp.getName().equals("Commentaire"))
			//	System.err.println("Ajout dans map "+comp.getName());

			neo_map_component_to_class.put(comp, rm);

		}

		panelLists.repaint();
		map_component_to_class = neo_map_component_to_class;
	}

	private void refreshComponent(JComponent component, ResultatMerge rm, List<?> selection2)
	{

		// Voir si je px reunifier avec la creation pour gagner un peu ... en decouplant
		Method setMeth = rm.getHasSet();
		Object value = rm.getValue();

		if (newSystem)
		{
			Object lastSelectedObject = selection2.get(selection2.size() - 1);
			Object value_of_last_object;
			try
			{
				value_of_last_object = rm.getNom().invoke(lastSelectedObject);
				//System.err.println("Value of last object is : "+value_of_last_object);
				if (rm.isEquals() == false)
					value = value_of_last_object;
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		// TODO : Et les plugin ici ???!
		OBJ2GUIPlug<?, ?> plugin = getPlugin(rm.getNom().getReturnType());

		
			Class<?> returnType = rm.getNom().getReturnType();
			int offset = 3;
			if ((returnType == boolean.class) || (returnType == Boolean.class))
				offset = 2;
			String varName = rm.getNom().getName().substring(offset);
			
			OBJ2GUIPlugExtended<?, ?> plugin2 = getPlugin2(returnType, selection2.get(0).getClass(), varName);
			
		
		//Class<?> returnType = rm.getNom().getReturnType();

		component.setBackground(Color.white);

		if (plugin2 != null)
		{
			plugin2.refresh(value, component);
			//component.setBackground(Color.white);
			if (rm.isEquals() == false)
			{
				//component.setBackground(Color.orange);
			}
		} else
		if (plugin != null)
		{
			plugin.refresh(value, component);
			//component.setBackground(Color.white);
			if (rm.isEquals() == false)
			{
				//component.setBackground(Color.orange);
			}
		} else if (component instanceof JTextArea)
		{
			JTextArea componentM = (JTextArea) component;
			//componentM.setBackground(Color.WHITE);

			if (value != null)
				if (value.equals(componentM.getText()) == false)
					componentM.setText("" + value);
			componentM.repaint();
			if (rm.isEquals() == false)
			{
				//componentM.setText("Differents values");
				//component.setBackground(Color.orange);
			}
		} else if (component instanceof JTextPane)
		{
			JTextPane componentM = (JTextPane) component;
			//componentM.setBackground(Color.WHITE);
			if (value != null)
				if (value.equals(componentM.getText()) == false)
					componentM.setText("" + value);
			componentM.repaint();
			if (rm.isEquals() == false)
			{
				//componentM.setText("Differents values");
				//	component.setBackground(Color.orange);
			}
		} else if (component instanceof JFormattedTextField)
		{
			JFormattedTextField componentM = (JFormattedTextField) component;
			//componentM.setBackground(Color.WHITE);
			componentM.repaint();
			if (value != null)
			{
				if (value instanceof Number)
					(componentM).setValue(value);
				else
					(componentM).setText("" + value);
			} else
			{

				//System.err.println("Voila voila");
			}

			if (rm.isEquals() == false)
			{
				if (value != null)
					if (newSystem == false)
						componentM.setText(valeur_differentes);
				//	System.err.println(componentM.getName()+ " is different"); 
				//	component.setBackground(Color.orange);
			}
		} else if (component instanceof JTextField)
		{
			JTextField componentM = (JTextField) component;
			//componentM.setBackground(Color.WHITE);

			componentM.setText("" + value);

			// TODO : Pr avoir la virgule ca serait ptet pas plus mal.
			// Rajout du 27/01/2016.
			if ((value instanceof Number) && (formatTextFieldWithDotOnly == false))
			{
				//integer ou flottant ?	
				if ((value instanceof Long) || (value instanceof Integer))
					componentM.setText("" + String.format("%d", value));
				else
					componentM.setText("" + String.format("%f", value));
			}

			if (rm.isEquals() == false)
			{
				if (newSystem == false)
					componentM.setText(valeur_differentes);

			}
		} else if (component instanceof WFlatSlider)
		{
			WFlatSlider componentM = (WFlatSlider) component;
			//componentM.setBackground(Color.WHITE);
			if (rm.isEquals() == false)
			{
				//	componentM.setBackground(Color.orange);
			}
			double val = Double.parseDouble("" + value);
			componentM.setValue((int) (val * componentM.getDivider()), false);
		} else if (component instanceof JSlider)
		{
			JSlider componentM = (JSlider) component;
			//componentM.setBackground(Color.WHITE);
			if (rm.isEquals() == false)
			{
				//	componentM.setBackground(Color.orange);
			}
			double val = Double.parseDouble("" + value);
			componentM.setValue((int) (val)); // Pb si divider ... ?
		} else if (component instanceof JCheckBox)
		{
			JCheckBox componentM = (JCheckBox) component;
			//componentM.setBackground(Color.WHITE);
			componentM.setOpaque(false);
			//componentM.setBackground(Color.orange);
			if (rm.isEquals() == false)
			{
				componentM.setOpaque(true);
				//componentM.setBackground(Color.orange);

			}
			if (value == null)
				value = false;
			componentM.setSelected(Boolean.parseBoolean("" + value));
		} else
		// Faut exploser ca dans une methode privée ...
		if (component instanceof JComboBox)
		{
			JComboBox<?> componentM = (JComboBox<?>) component;
			ActionListener[] old_listeners = componentM.getActionListeners();
			for (int i = 0; i < old_listeners.length; i++)
			{
				componentM.removeActionListener(old_listeners[i]);
			}
			
			//componentM.setBackground(Color.white);
			// Identifialbe or enum ? : TODO : y'a un bug avec les id surement ...
			if (rm.isEquals() == false)
			{
				if (newSystem == false)
					componentM.setSelectedIndex(-1);
				else
					componentM.setSelectedItem(value);
				//componentM.setBackground(Color.orange);
			} else
			{
				if (value instanceof Identifiable)
					componentM.setSelectedIndex((int) ((Identifiable) value).getId());
				else
				{
					if (value != null)
					{
						if ((value.getClass().isEnum() == true))
							componentM.setSelectedItem(value);
						else
						{
							try
							{
								// TODO : allez voir dans le binding apres
								Class<?> valueClass = setMeth.getParameterTypes()[0];

								offset = 3;
								if ((valueClass == boolean.class) || (valueClass == Boolean.class))
									offset = 2;
								varName = rm.getNom().getName().substring(offset);
								Binding bind = getBinding(rm.getOwnerclass(), varName.toLowerCase());

								if (bind != null)
								{
									Identifiable id = getIdentifiableFromList(bind.list_mats, Integer.parseInt("" + value));
									if (id != null)
										componentM.setSelectedItem(id);
									else
										componentM.setSelectedIndex(-1);
								} else
									componentM.setSelectedIndex(Integer.parseInt("" + value));
							} catch (IllegalArgumentException e)
							{
								log.fatal(e, e);
								log.fatal(e.getCause());
								e.printStackTrace();
							}
						}
					} else
						componentM.setSelectedIndex(-1);
				}
			}
			for (int i = 0; i < old_listeners.length; i++)
			{
				componentM.addActionListener(old_listeners[i]);
			}
		} else if (component instanceof JXDatePicker)
		{
			JXDatePicker componentM = (JXDatePicker) component;
			//componentM.setBackground(Color.WHITE);
			if (rm.isEquals() == false)
			{
				//component.setBackground(Color.orange);
			}
			componentM.setDate((Date) value);
		} else if (component instanceof FileChooser)
		{
			FileChooser componentM = (FileChooser) component;
			componentM.setOpaque(false);
			componentM.setFile((File) value);
			//componentM.setBackground(Color.WHITE);
			if (rm.isEquals() == false)
			{
				//component.setBackground(Color.orange);
				((FileChooser) component).setText("Different files");
			}
		} else if (component instanceof JColorChooserButton)
		{
			JColorChooserButton componentM = (JColorChooserButton) component;
			componentM.setCol((JWColor) value);
			componentM.setBackground(((JWColor) value).getColor());
		}

		if (rm.isEquals() == false)
			component.setBackground(Color.orange);
	}

	private Identifiable getIdentifiableFromList(List<?> list_mats, Integer integer)
	{
		for (int i = 0; i < list_mats.size(); i++)
		{
			Object o = list_mats.get(i);
			Identifiable identif = (Identifiable)o;
			if (identif.getId() == integer.intValue())
				return identif;
		}
		return null;
	}

	private JComponent getComponentNamed(String name)
	{

		for (int i = 0; i < panelFields.getComponentCount(); i++)
		{
			JComponent cp = (JComponent) panelFields.getComponent(i);

			if (cp.getName().startsWith("ReunionMig"))
			{
				JPanel reunion = (JPanel) cp;
				for (int j = 0; j < reunion.getComponentCount(); j++)
				{
					JComponent cp2 = (JComponent) reunion.getComponent(j);
					if ((cp2.getName() != null) && (cp2.getName().equals(name)))
						return cp2;
				}
			} else if ((cp.getName() != null) && (cp.getName().equals(name)))
				return cp;
		}

		return null;
	}

	/**
	 * This methods allows classes to register for MyEvents
	 */
	public void addMyEventListener(MyEventListener listener)
	{

		if (listener == null)
		{
			log.fatal("Grave erreur ... 654");
			System.exit(-1);
		}
		if ((dbg_contains(listenerList, listener) == true))
		{
			log.fatal("You made a mistake by trying to add the same listener.... : " + listener);
		} else
		{
			listenerList.add(MyEventListener.class, listener);
			if (PRINT_DEBUG == true)
				log.info("Add myEventList in " + this + " to add " + listener);
		}
	}

	/**
	 * @param listenerList
	 * @param listener
	 * @return
	 */
	private boolean dbg_contains(EventListenerList listenerList, MyEventListener listener)
	{
		for (int i = 0; i < listenerList.getListenerList().length; i += 2)
		{
			MyEventListener l = (MyEventListener) listenerList.getListenerList()[i + 1];
			if (l == listener)
				return true;
		}
		return false;
	}

	/**
	 * This methods allows classes to unregister for MyEvents
	 * 
	 * @param listener
	 */
	public void removeMyEventListener(MyEventListener listener)
	{

		listenerList.remove(MyEventListener.class, listener);
		if (PRINT_DEBUG == true)
			log.info("Remove myEventList in " + this + " to remove " + listener);
	}

	/**
	 * This private class is used to fire MyEvents
	 * 
	 * @param evt
	 */
	public void fireMyEvent(MyChangedEvent evt)
	{
		String message = "Firing event from " + this.hashCode() + " to : ";
		Object[] listeners = listenerList.getListenerList();
		// Each listener occupies two elements - the first is the listener class
		// and the second is the listener instance
		for (int i = 0; i < listeners.length; i += 2)
		{
			if (listeners[i] == MyEventListener.class)
			{
				if (listeners[i + 1] instanceof JPanelMagique)
					message += (((JPanelMagique) listeners[i + 1]).hashCode() + ", ");
				else
					message += (((MyEventListener) listeners[i + 1]).hashCode() + ", ");
			}
		}
		message += "\r\n";

		if (PRINT_DEBUG == true)
			log.info(message);

		for (int i = 0; i < listeners.length; i += 2)
		{
			if (listeners[i] == MyEventListener.class)
			{
				((MyEventListener) listeners[i + 1]).myEventOccurred(evt);
			}
		}
	}

	@Override
	public void myEventOccurred(MyChangedEvent evt)
	{
		// Propage au parent qu'il y'a eu changement.
		// TODO : Ici il faudrait voir a remonter au parent, mais verifier que ca ne boucle pas...
		fireMyEvent(evt);
		// Rafraichir aussi les fils
		for (int i = 0; i < panelSons.getComponentCount(); i++)
		{
			JComponent fils = (JComponent) panelSons.getComponent(i);
			if (fils instanceof JPanelMagique)
			{
				try
				{
					((JPanelMagique) fils).refresh();
				} catch (Exception e1)
				{
					log.fatal(e1, e1);
					log.fatal(e1.getCause());
					e1.printStackTrace();
				}
			}
		}
		try
		{
			refresh();
		} catch (Exception e)
		{
			log.fatal(e, e);
			log.fatal(e.getCause());
			e.printStackTrace();
		}
	}


	/**
	 * This method will generate a JPanel that will contains all annotated
	 * fields and methods software developper has choosen.
	 * @param selection
	 * @param binds
	 * @return
	 * @throws Exception
	 */
	public static JPanelMagique GenerateJPanelFromSelectionAndBindings(List<?> selection, List<Binding> binds) throws Exception
	{
		return GenerateJPanelFromSelectionAndBindings(null, selection, true, binds, null, true, true);
	}

	/**
	 * This method will generate a JPanel that will contains all annotated
	 * fields and methods software developper has choosen.
	 * 
	 * @param selection2
	 * @param binds
	 * @param bindsEnum
	 * @param object
	 * @return
	 */
	public static JPanelMagique GenerateJPanelFromSelectionAndBindings(List<?> selection, List<Binding> binds, boolean OnlyAnnotatedMethods) throws Exception
	{
		return GenerateJPanelFromSelectionAndBindings(null, selection, true, binds, null, OnlyAnnotatedMethods, true);
	}

	/**
	 * This method will generate a JPanel that will contains all annotated
	 * fields and methods software developper has choosen.
	 * 
	 * @param selection2
	 * @param binds
	 * @param bindsEnum
	 * @param object
	 * @return
	 */
	public static JPanelMagique GenerateJPanelFromSelectionAndBindings(JPanelMagique parent, List<?> selection, List<Binding> binds, boolean OnlyAnnotatedMethods) throws Exception
	{
		return GenerateJPanelFromSelectionAndBindings(parent, selection, true, binds, null, OnlyAnnotatedMethods, true);
	}

	public static JPanelMagique GenerateJPanelFromSelectionAndBindings(JPanelMagique parent, List<?> selection, boolean recursive, List<Binding> binds, boolean OnlyAnnotatedMethods) throws Exception
	{
		return GenerateJPanelFromSelectionAndBindings(parent, selection, recursive, binds, null, OnlyAnnotatedMethods, true);
	}

	public static JPanelMagique GenerateJPanelFromSelectionAndBindings(JPanelMagique parent, List<?> selection, List<Binding> binds, List<BindingEnum> bindsEnum) throws Exception
	{
		return GenerateJPanelFromSelectionAndBindings(parent, selection, true, binds, bindsEnum, true, true);
	}

	public static JPanelMagique GenerateJPanelFromSelectionAndBindings(List<?> selection, List<Binding> binds, List<BindingEnum> bindsEnum) throws Exception
	{
		return GenerateJPanelFromSelectionAndBindings(null, selection, true, binds, bindsEnum, true, true);
	}

	public static JPanelMagique GenerateJPanelFromSelectionAndBindings(JPanelMagique parent, List<?> selection, List<Binding> binds, List<BindingEnum> bindsEnum, boolean OnlyAnnotatedMethods) throws Exception
	{
		return GenerateJPanelFromSelectionAndBindings(parent, selection, true, binds, bindsEnum, OnlyAnnotatedMethods, true);
	}

	/**
	 * This method will generate a JPanel that will contains all annotated
	 * fields and methods software developper has choosen.
	 * 
	 * @param selection
	 *            list of objects of same type. (use it with filtering object
	 *            within the list).
	 * @param recursive
	 *            Go trough sons object.
	 * @param binds
	 *            List of binding.
	 * @param bindsEnum
	 *            List of binding for enumeration type.
	 * @param OnlyAnnotatedMethods
	 *            Only create componenent for annotated getters methods
	 * @param showTreeViewButtons
	 *            Show the button on down for having different kind of view for
	 *            edition.
	 * @return The magic panel.
	 * @throws Exception
	 */
	public static JPanelMagique GenerateJPanelFromSelectionAndBindings(JPanelMagique parent, final List<?> selection, boolean recursive, final List<Binding> binds, final List<BindingEnum> bindsEnum, boolean OnlyAnnotatedMethods, boolean showTreeViewButtons) throws Exception
	{
		// Si on donne une liste vide alors on retourne un panneau avec un label dedans indiquant que y'a pas de selection.
		if (selection.size() == 0)
		{
			JPanelMagique panel = new JPanelMagique(null, null, null, null, false, false);
			panel.add(new JLabel("No selection"));
			return panel;
		}
		;
		// On prends un objet de reference. (on espere que la liste ne contients que des objets du méme type).
		final Object obj = selection.get(0);

		// Récupere le nom de la classe
		String VarName = obj.getClass().getName();
		// Refiltre un peu le nom de la variable
		if (VarName.contains("."))
			VarName = VarName.substring(VarName.lastIndexOf(".") + 1);

		final JPanelMagique panel = new JPanelMagique(parent, selection, binds, bindsEnum, OnlyAnnotatedMethods, showTreeViewButtons);
		panel.setName("JPanelMagique::" + VarName);

		String titledBorder = getRegisteredTitleBorder(obj.getClass());

		if (selection.size() > 1)
			panel.setBorder(BorderFactory.createTitledBorder(titledBorder + " (" + selection.size() + ")"));
		else
			panel.setBorder(BorderFactory.createTitledBorder(titledBorder));

		//panel.setBorder(BorderFactory.createTitledBorder("TROUTROU"));

		// Layouting
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setLayout(new MigLayout("wrap 1", "[grow,fill]", "[fill, grow]"));
		panel.setLayout(new MigLayout("wrap 1", "[grow,fill]", ""));
		
		PROPERTY_MIGLAYOUT class_layout_annotation = obj.getClass().getAnnotation(PROPERTY_MIGLAYOUT.class);

		//GridLayout gl = new GridLayout(0, 2);

		if (class_layout_annotation != null)
			AssigneLayoutToPanel(panel.panelFields, class_layout_annotation);
		else
		{
//			MigLayout ml = new MigLayout("fill, gap 0, insets 0 0 0 0", "[right]rel[grow,fill]", "[fill, grow]1[fill, grow]");
			MigLayout ml = new MigLayout("fill, gap 0, insets 0 0 0 0", "[right]rel[grow,fill]", "[fill, grow]1[fill, grow]");
			
			//ml = new MigLayout("", "[right]rel[grow,fill]", "[top]1[top]");
			
			//ml=new MigLayout("wrap 2, fill", "[grow,fill]", "[fill, grow]");
			//ml=new MigLayout("", "[grow][]", "[grow]");

			
			
			panel.panelFields.setLayout(ml);
			
			//panel.panelFields.setLayout(new BoxLayout(panel.panelFields, BoxLayout.Y_AXIS));
			
			

		}

		panel.panelSons.setLayout(new BoxLayout(panel.panelSons, BoxLayout.Y_AXIS));
		panel.panelSons.setLayout(new MigLayout());
		panel.panelSons.setLayout(new MigLayout("fill, gap 0 0, insets 0 0 0 0, wrap 1", "[grow,fill]", "[fill, grow]1[fill, grow]"));
		panel.panelLists.setLayout(new BoxLayout(panel.panelLists, BoxLayout.Y_AXIS));

		boolean PanelFieldsFilled = false;
		boolean PanelSonsFilled = false;
		boolean PanelListsFilled = false;

		// Remplissage
		// Analyser pour avoir les variables
		List<?> merged = TemplatePropertyMergerV2.MergeCollection(selection, OnlyAnnotatedMethods);

		for (int i = 0; i < merged.size(); i++)
		{
			ResultatMerge rm = (ResultatMerge) merged.get(i);

			Class<?> returnType = rm.getNom().getReturnType();
			int offset = 3;
			if ((returnType == boolean.class) || (returnType == Boolean.class))
				offset = 2;
			String name = rm.getNom().getName().substring(offset);

			PROPERTY_MIG_GRID class_miglayout_prop = rm.getNom().getAnnotation(PROPERTY_MIG_GRID.class);

			JComponent component = createComponent(panel, rm, selection);

			//	if (component.getName().equals("Commentaire"))
			//		System.err.println("Ajout dans map "+component.getName());

			panel.map_component_to_class.put(component, rm);
			JLabel label = new JLabel("" + name);
			label.setHorizontalAlignment(SwingConstants.RIGHT);
			label.setLabelFor(component);

			if (class_layout_annotation != null)
			{
				class_layout_annotation.labelPosition();
				if (class_layout_annotation.labelPosition() == lblposition.LEFT)
					label.setHorizontalAlignment(SwingConstants.LEFT);
				class_layout_annotation.labelPosition();
				if (class_layout_annotation.labelPosition() == lblposition.CENTER)
					label.setHorizontalAlignment(SwingConstants.CENTER);
				class_layout_annotation.labelPosition();
				if (class_layout_annotation.labelPosition() == lblposition.RIGHT)
					label.setHorizontalAlignment(SwingConstants.RIGHT);
			}

			label.setName("Label_" + name);

			PROPERTY_interface class_prop = rm.getNom().getAnnotation(PROPERTY_interface.class);
			// Il n'y a pas de definition du layout pour ce composant, donc on fait un y_layout normal.
			if (class_miglayout_prop == null)
			{
				if ((class_prop != null) && (class_prop.isDisplayLabel() == true))
				{
					panel.panelFields.add(label, "grow");
					panel.panelFields.add(component, "wrap");
				} else
				{
					if ((class_prop != null) && (class_prop.isDisplayLabel() == false))
						panel.panelFields.add(component, "span 2,grow, wrap");
					else
					{
						panel.panelFields.add(label, "grow");
						panel.panelFields.add(component, "wrap");
					}

				}
			} else
			{

				if (class_prop.isDisplayLabel() == true)
				{
					JPanel reunion = new JPanel();
					reunion.setName("ReunionMig" + reunion.hashCode());
					reunion.setOpaque(true);
					//reunion.setBackground(Color.BLUE);
					reunion.setLayout(new MigLayout("gap 0 0, insets 0 0 0 0, nocache", "[30%, fill][70%, fill]", "100%, fill"));
					reunion.add(label, "grow");
					reunion.add(component, "grow");
					panel.panelFields.add(reunion, class_miglayout_prop.attribut());
				} else
				{
					panel.panelFields.add(component, class_miglayout_prop.attribut());
				}
			}

			PanelFieldsFilled = true;
		}

		// S'arrange pour pouvoir utiliser tab et passer d'un champ a l'autre...
		// TODO : Not sure it's usefull
		panel.panelFields.setFocusTraversalPolicy(null);
		panel.panelFields.setFocusCycleRoot(true);
		panel.panelFields.setFocusTraversalKeysEnabled(true);
		panel.panelFields.setFocusTraversalPolicyProvider(true);

		// Charge les labels a partir du fichier XML si celui ci existe (sinon le crée).
		ChargeLabelXMLEtCreeLabelXMLSiExistePasEtUpdateLeFichierPourNouveauChamps(panel.panelFields, obj.getClass().getName());

		// Detecter s'il y'a des fils
		Class<?> objectClass = obj.getClass();
		Field[] fields = getAllFields(objectClass);

		if (recursive)
		{
			for (int i = 0; i < fields.length; i++)
			{
				Annotation anot;

				//	if (PRINT_DEBUG)
				//		log.info("JPanelMagique::FIELD = "+fields[i]);
				// Pour les "FILS"
				anot = fields[i].getAnnotation(PROPERTY_FIELD_XXXABLE.class);
				if (anot != null)/* || (OnlyAnnotatedMethods==false)) */
				{
					List<Object> selection_fils = new ArrayList<Object>();
					for (int j = 0; j < selection.size(); j++)
					{
						Object objM = selection.get(j);
						fields[i].setAccessible(true);
						Object objFils = fields[i].get(objM);
						if (objFils != null)
							selection_fils.add(objFils);
						else
							log.info("Field = " + fields[i] + " has a NULL son");
					}

					// sub selection ... ?!
					JPanelMagique panelfils = GenerateJPanelFromSelectionAndBindings(panel, selection_fils, recursive, binds, bindsEnum, OnlyAnnotatedMethods, showTreeViewButtons);

					if (fields[i].getName() != null)
					{
						String titledBorderM = getRegisteredTitleBorder(fields[i].getType()); //fields[i].getName()
						panelfils.setBorder(BorderFactory.createTitledBorder(titledBorderM));
					}

					panel.panelSons.add(panelfils);
					//panelfils.addMyEventListener(panel); // Deja fait dans GenerateJPanel je pense.
					PanelSonsFilled = true;
				}

				// Pour les LISTS
				anot = fields[i].getAnnotation(PROPERTY_FIELD_LISTABLE.class);
				//Class clazzDetected = null;

				
				if (anot != null)
				{
					//	List selection_fils = new ArrayList();
					DefaultListModel<Object> dlm = new DefaultListModel<Object>();
					// TODO : C'est peut étre pas une bonne idée de vouloir mettre != objets dans la liste car apres on ne sais plus quel objet appartient a quel autre objet.
					//for (int j = 0; j < selection.size(); j++)
					//{

					Object objM = selection.get(0/* j */); // Indisponibilities (l'objet pére)
					fields[i].setAccessible(true);
					if (PRINT_DEBUG)
						log.info("Field = " + fields[i]);

					Object objFils = fields[i].get(objM);

					Type bestType = null;
					// ObjFils is a list;
					{
						List<?> listCast = (List<?>) objFils;
						if (listCast != null)
						{
							objFils.getClass().getGenericInterfaces();
							objFils.getClass().getGenericSuperclass();
							
							Class<?> returnClass = fields[i].getType();
							if (Collection.class.isAssignableFrom(returnClass))
							{
								Type returnType = fields[i].getGenericType();
								
								/**
								 * Ajout du 25-01-2023
								 */
								if (objFils.getClass().getGenericSuperclass() instanceof ParameterizedType)
								{
									ParameterizedType paramType = (ParameterizedType) objFils.getClass().getGenericSuperclass();
									Type[] argTypes = paramType.getActualTypeArguments();
									if (argTypes.length > 0)
									{
										bestType = argTypes[0];
									}
								}
								/**
								 * FIN Ajout du 25-01-2023
								 */
								
								if (returnType instanceof ParameterizedType)
								{
									ParameterizedType paramType = (ParameterizedType) returnType;
									Type[] argTypes = paramType.getActualTypeArguments();
									if (argTypes.length > 0)
									{
										// System.out.println("Generic type is " + argTypes[0]);

										bestType = argTypes[0];
									}
								}
							}
						} else
							System.err.println("Doh !!!!");
					}

					// DETECTION DU TYPE

					if (objFils != null)
						for (int k = 0; k < ((List<?>) objFils).size(); k++)
						{
							if (objFils != null)
							{
								dlm.addElement(((List<?>) objFils).get(k));
							}
						}
					//}	

					String BorderTitle = fields[i].getName();

					//System.err.println("-> "+ObjectClassInTheList);

					if (bestType != null)
					{

						//System.err.println("Classe : "+bestType.getTypeName());
						try
						{
							BorderTitle = getRegisterTitleBorderForList(objectClass, BorderTitle);
						} catch (Exception e)
						{
							//e.printStackTrace();
						}
						//panelfils.setBorder(new TitledBorder("Miaou : "+borderTitle));

						JPanel panelfils = create_Panel_List_Editor(panel, dlm, BorderTitle, (List<Object>) objFils, bestType);
						panel.panelLists.add(panelfils, BorderLayout.CENTER);
						PanelListsFilled = true;
					} else
						System.err.println("Probleme wax 64e46e54e");
				}
			}
		}

		boolean PanelBouttonFilled = false;
		JPanel panel_boutons = new JPanel();
		panel_boutons.setLayout(new MigLayout());
		// Les boutons pour faire des call de methodes...
		for (int i = 0; i < fields.length; i++)
		{
			PROPERTY_button anot = fields[i].getAnnotation(PROPERTY_button.class);
			if (anot != null)
			{
				JButton button = new JButton();
				button.setName("Button_" + anot.method_name());
				if (anot.text().length() == 0)
					button.setText(anot.method_name());
				else
					button.setText(anot.text());

				if (anot.toolTipText().length() != 0)
					button.setToolTipText(anot.toolTipText());

				// Action listener ?
				button.addActionListener(panel);
				panel.map_button_to_method.put(button, anot);
				panel_boutons.add(button);
				PanelBouttonFilled = true;
			}
		}

		if (PanelFieldsFilled == true)
			panel.add(panel.panelFields);
		if (PanelSonsFilled == true)
			panel.add(panel.panelSons);
		if (PanelListsFilled == true)
			panel.add(panel.panelLists);
		if (PanelBouttonFilled == true)
			panel.add(panel_boutons);

		if (showTreeViewButtons == true)
		{
			JButton treeExplorerButton1 = createTreeViewButton(selection, binds, bindsEnum, panel, true, showTreeViewButtons);
			treeExplorerButton1.setName("Button_TreeView1");
			JButton treeExplorerButton2 = createTreeViewButton(selection, binds, bindsEnum, panel, false, showTreeViewButtons);
			treeExplorerButton2.setName("Button_TreeView2");
			JPanel panelBtTree = new JPanel();

			panelBtTree.setBackground(panelBtTree.getBackground().darker());
			panelBtTree.setLayout(new MigLayout());
			panelBtTree.add(treeExplorerButton1);
			panelBtTree.add(treeExplorerButton2);

			panel.add(panelBtTree);

		}

		panel.doLayout();
		panel.validate();
		return panel;
	}

	/**
	 * Ceci va s'occuper de charger/sauver (si pas existant) le fichier XML avec
	 * les labels. (sans tenir compte des la localisation encore a venir).
	 * 
	 * @param panel
	 * @throws JDOMException
	 * @throws IOException
	 */
	private static void ChargeLabelXMLEtCreeLabelXMLSiExistePasEtUpdateLeFichierPourNouveauChamps(JPanel panel, String fileclassname) throws Exception
	{
		// Une fois que notre panel contenant les elements de base (non recursive) alors on tente de charger son fichier xml pour les label
		// Ou on sauve un par defaut puis on le charge.
		String LABEL_XML_DIRECTORY1 = LABEL_XML_DIRECTORY + "/obj2gui_Labels/" + Locale.getDefault().getLanguage() + "/";

		File dirForLabel = new File(LABEL_XML_DIRECTORY1);
		// Si le repertoire n'existe pas alors on tente de le créer
		if (dirForLabel.exists() == false)
		{
			boolean ret = dirForLabel.mkdirs();
			// Si la création ne marche pas
			if (ret == false)
			{
				String errormsg = "Cannot create Directory " + dirForLabel;
				log.fatal(errormsg);
				DialogDivers.Show_dialog(errormsg);
			}
		}
		String label_filename = LABEL_XML_DIRECTORY1 + "/" + fileclassname + "_labels.xml";
		File file_label_file = new File(label_filename);
		// On sauvegarde les label si jamais ils n'existent pas.
		if (file_label_file.exists() == false)
			GUI2XMLLabel.SaveLabel(panel, label_filename);

		// On charge les labels (qui existent quoiqu'il arrive).
		// panel.panelFields.LoadLabel(label_filename);
		GUI2XMLLabel.LoadLabel(panel, label_filename);
		// Resauve le fichier car entre temps on a peut etre rajouté des variable dans une classe ou quoi...
		if (activateAutoSaveAutoUpdate == true)
			GUI2XMLLabel.SaveLabel(panel, label_filename);
	}

	/**
	 * Crée un bouton qui va creer une fenetre avec une arbre pour editer les
	 * propriétés des objets.
	 * 
	 * @param selection
	 * @param binds
	 * @param bindsEnum
	 * @param panel
	 * @return
	 */
	private static JButton createTreeViewButton(final List<?> selection, final List<Binding> binds, final List<BindingEnum> bindsEnum, final JPanelMagique panel, final boolean UseTabbedPane, final boolean showTreeViewButtons)
	{
		JButton treeExplorerButton = new JButton();
		if (UseTabbedPane == false)
			treeExplorerButton.setText("Tree View");
		else
			treeExplorerButton.setText("Tab Tree View");
		treeExplorerButton.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				JTreeMagique paneltree = JTreeMagique.GenerateJTreeFromSelectionAndBindings(selection, binds, bindsEnum, panel.XMLLabelFile, UseTabbedPane, showTreeViewButtons);
				paneltree.addMyEventListener(panel);
				//panel.addMyEventListener(paneltree);

				JFrameTreeView fr2 = new JFrameTreeView(panel, paneltree);
				fr2.doLayout();
				fr2.setVisible(true);
				panel.addFrameTreeView(fr2);
			}

		});
		return treeExplorerButton;
	}

	/**
	 * @param panel_parent
	 * @param dlm
	 * @param parentList
	 * @param bestType
	 * @param clazzDetected
	 * @return
	 */
	private static JPanel create_Panel_List_Editor(final JPanelMagique panel_parent, final DefaultListModel<Object> dlm, String borderTitle, final List<Object> parentList, final Type bestType)
	{
		final JList<Object> jliste = new JList<Object>(dlm);

		jliste.addKeyListener(new KeyListener()
		{
			@Override
			public void keyPressed(KeyEvent arg0)
			{
			}

			@Override
			public void keyReleased(KeyEvent arg0)
			{
				if (arg0.getKeyCode() == KeyEvent.VK_ENTER)
					ViewEditElementFromListable(panel_parent, jliste);
				if (arg0.getKeyCode() == KeyEvent.VK_DELETE)
				{
					// TODO : Show confirm ???
				}
			}

			@Override
			public void keyTyped(KeyEvent arg0)
			{
			}
		});
		jliste.addMouseListener(new MouseListener()
		{
			@Override
			public void mouseClicked(MouseEvent arg0)
			{
				if ((arg0.getClickCount() == 2) && (arg0.getButton() == MouseEvent.BUTTON1))
					ViewEditElementFromListable(panel_parent, jliste);
			}

			@Override
			public void mouseEntered(MouseEvent arg0)
			{
			}

			@Override
			public void mouseExited(MouseEvent arg0)
			{
			}

			@Override
			public void mousePressed(MouseEvent arg0)
			{
			}

			@Override
			public void mouseReleased(MouseEvent arg0)
			{
			}
		});

		JPanel panelfils = new JPanel();

		if (borderTitle != null)
		{
			panelfils.setBorder(new TitledBorder(borderTitle));
		}

		//panelfils.setLayout(new BoxLayout(panelfils, BoxLayout.X_AXIS));
		panelfils.setLayout(new MigLayout("", "[grow][]", "[grow]"));

		JPanel panelfilsboutons = new JPanel();
		panelfilsboutons.setLayout(new MigLayout());
		JScrollPane	scrollpanellist = new JScrollPane(jliste);
		panelfils.add(scrollpanellist, "cell 0 0, grow");
		panelfils.add(panelfilsboutons, "cell 1 0, grow");

		final JButton boutonEdit = new JButton(TEXT_BOUTON_VIEW_EDIT);
		if (IMG_BOUTON_VIEW_EDIT != null)
			boutonEdit.setIcon(IMG_BOUTON_VIEW_EDIT);
		boutonEdit.setToolTipText(TEXT_TOOLTIP_BOUTON_VIEW_EDIT);
		boutonEdit.setName("Button_ViewEdit");

		boutonEdit.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				ViewEditElementFromListable(panel_parent, jliste);
			}
		});

		boutonEdit.setEnabled(true);
		if (jliste.getModel().getSize() == 0)
			boutonEdit.setEnabled(false);

		JButton boutonAddElement = new JButton(TEXT_BOUTON_ADD_ELEMENT);
		if (IMG_BOUTON_ADD_ELEMENT != null)
			boutonAddElement.setIcon(IMG_BOUTON_ADD_ELEMENT);
		boutonAddElement.setToolTipText(TEXT_TOOLTIP_BOUTON_ADD_ELEMENT);
		boutonAddElement.setName("Button_ADD");

		try
		{
			//System.err.println("Classe : "+bestType.getTypeName());
			
			Class<?> bonneclasse = Class.forName(bestType.getTypeName());
			//System.err.println("Bonne Classe : "+bonneclasse);
			final Constructor<?> cst = bonneclasse.getConstructor();
			if (cst == null)
				boutonAddElement.setEnabled(false);
			boutonAddElement.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent arg0)
				{

					try
					{
						Object nouvelObjet = cst.newInstance();
						parentList.add(nouvelObjet);
						dlm.addElement(nouvelObjet);
						panel_parent.refresh();

						boutonEdit.setEnabled(true);
						if (jliste.getModel().getSize() == 0)
							boutonEdit.setEnabled(false);

						panel_parent.fireMyEvent(new MyChangedEvent(this, null));

					} catch (SecurityException e)
					{
						e.printStackTrace();
						log.fatal(e, e);
					} catch (InstantiationException e)
					{
						e.printStackTrace();
						log.fatal(e, e);
					} catch (IllegalAccessException e)
					{
						e.printStackTrace();
						log.fatal(e, e);
					} catch (IllegalArgumentException e)
					{
						e.printStackTrace();
						log.fatal(e, e);
					} catch (InvocationTargetException e)
					{
						e.printStackTrace();
						log.fatal(e, e);
					} catch (Exception e)
					{
						e.printStackTrace();
						log.fatal(e, e);
					}
				}
			});
		} catch (NoSuchMethodException | SecurityException e1)
		{
			boutonAddElement.setEnabled(false);
			e1.printStackTrace();
			log.fatal(e1, e1);
		} catch (ClassNotFoundException e1)
		{
			boutonAddElement.setEnabled(false);
			e1.printStackTrace();
			log.fatal(e1, e1);
		}

		JButton boutonRemoveElement = new JButton(TEXT_BOUTON_REMOVE_ELEMENT);
		if (IMG_BOUTON_REMOVE_ELEMENT != null)
			boutonRemoveElement.setIcon(IMG_BOUTON_REMOVE_ELEMENT);
		boutonRemoveElement.setToolTipText(TEXT_TOOLTIP_BOUTON_REMOVE_ELEMENT);
		boutonRemoveElement.setName("Button_REMOVE");

		boutonRemoveElement.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				List<Object> selectionned = jliste.getSelectedValuesList();
				for (int i = 0; i < selectionned.size(); i++)
				{
					Object o = selectionned.get(i);
					parentList.remove(o);
					dlm.removeElement(o);
					try
					{
						panel_parent.refresh();
						panel_parent.fireMyEvent(new MyChangedEvent(this, null));
					} catch (Exception e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					boutonEdit.setEnabled(true);
					if (jliste.getModel().getSize() == 0)
						boutonEdit.setEnabled(false);

				}
			}
		});

		panelfilsboutons.add(boutonAddElement, "cell 0 1, growx");
		panelfilsboutons.add(boutonRemoveElement, "cell 0 2, growx");
		panelfilsboutons.add(boutonEdit, "cell 0 0, growx");

		return panelfils;
	}

	/**
	 * @param jliste
	 */
	protected static void ViewEditElementFromListable(JPanelMagique panel_parent, JList<Object> jliste)
	{
		// TODO : Ici aussi il faut faire attention au types différents.
		List<?> objs = jliste.getSelectedValuesList();
		log.info("Selection : " + objs.size());

		if (objs.size() == 0)
			return;

		HashMap<Class<?>, List<Object>> map = new HashMap<Class<?>, List<Object>>();
		//List<Object> totos = new ArrayList<Object>();
		for (int i = 0; i < objs.size(); i++)
		{
			Class<?> objectClass = objs.get(i).getClass();
			if (map.get(objectClass) == null)
				map.put(objectClass, new ArrayList<Object>());
			map.get(objectClass).add(objs.get(i));
		}

		JTabbedPane multiplePanel = new JTabbedPane();

		// TODO : Boucle ICI
		Set<Class<?>> classesToPanelise = map.keySet();
		for (Iterator<Class<?>> iterator = classesToPanelise.iterator(); iterator.hasNext();)
		{
			Class<?> class1 = iterator.next();

			try
			{
				boolean recursive = true;
				JPanelMagique pan = JPanelMagique.GenerateJPanelFromSelectionAndBindings(panel_parent, map.get(class1), recursive, panel_parent.bindList_identifiables, panel_parent.bindList, panel_parent.OnlyAnnotatedMethods, panel_parent.showTreeViewButtons);

				pan.addMyEventListener(panel_parent);
				multiplePanel.addTab(class1.getName(), pan);

			} catch (Exception e)
			{
				log.fatal(e, e);
				log.fatal(e.getCause());
				e.printStackTrace();
				multiplePanel.add(new JLabel("Error generating panel"));
				DialogDivers.Show_dialog(e, "Error generating panel");

			}
		}

		JFrame frame = embbed(multiplePanel);
		frame.setSize(640, 480);
		frame.setVisible(true);

	}

	/**
	 * This method return all Field of a class and all this parent class
	 * 
	 * @param classType
	 * @return all Field of the classType and all this parent class
	 */
	public static Field[] getAllFields(Class<?> classType)
	{

		List<Field> retour = new ArrayList<Field>();
		Field[] fields = classType.getDeclaredFields();
		for (int i = 0; i < fields.length; i++)
			retour.add(fields[i]);

		Class<?> parentclass = classType.getSuperclass();
		if (parentclass != null)
		{
			Field[] r = getAllFields(parentclass);
			for (int i = 0; i < r.length; i++)
				retour.add(r[i]);
		}

		Field[] arrRet = new Field[retour.size()];
		for (int i = 0; i < retour.size(); i++)
		{
			arrRet[i] = retour.get(i);
		}
		return arrRet;
	}

	/**
	 * @param pan
	 * @return
	 */
	protected static JFrame embbed(JComponent pan)
	{
		JFrame frame = new JFrame();
		frame.add(new JScrollPane(pan), BorderLayout.CENTER);
		return frame;
	}

	/*
	 * @param
	 * @param rm
	 * @return
	 * @throws Exception
	 */
	private static JComponent createComponent(JPanelMagique panel, ResultatMerge rm, List<?> selection2) throws Exception
	{
		PROPERTY_interface anot = rm.getNom().getAnnotation(PROPERTY_interface.class);

		if (anot == null)
		{
			if (PRINT_DEBUG)
				log.info("This method has no annotation to createComponent() : " + rm);
		}

		JComponent comp = null;
		Object value = rm.getValue();

		// TODO : Ceci permettrait de ne plus avoir de "Valeurs differentes" mais d'afficher la valeur pour le
		// dernier objet selectionné.
		if (newSystem)
		{
			Object lastSelectedObject = selection2.get(selection2.size() - 1);
			Object value_of_last_object = rm.getNom().invoke(lastSelectedObject);
			//System.err.println("Value of last object is : "+value_of_last_object);
			if (rm.isEquals() == false)
				value = value_of_last_object;
		}

		Class<?> returnType = rm.getNom().getReturnType();
		int offset = 3;
		if ((returnType == boolean.class) || (returnType == Boolean.class))
			offset = 2;
		String varName = rm.getNom().getName().substring(offset);
		boolean readOnly = false;
		if (anot != null)
			readOnly = anot.readOnly();

		//if (PRINT_DEBUG)
		//	log.info("JPanelMagique::varName=="+varName);

		/*
		 * log.info("JPanelMagique::varName=="+varName);
		 * log.info("JPanelMagique::returnType=="+returnType); if
		 * (returnType.toGenericString().contains("TimeSlot")) {
		 * System.err.println("DUDU654"); }
		 */

		OBJ2GUIPlug<?, ?> plugin = getPlugin(returnType);

		OBJ2GUIPlugExtended<?, ?> plugin2 = getPlugin2(returnType, selection2.get(0).getClass(), varName);
				
		// Est-ce une array ?
		if (returnType.isArray())
		{
			// Faut repliquer comment "PLUGIN" fonctionne... avec un petite modif pour gere 1 ou N objet (1 jusque la).	
			Class<?> classComp = returnType.getComponentType();
			// Est-ce une array 2D ?

			if (classComp.isArray() == false)
				plugin = new TableMagiquePlugin1D();

			else
				plugin = new TableMagiquePlugin2D();

			comp = (JComponent) plugin.build(null/* val */, selection2, rm.getNom(), rm.getHasSet(), panel);
			comp.setBackground(Color.white);
			if (rm.isEquals() == false)
			{
				//	comp.setBackground(Color.orange);
			}
		} else if (plugin2 != null)
		{
			comp = (JComponent) plugin2.build(value, selection2, rm.getNom(), rm.getHasSet(), panel);
			comp.setBackground(Color.white);
			//if (rm.isEquals()==false)
			//	comp.setBackground(Color.orange);
		}else if (plugin != null)
		{
			comp = (JComponent) plugin.build(value, selection2, rm.getNom(), rm.getHasSet(), panel);
			comp.setBackground(Color.white);
			//if (rm.isEquals()==false)
			//	comp.setBackground(Color.orange);
		} else
		// Type Slider
		if ((anot != null) && (anot.gui_type() == gui_type.FLATSLIDER))
		{
			float min = anot.min(), max = anot.max(), div = anot.divider();
			double val = Double.parseDouble("" + value);
			comp = new WFlatSlider((int) min, (int) max);
			((WFlatSlider) comp).setDivider(div);
			((WFlatSlider) comp).setValue((int) (val * div));
			if (rm.isEquals() == false)
			{
				//	((WFlatSlider)comp).setBackground(Color.orange);
			}
			((WFlatSlider) comp).addActionListener(panel);
		} else if ((anot != null) && (anot.gui_type() == gui_type.SLIDER))
		{
			float min = anot.min(), max = anot.max(), div = anot.divider();
			double val = Double.parseDouble("" + value);
			comp = new JSlider((int) min, (int) max);
			//((JSlider)comp).setDivider(div);
			((JSlider) comp).setValue((int) (val * div));
			if (rm.isEquals() == false)
			{
				//((JSlider)comp).setBackground(Color.orange);
			}
			((JSlider) comp).addChangeListener(panel);
		} else if ((anot != null) && (anot.gui_type() == gui_type.TEXTAREA))
		{
			comp = new JTextArea();
			comp.setBorder(new EtchedBorder());
			((JTextArea) comp).setText((String) value);
			if (rm.isEquals() == false)
			{
				//((JTextArea)comp).setBackground(Color.orange);
			}
			//((JTextArea)comp).repaint();
			//((JTextArea)comp).addFocusListener(panel);
			((JTextArea) comp).getDocument().putProperty("owner", comp); //set the owner pour retrouver dans le listener
			((JTextArea) comp).getDocument().addDocumentListener(panel);

		} else if ((anot != null) && (anot.gui_type() == gui_type.TEXTPANE))
		{
			comp = new JTextPane();
			comp.setBorder(new EtchedBorder());
			((JTextPane) comp).setText((String) value);
			if (rm.isEquals() == false)
			{
				//((JTextPane)comp).setBackground(Color.orange);
			}
			//((JTextPane)comp).repaint();
			//((JTextArea)comp).addFocusListener(panel);
			((JTextPane) comp).getDocument().putProperty("owner", comp); //set the owner pour retrouver dans le listener
			((JTextPane) comp).getDocument().addDocumentListener(panel);

		} else if ((anot != null) && (anot.gui_type() == gui_type.COMBO))
		{
			comp = new JComboBox<Object>();
			((JComboBox<?>) comp).setEditable(false);
			Binding bind = panel.getBinding(rm.getOwnerclass(), varName);
			BindingEnum bindenum = panel.getBindingEnum(rm.getOwnerclass(), varName);
			// Y'a un binding (parce que ID material par exemple)
			if (bind != null)
			{
				for (int i = 0; i < bind.list_mats.size(); i++)
				{
					Identifiable o = (Identifiable) bind.list_mats.get(i);
					((JComboBox<Object>) comp).insertItemAt(o, i);
					if (rm.isEquals() == true)
						if (Long.parseLong("" + value) == o.getId())
							((JComboBox<?>) comp).setSelectedIndex(i);
				}
			} else if (bindenum != null)
			{
				for (int i = 0; i < bindenum.list_mats.length; i++)
				{
					Object o = bindenum.list_mats[i];
					((JComboBox<Object>) comp).insertItemAt(o, i);
					if (rm.isEquals() == true)
						if (Long.parseLong("" + value) == i + bindenum.getIndexoffset()) // + 1 d'imbecilerie de lbr5
							((JComboBox<?>) comp).setSelectedIndex(i);
				}
			} else
			// Ha non alors bete enum.
			{
				Object[] enums = returnType.getEnumConstants();
				if (enums == null)
					throw new Exception("Enumeration/Binding cannot be found for " + rm);
				for (int i = 0; i < enums.length; i++)
				{
					Object o = enums[i];
					((JComboBox<Object>) comp).insertItemAt(o, i);
					//if (rm.isEquals() == true) // CECI A ETE COMMENTE LE 23-12-2024
						if (value == o)
							((JComboBox<?>) comp).setSelectedIndex(i);
				}
			}

			if (rm.isEquals() == false)
			{
				//((JComboBox<?>) comp).setSelectedIndex(-1); // CECI A ETE COMMENTE LE 23-12-2024

			}
			((JComboBox<?>) comp).addActionListener(panel);
		} else
		{
			//JWColor col = (JWColor) Value;
			//component = new JColorChooserButton(col);
			if ((returnType == JWColor.class))
			{
				comp = new JColorChooserButton(((JWColor) value).getColor());
				//((JXDatePicker)comp).setDate((Date) value);
				if (rm.isEquals() == false)
				{
					//((JColorChooserButton)comp).setBackground(Color.orange);
				}
				// KOPKOK cause du foutu AddActionListener dans le construteur de JCOlorChooserButton
				// Il faut que je puisse rajouter le 2 eme listener mais qui s'execute bien apres l'action du jcolorchooserbutton
				// ((JColorChooserButton)component).addChangeListener(list);
				ActionListener[] e = ((JColorChooserButton) comp).getActionListeners();
				for (int i = 0; i < e.length; i++)
				{
					((JColorChooserButton) comp).removeActionListener(e[i]);
				}
				((JColorChooserButton) comp).addActionListener(panel);
				//	((JColorChooserButton) comp).addActionListener(this);
				for (int i = 0; i < e.length; i++)
				{
					((JColorChooserButton) comp).addActionListener(e[i]);
				}

			} else if ((returnType == Date.class))
			{
				comp = new JXDatePicker();

				((JXDatePicker) comp).getMonthView().setFlaggedDates(new Date());

				((JXDatePicker) comp).setDate((Date) value);
				if (rm.isEquals() == false)
				{
					//((JXDatePicker)comp).setBackground(Color.orange);
				}
				((JXDatePicker) comp).addActionListener(panel);
			} else if ((returnType == File.class))
			{
				comp = new FileChooser((File) value, anot.extension(), anot.isDirectoryOnly());
				//((FileChooser)comp).setDate((Date) value);
				if (rm.isEquals() == false)
				{
					//	((FileChooser)comp).setBackground(Color.orange);
					((FileChooser) comp).setText("Different files");
				}
				((FileChooser) comp).addActionListener(panel);
			} else if ((returnType == boolean.class) || (returnType == Boolean.class))
			{
				comp = new JCheckBox();

				((JCheckBox) comp).setSelected(Boolean.parseBoolean("" + value));
				if (rm.isEquals() == false)
				{
					//((JCheckBox)comp).setBackground(Color.orange);
				}
				((JCheckBox) comp).addActionListener(panel);
			} else
			{

				if ((anot != null) && (anot.gui_type() == gui_type.REGEXPFORMATTEDFIELD))
				{
					// TODO : bug si multiple selection avec different truc
					String regExp = anot.displayFormat(); // "#0.0###E0"

					RegexFormatter defaultFormat = new RegexFormatter(regExp);
					comp = new JFormattedTextField(defaultFormat);

					if ((rm.isEquals() == false) && (newSystem == false))
						((JTextField) comp).setText(valeur_differentes);
					else if (value == null)
						((JFormattedTextField) comp).setText("NULL values");
					else
					{
						if (value instanceof Number)
							((JFormattedTextField) comp).setValue(value);
						else
							((JFormattedTextField) comp).setText("" + value);
					}

				} else if ((anot != null) && (anot.gui_type() == gui_type.MASKFORMATTEDTEXTFIELD))
				{
					// TODO : bug si multiple selection avec different truc
					String displayFormat = anot.displayFormat(); // "#0.0###E0"

					MaskFormatter defaultFormat = new MaskFormatter(displayFormat);
					comp = new JFormattedTextField(defaultFormat);

					if ((rm.isEquals() == false) && (newSystem == false))
						((JTextField) comp).setText(valeur_differentes);
					else if (value == null)
						((JFormattedTextField) comp).setText("NULL values");
					else
					{
						if (value instanceof Number)
							((JFormattedTextField) comp).setValue(value);
						else
							((JFormattedTextField) comp).setText("" + value);
					}

				} else if ((anot != null) && (anot.gui_type() == gui_type.DECIMALFORMATTEDTEXTFIELD))
				//if ((editFormat.length()!=0) && (displayFormat.length()!=0))
				{
					String editFormat = anot.editFormat(); // "#0.0###"
					String displayFormat = anot.displayFormat(); // "#0.0###E0"
					//System.err.println("Display : "+displayFormat);
					//System.err.println("Edit : "+editFormat);

					NumberFormatter defaultFormat = new NumberFormatter(new DecimalFormat(displayFormat));
					NumberFormatter textFormatter = new NumberFormatter(new DecimalFormat(displayFormat));
					NumberFormatter editFormatter = new NumberFormatter(new DecimalFormat(editFormat));
					comp = new JFormattedTextFieldW(new DefaultFormatterFactory(defaultFormat, textFormatter, editFormatter));

					if ((rm.isEquals() == false) && (newSystem == false))
						((JTextField) comp).setText(valeur_differentes);
					else if (value == null)
						((JFormattedTextField) comp).setText("NULL values");
					else
					{
						if (value instanceof Number)
							((JFormattedTextField) comp).setValue(value);
						else
							((JFormattedTextField) comp).setText("" + value);
					}

				} else
				{
					comp = new JTextField();
					if (value != null)
					{
						((JTextField) comp).setText("" + value);

						// TODO : Pr avoir la virgule ca serait ptet pas plus mal.
						// Rajout du 27/01/2016.
						if ((value instanceof Number) && (formatTextFieldWithDotOnly == false))
						{
							//integer ou flottant ?	
							if ((value instanceof Long) || (value instanceof Integer))
								((JTextField) comp).setText("" + String.format("%d", value));
							else
								((JTextField) comp).setText("" + String.format("%f", value));
						}
					} else
						((JTextField) comp).setText("NULL values");
				}

				if ((rm.isEquals() == false) && (newSystem == false))
				{
					//	((JTextField)comp).setBackground(Color.orange);
					((JTextField) comp).setText(valeur_differentes);
				}
				((JTextField) comp).addActionListener(panel);
				/**
				 * ADDITION MAURICIO 20-09-2023
				 * Remove the comment if something weird ...
				 */
				//if (newSystem == false) // -> WARNING Sinon ca pose des probleme si on clique dans la case puis qu'on perds le focus ca set les valeurs.
					((JTextField) comp).addFocusListener(panel);
			}
		}

		if (rm.isEquals() == false)
			comp.setBackground(Color.orange);

		comp.setEnabled(true);
		if ((rm.getHasSet() == null) || (readOnly == true))
			comp.setEnabled(false);
		comp.setName(varName);
		return comp;

	}

	/**
	 * @param jFrameTreeView
	 */
	public void removeFrameTreeView(JFrameTreeView jFrameTreeView)
	{
		this.FrameTreeViews.remove(jFrameTreeView);
	}

	/**
	 * @param jFrameTreeView
	 */
	public void addFrameTreeView(JFrameTreeView jFrameTreeView)
	{
		this.FrameTreeViews.add(jFrameTreeView);
	}

	/**
	 * @param string
	 * @throws JDOMException
	 * @throws IOException
	 */
	public void LoadLabel(String string) throws Exception
	{
		if (string != null)
		{
			this.XMLLabelFile = string;
			GUI2XMLLabel.LoadLabel(this, string);
		}
	}

	private static void AssigneLayoutToPanel(JPanel panel, PROPERTY_MIGLAYOUT class_layout_annotation) throws Exception
	{

		MigLayout ml = new MigLayout(class_layout_annotation.LayoutConstraints(), class_layout_annotation.ColumnConstraints(), class_layout_annotation.RowConstraints());
		panel.setLayout(ml);
	}

	@Override
	public int hashCode()
	{
		return id;
	}

	@Override
	public String toString()
	{
		return getClass().getName() + ":" + hashCode();
	}

	/**
	 * Listener pour le changement d'un eventuel JSlider
	 */
	@Override
	public void stateChanged(ChangeEvent arg0)
	{
		System.err.println("ARG564 = " + arg0);
		actionPerformed(new ActionEvent(arg0.getSource(), 0, "Sliderchange"));

	}

	/*
	 * (non-Javadoc)
	 * @see
	 * java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
	 */
	@Override
	public void itemStateChanged(ItemEvent arg0)
	{
		//DateExComboBox ex = arg0.getSource();
		if (arg0.getStateChange() == ItemEvent.DESELECTED)
			actionPerformed(new ActionEvent(arg0.getSource(), 0, "ItemStateChanged"));
	}

	/*
	 * (non-Javadoc)
	 * @see java.awt.event.FocusListener#focusGained(java.awt.event.FocusEvent)
	 */
	@Override
	public void focusGained(FocusEvent e)
	{
		
		/**
		 * ADDITION MAURICIO 20-09-2023
		 * Remove the comment if something weird ...
		 * Normalement ceci devrait pas poser de problèmes
		 */
		Component comp = e.getComponent();
		if (comp instanceof JTextField)
		{
			((JTextField)comp).selectAll();
		}
		
	}

	/*
	 * (non-Javadoc)
	 * @see java.awt.event.FocusListener#focusLost(java.awt.event.FocusEvent)
	 */
	@Override
	public void focusLost(FocusEvent arg0)
	{
		//System.err.println("Lost "+arg0);
		actionPerformed(new ActionEvent(arg0.getSource(), 0, "FocusLost"));
	}

	/*
	 * (non-Javadoc)
	 * @see javax.swing.event.DocumentListener#insertUpdate(javax.swing.event.
	 * DocumentEvent)
	 */
	@Override
	public void insertUpdate(DocumentEvent e)
	{
		Object owner = e.getDocument().getProperty("owner");
		actionPerformed(new ActionEvent(owner, 0, "Documentchanged"));
	}

	/*
	 * (non-Javadoc)
	 * @see javax.swing.event.DocumentListener#removeUpdate(javax.swing.event.
	 * DocumentEvent)
	 */
	@Override
	public void removeUpdate(DocumentEvent e)
	{

		Object owner = e.getDocument().getProperty("owner");
		actionPerformed(new ActionEvent(owner, 0, "Documentchanged"));
	}

	/*
	 * (non-Javadoc)
	 * @see javax.swing.event.DocumentListener#changedUpdate(javax.swing.event.
	 * DocumentEvent)
	 */
	@Override
	public void changedUpdate(DocumentEvent e)
	{
		Object owner = e.getDocument().getProperty("owner");
		actionPerformed(new ActionEvent(owner, 0, "Documentchanged"));
	}

	/**
	 * @param jp
	 * @return
	 */
	public static JFrame embbedInFrame(JPanelMagique jp)
	{
		JFrame fr = new JFrame();
		fr.setSize(640, 480);
		fr.add(new JScrollPane(jp), BorderLayout.CENTER);
		fr.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		return fr;
	}

	/**
	 * @param plugin1
	 */
	public static void registerPlugin(OBJ2GUIPlug<?, ?> plugin)
	{
		if (map_plugins.containsKey(plugin.getType()))
			log.info("Plugin already registered : " + plugin.getType());
		else
			map_plugins.put(plugin.getType(), plugin);
	}

	public static void registerPlugin2(OBJ2GUIPlugExtended<?, ?> plugin)
	{
		map_plugins2.add(plugin);
	}

	public static OBJ2GUIPlug<?, ?> getPlugin(Class<?> cls)
	{
		return map_plugins.get(cls);
	}	
	public static OBJ2GUIPlugExtended<?, ?> getPlugin2(Class<?> cls, Class<? extends Object> userTargetClass, String varName)
	{
		
		for (int i = 0; i < map_plugins2.size(); i++) {
			OBJ2GUIPlugExtended plug = map_plugins2.get(i);
			
			
			if ((plug.getType() == cls) || (sontClassesEquivalentes(plug.getType(), cls)))
				//if (plug.getUserTargetClass()==userTargetClass) // Modification 17-12-2024
				if (plug.getUserTargetClass().isAssignableFrom(userTargetClass))					
					if (plug.getUserTargetVariable().equalsIgnoreCase(varName))
				{
					
					return plug;
				}
		}
		
		return null;
	}
	
	 private static final Map<Class<?>, Class<?>> PRIMITIVE_TO_WRAPPER = new HashMap<>();
	    private static final Map<Class<?>, Class<?>> WRAPPER_TO_PRIMITIVE = new HashMap<>();

	    static {
	        PRIMITIVE_TO_WRAPPER.put(int.class, Integer.class);
	        PRIMITIVE_TO_WRAPPER.put(double.class, Double.class);
	        PRIMITIVE_TO_WRAPPER.put(boolean.class, Boolean.class);
	        PRIMITIVE_TO_WRAPPER.put(char.class, Character.class);
	        PRIMITIVE_TO_WRAPPER.put(long.class, Long.class);
	        PRIMITIVE_TO_WRAPPER.put(float.class, Float.class);
	        PRIMITIVE_TO_WRAPPER.put(short.class, Short.class);
	        PRIMITIVE_TO_WRAPPER.put(byte.class, Byte.class);

	        // Inverse mapping for wrapper to primitive
	        for (Map.Entry<Class<?>, Class<?>> entry : PRIMITIVE_TO_WRAPPER.entrySet()) {
	            WRAPPER_TO_PRIMITIVE.put(entry.getValue(), entry.getKey());
	        }
	    }

	    public static boolean sontClassesEquivalentes(Class<?> classe1, Class<?> classe2) {
	        if (classe1.equals(classe2)) {
	            return true; // Même classe
	        }
	        // Comparaison après conversion
	        return (PRIMITIVE_TO_WRAPPER.getOrDefault(classe1, classe1).equals(PRIMITIVE_TO_WRAPPER.getOrDefault(classe2, classe2)) ||
	                WRAPPER_TO_PRIMITIVE.getOrDefault(classe1, classe1).equals(WRAPPER_TO_PRIMITIVE.getOrDefault(classe2, classe2)));
	    }
	

	public static OBJ2GUIPlug<?, ?> getPlugin(JComponent component)
	{
		Set<Entry<Class<?>, OBJ2GUIPlug<?, ?>>> set = map_plugins.entrySet();
		for (Iterator<Entry<Class<?>, OBJ2GUIPlug<?, ?>>> iterator = set.iterator(); iterator.hasNext();)
		{
			//Entry<Class<?>, OBJ2GUIPlug<?, ?>> entry = iterator.next();
			for (Iterator<Entry<Class<?>, OBJ2GUIPlug<?, ?>>> iterator2 = set.iterator(); iterator2.hasNext();)
			{
				Entry<Class<?>, OBJ2GUIPlug<?, ?>> entry2 = iterator2.next();
				OBJ2GUIPlug<?, ?> plugin = entry2.getValue();
				if (plugin.getComponent() == component.getClass())
				{
					System.err.println("TU");
				}
			}
		}
		return null;
	}
	public static OBJ2GUIPlugExtended<?, ?> getPlugin2(JComponent component)
	{/*
		Set<Entry<Class<?>, OBJ2GUIPlug2<?, ?>>> set = map_plugins2.entrySet();
		for (Iterator<Entry<Class<?>, OBJ2GUIPlug2<?, ?>>> iterator = set.iterator(); iterator.hasNext();)
		{
			//Entry<Class<?>, OBJ2GUIPlug<?, ?>> entry = iterator.next();
			for (Iterator<Entry<Class<?>, OBJ2GUIPlug2<?, ?>>> iterator2 = set.iterator(); iterator2.hasNext();)
			{
				Entry<Class<?>, OBJ2GUIPlug2<?, ?>> entry2 = iterator2.next();
				OBJ2GUIPlug2<?, ?> plugin = entry2.getValue();
				if (plugin.getComponent() == component.getClass())
				{
					System.err.println("TU");
				}
			}
		}*/
		return null;
	}

	public static void removePlugin(Class<?> cls)
	{
		map_plugins.remove(cls);
	}

	public static void removePlugin2(Class<?> cls)
	{
		map_plugins2.remove(cls);
	}

	public static void clearPlugins(Class<?> cls)
	{
		map_plugins.clear();
	}
	public static void clearPlugins2(Class<?> cls)
	{
		map_plugins2.clear();
	}

	// TODO : Un convertisseur comme dans JIDE qui permettrait par exemple de tapper 1 et le convertisseur retournerai un objet d'un autre type

	// BETA : Validators;

	public static Map<Class<?>, Map<Method, Validator<?, ?>>> map_Validator = new HashMap<>();

	/**
	 * TODO : Ouais, mais Method, si on refactor, le programme va kister apres
	 * faudra remodifier etc....
	 * 
	 * @param setMethod
	 * @param validatorImpl
	 */
	public static <T, R> void registerValidator(Method setMethod, Validator<?, ?> validatorImpl)
	{
		// Cherche si un truc existe déjà pour l'objet en question;

		Class<?> parentClass = setMethod.getDeclaringClass();

		Map<Method, Validator<?, ?>> exi = map_Validator.get(parentClass);

		if (exi == null)
		{
			exi = new HashMap<>();
			map_Validator.put(parentClass, exi);
		}
		exi.put(setMethod, validatorImpl);

	}

	/**
	 * @param  
	 * @param <T>
	 * @param <R>
	 * @param class1
	 * @param object
	 * @param value
	 * @throws ValidationException
	 */
	private static Object validateValue(Object object, Method setMeth, Object oldValue, Object value) throws ValidationException
	{
		Class<?> parentClass = setMeth.getDeclaringClass();
		Map<Method, Validator<?, ?>> map_func = map_Validator.get(parentClass);

		// Pas de validateur donc c'est true....
		if (map_func == null)
			return value;
		
		Validator validateur = map_func.get(setMeth);

		if (validateur != null)
		{
			System.err.println(parentClass + " MUST VALIDE SOMETHING SOMEWHERE with value = " + value + " for method = " + setMeth);
			// TODO : Je me demande s'il ne faudrait pas avoir aussi la OldValue au cas ou ... ça pourrait être interessant.
			return validateur.valideValue(object, oldValue, value);
		}
		return value;
		
	}

	// Pour les label des bordures en fct du nom de la classe

	static Map<Class<?>, String>				map_title_border			= new HashMap<>();
	static Map<Class<?>, Map<String, String>>	map_title_border_for_list	= new HashMap<>();

	public static void registerTitleBorder(Class<?> cls, String label)
	{
		map_title_border.put(cls, label);
	}

	public static String getRegisteredTitleBorder(Class<?> cls)
	{
		String val = map_title_border.get(cls);
		if (val == null)
			return cls.getSimpleName();
		return val;
	}

	// Pour les label des bordures quand il s'agit d'une liste

	/**
	 * @param class1
	 * @param listvariableName
	 * @param string2
	 * @throws Exception
	 */
	public static void registerTitleBorderForList(Class<?> class1, String listvariableName, String LeLabel) throws Exception
	{
		// Detecte si on trouve bien la variable listvariableName dans la classe.
		Field f = null;

		Class<?> class1tmp = class1;

		while (f == null)
		{
			try
			{
				f = class1tmp.getDeclaredField(listvariableName);
			} catch (NoSuchFieldException | SecurityException e)
			{

				//throw new Exception(msg);
				class1tmp = class1tmp.getSuperclass();
				if (class1tmp == null)
				{
					String msg = "I cannot found variable " + listvariableName + " inside class " + class1.getName() + " or from parent classes";
					log.fatal(msg);
					throw new Exception(msg);
				}
			}
		}


		// Detecte si on est bien sur un type "List".
		if ( ((f.getType().isAssignableFrom(List.class)) == false) && (Collection.class.isAssignableFrom(f.getType())==false))
		//if ((f.getType().isAssignableFrom(List.class)) == false)	
		{
			String msg = "I found variable " + listvariableName + " inside class " + class1.getName() + " but it's not a List";
			log.fatal(msg);
			throw new Exception(msg);
		}

		Map<String, String> m = map_title_border_for_list.get(class1);
		if (m == null)
		{
			m = new HashMap<String, String>();
			map_title_border_for_list.put(class1, m);
		}
		m.put(listvariableName, LeLabel);
	}

	public static String getRegisterTitleBorderForList(Class<?> class1, String listvariableName) throws Exception
	{
		Map<String, String> mcls = map_title_border_for_list.get(class1);
		if (mcls == null)
		{
			
			String str = String.format("JPanelMagique.registerTitleBorderForList(%s, \"%s\", \"%s\");", class1, listvariableName, "The text you want" );
			log.fatal("map_title_border_for_list doesn't contains registered class " + class1+ " for the variable list named : "+listvariableName+ " --- "+str);
			throw new Exception("map_title_border_for_list doesn't contains registered class " + class1+ " for the variable list named : "+listvariableName+ " --- "+str);
		}
		String ret = mcls.get(listvariableName);
		if (ret == null)
		{
			log.fatal("map_title_border_for_list doesn't contains key for " + listvariableName + " on class "+class1);
			throw new Exception("map_title_border_for_list doesn't contains key for " + listvariableName + " on class "+class1);
		}
		return ret;
	}

	/**
	 * @return the tEXT_BOUTON_VIEW_EDIT
	 */
	public static synchronized String getTEXT_BOUTON_VIEW_EDIT()
	{
		return TEXT_BOUTON_VIEW_EDIT;
	}

	/**
	 * @param tEXT_BOUTON_VIEW_EDIT
	 *            the tEXT_BOUTON_VIEW_EDIT to set
	 */
	public static synchronized void setTEXT_BOUTON_VIEW_EDIT(String tEXT_BOUTON_VIEW_EDIT)
	{
		TEXT_BOUTON_VIEW_EDIT = tEXT_BOUTON_VIEW_EDIT;
	}

	/**
	 * @return the tEXT_BOUTON_ADD_ELEMENT
	 */
	public static synchronized String getTEXT_BOUTON_ADD_ELEMENT()
	{
		return TEXT_BOUTON_ADD_ELEMENT;
	}

	/**
	 * @param tEXT_BOUTON_ADD_ELEMENT
	 *            the tEXT_BOUTON_ADD_ELEMENT to set
	 */
	public static synchronized void setTEXT_BOUTON_ADD_ELEMENT(String tEXT_BOUTON_ADD_ELEMENT)
	{
		TEXT_BOUTON_ADD_ELEMENT = tEXT_BOUTON_ADD_ELEMENT;

	}

	/**
	 * @return the tEXT_BOUTON_REMOVE_ELEMENT
	 */
	public static synchronized String getTEXT_BOUTON_REMOVE_ELEMENT()
	{
		return TEXT_BOUTON_REMOVE_ELEMENT;
	}

	/**
	 * @param tEXT_BOUTON_REMOVE_ELEMENT
	 *            the tEXT_BOUTON_REMOVE_ELEMENT to set
	 */
	public static synchronized void setTEXT_BOUTON_REMOVE_ELEMENT(String tEXT_BOUTON_REMOVE_ELEMENT)
	{
		TEXT_BOUTON_REMOVE_ELEMENT = tEXT_BOUTON_REMOVE_ELEMENT;

	}

	/**
	 * @return the iMG_BOUTON_VIEW_EDIT
	 */
	public static synchronized Icon getIMG_BOUTON_VIEW_EDIT()
	{
		return IMG_BOUTON_VIEW_EDIT;
	}

	/**
	 * @param iMG_BOUTON_VIEW_EDIT
	 *            the iMG_BOUTON_VIEW_EDIT to set
	 */
	public static synchronized void setIMG_BOUTON_VIEW_EDIT(Icon iMG_BOUTON_VIEW_EDIT)
	{
		IMG_BOUTON_VIEW_EDIT = iMG_BOUTON_VIEW_EDIT;

	}

	/**
	 * @return the iMG_BOUTON_ADD_ELEMENT
	 */
	public static synchronized Icon getIMG_BOUTON_ADD_ELEMENT()
	{
		return IMG_BOUTON_ADD_ELEMENT;
	}

	/**
	 * @param iMG_BOUTON_ADD_ELEMENT
	 *            the iMG_BOUTON_ADD_ELEMENT to set
	 */
	public static synchronized void setIMG_BOUTON_ADD_ELEMENT(Icon iMG_BOUTON_ADD_ELEMENT)
	{
		IMG_BOUTON_ADD_ELEMENT = iMG_BOUTON_ADD_ELEMENT;

	}

	/**
	 * @return the iMG_BOUTON_REMOVE_ELEMENT
	 */
	public static synchronized Icon getIMG_BOUTON_REMOVE_ELEMENT()
	{
		return IMG_BOUTON_REMOVE_ELEMENT;
	}

	/**
	 * @param iMG_BOUTON_REMOVE_ELEMENT
	 *            the iMG_BOUTON_REMOVE_ELEMENT to set
	 */
	public static synchronized void setIMG_BOUTON_REMOVE_ELEMENT(Icon iMG_BOUTON_REMOVE_ELEMENT)
	{
		IMG_BOUTON_REMOVE_ELEMENT = iMG_BOUTON_REMOVE_ELEMENT;

	}

	/**
	 * @return the tEXT_TOOLTIP_BOUTON_VIEW_EDIT
	 */
	public static synchronized String getTEXT_TOOLTIP_BOUTON_VIEW_EDIT()
	{
		return TEXT_TOOLTIP_BOUTON_VIEW_EDIT;
	}

	/**
	 * @param tEXT_TOOLTIP_BOUTON_VIEW_EDIT
	 *            the tEXT_TOOLTIP_BOUTON_VIEW_EDIT to set
	 */
	public static synchronized void setTEXT_TOOLTIP_BOUTON_VIEW_EDIT(String tEXT_TOOLTIP_BOUTON_VIEW_EDIT)
	{
		TEXT_TOOLTIP_BOUTON_VIEW_EDIT = tEXT_TOOLTIP_BOUTON_VIEW_EDIT;

	}

	/**
	 * @return the tEXT_TOOLTIP_BOUTON_ADD_ELEMENT
	 */
	public static synchronized String getTEXT_TOOLTIP_BOUTON_ADD_ELEMENT()
	{
		return TEXT_TOOLTIP_BOUTON_ADD_ELEMENT;
	}

	/**
	 * @param tEXT_TOOLTIP_BOUTON_ADD_ELEMENT
	 *            the tEXT_TOOLTIP_BOUTON_ADD_ELEMENT to set
	 */
	public static synchronized void setTEXT_TOOLTIP_BOUTON_ADD_ELEMENT(String tEXT_TOOLTIP_BOUTON_ADD_ELEMENT)
	{
		TEXT_TOOLTIP_BOUTON_ADD_ELEMENT = tEXT_TOOLTIP_BOUTON_ADD_ELEMENT;

	}

	/**
	 * @return the tEXT_TOOLTIP_BOUTON_REMOVE_ELEMENT
	 */
	public static synchronized String getTEXT_TOOLTIP_BOUTON_REMOVE_ELEMENT()
	{
		return TEXT_TOOLTIP_BOUTON_REMOVE_ELEMENT;
	}

	/**
	 * @param tEXT_TOOLTIP_BOUTON_REMOVE_ELEMENT
	 *            the tEXT_TOOLTIP_BOUTON_REMOVE_ELEMENT to set
	 */
	public static synchronized void setTEXT_TOOLTIP_BOUTON_REMOVE_ELEMENT(String tEXT_TOOLTIP_BOUTON_REMOVE_ELEMENT)
	{
		TEXT_TOOLTIP_BOUTON_REMOVE_ELEMENT = tEXT_TOOLTIP_BOUTON_REMOVE_ELEMENT;

	}

}
