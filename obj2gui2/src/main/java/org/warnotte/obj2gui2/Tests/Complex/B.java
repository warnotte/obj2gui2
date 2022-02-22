package org.warnotte.obj2gui2.Tests.Complex;
import java.util.ArrayList;
import java.util.List;

import org.warnotte.obj2gui2.PROPERTY_FIELD_LISTABLE;
import org.warnotte.obj2gui2.PROPERTY_LAYOUT;

import io.github.warnotte.waxlib3.core.TemplatePropertyMerger.property_mode;
import io.github.warnotte.waxlib3.core.TemplatePropertyMerger.Annotations.PROPERTY_FIELD_XXXABLE;
import io.github.warnotte.waxlib3.core.TemplatePropertyMerger.Annotations.PROPERTY_interface;
import io.github.warnotte.waxlib3.core.TemplatePropertyMerger.Annotations.PROPERTY_interface.gui_type;

/**
 * @author Warnotte Renaud
 *
 */
@PROPERTY_LAYOUT(type=PROPERTY_LAYOUT.Type.BoxLayout, BoxLayout_property=PROPERTY_LAYOUT.Type_BoxLayout.X)
public class B
{
	
	String d="JeSuisB";
	
	cards card1 = cards.BOUZO;
	cards card2 = cards.values()[(int)(Math.random()*cards.values().length)];
	
	long id_card_reference = 3;
	
	@PROPERTY_FIELD_LISTABLE
	List<E> list_des_E = new ArrayList<E>();
	
	double c=(int)(Math.random()*3);
	
	double cC=(int)(Math.random()*1);
	@PROPERTY_FIELD_XXXABLE
	E classeE = new E();
	
	long material_id = (int)(Math.random()*5);
	
	public B()
	{
		// Adding some element (new and linked to this reference to see if parent panel is repainting when value inside the list change).
		//list_des_E.add(classeE);
		for (int i = 0; i < 5; i++)
		{
			E e = new E();
			list_des_E.add(e);
		}
		//list_des_E.add(classeE);
		
	}
	
	@PROPERTY_interface(Operation=property_mode.PROPERTY_MERGEABLE, gui_type=gui_type.COMBO)
	public synchronized long getId_card_reference()
	{
		return id_card_reference;
	}
	
	public synchronized void setId_card_reference(long id_card_reference)
	{
		this.id_card_reference = id_card_reference;
	
	}
	@PROPERTY_interface(Operation=property_mode.PROPERTY_MERGEABLE, gui_type=gui_type.COMBO)
	public synchronized cards getCard1()
	{
		return card1;
	}
	public synchronized void setCard1(cards card1)
	{
		this.card1 = card1;
	}
	@PROPERTY_interface(Operation=property_mode.PROPERTY_MERGEABLE, gui_type=gui_type.COMBO)
	public synchronized cards getCard2()
	{
		return card2;
	}
	public synchronized void setCard2(cards card2)
	{
		this.card2 = card2;
	
	}
	@PROPERTY_interface(Operation=property_mode.PROPERTY_MERGEABLE)
	public synchronized String getD()
	{
		return d;
	}
	public synchronized void setD(String d)
	{
		this.d = d;
	
	}
	@PROPERTY_interface(Operation=property_mode.PROPERTY_MERGEABLE)
	public synchronized double getC()
	{
		return c;
	}
	public synchronized void setC(double c)
	{
		this.c = c;
	
	}
	public synchronized E getClasseE()
	{
		return classeE;
	}
	public synchronized void setClasseE(E classeE)
	{
		this.classeE = classeE;
	
	}

	@PROPERTY_interface(Operation=property_mode.PROPERTY_MERGEABLE, gui_type=gui_type.COMBO)
	public synchronized long getMaterial_id()
	{
		return material_id;
	}
	@PROPERTY_interface(Operation=property_mode.PROPERTY_MERGEABLE)
	public synchronized long getMaterial_id2()
	{
		return material_id;
	}
	public synchronized void setMaterial_id(long material_id)
	{
		this.material_id = material_id;
	}
	public synchronized void setMaterial_id2(long material_id)
	{
		this.material_id = material_id;
	}

	@PROPERTY_interface(Operation=property_mode.PROPERTY_MERGEABLE)
	public synchronized double getcC()
	{
		return cC;
	}
	public synchronized void setcC(double cC)
	{
		this.cC = cC;
	}
	@Override
	public String toString()
	{
		return "B [d=" + d + ", card1=" + card1 + ", card2=" + card2 + ", c=" + c + ", cC=" + cC + ", classeE=" + classeE + ", material_id=" + material_id + "]";
	}
	

	
	
}
