package io.github.warnotte.obj2gui2.experimentOBJ2GUIPlugin3;

import java.lang.reflect.Method;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JTextField;

import io.github.warnotte.obj2gui2.JPanelMagique;
import io.github.warnotte.obj2gui2.Tests.SimpleType.Material;

public class MaterialCombobox extends OBJ2GUI2Plug3Experiment<Material, JComboBox, Flotteur> {

	public MaterialCombobox() {
		super(Flotteur::getMaterial);
	}

	@Override
	protected JComboBox build(Material value, Object parent, Method getter, Method setter, JPanelMagique panel_magique_parent, boolean dummy) {
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
		JComboBox<Material>	component	= new JComboBox<Material>(model);
		component.setSelectedItem(value);
		component.addFocusListener(panel_magique_parent);
		component.addActionListener(panel_magique_parent);
		return component;
	}

	@Override
	protected Material getValue(JComboBox component, boolean dummy) {
		System.err.println(component.getName() + " GETVALUE");

		return (Material) component.getSelectedItem();
	}

	@Override
	public void refresh(Material value, JComboBox component, boolean dummy) {
		{
			System.err.println(component.getName() + " REFRESH : " + value);

			component.setSelectedItem(value);
		}

	}

}
