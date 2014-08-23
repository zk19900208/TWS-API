package apidemo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import com.ib.controller.NewComboLeg;
import com.ib.controller.NewContract;
import com.ib.controller.NewOrder;

public class MSStrategyInfo {
	HashMap<Integer,MSComboLeg> msLegs=new HashMap<Integer,MSComboLeg>();
	HashMap<String,String> strategyParam=new HashMap<String,String>();

	public MSStrategyInfo() {
	}
	
	public void addLeg(int legID, MSComboLeg msLeg) {
		msLegs.put(legID,msLeg);
	}
	
	public void clear() {
		msLegs.clear();
	}
	
	public int size() {
		return msLegs.size();
	}
	
	public MSComboLeg getLeg(int i) {
		return msLegs.get(i);
	}
	
}

class MSComboLeg {
	public int m_legID;
	public NewContract m_contract;
	public NewComboLeg m_leg;
	public boolean m_selected=true;
	public HashMap<String,String> m_legConfig=new HashMap<String,String>();
	
	public MSComboLeg(int legID, NewContract c, NewComboLeg leg, HashMap<String,String> hm) {
		m_legID=legID;
		m_contract = c;
		m_leg = leg;
		m_legConfig=hm;
	}
	
}