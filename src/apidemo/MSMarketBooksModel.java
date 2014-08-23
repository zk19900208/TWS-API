/* Copyright (C) 2013 Interactive Brokers LLC. All rights reserved.  This code is subject to the terms
 * and conditions of the IB API Non-Commercial License or the IB API Commercial License, as applicable. */

package apidemo;

import static com.ib.controller.Formats.*;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;

import apidemo.MSOrderQueue.orderTypes;

import com.ib.controller.ApiController.IOptHandler;
import com.ib.controller.ApiController.TopMktDataAdapter;
import com.ib.controller.Formats;
import com.ib.controller.NewContract;
import com.ib.controller.NewOrder;
import com.ib.controller.NewTickType;
import com.ib.controller.Types.Action;
import com.ib.controller.Types.MktDataType;
import com.ib.controller.Types.SecType;

class MSMarketBooksModel extends AbstractTableModel {
	private ArrayList<MSMarketBookRow> m_rows = new ArrayList<MSMarketBookRow>();
	
	void addRow(int legID, NewContract contract) {
		MSMarketBookRow row = new MSMarketBookRow( this,legID,contract.description());
		m_rows.add( row);
		ApiDemo.INSTANCE.controller().reqOptionMktData(contract, "", false, row);
		fireTableRowsInserted( m_rows.size() - 1, m_rows.size() - 1);
	}

	void addRow( MSMarketBookRow row) {
		m_rows.add( row);
		fireTableRowsInserted( m_rows.size() - 1, m_rows.size() - 1);
	}
	
	public ArrayList getRows() {
		return m_rows;
	}
	
	public void updateRows(ArrayList<MSMarketBookRow> rows) {
		m_rows=rows;
	}

	public void desubscribe() {
		for (MSMarketBookRow row : m_rows) {
			ApiDemo.INSTANCE.controller().cancelTopMktData( row);
		}
	}
	
	@Override
	public void fireTableDataChanged() {
		super.fireTableDataChanged();
	}

	@Override public int getRowCount() {
		return m_rows.size();
	}
	
	@Override public int getColumnCount() {
		return 15;
	}
	
	@Override public String getColumnName(int col) {
		switch( col) {
		    case 0: return "LegID";
			case 1: return "Description";
			case 2: return "Bid Size";
			case 3: return "Bid";
			case 4: return "Ask";
			case 5: return "Ask Size";
			case 6: return "Last";
			case 7: return "Time";
			case 8: return "Change";
			case 9: return "Volume";
			case 10: return "ImpVol";
			case 11:return "Delta";
			case 12:return "Gamma";
			case 13:return "Vega";
			case 14:return "Theta";
			default: return null;
		}
	}

	@Override public Object getValueAt(int rowIn, int col) {
		MSMarketBookRow row = m_rows.get( rowIn);
		switch( col) {
		    case 0: return row.m_legID;
			case 1: return row.m_description;
			case 2: return row.m_bidSize;
			case 3: return fmt( row.m_bid);
			case 4: return fmt( row.m_ask);
			case 5: return row.m_askSize;
			case 6: return fmt( row.m_last);
			case 7: return fmtTime( row.m_lastTime);
			case 8: return row.change();
			case 9: return Formats.fmt0( row.m_volume);
			case 10: return fmtPct( row.m_impVol);
			case 11: return fmtNz( row.m_delta);
			case 12: return fmtNz( row.m_gamma);
			case 13: return fmtNz( row.m_vega);
			case 14: return fmtNz( row.m_theta);
			default: return null;
		}
	}
	
	public void color(TableCellRenderer rend, int rowIn, Color def) {
		MSMarketBookRow row = m_rows.get( rowIn);
		Color c = row.m_frozen ? Color.gray : def;
		((JLabel)rend).setForeground( c);
	}

	public void cancel(int i) {
		ApiDemo.INSTANCE.controller().cancelTopMktData( m_rows.get( i) );
	}
	
	static class MSMarketBookRow implements IOptHandler {
		AbstractTableModel m_model;
		String m_description;
		double m_bid;
		double m_ask;
		double m_last;
		long m_lastTime;
		int m_bidSize;
		int m_askSize;
		double m_close;
		int m_volume;
		boolean m_frozen;
		double m_impVol;
		double m_delta;
		double m_gamma;
		double m_vega;
		double m_theta;
		int m_legID;
		
		MSMarketBookRow( AbstractTableModel model, int legID, String description) {
			m_model = model;
			m_description = description;
			m_legID=legID;
		}

		public String change() {
			return m_close == 0	? null : fmtPct( (m_last - m_close) / m_close);
		}

		@Override public void tickPrice( NewTickType tickType, double price, int canAutoExecute) {
			//System.out.println("MSTopModel.tickPrice");
			switch( tickType) {
				case BID:
					m_bid = price;
					break;
				case ASK:
					m_ask = price;
					break;
				case LAST:
					m_last = price;
					break;
				case CLOSE:
					m_close = price;
					break;
			}
			synchronized(m_model) {
				m_model.notify();
			}
			//m_model.fireTableDataChanged(); // should use a timer to be more efficient
		}

		@Override public void tickSize( NewTickType tickType, int size) {
			//System.out.println("MSTopModel.tickSize:"+Thread.currentThread().getName());
			switch( tickType) {
				case BID_SIZE:
					m_bidSize = size;
					break;
				case ASK_SIZE:
					m_askSize = size;
					break;
				case VOLUME:
					m_volume = size;
					break;
			}
			synchronized(m_model) {
				m_model.notify();
			}
			//m_model.fireTableDataChanged();
		}
		
		@Override public void tickString(NewTickType tickType, String value) {
			switch( tickType) {
				case LAST_TIMESTAMP:
					m_lastTime = Long.parseLong( value) * 1000;
					break;
			}
		}
		
		@Override public void marketDataType(MktDataType marketDataType) {
			m_frozen = marketDataType == MktDataType.Frozen;
			synchronized(m_model) {
				m_model.notify();
			}
			//m_model.fireTableDataChanged();
		}
		
		@Override public void tickOptionComputation( NewTickType tickType, double impVol, double delta, double optPrice, double pvDividend, double gamma, double vega, double theta, double undPrice) {
			//System.out.println("MSTopModel.tickOptionComputation:"+Thread.currentThread().getName());
			if (tickType == NewTickType.MODEL_OPTION) {
				m_impVol = impVol;
				m_delta = delta;
				m_gamma = gamma;
				m_vega = vega;
				m_theta = theta;
				System.out.println("MSTopModel.tickOptionComputation;impVol:"+impVol+";delta:"+delta+";optPrice:"+optPrice+";gamma:"+gamma+";vega:"+vega+";theta:"+theta);
				synchronized(m_model) {
					m_model.notify();
				}
				//m_model.fireTableDataChanged();
			}	
		}
		
		@Override public void tickSnapshotEnd() {
		}
	}
}
