package apidemo;

import java.util.ArrayList;

import apidemo.MSMarketBooksModel.MSMarketBookRow;
import apidemo.MSOrderQueue.orderTypes;

import com.ib.controller.NewContract;
import com.ib.controller.NewOrder;
import com.ib.controller.Types.Action;
import com.ib.controller.Types.SecType;

public class MSIndicator1 extends MSIndicator {
	
	public double mean,sigma;
	private ArrayList<MSMarketBookRow> m_marketBookRows;
	MSMarketBookRow leg1,leg2;
	boolean test=true;
	int k=0;
	
	protected MSIndicator1(MSStrategyThread strategyThread) {
		super(strategyThread);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void calculate() {
		// TODO Auto-generated method stub
		m_marketBookRows=marketBooksModel.getRows();
		MSMarketBookRow row=m_marketBookRows.get(0);
		if(row.m_legID==1) {
			leg1=row;
			leg2=m_marketBookRows.get(1);
		} else {
			leg2=row;
			leg1=m_marketBookRows.get(1);
		}
		MSPosition leg1_position=m_strategyThread.m_positionModel.m_positionMap.get(1);
		int legAmount=0;
		if(leg1_position.getTargetAmount()>=(leg1_position.getCurrentPosition()+leg1_position.Remaining+100)) {
			legAmount=100;
		} else if(leg1_position.getTargetAmount()<=(leg1_position.getCurrentPosition()+leg1_position.Remaining)) {
			legAmount=0;
		} else {
			legAmount=leg1_position.getTargetAmount()-(leg1_position.getCurrentPosition()+leg1_position.Remaining);
		}
	
		int strategyID=Integer.valueOf(Thread.currentThread().getName());
		String strategyLegID="";
		//if(((leg1.m_bid/leg2.m_ask)>=(mean+2*sigma)) && (legAmount>=0)){
		if(test) {
			int j=m_strategyThread.m_comboLegsMap.size();
			if(j!=2) {
				return;
			}
			MSComboLeg comboLeg=m_strategyThread.m_comboLegsMap.get(1);
			NewContract contract1=comboLeg.m_contract;
			NewOrder order1=new NewOrder();
			//leg1.m_ask=39;
			order1.lmtPrice(leg1.m_ask);
	        order1.action(Action.BUY);
	        order1.totalQuantity(legAmount);
	        strategyLegID=String.valueOf(strategyID)+"-"+String.valueOf(comboLeg.m_legID);
	        MSOrderInfo orderInfo1=new MSOrderInfo(strategyLegID,contract1,order1);
	        
	        comboLeg=m_strategyThread.m_comboLegsMap.get(2);
	        NewContract contract2=comboLeg.m_contract;
			NewOrder order2=new NewOrder();
			//leg2.m_bid=37;
			order2.lmtPrice(leg2.m_bid);
	        order2.action(Action.SELL);
	        order2.totalQuantity(legAmount);
	        strategyLegID=String.valueOf(strategyID)+"-"+String.valueOf(comboLeg.m_legID);
	        MSOrderInfo orderInfo2=new MSOrderInfo(strategyLegID,contract2,order2);
	        
			synchronized(MSPortfolioThread.ordersQueue) {
			try {
				//MSPortfolioThread.orderQueue.put(Thread.currentThread().getName());
				MSOrderQueue orderQueue1=new MSOrderQueue(orderTypes.MSOrderInfo,orderInfo1);
				MSPortfolioThread.ordersQueue.put(orderQueue1);
				MSOrderQueue orderQueue2=new MSOrderQueue(orderTypes.MSOrderInfo,orderInfo2);
				MSPortfolioThread.ordersQueue.put(orderQueue2);
				k+=1;
				if(k>=3) {
					test=false;
				}
				MSPortfolioThread.ordersQueue.notify();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
		} else if(((leg1.m_ask/leg2.m_bid)<=(mean-2*sigma)) && (legAmount>=0)) {
			NewContract contract1=m_strategyThread.m_comboLegsMap.get(1).m_contract;
			NewOrder order1=new NewOrder();
			order1.lmtPrice(leg1.m_bid);
	        order1.action(Action.SSHORT);
	        order1.totalQuantity(legAmount);
	        MSOrderInfo orderInfo1=new MSOrderInfo(strategyLegID,contract1,order1);
	        
	        NewContract contract2=m_strategyThread.m_comboLegsMap.get(2).m_contract;
			NewOrder order2=new NewOrder();
			order2.lmtPrice(leg2.m_ask);
	        order2.action(Action.BUY);
	        order2.totalQuantity(legAmount);
	        MSOrderInfo orderInfo2=new MSOrderInfo(strategyLegID,contract2,order2);
	        
			//synchronized(MSPortfolioThread.ordersQueue) {
			try {
				//MSPortfolioThread.orderQueue.put(Thread.currentThread().getName());
				MSOrderQueue orderQueue1=new MSOrderQueue(orderTypes.MSOrderInfo,orderInfo1);
				MSPortfolioThread.ordersQueue.put(orderQueue1);
				MSOrderQueue orderQueue2=new MSOrderQueue(orderTypes.MSOrderInfo,orderInfo2);
				MSPortfolioThread.ordersQueue.put(orderQueue2);
				MSPortfolioThread.ordersQueue.notify();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		mean=indicators.get("Mean");
		if((Double)mean==null) {
			mean=999;
			System.out.println("Original indicators have no mean!");
		}
		sigma=indicators.get("sigma");
		if((Double)sigma==null) {
			sigma=999;
			System.out.println("Original indicators have no mean!");
		}
	}

}
