package org.warnotte.obj2gui2;

/**
 * 
 * Classe qui va permettre d'assigner une list d'element (venant en fait d'un enum) 
 * a une variable d'une classe (de type int qui sert d'id).
 * 
 * Permet d'afficher les combobox en servant un peu de role de modele.
 * @author Warnotte Renaud.
 *
 */
public class BindingEnum
{
	Object[] list_mats;
	Class<?> class1;
	String variable;
	private int indexoffset=0; // Mettre 1 ici si les valeurs commencent a 1 au lieu de 0 (cas du fortran :()
	
	public BindingEnum(Object[] list_mats, Class<?> class1, String variable)
	{
		super();
		this.list_mats = list_mats;
		this.class1 = class1;
		this.variable = variable;
	}
	
	/**
	 * Mettre 1 ici si les valeurs commencent a 1 au lieu de 0 (cas du fortran :()
	  */
	public int getIndexoffset()
	{
		return indexoffset;
	}
	
	/**
	 * Mettre 1 ici si les valeurs commencent a 1 au lieu de 0 (cas du fortran :()
	 * @param indexoffset
	 */
	public void setIndexoffset(int indexoffset)
	{
		this.indexoffset = indexoffset;
		
		
	}
	
}
