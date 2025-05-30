package io.github.warnotte.obj2gui2.experimentOBJ2GUIPlugin3;

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
import io.github.warnotte.obj2gui2.Tests.SimpleType.Material_;

public class MaterialIDCombobox extends OBJ2GUI2Plug3Experiment<Integer, JComboBox, Flotteur__> {

	public MaterialIDCombobox() {
		super(Flotteur__::getIdMaterial);
	}

	@Override
	protected JComboBox build(Integer value, Object parent, Method getter, Method setter, JPanelMagique panel_magique_parent, boolean dummy) {
		ComboBoxModel		model		= new DefaultComboBoxModel() {
											@Override
											public int getSize() {
												return mainPluginTest.list_mats2.size();
											}

											@Override
											public Object getElementAt(int index) {
												return mainPluginTest.list_mats2.get(index).getId();
											}

										};
		JComboBox<Integer>	component	= new JComboBox<Integer>(model);
		
		component.setRenderer(new ListCellRenderer<Integer>()  {

			@Override
			public Component getListCellRendererComponent(JList<? extends Integer> list, Integer value, int index, boolean isSelected, boolean cellHasFocus) {
				
				if (value==null)
					return new JLabel("--");
				int id_material = value;
				Material_ mat = mainPluginTest.list_mats2.stream().filter(e -> e.getId() == id_material).collect(Collectors.toList()).get(0);
				JLabel label = new JLabel(mat.getId()+") "+mat.getNamed());
				label.setOpaque(true);
				if (isSelected) {
					label.setBackground(list.getSelectionBackground());
					label.setForeground(list.getSelectionForeground());
		        } else {
		        	label.setBackground(list.getBackground());
		        	label.setForeground(list.getForeground());
		        }
			 
				return label;
			}
			
		});
		
		component.setSelectedItem(value);
		component.addFocusListener(panel_magique_parent);
		component.addActionListener(panel_magique_parent);
		return component;
	}

	@Override
	protected Integer getValue(JComboBox component, boolean dummy) {
		System.err.println(component.getName() + " GETVALUE");
		return (Integer) component.getSelectedItem();
	}

	@Override
	public void refresh(Integer value, JComboBox component, boolean dummy) {
		System.err.println(component.getName() + " REFRESH : " + value);
			component.setSelectedItem(value);
	}

}
