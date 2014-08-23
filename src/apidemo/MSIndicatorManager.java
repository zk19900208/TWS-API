package apidemo;

import java.util.ArrayList;
import java.util.List;

public class MSIndicatorManager {
	private  MSIndicator indicator;
	
	public MSIndicatorManager() {
    }
	
	public void setIndicator(MSIndicator indicator) {
		this.indicator=indicator;
	}
	
	public void caculate() {
		if(indicator!=null) {
			indicator.calculate();
		}
	}
}
