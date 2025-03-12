package io.github.warnotte.obj2gui2;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import io.github.warnotte.waxlib3.core.Identifiable.Identifiable;


/**
 * 
 * Classe qui va permettre d'assigner une list d'element (qui derivent Indentifiable) 
 * à une variable d'une classe (de type int qui sert d'id).
 * 
 * Permet d'afficher les combobox en servant un peu de role de modele.
 * @author Warnotte Renaud.
 *
 */
public class Binding
{
	List<?> list_mats;
	// List<? implements Identifiable> list_mats; Dommage qu'on peux pas faire ainsi
	Class<?> class1;
	String variable;
	
	public Binding(List<?> list_mats, Class<?> class1, String variable) throws Exception
	{
		super();
		Class cls = list_mats.get(0).getClass();
		boolean isOk = Binding.isImplementingIndentifiable(list_mats.get(0));
		if (isOk == false)
			throw new Exception("Object "+ cls+ " not implement Identifiable");
		this.list_mats = list_mats;
		this.class1 = class1;
		this.variable = variable;
	}
	
	public static boolean isImplementingIndentifiable(Object o)
	{
		Class cls = o.getClass();
		List<Class<?>> allSuperInterfaces = getAllInterfaces(cls);
		for (Iterator<Class<?>> iterator = allSuperInterfaces.iterator(); iterator.hasNext();) {
			Class<?> class1 = iterator.next();
			if (class1==Identifiable.class)
				return true;
		}
		return false;
	}
	
	
	/**
	  * Gets a {@link List} of all interfaces implemented by the given class and its superclasses.
	  *
	  * <p>
	  * The order is determined by looking through each interface in turn as declared in the source file and following its
	  * hierarchy up. Then each superclass is considered in the same way. Later duplicates are ignored, so the order is
	  * maintained.
	  * </p>
	  *
	  * @param cls the class to look up, may be {@code null}
	  * @return the {@link List} of interfaces in order, {@code null} if null input
	  */
	 public static List<Class<?>> getAllInterfaces(final Class<?> cls) {
	     if (cls == null) {
	         return null;
	     }

	     final LinkedHashSet<Class<?>> interfacesFound = new LinkedHashSet<>();
	     getAllInterfaces(cls, interfacesFound);
	     return new ArrayList<>(interfacesFound);
	 }

	 /**
	  * Gets the interfaces for the specified class.
	  *
	  * @param cls the class to look up, may be {@code null}
	  * @param interfacesFound the {@link Set} of interfaces for the class
	  */
	 private static void getAllInterfaces(Class<?> cls, final HashSet<Class<?>> interfacesFound) {
	     while (cls != null) {
	         final Class<?>[] interfaces = cls.getInterfaces();
	         for (final Class<?> i : interfaces) {
	             if (interfacesFound.add(i)) {
	                 getAllInterfaces(i, interfacesFound);
	             }
	         }
	         cls = cls.getSuperclass();
	     }
	 }
	
}
