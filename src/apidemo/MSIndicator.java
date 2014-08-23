package apidemo;

import java.util.HashMap;

import apidemo.MSMarketBooksModel.MSMarketBookRow;

public abstract class MSIndicator {
	protected HashMap<String,Double> indicators;
    protected MSMarketBooksModel marketBooksModel;
    protected MSStrategyThread m_strategyThread;
    
    public abstract void calculate();

    public abstract void reset();

    protected MSIndicator(MSStrategyThread strategyThread) {
    	m_strategyThread=strategyThread;
    	marketBooksModel=strategyThread.marketBooksModel;
    }
    
    public void setIndicators(HashMap<String,String> map) {
    	indicators=new HashMap<String,Double>();
    	for(String key:map.keySet()) {
    		if(!key.equals("StrategyID") && !key.equals("StrategyName")) {
    			try {
    				Double value=Double.valueOf(map.get(key));
    				indicators.put(key, value);
    			} catch(NumberFormatException e) {
    				e.printStackTrace();
    			}
    		}
    	}
    }

    public void setMarketBook(MSMarketBooksModel marketBooksModel) {
        this.marketBooksModel = marketBooksModel;
    }

}
