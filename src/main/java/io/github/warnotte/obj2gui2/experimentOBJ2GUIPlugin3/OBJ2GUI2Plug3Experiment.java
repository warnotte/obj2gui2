package io.github.warnotte.obj2gui2.experimentOBJ2GUIPlugin3;
import java.io.Serializable;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JTextField;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.github.warnotte.obj2gui2.JPanelMagique;


public abstract class OBJ2GUI2Plug3Experiment<T, U, V> {

	protected static final Logger	logger	= LogManager.getLogger(OBJ2GUI2Plug3Experiment.class);
	
    private Class<T> type; // Le type de la variable -> ici un Integer
    private Class<U> component; // Ici ça sera JComboBox
    private Class<V> userTargetClass; // Ici ça sera la classe Flotteur
    private String userTargetVariable; // Ici ça sera le nom de la méthode getID_ObjetSurFlotteur

    public static void main(String[] args) {
        // Création correcte de l'instance avec types explicites
    	OBJ2GUI2Plug3Experiment<String, JTextField, Flotteur> c = new OBJ2GUI2Plug3Experiment<>(Flotteur::getID_ObjetSurFlotteur) {

			@Override
			protected JTextField build(String value, Object parent, Method setter, Method method, JPanelMagique panel_magique_parent, boolean dummy) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			protected String getValue(JTextField component, boolean dummy) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public void refresh(String value, JTextField component, boolean dummy) {
				// TODO Auto-generated method stub
				
			}

        	
        };
    }

    // Constructeur générique
    public OBJ2GUI2Plug3Experiment(MethodReference<V, T> reference) {
		this.type = (Class<T>) ((java.lang.reflect.ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		this.component = (Class<U>) ((java.lang.reflect.ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[1];
        this.userTargetClass = (Class<V>) Flotteur.class; // Exemple concret pour démonstration
        String variableName = extractVariableNameFromGetterMethod(reference);
        this.userTargetVariable = variableName;
        System.out.println("Nom de la variable : " + variableName);
        
		boolean isOk = isFieldExist(userTargetClass, userTargetVariable);
		if (isOk == false) {
			logger.fatal("Field " + userTargetVariable + " doesn't exist in " + userTargetClass);
		}
    }

    @FunctionalInterface
    interface MethodReference<V, T> extends Serializable {
        T apply(V v);
    }

    
    /**
     * 
     * @param <V>
     * @param <T>
     * @param reference
     * @return
     */
    /*public static <V, T> String extractVariableNameFromGetterMethod(MethodReference<V, T> reference) {
        try {
            // Accès au SerializedLambda via réflexion
            Method writeReplace = reference.getClass().getDeclaredMethod("writeReplace");
            writeReplace.setAccessible(true);
            SerializedLambda lambda = (SerializedLambda) writeReplace.invoke(reference);

            String implMethodName = lambda.getImplMethodName();

            // Deviner le nom du champ (convention getter "getX" => champ "x")
            if (implMethodName.startsWith("get")) {
                return Character.toLowerCase(implMethodName.charAt(3)) + implMethodName.substring(4);
            } else if (implMethodName.startsWith("is")) {
                return implMethodName.charAt(2) + implMethodName.substring(3);
            } else {
                throw new IllegalArgumentException("La méthode n'est pas un getter standard.");
            }
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de l'extraction du nom de méthode", e);
        }
    }*/
    
    public static <V, T> String extractVariableNameFromGetterMethod(MethodReference<V, T> reference) {
        try {
            // Accès au SerializedLambda via réflexion
            Method writeReplace = reference.getClass().getDeclaredMethod("writeReplace");
            writeReplace.setAccessible(true);
            SerializedLambda lambda = (SerializedLambda) writeReplace.invoke(reference);

            // Obtenir la classe cible et la méthode
            String className = lambda.getImplClass().replace('/', '.');
            String implMethodName = lambda.getImplMethodName();

            // Charger la classe cible
            Class<?> clazz = Class.forName(className);

            // Rechercher un champ correspondant
            Field[] fields = clazz.getDeclaredFields();
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

    
    
    static boolean isFieldExist(Class<?> classType, String fieldName) {
		Field[] fields = JPanelMagique.getAllFields(classType);
		for (int i = 0; i < fields.length; i++) {
			if (fields[i].getName().equalsIgnoreCase(fieldName))
				return true;
		}

		return false;
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
		// 23-12-2024 : Ajout d'un test pour eviter que par exemple les combobox n'ecrase les valeurs quand on change 
		// une autre propriété d'un ou des objets. 
		// Je desactive les 2 lignes rajoutée, ca cela est fait dans JPanelMagique...
		//if (value != null)
		//	if (value.equals(getValue(component)) == false)
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



