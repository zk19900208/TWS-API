package apidemo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import apidemo.MSComboLeg;

import com.ib.controller.NewComboLeg;
import com.ib.controller.NewContract;

class MSComboLegModel extends AbstractTableModel {
	/*
	private HashMap<Integer,MSStrategyInfo> m_strategies=new HashMap<Integer,MSStrategyInfo>();
	private HashMap<Integer,Vector> m_vectors=new HashMap<Integer,Vector>();
	private MSStrategyInfo m_legs=new MSStrategyInfo();

	void clear() {
		m_legs.clear();
		fireTableDataChanged();
	}
	
	MSComboLegModel() {
	}
	
	public boolean getStrategy(int strategyID) {

		if(m_strategies.keySet().contains(strategyID)) {
			m_legs=m_strategies.get(strategyID);
			System.out.println("MSComboLegModel.getStrategy():"+m_legs);
			return true;
		} else {
			return false;
		}
	}
	
	public void newStrategy() {
		m_legs=new MSStrategyInfo();
	}
	
	public void addStrategy(int strategyID) {
		System.out.println("MSComboLegModel.getStrategy()1:"+m_legs);
		m_strategies.put(strategyID, m_legs);
		System.out.println("MSComboLegModel.addStrategy()2:"+m_legs);
	}
	
	public void addLeg(MSComboLeg legRow) {
		System.out.println("MSComboLegModel.addLeg():"+m_legs);
		m_legs.addLeg(legRow);
		//boolean b=true;
		fireTableDataChanged();
	}
	*/

	ArrayList<MSComboLeg> m_legs=new ArrayList<MSComboLeg>();
	
	public void updateTable(ArrayList<MSComboLeg> legs){
		m_legs=legs;
		fireTableDataChanged();
	}
	
	@Override
	public int getRowCount() {
		try {
			return m_legs.size();
		} catch(NullPointerException e) {
			return 0;
		}
	}

	@Override
	public int getColumnCount() {
		return 5;
	}

	@Override
	public String getColumnName(int col) {
		switch( col) {
		    case 0: return "LegID";
			case 1: return "Ratio";
			case 2: return "Action";
			case 3: return "Description";
			case 4: return "Selected legs";
			default: return null;
		}
	}

	@Override
	public Object getValueAt(int rowIn, int col) {
		MSComboLeg row = m_legs.get( rowIn);    //m_legs.getLeg( rowIn);
		switch( col) {
		    case 0: return row.m_legID;
			case 1: return row.m_leg.ratio();
			case 2: return row.m_leg.action();
			case 3: return row.m_contract.description();
			case 4: return row.m_selected;
			default: return null;
		}
	}
	
	@Override
	public void setValueAt(Object value, int rowIndex, int columnIndex) {
		//System.out.println("setValueAt()");
		if(columnIndex==3) {
			MSComboLeg row = m_legs.get(rowIndex);    //m_legs.getLeg(rowIndex);
			row.m_selected=(boolean)value;
		}
		this.fireTableCellUpdated(rowIndex, columnIndex);
	}
	
	@Override
	public Class getColumnClass(int columnIndex){
		return getValueAt(0,columnIndex).getClass();
	}
	
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		if(columnIndex==3) {
			return true;
		} else {
			return false;
		}
	}
	
}