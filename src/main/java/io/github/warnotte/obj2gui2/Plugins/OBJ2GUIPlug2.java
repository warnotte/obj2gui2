package io.github.warnotte.obj2gui2.Plugins;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JComponent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.github.warnotte.obj2gui2.JPanelMagique;

public abstract class OBJ2GUIPlug2<T, U>{
	
	protected static final Logger			logger		= LogManager.getLogger(OBJ2GUIPlug2.class);
	
	private Class<T>	type; // Le type de la va
	private Class<U>	component;
	
	// Ceci pourrait permettre de ne cible que certaines classe user et certaines m�thodes.
	// Du genre je vx appliquer le Datechoooser magique a l'objet Task methode getStart();
	
	private Class/*<T>*/ 		userTargetClass; // La classe "Chaussure" par exemple.
	private String 		userTargetVariable; // La classe "Chaussure" par exemple.
	
	
	public OBJ2GUIPlug2(Class/*<T>*/ userTargetClass, String userTargetVariable) {
		this.type = (Class<T>) ((java.lang.reflect.ParameterizedType)this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		this.component = (Class<U>) ((java.lang.reflect.ParameterizedType)this.getClass().getGenericSuperclass()).getActualTypeArguments()[1];
		this.userTargetVariable = userTargetVariable;
		this.userTargetClass = userTargetClass;
		
		// Check if userTargetVariable exists.
		/*try {
			// Peut-être faudra il utiliser la methode getFields() de JPanelMagique ???
			userTargetClass.getDeclaredField(userTargetVariable);			
		} catch (NoSuchFieldException | SecurityException e) {
			logger.fatal(e, e);
			e.printStackTrace();
		}*/
		
		boolean isOk = isFieldExist(userTargetClass, userTargetVariable);
		if (isOk==false)
		{
			logger.fatal("Field "+userTargetVariable+" doesn't exist in "+userTargetClass);
			
		}
		
	}

	private OBJ2GUIPlug2() {
		//System.err.println("Class = "+this.type.getName());
		//System.err.println("component = "+this.component.getName());
   }
	
	
	
	static boolean isFieldExist(Class<?> classType, String fieldName)
	{
		Field[] fields = JPanelMagique.getAllFields(classType);
		for (int i = 0; i < fields.length; i++) {
			if (fields[i].getName().equalsIgnoreCase(fieldName))
				return true;
		}
		
		return false;
	}
	
	/**
	 * Crée un composant avec la value, et ajoute un listener (a implementer toi même);
	 * @param value
	 * @param panel_parent
	 * @return
	 */
	public U build(Object value, Object parent, Method getter, Method setter, JPanelMagique panel_magique_parent)
	{
		return build((T)value, parent, getter, setter, panel_magique_parent, true);
	}
	
	protected abstract U build(T value, Object parent, Method setter, Method method, JPanelMagique panel_magique_parent, boolean dummy);
	
	public U getValue(Object component)
	{
		return (U) getValue((U)component, true);
		// return null;
	}
	
	protected abstract T getValue(U component, boolean dummy);

	/**
	 * Set the value to the component
	 * @param value
	 * @param component
	 */
	public abstract void refresh(T value, U component, boolean dummy);
	
	public void refresh(Object value, JComponent component)
	{
		refresh((T)value,(U) component, true);
		// return null;
	}
	
	
	/**
	 * @return the type
	 */
	public synchronized Class<T> getType()
	{
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public synchronized void setType(Class<T> type)
	{
		this.type = type;
	
	}

	/**
	 * @return the component
	 */
	public synchronized Class<U> getComponent()
	{
		return component;
	}

	/**
	 * @param component the component to set
	 */
	public synchronized void setComponent(Class<U> component)
	{
		this.component = component;
	
	}

	public Class getUserTargetClass() {
		return userTargetClass;
	}

	public void setUserTargetClass(Class userTargetClass) {
		this.userTargetClass = userTargetClass;
	}

	public String getUserTargetVariable() {
		return userTargetVariable;
	}

	public void setUserTargetVariable(String userTargetVariable) {
		this.userTargetVariable = userTargetVariable;
	}
	

}
