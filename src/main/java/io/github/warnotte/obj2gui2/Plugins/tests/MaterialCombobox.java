package io.github.warnotte.obj2gui2.Plugins.tests;

import java.awt.Component;
import java.lang.reflect.Method;
import java.util.stream.Collectors;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import io.github.warnotte.obj2gui2.JPanelMagique;
import io.github.warnotte.obj2gui2.Plugins.OBJ2GUIPlugExtended;
import io.github.warnotte.obj2gui2.Tests.SimpleType.Material;

public class MaterialCombobox extends OBJ2GUIPlugExtended<Integer, JComboBox> {

	public MaterialCombobox(Class userTargetClass, String userTargetVariable) {
		super(userTargetClass, userTargetVariable);

	}
	@Override
	protected JComboBox build(Integer value, Object parent, Method setter, Method method, JPanelMagique panel_magique_parent, boolean dummy) {
		ComboBoxModel model = new DefaultComboBoxModel() {
			/* (non-Javadoc)
			 * @see javax.swing.ListModel#getSize()
			 */
			@Override
			public int getSize()
			{
				return mainPluginTest.list_mats2.size();
			}

			/* (non-Javadoc)
			 * @see javax.swing.ListModel#getElementAt(int)
			 */
			@Override
			public Object getElementAt(int index)
			{
				return mainPluginTest.list_mats2.get(index).getId();
			}

		};
		JComboBox<Integer> component = new JComboBox<Integer>(model);
		component.setRenderer(new ListCellRenderer<Integer>() {

			@Override
			public Component getListCellRendererComponent(JList<? extends Integer> list, Integer value, int index, boolean isSelected, boolean cellHasFocus) {
				int id_material = value;
				Material mat = mainPluginTest.list_mats2.stream().filter(e -> e.getId() == id_material).collect(Collectors.toList()).get(0);
				return new JLabel(mat.getId()+") "+mat.getNamed());
			}
			
		});
		component.setSelectedItem(value);
		component.addFocusListener(panel_magique_parent);
		component.addActionListener(panel_magique_parent);
		return component;
	}

	@Override
	protected Integer getValue(JComboBox component, boolean dummy) {
		return (Integer) component.getSelectedItem();
	}

	@Override
	public void refresh(Integer value, JComboBox component, boolean dummy) {
		component.setSelectedItem(value);
		
	}
}
