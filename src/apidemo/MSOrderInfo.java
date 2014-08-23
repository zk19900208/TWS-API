package apidemo;

import com.ib.controller.NewContract;
import com.ib.controller.NewOrder;

class MSOrderInfo {
	String m_strategyLegID;
	NewContract m_contract;
	NewOrder m_order;
	
	MSOrderInfo(String id,NewContract contract,NewOrder order) {
		m_strategyLegID=id;
		m_contract=contract;
		m_order=order;
	}
}