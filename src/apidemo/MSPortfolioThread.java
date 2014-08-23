package apidemo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import apidemo.MSOrderQueue.orderTypes;

import com.ib.controller.NewContract;
import com.ib.controller.NewOrder;
import com.ib.controller.NewOrderState;
import com.ib.controller.ApiController.IOrderHandler;

public class MSPortfolioThread implements Runnable {
	HashMap<Integer,MSStrategyInfo> hM_MSStrategyInfo=new HashMap<Integer,MSStrategyInfo>();
	Vector strategyFlags=new Vector();
	HashMap<Integer,MSStrategyThread> hM_MSStrategyThread=new HashMap<Integer,MSStrategyThread>();
	static int queue_size=1000;
	boolean start=true;
	//public static BlockingQueue<MSOrderInfo> orderQueue = new ArrayBlockingQueue<MSOrderInfo>(queue_size); 
	public HashMap<Integer,String> orderID_strategyID=new HashMap<Integer,String>();
	//public static BlockingQueue<String> orderQueue = new ArrayBlockingQueue<String>(queue_size); 
	//public static BlockingQueue<MSOrderRow> ordersRow=new ArrayBlockingQueue<MSOrderRow>(queue_size);
	public static BlockingQueue<MSOrderQueue> ordersQueue=new ArrayBlockingQueue<MSOrderQueue>(queue_size);
	
	public void addStrategyInfo(int strategyID, MSStrategyInfo strategyInfo) {
		hM_MSStrategyInfo.put(strategyID, strategyInfo);
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		for(int strategyID:hM_MSStrategyInfo.keySet()) {
			try {
				MSStrategyInfo sI=hM_MSStrategyInfo.get(strategyID);
				MSStrategyThread strategyThreadClass=new MSStrategyThread(sI);
				hM_MSStrategyThread.put(strategyID, strategyThreadClass);
				Thread strategyThread=new Thread(strategyThreadClass,Integer.toString(strategyID));
				strategyThread.start();
			} catch(NullPointerException e) {
				e.printStackTrace();
			}
		}
		
		while(start) {
			synchronized(ordersQueue) {
				if(!ordersQueue.isEmpty()) {
					MSOrderQueue orderQueue=ordersQueue.poll();
					orderTypes type=orderQueue.m_type;
					switch(type) {					
						case MSOrderInfo:
							System.out.println("MSPortfolioThread.run();placeOrder,"+Thread.currentThread().getName());
							placeOrder(orderQueue.m_orderInfo);
							break;
						case MSOrderRow:
							try {
								String strategyLegID=orderID_strategyID.get(orderQueue.m_orderRow.m_order.orderId());
								System.out.println("MSPortfolioThread.run();addorderRow,"+Thread.currentThread().getName()+";ordersRow;"+strategyLegID);
								String[] strategyLeg=strategyLegID.split("-");
								int strategyID=Integer.valueOf(strategyLeg[0]);
								int legID=Integer.valueOf(strategyLeg[1]);
								MSStrategyThread strategyThread=hM_MSStrategyThread.get(strategyID);
								orderQueue.m_orderRow.setLegID(legID);
								strategyThread.strategyOrdersModel.add(orderQueue.m_orderRow);
								strategyThread.m_positionModel.add(orderQueue.m_orderRow);
							} catch(NullPointerException e) {
								e.printStackTrace();
							} finally {
								break;
							}
						case MSOrderStatus:
							String strategyLegID=orderID_strategyID.get(orderQueue.m_orderRow.m_order.orderId());
							String[] strategyLeg=strategyLegID.split("-");
							int strategyID=Integer.valueOf(strategyLeg[0]);
							MSStrategyThread strategyThread=hM_MSStrategyThread.get(strategyID);
							strategyThread.strategyOrdersModel.fireTableDataChanged();;
							strategyThread.m_positionModel.fireTableDataChanged();
						default:
							System.out.println("MSPortfolioThread.run();orderTypes==Other");
					}
					//System.out.println(Thread.currentThread().getName()+";"+orderQueue.poll());
				} else {
					try {
						ordersQueue.wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		
	}
	
	public MSStrategyThread getStrategy(int strategyID) {
		MSStrategyThread strategyThread=hM_MSStrategyThread.get(strategyID);
		return strategyThread;
	}
	
	public synchronized void placeOrder(MSOrderInfo orderInfo) {
		final NewContract m_contract=orderInfo.m_contract;
		final NewOrder m_order=orderInfo.m_order;
		int orderID=ApiDemo.INSTANCE.controller().placeMyModifyOrder( m_contract, m_order, new IOrderHandler() {
			@Override public void orderState(NewOrderState orderState) {
				ApiDemo.INSTANCE.controller().removeOrderHandler( this);
			}
			@Override public void handle(int errorCode, final String errorMsg) {
				m_order.orderId( 0);
			}
		});
		String strategyLegID=orderInfo.m_strategyLegID;
		orderID_strategyID.put(orderID,strategyLegID);
	}
}