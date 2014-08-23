package apidemo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;

public class MSStrategyModel extends AbstractTableModel {
	ArrayList<StrategyRow> m_list = new ArrayList<StrategyRow>();
	boolean m_editable=true;

	void clear() {
		m_list.clear();
		fireTableDataChanged();
	}
		
	public void addStrategy(int strategyID, String strategySymbols, String strategyType) {	
		int id = strategyID;
		StrategyRow row = new StrategyRow(strategyID,strategySymbols, strategyType);
		m_list.add(row);
		Collections.sort(m_list);
		
		fireTableDataChanged();
		/*
		SwingUtilities.invokeLater( new Runnable() {
			@Override public void run() {
				fireTableDataChanged();
			}
		});
		*/
	}
	
	public ArrayList getList() {
		return m_list;
	}
		
	public void delete(int selectedRow) {
		StrategyRow s=m_list.get(selectedRow);
		m_list.remove(selectedRow);
			
		SwingUtilities.invokeLater( new Runnable() {
			@Override public void run() {
				fireTableDataChanged();
			}
		});
	}

	@Override
	public int getRowCount() {
		return m_list.size();
	}

	@Override
	public int getColumnCount() {
		return 6;
	}
		
	@Override
	public String getColumnName(int col) {
		switch( col) {
			case 0: return "StrategyID";
			case 1: return "Symbols";
			case 2: return "StrategyType";
			case 3: return "Finished";
			case 4: return "Performance";
			case 5: return "Selected";
			default: return null;
		}
	}

	@Override
	public Object getValueAt(int rowIn, int col) {
		StrategyRow row = m_list.get( rowIn);
		switch( col) {
			case 0: return row.m_strategyID;
			case 1: return row.m_symbols;
			case 2: return row.m_strategyTypes;
			case 3: return row.m_finished;
			case 4: return row.m_performance;
			case 5: return row.m_selected;
			default: return null;
		}
	}

	public StrategyRow getRowAt(int rowIn) {
		StrategyRow row = m_list.get( rowIn);
		return row;
	}
	
	@Override
	public void setValueAt(Object value, int rowIndex, int columnIndex) {
		//System.out.println("setValueAt()");
		if(columnIndex==5) {
			StrategyRow s=m_list.get(rowIndex);
			s.m_selected=(boolean)value;
		}
		this.fireTableCellUpdated(rowIndex, columnIndex);
	}
	
	@Override
	public Class getColumnClass(int columnIndex){
		return getValueAt(0,columnIndex).getClass();
	}
	
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		if(columnIndex==5) {
			return m_editable;
		} else {
			return false;
		}
	}
	
	public void setEditable(boolean editable) {
		m_editable=false;
		this.fireTableDataChanged();
	}

}

class StrategyRow implements Comparable<StrategyRow>{
	int m_strategyID;
	String m_symbols;
	String m_strategyTypes;
	String m_finished="No";
	String m_performance="0";
	boolean m_selected;

	StrategyRow( int strategyID, String strategySymbols, String strategyTypes) {
		m_strategyID = strategyID;
		m_symbols = strategySymbols;
		m_strategyTypes = strategyTypes;
		m_selected=true;
	}
	
	public int getStrategyID() {
		return m_strategyID;
	}
	
	@Override
	public int compareTo(StrategyRow o) {
		return m_strategyID > o.m_strategyID ? 1:-1;
	}
}