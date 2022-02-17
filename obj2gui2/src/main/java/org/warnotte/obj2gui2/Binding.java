package org.warnotte.obj2gui2;
import java.util.List;

import io.github.warnotte.waxlib3.waxlib2.Identifiable.Identifiable;

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
	List<? extends Identifiable> list_mats;
	Class<?> class1;
	String variable;
	
	public Binding(List<? extends Identifiable> list_mats, Class<?> class1, String variable)
	{
		super();
		this.list_mats = list_mats;
		this.class1 = class1;
		this.variable = variable;
	}
	
}
