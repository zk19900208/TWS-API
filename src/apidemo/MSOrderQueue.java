package apidemo;

public class MSOrderQueue {
	public enum orderTypes {
		MSOrderInfo,MSOrderRow,MSOrderStatus,Other
	}
	
	orderTypes m_type=orderTypes.Other;
	MSOrderInfo m_orderInfo=null;
	MSOrderRow m_orderRow=null;
	
	MSOrderQueue(orderTypes type,MSOrderInfo orderInfo) {
		m_type=type;
		m_orderInfo=orderInfo;
	}
	
	MSOrderQueue(orderTypes type,MSOrderRow orderRow) {
		m_type=type;
		m_orderRow=orderRow;
	}
	
}