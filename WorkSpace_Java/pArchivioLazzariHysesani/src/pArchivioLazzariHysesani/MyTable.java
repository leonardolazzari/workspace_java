package pArchivioLazzariHysesani;

import java.awt.Color;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;

public class MyTable extends JTable {

	
	MyTable(Object[][] data, String[] nomeColonne){
		super(data, nomeColonne);
		super.setBorder(new LineBorder(new Color(0, 0, 0)));
		super.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		super.setBounds(10, 50, 674, 351);
		super.sizeColumnsToFit(true);
		super.setDefaultEditor(Object.class, null);
	}
	
	
}
