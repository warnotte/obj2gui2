package io.github.warnotte.obj2gui2;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Warnotte Renaud
 */
public class JPanel_MultiPropsEditor extends JPanel implements MyEventListener
{
	protected static final Logger	Logger				= LogManager.getLogger("Panel_MultiPropsEditor");
	/**
	 * 
	 */
	private static final long		serialVersionUID	= 8124954700368638697L;
	private JPanel					panel_boutons;
	JPanel							noselectionpanel;
	CardLayout						cardlayout;
	JPanel							centre;
	JScrollPane						scrollPane;
	private List<?>					selection			= new ArrayList<>();

	boolean isRecursive = true;

	List<Binding>		binds					= null;
	List<BindingEnum>	bindsEnum				= null;
	boolean				OnlyAnnotatedMethods	= true;
	boolean				showTreeViewButtons		= false;

	public static String text_nothing_selected = "Nothing selected";

	/**
	 * @param isRecursive
	 * @param binds
	 * @param bindsEnum
	 * @param onlyAnnotatedMethods
	 * @param showTreeViewButtons
	 */
	public JPanel_MultiPropsEditor(boolean isRecursive, List<Binding> binds, List<BindingEnum> bindsEnum, boolean onlyAnnotatedMethods, boolean showTreeViewButtons)
	{
		super();
		this.isRecursive = isRecursive;
		this.binds = binds;
		this.bindsEnum = bindsEnum;
		this.OnlyAnnotatedMethods = onlyAnnotatedMethods;
		this.showTreeViewButtons = showTreeViewButtons;
		initialize();
	}

	public JPanel_MultiPropsEditor(boolean isRecursive, List<Binding> binds, List<BindingEnum> bindsEnum)
	{
		super();
		this.isRecursive = isRecursive;
		this.binds = binds;
		this.bindsEnum = bindsEnum;
		initialize();
	}

	public JPanel_MultiPropsEditor(boolean isRecursive, List<Binding> binds)
	{
		super();
		this.isRecursive = isRecursive;
		this.binds = binds;
		initialize();

	}

	public JPanel_MultiPropsEditor(boolean isRecursive)
	{
		super();
		this.isRecursive = isRecursive;
		initialize();
	}

	/**
	 * Create the panel.
	 * 
	 * @param gui_ViewerMPX
	 */
	public JPanel_MultiPropsEditor(/* GUI_ViewerSimuloic gui_ViewerMPX */)
	{
		initialize();
	}

	private void initialize()
	{
		this.setLayout(new BorderLayout());
		//List<?> selection = new ArrayList<>();
		//selection.add(new TaskProperties(null));
		/*
		 * try { jm = JPanelMagique.GenerateJPanelFromSelectionAndBindings(null,
		 * selection, false, null, null, true, false); } catch (Exception e) {
		 * e.printStackTrace(); DialogDivers.Show_dialog(e,
		 * "Probleme suspect : "+e); Logs.getLogger().fatal(e, e); }
		 */

		//this.add(jm, BorderLayout.CENTER);

		centre = new JPanel();
		cardlayout = new CardLayout();
		noselectionpanel = new JPanel();
		noselectionpanel.setLayout(new java.awt.GridBagLayout());
		JLabel label_nothingselected = new JLabel(text_nothing_selected); //$NON-NLS-1$
		//label_nothingselected.setFont(new Font("Tahoma", Font.BOLD, 16)); //$NON-NLS-1$
		noselectionpanel.add(label_nothingselected);

		centre.setLayout(cardlayout);
		scrollPane = new JScrollPane();
		scrollPane.setName("ScrollPaneOfMultipropseditor");
		//scrollPane.setBackground(Color.BLUE);
		scrollPane.getVerticalScrollBar().setUnitIncrement(16);

		centre.add(scrollPane, "FULL"); //$NON-NLS-1$

		centre.add(noselectionpanel, "NONE"); //$NON-NLS-1$

		add(centre, BorderLayout.CENTER);

		//add(getPanel_boutons(), BorderLayout.SOUTH);

		cardlayout.show(centre, "NONE"); //$NON-NLS-1$
		getPanel_boutons().setVisible(false);
	}

	public void setTaskProperties(final List<?> selection)
	{
		/*
		 * if (this.selection.containsAll(selection)) {
		 * System.err.println("Meme objets qu'avant"); return; }
		 */

		this.setSelection(selection);
		if (selection == null)
			Logger.fatal("Ceci ne devrait pas arriver wax (654e6e5321e) - Selection vide. OSEF normalement..."); //$NON-NLS-1$

		if ((selection == null) || (selection.size() == 0))
		{
			cardlayout.show(centre, "NONE"); //$NON-NLS-1$
			getPanel_boutons().setVisible(false);
			return;
		} else
		{
			cardlayout.show(centre, "FULL"); //$NON-NLS-1$
			getPanel_boutons().setVisible(true);
		}

		JPanel panel_all = new JPanel();
		panel_all.setLayout(new BoxLayout(panel_all, BoxLayout.Y_AXIS));

		try
		{

			{
				Map<Class<?>, List<Object>>			map		= selection.stream().collect(Collectors.groupingBy(artist -> artist.getClass()));
				Set<Entry<Class<?>, List<Object>>>	sets	= map.entrySet();
				for (Iterator<?> iterator = sets.iterator(); iterator.hasNext();)
				{
					Entry<Class<?>, List<Object>>	entry	= (Entry<Class<?>, List<Object>>) iterator.next();
					JPanelMagique					jm		= JPanelMagique.GenerateJPanelFromSelectionAndBindings(null, entry.getValue(), isRecursive, binds, bindsEnum, OnlyAnnotatedMethods, showTreeViewButtons);
					jm.addMyEventListener(this);
					panel_all.add(jm);
				}
			}
			scrollPane.setViewportView(panel_all);
		} catch (Exception e1)
		{
			Logger.fatal(e1.getMessage(), e1);
			e1.printStackTrace();
		}
	}

	private JPanel getPanel_boutons()
	{
		if (panel_boutons == null)
		{
			panel_boutons = new JPanel();
			panel_boutons.add(new JButton("Dummy")); //$NON-NLS-1$

		}
		return panel_boutons;
	}

	/**
	 * This methods allows classes to register for MyEvents
	 */
	public void addMyEventListener(MyEventListener listener)
	{

		if (listener == null)
		{
			//log.fatal("Grave erreur ... 654");
			System.exit(-1);
		}
		listenerList.add(MyEventListener.class, listener);
	}

	/**
	 * 
	 */
	public void refresh()
	{
		JPanel panel_all = (JPanel) scrollPane.getViewport().getView();
		if (panel_all == null)
			return;
		int nbr = panel_all.getComponentCount();

		for (int i = 0; i < nbr; i++)
		{
			JPanelMagique jpm = (JPanelMagique) panel_all.getComponent(i);

			try
			{
				jpm.refresh();
			} catch (Exception e)
			{
				Logger.fatal(e, e);
				e.printStackTrace();
			}
		}

		/*
		 * try { setTaskProperties(selection); } catch (Exception e1) {
		 * e1.printStackTrace(); Logs.getLogger().fatal(e1, e1); }
		 */

	}

	/*
	 * (non-Javadoc)
	 * @see
	 * GuiGenerator.MyEventListener#myEventOccurred(GuiGenerator.MyChangedEvent)
	 */
	@Override
	public void myEventOccurred(MyChangedEvent evt)
	{
		for (int i = 0; i < listenerList.getListenerCount(); i += 2)
		{
			if (listenerList.getListenerList()[i] == MyEventListener.class)
			{
				((MyEventListener) listenerList.getListenerList()[i + 1]).myEventOccurred(evt);
			}
		}

	}

	public List<?> getSelection()
	{
		return selection;
	}

	public void setSelection(List<?> selection)
	{
		this.selection = selection;

	}

	/**
	 * @return the isRecursive
	 */
	public boolean isRecursive()
	{
		return isRecursive;
	}

	/**
	 * @param isRecursive
	 *            the isRecursive to set
	 */
	public void setRecursive(boolean isRecursive)
	{
		this.isRecursive = isRecursive;
		setTaskProperties(getSelection());
	}

	/**
	 * @return the binds
	 */
	public List<Binding> getBinds()
	{
		return binds;
	}

	/**
	 * @param binds
	 *            the binds to set
	 */
	public void setBinds(List<Binding> binds)
	{
		this.binds = binds;

	}

	/**
	 * @return the bindsEnum
	 */
	public List<BindingEnum> getBindsEnum()
	{
		return bindsEnum;
	}

	/**
	 * @param bindsEnum
	 *            the bindsEnum to set
	 */
	public void setBindsEnum(List<BindingEnum> bindsEnum)
	{
		this.bindsEnum = bindsEnum;

	}

	/**
	 * @return the onlyAnnotatedMethods
	 */
	public boolean isOnlyAnnotatedMethods()
	{
		return OnlyAnnotatedMethods;
	}

	/**
	 * @param onlyAnnotatedMethods
	 *            the onlyAnnotatedMethods to set
	 */
	public void setOnlyAnnotatedMethods(boolean onlyAnnotatedMethods)
	{
		OnlyAnnotatedMethods = onlyAnnotatedMethods;
		setTaskProperties(getSelection());
	}

	/**
	 * @return the showTreeViewButtons
	 */
	public boolean isShowTreeViewButtons()
	{
		return showTreeViewButtons;
	}

	/**
	 * @param showTreeViewButtons
	 *            the showTreeViewButtons to set
	 */
	public void setShowTreeViewButtons(boolean showTreeViewButtons)
	{
		this.showTreeViewButtons = showTreeViewButtons;
		setTaskProperties(getSelection());

	}

}