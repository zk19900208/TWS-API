package apidemo;

import java.util.ArrayList;

import com.ib.controller.NewOrder;
import com.ib.controller.Types.Action;

public class MSPosition implements Comparable<MSPosition> {
	private int m_currentPosition=0,m_targetAmount=1000,m_targetPosition=0,m_remaining=0;
    public double m_avgFillPrice,m_total,m_commission;
    MSStrategyThread m_strategyThread;
    ArrayList<MSOrderRow> m_orderRows=new ArrayList<MSOrderRow>();
    public int m_legID,Quantity;
    public String Contract,Status,OrdersID="";
    public int Filled,Remaining;

    public MSPosition(MSStrategyThread strategyThread) {
    	m_strategyThread=strategyThread;
	}

	public void setAvgFillPrice(double avgFillPrice) {
        m_avgFillPrice = avgFillPrice;
    }
    
    public void setCurrentPosition(int currentPosition) {
    	m_currentPosition=currentPosition;
    }
    
    public void setTargetAmount(int targetAmount) {
    	m_targetAmount=targetAmount;
    }
    
    public void setTargetPosition(int targetPosition) {
    	m_targetPosition=targetPosition;
    }
    
    public void setUnfilledPosition(int unfilledPosition) {
    	m_remaining=unfilledPosition;
    }
    
    public void setLegID(int legID) {
    	m_legID=legID;
    }
    
    public void addOrderRow(MSOrderRow orderRow) {
    	m_orderRows.add(orderRow);
    	NewOrder order=orderRow.m_order;
    	Quantity+=order.totalQuantity();
    	Contract=orderRow.m_contract.description();
    	OrdersID+=("/"+order.orderId());
    }

	public void updatePosition() {
		Filled=0;
		Remaining=0;
		m_total=0;
		for(int i=0;i<m_orderRows.size();i++) {
			MSOrderRow orderRow=m_orderRows.get(i);
			NewOrder order=orderRow.m_order;
			
			if(order.action().equals(Action.BUY)) {
				Filled+=orderRow.m_filled;
				Remaining+=orderRow.m_remaining;
				m_total+=(orderRow.m_filled*orderRow.m_avgFillPrice);
			} else if(order.action().equals(Action.SELL)) {
				Filled-=orderRow.m_filled;
				Remaining-=orderRow.m_remaining;
				m_total-=(orderRow.m_filled*orderRow.m_avgFillPrice);
			}
		}
		if (Filled==m_targetAmount) {
			System.out.println("MSPosition.updatePosition();m_targetPosition="+m_targetAmount);
			Status="Finished";
		} else{
			Status="UnFinished";
		}
	}

	public double getAvgFillPrice() {
        return m_avgFillPrice;
    }
    
    public int getCurrentPosition() {
        return m_currentPosition;
    }

    public int getTargetAmount() {
        return m_targetAmount;
    }
    
    public int getTargetPosition() {
    	return m_targetPosition;
    }

	@Override
	public int compareTo(MSPosition arg0) {
		return m_legID>arg0.m_legID ? 1:-1;
	}
    
}
