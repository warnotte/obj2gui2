package io.github.warnotte.obj2gui2.experimentOBJ2GUIPlugin3;

import java.lang.reflect.Method;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JTextField;

import io.github.warnotte.obj2gui2.JPanelMagique;

public class MaterialIDCombobox extends OBJ2GUI2Plug3Experiment<Integer, JComboBox, Flotteur> {

	public MaterialIDCombobox() {
		super(Flotteur::getIdMaterial);
	}

	@Override
	protected JComboBox build(Integer value, Object parent, Method setter, Method method, JPanelMagique panel_magique_parent, boolean dummy) {
		ComboBoxModel		model		= new DefaultComboBoxModel() {
											@Override
											public int getSize() {
												return mainPluginTest.list_mats2.size();
											}

											@Override
											public Object getElementAt(int index) {
												// Find the real good index, here i cheated
												return mainPluginTest.list_mats2.get(index);
											}

										};
		JComboBox<Integer>	component	= new JComboBox<Integer>(model);
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

		// if (value != null)
		// if (value.equals(component.getSelectedItem()) == false)
		{
			System.err.println(component.getName() + " REFRESH : " + value);

			component.setSelectedItem(value);
		}

	}

}
