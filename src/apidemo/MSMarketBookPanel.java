package apidemo;

import java.awt.BorderLayout;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import apidemo.util.HtmlButton;
import apidemo.util.TCombo;
import apidemo.util.VerticalPanel;
import apidemo.util.NewTabbedPanel.NewTabPanel;

import com.ib.controller.Types.MktDataType;

class MSMarketBookPanel extends NewTabPanel {
	MSMarketBooksModel m_model = new MSMarketBooksModel();
	final JTable m_tab = new MSTopTable( m_model);
	final TCombo<MktDataType> m_typeCombo = new TCombo<MktDataType>( MktDataType.values() );

	MSMarketBookPanel() {
		m_typeCombo.removeItemAt( 0);
		MSTabbedPanel.fitTableColumns(m_tab);
		JScrollPane scroll = new JScrollPane( m_tab);

		HtmlButton reqType = new HtmlButton( "Go") {
			@Override protected void actionPerformed() {
				onReqType();
			}
		};

		VerticalPanel butPanel = new VerticalPanel();
		butPanel.add( "Market data type", m_typeCombo, reqType);
		
		setLayout( new BorderLayout() );
		add( scroll);
		add( butPanel, BorderLayout.SOUTH);
	}
	
	public void updateTopModel(MSMarketBooksModel topModel) {
		//m_model.updateRows(topModel.getRows());
		m_model=topModel;
		m_tab.setModel(m_model);
		MSTabbedPanel.fitTableColumns(m_tab);
		m_model.fireTableDataChanged();
	}
	
	/** Called when the tab is first visited. */
	@Override public void activated() {
	}

	/** Called when the tab is closed by clicking the X. */
	@Override public void closed() {
		m_model.desubscribe();
	}

	void onReqType() {
		ApiDemo.INSTANCE.controller().reqMktDataType( m_typeCombo.getSelectedItem() );
	}
	
	class MSTopTable extends JTable {
		public MSTopTable(MSMarketBooksModel model) { 
			super( model); 
		}

		@Override public TableCellRenderer getCellRenderer(int rowIn, int column) {
			TableCellRenderer rend = super.getCellRenderer(rowIn, column);
			m_model.color( rend, rowIn, getForeground() );
			return rend;
		}
	}
}