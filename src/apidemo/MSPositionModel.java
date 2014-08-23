package apidemo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import javax.swing.table.AbstractTableModel;

public class MSPositionModel extends AbstractTableModel {
	public ArrayList<MSPosition> m_positions=new ArrayList<MSPosition>();
	public HashMap<Integer,MSPosition> m_positionMap=new HashMap<Integer,MSPosition>();
	String[] columnNames={"LegID","Quantity","Status","Filled","Remaining","Total","Orders ID"};
	public void addPosition(int legID,MSPosition position){
		m_positions.add(position);
		m_positionMap.put(legID, position);
		Collections.sort(m_positions);
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return columnNames.length;
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return m_positions.size();
	}
	
	@Override
	public String getColumnName(int col) {
		try {
			return columnNames[col];
		} catch(ArrayIndexOutOfBoundsException e) {
			return null;
		}
	}

	@Override
	public Object getValueAt(int row, int col) {
		// TODO Auto-generated method stub
		MSPosition position=m_positions.get(row);
		switch(col) {
			case 0: return position.m_legID;
			case 1: return position.Quantity;
			case 2: return position.Status;
			case 3: return position.Filled;
			case 4: return position.Remaining;
			case 5: return position.m_total;
			case 6: return position.OrdersID;
			default : return null;
		}
	}
	
	@Override
	public void fireTableDataChanged() {
		for(int i=0;i<m_positions.size();i++) {
			MSPosition position=m_positions.get(i);
			position.updatePosition();
		}
		super.fireTableDataChanged();
	}

	public synchronized void add(MSOrderRow orderRow) {
		// TODO Auto-generated method stub
		int legID=orderRow.m_legID;
		MSPosition position=m_positionMap.get(legID);
		if(position!=null) {
			position.addOrderRow(orderRow);
		}
		this.fireTableDataChanged();
	}
}