/**
 * 
 */
package io.github.warnotte.obj2gui2.Plugins;

import java.lang.reflect.Method;

import javax.swing.JComponent;

import io.github.warnotte.obj2gui2.JPanelMagique;

/**
 * Class qui permet de faire en sorte que dans n'importe quelle classe si on une
 * variable type TimeSlot (par exemple), on assigne un composant speciale pour
 * editer TimeSlot genre TimeSlotPanel
 * 
 * @author Warnotte Renaud
 * @param <T> Type de la variable a representer
 * @param <U> Composant Swing
 */
public abstract class OBJ2GUIPlug<T, U> {
	// protected T class_target;
	// protected U component;

	private Class<T>	type;
	Method				method;
	private Class<U>	component;

	// Ceci pourrait permettre de ne cible que certaines classe user et certaines
	// méthodes.
	// Du genre je vx appliquer le Datechoooser magique a l'objet Task methode
	// getStart();
	/*
	 * private Class user_class_target; // La classe "Chaussure" par exemple.
	 * private Method user_method_target; // La méthode visée.
	 * 
	 * --> Voir OBJ2GUIPlug2
	 * 
	 */

	public OBJ2GUIPlug() {
		this.type = (Class<T>) ((java.lang.reflect.ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		this.component = (Class<U>) ((java.lang.reflect.ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[1];
		// System.err.println("Class = "+this.type.getName());
		// System.err.println("component = "+this.component.getName());
	}

	/**
	 * Crée un composant avec la value, et ajoute un listener (a implementer toi
	 * même);
	 * 
	 * @param value
	 * @param panel_parent
	 * @return
	 */
	public U build(Object value, Object parent, Method getter, Method setter, JPanelMagique panel_magique_parent) {
		return build((T) value, parent, getter, setter, panel_magique_parent, true);
	}

	protected abstract U build(T value, Object parent, Method setter, Method method, JPanelMagique panel_magique_parent, boolean dummy);

	public U getValue(Object component) {
		return (U) getValue((U) component, true);
		// return null;
	}

	protected abstract T getValue(U component, boolean dummy);

	/**
	 * Set the value to the component
	 * 
	 * @param value
	 * @param component
	 */
	public abstract void refresh(T value, U component, boolean dummy);

	public void refresh(Object value, JComponent component) {
		refresh((T) value, (U) component, true);
		// return null;
	}

	/**
	 * @return the type
	 */
	public synchronized Class<T> getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public synchronized void setType(Class<T> type) {
		this.type = type;

	}

	/**
	 * @return the component
	 */
	public synchronized Class<U> getComponent() {
		return component;
	}

	/**
	 * @param component the component to set
	 */
	public synchronized void setComponent(Class<U> component) {
		this.component = component;

	}

}
