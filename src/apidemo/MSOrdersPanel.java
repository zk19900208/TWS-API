package apidemo;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.table.AbstractTableModel;

import apidemo.MSOrderQueue.orderTypes;
import apidemo.OrdersPanel.OrderRow;
import apidemo.util.HtmlButton;
import apidemo.util.VerticalPanel;

import com.ib.controller.NewContract;
import com.ib.controller.NewOrder;
import com.ib.controller.NewOrderState;
import com.ib.controller.OrderStatus;
import com.ib.controller.OrderType;
import com.ib.controller.ApiController.ILiveOrderHandler;

public class MSOrdersPanel extends JPanel {
	public MSOrdersModel m_model=new MSOrdersModel();
	MSOrdersModel m=new MSOrdersModel();
	private JTable m_table=new JTable(m);
	
	MSOrdersPanel() {
		JScrollPane scrollPane=new JScrollPane(m_table);
		scrollPane.setBorder(new TitledBorder("Strategy Live Orders"));
		MSTabbedPanel.fitTableColumns(m_table);
		m_table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					onDoubleClick();
				}
			}
		});
		
		HtmlButton ticket = new HtmlButton( "Place New Order") {
			@Override public void actionPerformed() {
				onPlaceOrder();
			}
		};

		HtmlButton modify = new HtmlButton( "Modify Selected Order") {
			@Override public void actionPerformed() {
				onDoubleClick();
			}
		};

		HtmlButton attach = new HtmlButton( "Attach New Order to Selected Order") {
			@Override public void actionPerformed() {
				onAttachOrder();
			}
		};

		HtmlButton reqExisting = new HtmlButton( "Take Over Existing TWS Orders") {
			@Override public void actionPerformed() {
				onTakeOverExisting();
			}
		};

		HtmlButton reqFuture = new HtmlButton( "Take Over Future TWS Orders") {
			@Override public void actionPerformed() {
				onTakeOverFuture();
			}
		};

		HtmlButton cancel = new HtmlButton( "Cancel Selected Order") {
			@Override public void actionPerformed() {
				onCancel();
			}
		};

		HtmlButton cancelAll = new HtmlButton( "Cancel All Orders") {
			@Override public void actionPerformed() {
				onCancelAll();
			}
		};

		HtmlButton refresh = new HtmlButton( "Refresh") {
			@Override public void actionPerformed() {
				onRefresh();
			}
		};

		JPanel buts = new VerticalPanel();
		buts.add( ticket);
		buts.add( modify);
		buts.add( attach);
		buts.add( cancel);
		buts.add( cancelAll);
		buts.add( reqExisting);
		buts.add( reqFuture);
		buts.add( refresh);
		
		setLayout( new BorderLayout() );
		add( buts, BorderLayout.EAST);
		add( scrollPane);
	}

	protected void onDoubleClick() {
		MSOrderRow order = getSelectedOrder();
		if (order != null) {
			TicketDlg dlg = new TicketDlg( order.m_contract, order.m_order);
			dlg.setVisible( true);
		}
	}

	protected void onTakeOverExisting() {
		ApiDemo.INSTANCE.controller().takeTwsOrders( m_model);
	}

	protected void onTakeOverFuture() {
		ApiDemo.INSTANCE.controller().takeFutureTwsOrders( m_model);
	}

	protected void onCancel() {
		MSOrderRow order = getSelectedOrder();
		if (order != null) {
			ApiDemo.INSTANCE.controller().cancelOrder( order.m_order.orderId() );
		}
	}

	protected void onCancelAll() {
		ArrayList<MSOrderRow> m_orders=m.getOrders();
		for(int i=0;i<m_orders.size();i++) {
			ApiDemo.INSTANCE.controller().cancelOrder( m_orders.get(i).m_order.orderId());
		}
		//ApiDemo.INSTANCE.controller().cancelAllOrders();
	}

	private MSOrderRow getSelectedOrder() {
		int i = m_table.getSelectedRow();
		return i != -1 ? m.get( i) : null;
	}
	
	private static void onPlaceOrder() {
		TicketDlg dlg = new TicketDlg( null, null);
		dlg.setVisible( true);
	}
	
	protected void onAttachOrder() {
		MSOrderRow row = getSelectedOrder();
		if (row != null) {
			NewOrder parent = row.m_order;
			
			NewOrder child = new NewOrder();
			child.parentId( parent.orderId() );
			child.action( parent.action() );
			child.totalQuantity( parent.totalQuantity() );
			child.orderType( OrderType.TRAIL);
			child.auxPrice( 1);
			
			TicketDlg dlg = new TicketDlg( row.m_contract.clone(), child);
			dlg.setVisible( true);
		}
	}
	
	public void activated() {
		onRefresh();
	}
	
	protected void onRefresh() {
		//m_model.clear();
		//m_table.setModel(m);
		m.fireTableDataChanged();
		ApiDemo.INSTANCE.controller().reqLiveOrders( m_model);
	}

	public void updateOrdersModel(MSOrdersModel ordersModel) {
		// TODO Auto-generated method stub
		m_table.setModel(ordersModel);
		m=(MSOrdersModel) m_table.getModel();
		ordersModel.fireTableDataChanged();
		//m=ordersModel;
		//m.fireTableDataChanged();
	}
}

class MSOrdersModel extends AbstractTableModel implements ILiveOrderHandler {
	private HashMap<Long,MSOrderRow> m_map = new HashMap<Long,MSOrderRow>();
	private ArrayList<MSOrderRow> m_orders = new ArrayList<MSOrderRow>();
	private String[] columnNames={"Perm ID","Client ID","Leg ID","Order ID","Account",
			"Action","Quantity","Contract","Status","Filled","avgFillPrice"};

	@Override public int getRowCount() {
		return m_orders.size();
	}

	public ArrayList getOrders() {
		return m_orders;
	}
	
	public void clear() {
		m_orders.clear();
		m_map.clear();
	}

	public MSOrderRow get(int i) {
		return m_orders.get( i);
	}
	
	@Override
	public void fireTableDataChanged() {
		super.fireTableDataChanged();
	}

	@Override 
	public void openOrder(NewContract contract, NewOrder order, NewOrderState 
			orderState) {
		MSOrderRow full = m_map.get( order.permId() );
		System.out.println("MSOrdersPanel.openOrder;"+Thread.currentThread().getName());
		if (full != null) {
			full.m_order = order;
			full.m_state = orderState;
			fireTableDataChanged();
		}
		else if (shouldAdd(contract, order, orderState) ) {
			full = new MSOrderRow( contract, order, orderState, 0);
			add( full);
			m_map.put( order.permId(), full);
			System.out.println("MSOrdersPanel.openOrders();m_map;currentThreadName:"+
					Thread.currentThread().getName());
			
			synchronized(MSPortfolioThread.ordersQueue) {
				try {
					System.out.println("MSOrdersPanel.openOrders();currentThreadName:"+
							Thread.currentThread().getName());
					MSOrderQueue orderQueue=new MSOrderQueue(orderTypes.MSOrderRow,full);
					MSPortfolioThread.ordersQueue.put(orderQueue);
					MSPortfolioThread.ordersQueue.notify();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			fireTableDataChanged();
		}
	}

	protected boolean shouldAdd(NewContract contract, NewOrder order, NewOrderState orderState) {
		return true;
	}

	protected void add(MSOrderRow full) {
		m_orders.add( full);
	}

	@Override public void openOrderEnd() {
	}
	
	@Override 
	public void orderStatus(int orderId, OrderStatus status, int filled, int 
			remaining, double avgFillPrice, long permId, int parentId, double 
			lastFillPrice, int clientId, String whyHeld) {
		MSOrderRow full = m_map.get( permId);
		if (full != null) {
			full.m_state.status( status);
			full.setFilled(filled);
			full.setRemaining(remaining);
			full.setAvgFillPrice(avgFillPrice);
		}
		fireTableDataChanged();
		synchronized(MSPortfolioThread.ordersQueue) {
			try {
				System.out.println("MSOrdersPanel.OrderStatus();currentThreadName:"+
						Thread.currentThread().getName());
				MSOrderQueue orderQueue=new MSOrderQueue(orderTypes.MSOrderStatus,full);
				MSPortfolioThread.ordersQueue.put(orderQueue);
				MSPortfolioThread.ordersQueue.notify();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	@Override public int getColumnCount() {
		return columnNames.length;
	}
	
	@Override public String getColumnName(int col) {
		return columnNames[col];
	}
	
	@Override public Object getValueAt(int row, int col) {
		MSOrderRow fullOrder = m_orders.get( row);
		NewOrder order = fullOrder.m_order;
		switch( col) {
			case 0: return order.permId();
			case 1: return order.clientId();
			case 2: return fullOrder.m_legID;
			case 3: return order.orderId();
			case 4: return order.account();
			case 5: return order.action();
			case 6: return order.totalQuantity();
			case 7: return fullOrder.m_contract.description();
			case 8: return fullOrder.m_state.status();
			case 9: return fullOrder.m_filled;
			case 10: return fullOrder.m_avgFillPrice;
			default: return null;
		}
	}

	@Override public void handle(int orderId, int errorCode, String errorMsg) {
	}
}

class MSOrderRow {
	NewContract m_contract;
	NewOrder m_order;
	NewOrderState m_state;
	int m_filled;
	int m_remaining;
	double m_avgFillPrice;
	int m_legID;

	MSOrderRow() {
	}
	
	public void setRemaining(int remaining) {
		// TODO Auto-generated method stub
		m_remaining=remaining;
	}

	MSOrderRow( NewContract contract, NewOrder order, NewOrderState state, int filled) {
		m_contract = contract;
		m_order = order;
		m_state = state;
		m_filled=filled;
	}
	
	public void setFilled(int filled) {
		m_filled=filled;
	}
	
	public void setAvgFillPrice(double avgFillPrice) {
		m_avgFillPrice=avgFillPrice;
	}
	
	public void setLegID(int legID) {
		m_legID=legID;
	}
}

class Key {
	int m_clientId;
	int m_orderId;
	
	Key( int clientId, int orderId) {
		m_clientId = clientId;
		m_orderId = orderId;
	}
}