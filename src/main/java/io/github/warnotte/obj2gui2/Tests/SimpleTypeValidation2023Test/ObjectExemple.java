package io.github.warnotte.obj2gui2.Tests.SimpleTypeValidation2023Test;

import java.io.File;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import javax.vecmath.Vector2d;

import io.github.warnotte.obj2gui2.PROPERTY_LAYOUT;
import io.github.warnotte.waxlib3.core.TemplatePropertyMerger.property_mode;
import io.github.warnotte.waxlib3.core.TemplatePropertyMerger.Annotations.PROPERTY_FIELD_XXXABLE;
import io.github.warnotte.waxlib3.core.TemplatePropertyMerger.Annotations.PROPERTY_interface;
import io.github.warnotte.waxlib3.core.TemplatePropertyMerger.Annotations.PROPERTY_interface.gui_type;
import io.github.warnotte.waxlib3.waxlibswingcomponents.Swing.Component.JWColor;

/**
 * @author Warnotte Renaud.
 */
@PROPERTY_LAYOUT(type = PROPERTY_LAYOUT.Type.BoxLayout, BoxLayout_property = PROPERTY_LAYOUT.Type_BoxLayout.Y)
public class ObjectExemple
{
	
	// Divers test pour les type simple primitif
	double	a				= (int) (Math.random() * 10);
	
	
	public ObjectExemple()
	{

	}

	@PROPERTY_interface(Operation = property_mode.PROPERTY_MERGEABLE)
	public double getA() {
		return a;
	}


	public void setA(double a) {
		this.a = a;
	}

		

}
