package io.github.warnotte.obj2gui2.experimentOBJ2GUIPlugin3;

import java.io.Serializable;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import javax.swing.JComponent;
import javax.swing.JTextField;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.github.warnotte.obj2gui2.JPanelMagique;

/**
 * Classe abstraite générique pour expérimenter une extension par plugin d'OBJ2GUI2.
 *
 * Trois paramètres génériques : 
 * - T : type de la valeur (ex. String, Integer) 
 * - U : type du composant Swing utilisé (ex. JTextField, JComboBox) 
 * - V : classe cible sur laquelle opère le plugin (ex. Flotteur)
 *
 * Elle fournit un mécanisme d'extraction du nom d'un champ via une référence de méthode et définit un contrat (build, getValue, refresh) pour créer et gérer le composant.
 */
public abstract class OBJ2GUI2Plug3Experiment<T, U, V> {

	protected static final Logger	logger	= LogManager.getLogger(OBJ2GUI2Plug3Experiment.class);

	private Class<T>				type;															// Type de la valeur (ex. Integer)
	private Class<U>				component;														// Type du composant Swing (ex. JTextField)
	private Class<V>				userTargetClass;												// Classe cible (ex. Flotteur)
	private String					userTargetVariable;												// Nom du champ extrait, de sa méthode getters (ex. "getID_ObjetSurFlotteur")

	public static void main(String[] args) {
		// Création correcte de l'instance avec types explicites
		OBJ2GUI2Plug3Experiment<String, JTextField, Flotteur__> c = new OBJ2GUI2Plug3Experiment<>(Flotteur__::getID_ObjetSurFlotteur) {

			@Override
			protected JTextField build(String value, Object parent, Method setter, Method method, JPanelMagique panel_magique_parent, boolean dummy) {
				// Exemple simple : création d'un JTextField initialisé avec la valeur
				return new JTextField(value);
			}

			@Override
			protected String getValue(JTextField component, boolean dummy) {
				return component.getText();
			}

			@Override
			public void refresh(String value, JTextField component, boolean dummy) {
				component.setText(value);

			}

		};
	}

	// Constructeur générique
	public OBJ2GUI2Plug3Experiment(MethodReference<V, T> reference) {
		// Extraction des types génériques déclarés dans la sous-classe
		this.type = (Class<T>) ((java.lang.reflect.ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		this.component = (Class<U>) ((java.lang.reflect.ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[1];

		// Pour cet exemple, on fixe la classe cible à Flotteur
		// this.userTargetClass = (Class<V>) Flotteur.class;
		this.userTargetClass = (Class<V>) ((java.lang.reflect.ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[2];

		// Extraction du nom de la variable à partir de la référence de méthode (getter)
		String variableName = extractVariableNameFromGetterMethod(reference);
		this.userTargetVariable = variableName;
		//System.out.println("Nom de la variable : " + variableName);

		// Vérification que le champ existe dans la classe cible
		if (!isFieldExist(userTargetClass, userTargetVariable)) {
			logger.fatal("Field " + userTargetVariable + " doesn't exist in " + userTargetClass);
		}
	}

	@FunctionalInterface
	public interface MethodReference<V, T> extends Serializable {
		T apply(V v);
	}

	/**
	 * Extrait le nom du champ à partir d'une référence de méthode (getter) en utilisant SerializedLambda.
	 *
	 * @param <V>
	 * @param <T>
	 * @param reference
	 * @return
	 */
	private static <V, T> String extractVariableNameFromGetterMethod(MethodReference<V, T> reference) {
		try {
			// Accès au SerializedLambda via réflexion
			Method writeReplace = reference.getClass().getDeclaredMethod("writeReplace");
			writeReplace.setAccessible(true);
			SerializedLambda	lambda			= (SerializedLambda) writeReplace.invoke(reference);

			// Obtenir la classe cible et la méthode
			String				className		= lambda.getImplClass().replace('/', '.');
			String				implMethodName	= lambda.getImplMethodName();

			// Charger la classe cible
			Class<?>			clazz			= Class.forName(className);

			// Rechercher un champ correspondant
			Field[]				fields			= clazz.getDeclaredFields();
			for (Field field : fields) {
				field.setAccessible(true);
				// Comparer le nom du champ avec le nom de la méthode implémentée
				if (field.getName().equalsIgnoreCase(implMethodName)) {
					return field.getName();
				}
			}

			// Si aucune correspondance exacte, essayer de deviner à partir des méthodes
			if (implMethodName.startsWith("get") || implMethodName.startsWith("is")) {
				String guessedFieldName = Character.toLowerCase(implMethodName.charAt(3)) + implMethodName.substring(4);
				for (Field field : fields) {
					if (field.getName().equalsIgnoreCase(guessedFieldName)) {
						return field.getName();
					}
				}
			}

			throw new IllegalArgumentException("Aucun champ correspondant trouvé pour la méthode : " + implMethodName);
		} catch (Exception e) {
			throw new RuntimeException("Erreur lors de l'extraction du nom de variable", e);
		}
	}

	/**
	 * Vérifie l'existence d'un champ dans une classe donnée en utilisant JPanelMagique pour récupérer tous les champs.
	 */
	static boolean isFieldExist(Class<?> classType, String fieldName) {
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
	 * @param parent TODO : Je me demande a quoi ca sert au final ...
	 * @param getter TODO : Je me demande a quoi ca sert au final ...
	 * @param setter TODO : Je me demande a quoi ca sert au final ...
	 * @param panel_magique_parent TODO : Je me demande a quoi ca sert au final ...
	 * @return
	 */
	public U build(Object value, Object parent, Method getter, Method setter, JPanelMagique panel_magique_parent) {
		return build((T) value, parent, getter, setter, panel_magique_parent, true);
	}

	protected abstract U build(T value, Object parent, Method setter, Method method, JPanelMagique panel_magique_parent, boolean dummy);

	public U getValue(Object component) {
		return (U) getValue((U) component, true);
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
		// 23-12-2024 : Ajout d'un test pour eviter que par exemple les combobox
		// n'ecrase les valeurs quand on change
		// une autre propriété d'un ou des objets.
		// Je desactive les 2 lignes rajoutée, ca cela est fait dans JPanelMagique...
		// if (value != null)
		// if (value.equals(getValue(component)) == false)
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

	public synchronized Class<U> getComponent() {
		return component;
	}

	public synchronized void setComponent(Class<U> component) {
		this.component = component;
	}

	public Class<V> getUserTargetClass() {
		return userTargetClass;
	}

	public void setUserTargetClass(Class<V> userTargetClass) {
		this.userTargetClass = userTargetClass;
	}

	public String getUserTargetVariable() {
		return userTargetVariable;
	}

	public void setUserTargetVariable(String userTargetVariable) {
		this.userTargetVariable = userTargetVariable;
	}

}
