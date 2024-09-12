package io.github.warnotte.obj2gui2;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.ClassUtils;


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
		List<Class<?>> allSuperInterfaces = ClassUtils.getAllInterfaces(cls);
		for (Iterator<Class<?>> iterator = allSuperInterfaces.iterator(); iterator.hasNext();) {
			Class<?> class1 = iterator.next();
			if (class1==Identifiable.class)
				return true;
		}
		return false;
	}
	
}
