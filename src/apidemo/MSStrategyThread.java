package apidemo;

import java.util.ArrayList;
import java.util.HashMap;

import apidemo.MSMarketBooksModel.MSMarketBookRow;

import com.ib.controller.NewComboLeg;
import com.ib.controller.NewContract;
import com.ib.controller.NewContractDetails;
import com.ib.controller.ApiController.IContractDetailsHandler;

public class MSStrategyThread implements Runnable {
	MSStrategyInfo strategyInfo=new MSStrategyInfo();
	ArrayList<MSComboLeg> comboLegs = new ArrayList<MSComboLeg>();
	HashMap<Integer,MSComboLeg> m_comboLegsMap=new HashMap<Integer,MSComboLeg>();
	MSMarketBooksModel marketBooksModel=new MSMarketBooksModel();
	boolean start=true;
	MSOrdersModel strategyOrdersModel=new MSOrdersModel();
	MSIndicatorManager strategyIndicatorManager=new MSIndicatorManager();
	MSPositionModel m_positionModel=new MSPositionModel();
	
	MSStrategyThread(MSStrategyInfo sI) {
		strategyInfo=sI;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		String ThreadName=Thread.currentThread().getName();
		setContractDetails();
		
		while(start) {
			try {
				synchronized(marketBooksModel) {
					marketBooksModel.fireTableDataChanged();
					if(ThreadName.equals("1")) {
						strategyIndicatorManager.caculate();
					}
					marketBooksModel.wait();
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	public void setContractDetails() {
		for(int i:strategyInfo.msLegs.keySet()) {
			MSComboLeg leg=strategyInfo.getLeg(i);
			NewContract m_contract=leg.m_contract;
			
			final NewComboLeg m_leg = leg.m_leg;
			final int m_legID=leg.m_legID;
			final HashMap<String,String> m_legConfig=leg.m_legConfig;
			
			ApiDemo.INSTANCE.controller().reqContractDetails(m_contract, new IContractDetailsHandler() {
				@Override public void contractDetails(ArrayList<NewContractDetails> list) {
					for (NewContractDetails details : list) {
						addComboLeg( details,m_leg,m_legID,m_legConfig);
					}
				}
			});
			
			marketBooksModel.addRow(m_legID,m_contract);
			int targetAmount=1000;
			try {
				String sTargetAmount=leg.m_legConfig.get("TargetAmount");
				if(sTargetAmount!=null) {
					sTargetAmount=sTargetAmount.replaceAll(",", "");
					targetAmount=Integer.valueOf(sTargetAmount);
				}
			} catch(NumberFormatException e) {
				e.printStackTrace();
			}
			MSPosition position=new MSPosition(this);
			position.setLegID(m_legID);
			position.setTargetAmount(targetAmount);
			m_positionModel.addPosition(m_legID, position);
		}
		MSIndicator indicator=new MSIndicator1(this);
		indicator.setIndicators(strategyInfo.strategyParam);
		strategyIndicatorManager.setIndicator(indicator);
	}
	
	protected synchronized void addComboLeg(NewContractDetails contractDetails, NewComboLeg leg, int legID, HashMap<String,String> legConfig) {
    	NewContract c = contractDetails.contract();
    	    	
		leg.conid( c.conid() );
		leg.exchange( c.exchange() );

		MSComboLeg row = new MSComboLeg(legID, c, leg, legConfig);
		comboLegs.add(row);
		m_comboLegsMap.put(legID, row);
	}
	
	protected synchronized ArrayList getComboLegs() {
		return comboLegs;
	}
	
	protected synchronized MSMarketBooksModel getMarketBooksModel() {
		return marketBooksModel;
	}
	
}