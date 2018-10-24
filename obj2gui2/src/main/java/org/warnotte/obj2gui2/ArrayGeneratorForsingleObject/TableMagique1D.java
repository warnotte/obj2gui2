package org.warnotte.obj2gui2.ArrayGeneratorForsingleObject;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

public class TableMagique1D extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4096503933324370525L;
	
	TableMagiqueModel1D model;
	
	JTable table;
	
	private JPanel panel;
	private JButton button;
	private JButton button_1;
	private JButton btnCopyToAll;
	
	public TableMagique1D(TableMagiqueModel1D model) {
		this.model = model;
		table = new JTable(model);
		table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		table.setCellSelectionEnabled(true);
		initialize();
	}
	private void initialize() {
		this.setLayout(new BorderLayout());
		this.add(new JScrollPane(table), BorderLayout.CENTER);
		add(getPanel(), BorderLayout.SOUTH);
		this.setMinimumSize(new Dimension(320,320));
	}

	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			panel.add(getButton());
			panel.add(getButton_1());
			panel.add(getBtnCopyToAll());
		}
		return panel;
	}
	private JButton getButton() {
		if (button == null) {
			button = new JButton("+");
			button.setName("TableMagique_Addrow");
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try
					{
						addRow();
					} catch (IllegalAccessException e1)
					{
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IllegalArgumentException e1)
					{
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (InvocationTargetException e1)
					{
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});
		}
		return button;
	}

	private JButton getButton_1() {
		if (button_1 == null) {
			button_1 = new JButton("-");
			button_1.setName("TableMagique_RemoveRow");
			button_1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					deleteRows();
				}
			});
		}
		return button_1;
	}
	
	private JButton getBtnCopyToAll() {
		if (btnCopyToAll == null) {
			btnCopyToAll = new JButton("Copy to all");
			btnCopyToAll.setName("TableMagique_Propagate");
			btnCopyToAll.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					propatageAll();
				}
			});
		}
		return btnCopyToAll;
	}
	
	
	/**
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 * 
	 */
	protected void addRow() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException
	{
		model.addRow();
	}
		
	/**
	 * 
	 */
	protected void deleteRows()
	{
		int rows [] = table.getSelectedRows();
		model.removeRows( rows);
	}
	
	/**
	 * 
	 */
	protected void propatageAll()
	{
		model.propagateAll();
		
	}
}
