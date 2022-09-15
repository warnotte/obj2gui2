/**
 * 
 */
package org.warnotte.obj2gui2.Tests.Complex;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.text.DecimalFormat;

import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
import javax.swing.JButton;

/**
 * @author Warnotte Renaud
 *
 */
public class testformattedField extends JFrame
{

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1116708935877189219L;
	private JPanel	contentPane;
	//private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					testformattedField frame = new testformattedField();
					frame.setVisible(true);
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public testformattedField()
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		
		DecimalFormat decimalFormat = new DecimalFormat("#0.0###E0");
		NumberFormatter textFormatter = new NumberFormatter(decimalFormat);
		DecimalFormat decimalFormat2 = new DecimalFormat("#0.0###");
		NumberFormatter editFormatter = new NumberFormatter(decimalFormat2);
		
		DecimalFormat decimalFormat24 = new DecimalFormat("");
		NumberFormatter textFormatter2 = new NumberFormatter(decimalFormat24);
		DecimalFormat decimalFormat22 = new DecimalFormat("");
		NumberFormatter editFormatter2 = new NumberFormatter(decimalFormat22);
		 
	//	String percentEditFormat = "";
	//	String percentDisplayFormat = "";
		
		/*
		NumberFormatter percentEditFormatter =
		        new NumberFormatter(percentEditFormat) {
		    public String valueToString(Object o)
		          throws ParseException {
		        Number number = (Number)o;
		        if (number != null) {
		            double d = number.doubleValue() * 100.0;
		            number = new Double(d);
		        }
		        return super.valueToString(number);
		    }
		    public Object stringToValue(String s)
		           throws ParseException {
		        Number number = (Number)super.stringToValue(s);
		        if (number != null) {
		            double d = number.doubleValue() / 100.0;
		            number = new Double(d);
		        }
		        return number;
		    }
		};
		*/
		
		
		JFormattedTextField rateField = new JFormattedTextField(
                new DefaultFormatterFactory(
               		 textFormatter,
               		 textFormatter,
               		 editFormatter));
rateField.setValue(new Double(-8.65469));


JFormattedTextField rateField2 = new JFormattedTextField(
        new DefaultFormatterFactory(
       		 textFormatter2,
       		 textFormatter2,
       		 editFormatter2));
rateField2.setValue(new Double(-32.3134));

contentPane.add(rateField, BorderLayout.WEST);
contentPane.add(rateField2, BorderLayout.EAST);
		
		JButton btnNewButton = new JButton("New button");
		contentPane.add(btnNewButton, BorderLayout.SOUTH);
		
	}

}
