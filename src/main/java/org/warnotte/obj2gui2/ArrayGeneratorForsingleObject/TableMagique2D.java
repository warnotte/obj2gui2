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

public class TableMagique2D extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4096503933324370525L;
	
	TableMagiqueModel2D model;
	
	JTable table;
	
	private JPanel panel;
	private JButton buttonAddRow;
	private JButton buttonRemoveRows;
	private JButton btnCopyToAll;
	private JButton buttonAddColumn;
	private JButton buttonRemoveColumns;
	
	public TableMagique2D(TableMagiqueModel2D model) {
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
			panel.add(getButtonAddRow());
			panel.add(getButtonRemoveRows());
			panel.add(getButton_Addcolumn());
			panel.add(getButton_RemoveColumns());
			panel.add(getBtnCopyToAll());
		}
		return panel;
	}
	private JButton getButtonAddRow() {
		if (buttonAddRow == null) {
			buttonAddRow = new JButton("+Row");
			buttonAddRow.setName("TableMagique_Addrow");
			buttonAddRow.addActionListener(new ActionListener() {
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
		return buttonAddRow;
	}

	private JButton getButtonRemoveRows() {
		if (buttonRemoveRows == null) {
			buttonRemoveRows = new JButton("-Row");
			buttonRemoveRows.setName("TableMagique_RemoveRow");
			buttonRemoveRows.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					deleteRows();
				}
			});
		}
		return buttonRemoveRows;
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
	

	private JButton getButton_Addcolumn() {
		if (buttonAddColumn == null) {
			buttonAddColumn = new JButton("+Column");
			buttonAddColumn.setName("TableMagique_AddCol");
			buttonAddColumn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					addColumn();
				}
			});
			
		}
		return buttonAddColumn;
	}
	
	private JButton getButton_RemoveColumns() {
		if (buttonRemoveColumns == null) {
			buttonRemoveColumns = new JButton("-Columns");
			buttonRemoveColumns.setName("TableMagique_RemoveCols");
			buttonRemoveColumns.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					removeColumns();
				}
			});
			
		}
		return buttonRemoveColumns;
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
	
	/**
	 * 
	 */
	protected void removeColumns()
	{
		int cols [] = table.getSelectedColumns();
		model.removeColumns(cols);
	}
	/**
	 * 
	 */
	protected void addColumn()
	{
		model.addColumn();
	}
}
