package io.github.warnotte.obj2gui2.experimentOBJ2GUIPlugin3;

import java.awt.Color;
import java.lang.reflect.Method;

import javax.swing.JTextField;

import io.github.warnotte.obj2gui2.JPanelMagique;

public class RenderedTextFieldValue extends OBJ2GUI2Plug3Experiment<Double, JTextField, Flotteur__> {

	JTextField component = new JTextField();
	Color coldefault;
	
	public RenderedTextFieldValue() {
		super(Flotteur__::getValeur);
		coldefault = component.getBackground();
		
	}

	@Override
	protected JTextField build(Double value, Object parent, Method setter, Method method, JPanelMagique panel_magique_parent, boolean dummy) {
		component.setText(""+value);
		component.addFocusListener(panel_magique_parent);
		component.addActionListener(panel_magique_parent);
		changeRendering(value);
		return component;
	}

	@Override
	protected Double getValue(JTextField component, boolean dummy) {
		return Double.parseDouble(component.getText());
	}

	@Override
	public void refresh(Double value, JTextField component, boolean dummy) {
		component.setText(""+value);
		
		changeRendering(value);
		
		
	}

	private void changeRendering(double value) {

		component.setBackground(coldefault);
		if (value>1.0)
			component.setBackground(Color.RED);
		
	}

}
