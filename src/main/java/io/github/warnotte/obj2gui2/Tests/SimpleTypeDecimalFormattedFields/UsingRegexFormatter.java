package io.github.warnotte.obj2gui2.Tests.SimpleTypeDecimalFormattedFields;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JTextField;

import io.github.warnotte.obj2gui2.RegexFormatter;



public class UsingRegexFormatter {

  public static void main(String[] a){
    JFrame frame = new JFrame();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

//    JFormattedTextField formattedField = new JFormattedTextField(new RegexFormatter("\\(\\d{3}\\)\\d{3}-\\d{4}"));
    final JFormattedTextField formattedField = new JFormattedTextField(new RegexFormatter("[A-Za-z]{4}[_]+[0-9]{2}"));
    formattedField.setText("ABCDEF");
    
    //formattedField.setFocusLostBehavior(JFormattedTextField.COMMIT_OR_REVERT);
    
    formattedField.addActionListener(new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e)
		{
			String val =formattedField.getText();
			System.err.println("Val = "+val);
		}
    	
    });
    
    formattedField.addFocusListener(new FocusListener() {

		@Override
		public void focusGained(FocusEvent e)
		{
			// TODO Auto-generated method stub
			
		}

		@Override
		public void focusLost(FocusEvent e)
		{
			String val =formattedField.getText();
			System.err.println("Val = "+val);
			
		}
    	
    });
    

    frame.add(formattedField,"North");
    
    frame.add(new JTextField(),"South");

    frame.setSize(300, 200);
    frame.setVisible(true);
  }


}