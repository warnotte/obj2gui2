package io.github.warnotte.obj2gui2.Tests.SimpleTypeValidationTest;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

import org.jdom2.JDOMException;

import io.github.warnotte.obj2gui2.Binding;
import io.github.warnotte.obj2gui2.BindingEnum;
import io.github.warnotte.obj2gui2.JPanelMagique;
import io.github.warnotte.obj2gui2.JTreeMagique;
import io.github.warnotte.obj2gui2.MyChangedEvent;
import io.github.warnotte.obj2gui2.MyEventListener;
import io.github.warnotte.obj2gui2.Validators.ValidationException;
import io.github.warnotte.obj2gui2.Validators.Validator;
import io.github.warnotte.obj2gui2.Validators.ValidatorImpl;
import io.github.warnotte.waxlib3.core.TemplatePropertyMerger.TemplatePropertyMergerV2;

/**
 * @author warnotte Renaud
 */
public class mainTest
{

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception
	{
		//String file = JPanelMagique.class.getResource("log4j_OBJ2GUI2.xml").getFile();
		JPanelMagique.PRINT_DEBUG=true;
		JTreeMagique.PRINT_DEBUG=true;
		TemplatePropertyMergerV2.PRINT_DEBUG=false;
		CreateExemple();
	}

	/**
	 * !!! Ceci continue de tourner dans le vide...
	 * @throws Exception
	 * @throws IOException
	 * @throws JDOMException
	 */
	private static JFrame CreateExemple() throws Exception, IOException, JDOMException
	{
		final List<Material> list_mats = new ArrayList<Material>();
		
		list_mats.add(new Material(1, "SteelA"));
		list_mats.add(new Material(5, "SteelB"));
		list_mats.add(new Material(8, "SteelC"));
		list_mats.add(new Material(9, "SteelD"));
		list_mats.add(new Material(11, "SteelE"));
				
		final List<ObjectExemple> selection = new ArrayList<ObjectExemple>();
		selection.add(new ObjectExemple());
		selection.add(new ObjectExemple());
		selection.add(new ObjectExemple());
	//	Thread.sleep(1000);
	//	selection.add(new KOPKOK());
	//	selection.add(new KOPKOK());
				
		// This will help OBJ2GUI to assign an Object (from a list of Identifiable) to the value.
		final ArrayList<Binding> binds = new ArrayList<Binding>();
		binds.add(new Binding(list_mats, ObjectExemple.class, "material_id"));

		// This will help OBJ2GUI to assign an enumeration value to a "long" or "int" ordinal value setted in a variable.
		final ArrayList<BindingEnum> bindsEnum = new ArrayList<BindingEnum>();
		BindingEnum bindE = new BindingEnum(cards.values(), ObjectExemple.class, "Id_card_reference");
		bindE.setIndexoffset(0);
		bindsEnum.add(bindE);
		
		// Validator Predefinis (valeur double doit etre plus > que 0.5)
		JPanelMagique.registerValidator(ObjectExemple.class.getMethod("setAslider", double.class), new ValidatorImpl());
		JPanelMagique.registerValidator(ObjectExemple.class.getMethod("setA", double.class), new ValidatorImpl());
		
		// Definition rappide avec override (valeur double doit etre entre 0.25 et 0.75)
		JPanelMagique.registerValidator(ObjectExemple.class.getMethod("setAa", double.class), 
				new Validator<Double>(){

					@Override
					public void valideValue(Double value) throws ValidationException
					{
						if ((value < 0.25) || (value > 0.75))
							throw new ValidationException("Value must be between 0.25 and 0.75");
					}
			
		}
		);
		
		// Definition rappide avec override (Ouvrier max 10 jours de travail);
		JPanelMagique.registerValidator(Time.class.getMethod("setDays_", int.class), 
				new Validator<Integer>(){

					@Override
					public void valideValue(Integer value) throws ValidationException
					{
						if (value>10)
							throw new ValidationException("Les ouvriers ne peuvent pas travailler plus de 10 jours");
					}
			
		}
		);

		// Dummy class for test (could be waterwayNetwork).
				final Object mainBenchMark = new MainBenchMark();
				// Definition rappide avec override (Ouvrier max 10 jours de travail);
				JPanelMagique.registerValidator(Time.class.getMethod("setDays_", int.class), 
						new Validator<Integer>(){

							@Override
							public void valideValue(Integer value) throws ValidationException
							{
								// Je prends hashCode pour avoir une methode qui retourne un truc (ca aurait put etre nbr jour max du projet)
								if (value>mainBenchMark.hashCode())
									throw new ValidationException("Les ouvriers ne peuvent pas travailler plus de 10 jours");
							}
					
				}
				);
				
				// Dummy class for test (could be waterwayNetwork).
				
				// Definition rappide avec override (Ouvrier max 10 jours de travail);
				JPanelMagique.registerValidator(ObjectExemple.class.getMethod("setMaterial_id", long.class), 
						new Validator<Long>(){

							@Override
							public void valideValue(Long value) throws ValidationException
							{
								// Je prends hashCode pour avoir une methode qui retourne un truc (ca aurait put etre nbr jour max du projet)
								if (value==5)
									throw new ValidationException("On fournit plus ce materiel");
							}
					
				}
				);
				
				// Definition rappide avec override (Ouvrier max 10 jours de travail);
				JPanelMagique.registerValidator(ObjectExemple.class.getMethod("setMaterial_id2", long.class), 
						new Validator<Long>(){

							@Override
							public void valideValue(Long value) throws ValidationException
							{
								for (int i = 0; i < list_mats.size(); i++)
								{
									Material o = list_mats.get(i);
									long id = o.getId();
									if (id==value)
									{
										// Je prends hashCode pour avoir une methode qui retourne un truc (ca aurait put etre nbr jour max du projet)
										if (value==5)
											throw new ValidationException("On fournit plus ce materiel");
										return;
									}
								}
								throw new ValidationException("Cet id n'existe pas!");
								
							}
					
				}
				);
		
		
		
		final JPanelMagique panel = JPanelMagique.GenerateJPanelFromSelectionAndBindings(selection, binds, bindsEnum);
		
		
		
		
		
		//GUI2XMLLabel.SaveLabel(panel, "labels_exemple_simple.xml");
		panel.LoadLabel("labels_exemple_simple.xml");
		
		panel.addMyEventListener(new MyEventListener()
		{
			public void myEventOccurred(MyChangedEvent e)
			{
				System.err.println("MAIN - From PANELMAGIQUE-  Object has changed make the needed things ..."+e);
			}
		});
		
		
		
		JFrame fr = new JFrame();
		fr.setSize(640, 480);
		fr.add(new JScrollPane(panel), BorderLayout.CENTER);
		fr.setVisible(true);
		fr.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		fr.addMouseMotionListener(new MouseMotionListener(){

			@Override
			public void mouseDragged(MouseEvent e)
			{
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseMoved(MouseEvent e)
			{
				System.err.println(" Mouse = "+e.getX());
				
			}

			
			
		});
		
		
		JButton button = new JButton();
		button.setText("Debug");
		button.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				for (int i = 0; i < selection.size(); i++)
				{
					System.err.println("Item " + i);
					System.err.println(selection.get(i));
				}
			}
		});
		fr.add(button, BorderLayout.SOUTH);
		
		JButton button2 = new JButton();
		button2.setText("AnotherView");
		button2.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				JPanelMagique panelM = null;
				try
				{
					panelM = JPanelMagique.GenerateJPanelFromSelectionAndBindings(panel, selection,binds, bindsEnum);
					JFrame fr = new JFrame();
					fr.setSize(640, 480);
					fr.add(panelM, BorderLayout.CENTER);
					fr.setVisible(true);
				} catch (IllegalArgumentException e1)
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IllegalAccessException e1)
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (InvocationTargetException e1)
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (Exception e1)
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		fr.add(button2, BorderLayout.EAST);
		return fr;
	}

	

	

}
