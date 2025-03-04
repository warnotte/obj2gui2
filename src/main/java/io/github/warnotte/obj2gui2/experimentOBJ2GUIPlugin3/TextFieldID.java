package io.github.warnotte.obj2gui2.experimentOBJ2GUIPlugin3;

import java.lang.reflect.Method;

import javax.swing.JTextField;

import io.github.warnotte.obj2gui2.JPanelMagique;

public class TextFieldID extends OBJ2GUI2Plug3Experiment<String, JTextField, Flotteur> {

	public TextFieldID() {
		super(Flotteur::getID_ObjetSurFlotteur);
	}

	@Override
	protected JTextField build(String value, Object parent, Method setter, Method method, JPanelMagique panel_magique_parent, boolean dummy) {
		JTextField component = new JTextField();
		component.setText(value);
		component.addFocusListener(panel_magique_parent);
		component.addActionListener(panel_magique_parent);
		return component;
	}

	@Override
	protected String getValue(JTextField component, boolean dummy) {
		return component.getText();
	}

	@Override
	public void refresh(String value, JTextField component, boolean dummy) {
		component.setText(value);
	}

}
