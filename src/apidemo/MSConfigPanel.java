package apidemo;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class MSConfigPanel extends JPanel{
	/*
	private JButton button1=new JButton("MSConfig");
	MSConfigPanel() {
		add(button1);
	}
	*/
	public void addTable(Object[][] cellData) {
		setLayout(new BorderLayout());
		
		String[] headers={"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
		DefaultTableModel model = new DefaultTableModel(cellData,headers) {
            public boolean isCellEditable(int row, int column) {
              return false;
            }
        };
        //setLayout(null);
		//MSConfigTable mSConfigTable=new MSConfigTable(s);
		JTable jTable=new JTable(model);
		DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();// 设置jTMyStrategy内容居中
		tcr.setHorizontalAlignment(SwingConstants.CENTER);// 这句和上句作用一样
		jTable.setDefaultRenderer(Object.class, tcr);
		JScrollPane jScrollPane=new JScrollPane(jTable);
		jScrollPane.setBounds(10, 10, 880, 170);
		//jScrollPane.setPreferredSize( new Dimension( 500, 150));
		add(jScrollPane,BorderLayout.CENTER);
	}
	
	private class MSConfigTable extends AbstractTableModel {

		String[] s;
		
		MSConfigTable(String[] s) {
			this.s=s.clone();
		}
		
		@Override
		public int getColumnCount() {
			// TODO Auto-generated method stub
			return (s.length/2)+2;
		}

		@Override
		public int getRowCount() {
			// TODO Auto-generated method stub
			return 1;
		}

		@Override 
		public String getColumnName(int col) {
			switch( col) {
				case 0: return s[0];
				default: return null;
			}
		}
		
		@Override
		public Object getValueAt(int arg0, int arg1) {
			// TODO Auto-generated method stub
			switch(arg1) {
			    case 0:return s[1];
			    default:return null;
			}
		}	
	}
}