package apidemo;

import java.io.File;
import java.util.Enumeration;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;

import com.ib.controller.NewContract;

import apidemo.util.NewTabbedPanel;
import apidemo.util.NewTabbedPanel.INewTab;

public class MSTabbedPanel extends NewTabbedPanel implements INewTab{
	private MSConfigPanel mSConfigPanel=new MSConfigPanel();
	public MSOrdersPanel mSOrdersPanel=new MSOrdersPanel();
	private MSMarketBookPanel mSMarketBookPanel=new MSMarketBookPanel();
	private MSPositionPanel mSPositionPanel=new MSPositionPanel();
	
	MSTabbedPanel() {
		NewTabbedPanel tabs=this;
		//mSConfigPanel.setBorder((Border) new EtchedBorder(EtchedBorder.RAISED));
		//mSConfigPanel.setLayout(null);
		tabs.addTab("StrategyConfig",mSConfigPanel);
		tabs.addTab("Live Orders", mSOrdersPanel);
		tabs.addTab("MarketBookOfLeg", mSMarketBookPanel);
		tabs.addTab("PositionPanel", mSPositionPanel);
		//strategyConfigFileChooser();
	}
	
	public static void fitTableColumns(JTable myTable)
    {
		DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
		tcr.setHorizontalAlignment(SwingConstants.CENTER);
		myTable.setDefaultRenderer(Object.class, tcr);
        myTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        JTableHeader header = myTable.getTableHeader();
        int rowCount = myTable.getRowCount();
        Enumeration columns = myTable.getColumnModel().getColumns();
        while(columns.hasMoreElements()) {
            TableColumn column = (TableColumn)columns.nextElement();
            int col = header.getColumnModel().getColumnIndex(column.getIdentifier());
            int width = (int)header.getDefaultRenderer().getTableCellRendererComponent
            (myTable, column.getIdentifier(), false, false, -1, col).getPreferredSize().getWidth();
            for(int row = 0; row < rowCount; row++){
                int preferedWidth = (int)myTable.getCellRenderer(row, col).getTableCellRendererComponent
                (myTable, myTable.getValueAt(row, col), false, false, row, col).getPreferredSize().getWidth();
                width = Math.max(width, preferedWidth);
            }
            header.setResizingColumn(column); // 此行很重要
            column.setWidth(width+myTable.getIntercellSpacing().width+20);
        }
    }
	
	public void addConfigTable(Object[][] cellData) {
		mSConfigPanel.addTable(cellData);
	}
	
	public void updateMarketBookOfLegPanel(MSMarketBooksModel topModel) {
		mSMarketBookPanel.updateTopModel(topModel);
	}
	
	public void updateStrategyLiveOrder(MSOrdersModel ordersModel) {
		mSOrdersPanel.updateOrdersModel(ordersModel);
	}
	
	public void updatePositionPanel(MSPositionModel positionModel) {
		mSPositionPanel.updatePositionModel(positionModel);
	}
	
	public void activated() {
		mSOrdersPanel.activated();
	}
	
	public void closed() {
	}
}