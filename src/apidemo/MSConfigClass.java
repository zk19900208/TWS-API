package apidemo;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.swing.JOptionPane;

public class MSConfigClass {
	
	Set<String> set=new HashSet<String>(Arrays.asList(new String[]{"Symbol","SecType","OptType","Action","Ratio","Multiplier","OptExpiry","OptStrike","Exchange","CURRENCY"}));;
	HashMap<String,String> hM_param=new HashMap<String,String>();
	HashMap<String,Leg> hM_legsConfig=new HashMap<String,Leg>();
	
	public void addAll(String[][] cellData) {
		String tag="";
		int l=cellData.length;
		for(int i=0;i<l;i+=2) {
			tag=cellData[i][0];
			if(tag.equals("StrategyID")) {
				addParam(cellData[i],cellData[i+1]);
			}
			if(tag.equals("LegID")) {
				addLeg(cellData[i],cellData[i+1]);
			}
		}
	}
	
	public void addParam(String[] keys,String[] values) {
		int lk=keys.length;
		int lv=values.length;
		if(lk==lv) {
			for(int i=1;i<lk;i++) {
				hM_param.put(keys[i], values[i]);
			}
		} else	{
			JOptionPane.showMessageDialog(JOptionPane.getRootFrame(),"Strategy "+values[0]+" 的参数名称与值的个数不同！！","警告",JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	public void addLeg(String[] keys,String[] values) {
		int lk=keys.length;
		int lv=values.length;
		if(lk==lv) {
			Leg leg=new Leg(keys,values);
			hM_legsConfig.put(values[0], leg);
		} else	{
			JOptionPane.showMessageDialog(JOptionPane.getRootFrame(),"Strategy "+values[0]+" 的参数名称与值的个数不同！！","警告",JOptionPane.INFORMATION_MESSAGE);
		}		
	}
	
	public void getLegConfig() {
		for(Object k:hM_legsConfig.keySet()) {
			Leg l=hM_legsConfig.get(k);
			System.out.println("legID"+k);
			for(Object k1:l.hM_legConfig.keySet()) {
				String s1=l.hM_legConfig.get(k1);
				System.out.println(k1+":"+s1);
			}
			for(Object k2:l.hM_legParam.keySet()) {
				String s2=l.hM_legParam.get(k2);
				System.out.println(k2+":"+s2);
			}
		}
		for(Object k3:hM_param.keySet()) {
			String s3=hM_param.get(k3);
			System.out.println(k3+":"+s3);
		}
	}
	
	public class Leg {
		
		HashMap<String,String> hM_legConfig=new HashMap<String,String>();
		HashMap<String,String> hM_legParam=new HashMap<String,String>();
		Set<String> tempset=new HashSet<String>();
		
		Leg(String[] keys,String[] values) {
			tempset.addAll(set);
			int l=keys.length;
			String tag="";
			for(int i=1;i<l;i++) {
				tag=keys[i];
				if(set.contains(tag)) {
					addLegConfig(keys[i],values[i]);
					tempset.remove(tag);
				} else {
					addLegParam(keys[i],values[i]);
				}
			}
			Iterator<String> it=tempset.iterator();
			while(it.hasNext()) {
				addLegConfig(it.next(),"");
			}
			tempset.clear();
		}
		
		public void addLegConfig(String key,String value) {
			hM_legConfig.put(key, value);
		}
		
		public void addLegParam(String key,String value) {
			hM_legParam.put(key, value);
		}
	}

}