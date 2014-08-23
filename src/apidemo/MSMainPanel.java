package apidemo;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

import com.ib.controller.NewComboLeg;
import com.ib.controller.NewContract;
import com.ib.controller.NewContractDetails;
import com.ib.controller.ApiController.IContractDetailsHandler;
import com.ib.controller.Types.Action;
import com.ib.controller.Types.Right;
import com.ib.controller.Types.SecType;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import apidemo.MSComboLeg;
import apidemo.MSConfigClass.Leg;
import apidemo.StrategyRow;
import apidemo.util.NewTabbedPanel;
import apidemo.util.NewTabbedPanel.NewTabPanel;

public class MSMainPanel extends NewTabPanel{
	
	private JPanel buttonPanel = new JPanel();
	
	private Object[][] cellData;
	private HashMap<Integer,MSConfigClass>  hM_MSConfigClass=new HashMap<Integer,MSConfigClass>();
	private JButton jBAddStrategyFromExcel=new JButton("AddStrategyFromExcel");
	
	private JButton jBAddStrategyFromPanel=new JButton("AddStrategyFromPanel");
	private JButton jBPlaceOrder=new JButton("PlaceOrder");
	private JButton jBStartStrategy=new JButton("StartStrategy");
	private JButton jBTest=new JButton("Test");
	
	private MSStrategyModel m_strategyModel=new MSStrategyModel();
	private JTable jTMyStrategy=new JTable(m_strategyModel);
	private JScrollPane jSPMyStrategy=new JScrollPane(jTMyStrategy);
	private JPanel strategyPanel = new JPanel();
	
	private HashMap<Integer,MSStrategyInfo> hM_MSStrategyInfo=new HashMap<Integer,MSStrategyInfo>();
	
	private JPopupMenu m_popupMenu=new JPopupMenu();
	private JMenuItem JMI_runStrategy=new JMenuItem("Run Strategy");
	private JMenuItem JMI_stopStrategy=new JMenuItem("Stop Strategy");
	private JMenuItem JMI_deleteStrategy=new JMenuItem("Delete Strategy");
	private JMenuItem JMI_setupEquityStrategy=new JMenuItem("Setup Equity Strategy");
	private JMenuItem JMI_setupOptionStrategy=new JMenuItem("Setup Option Strategy");
	private JMenuItem JMI_test=new JMenuItem("Test");
	
	private MSComboLegModel m_comboLegModel=new MSComboLegModel();
	private JTable jTLegs=new JTable(m_comboLegModel);
	private JScrollPane jSPLegs=new JScrollPane(jTLegs);
	private JPanel legsPanel=new JPanel();
	
	private MSTabbedPanel mSTabbedPanel=new MSTabbedPanel();
	
	private MSPortfolioThread mSPortfolio=new MSPortfolioThread();
	private boolean strategiesStart=false;
	
	private ArrayList<Integer> runStrategy=new ArrayList<Integer>();
	
	MSMainPanel() {
		setLayout(null);
		
		buttonPanel.setBounds(10, 0, 900, 50);
		buttonPanel.setBorder((Border) new EtchedBorder(EtchedBorder.RAISED));
		buttonPanel.setLayout(null);
		jBAddStrategyFromExcel.setBounds(20, 10, 170, 30);
		jBAddStrategyFromPanel.setBounds(210, 10, 170, 30);
		jBPlaceOrder.setBounds(400,10,170,30);
		jBStartStrategy.setBounds(590,10,170,30);
		jBTest.setBounds(800,10,80,30);
		
		buttonPanel.add(jBAddStrategyFromExcel);
		buttonPanel.add(jBAddStrategyFromPanel);
		buttonPanel.add(jBPlaceOrder);
		buttonPanel.add(jBStartStrategy);
		buttonPanel.add(jBTest);
		
		MSTabbedPanel.fitTableColumns(jTMyStrategy);
		MSTabbedPanel.fitTableColumns(jTLegs);
		/*
		jTMyStrategy.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();// 设置jTMyStrategy内容居中
		tcr.setHorizontalAlignment(SwingConstants.CENTER);// 这句和上句作用一样
		jTMyStrategy.setDefaultRenderer(Object.class, tcr);
		
		jTLegs.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		jTLegs.setDefaultRenderer(Object.class, tcr);
		*/
		strategyPanel.setBounds(10,60,440,230);
		strategyPanel.setBorder(new TitledBorder( "My Strategies"));
		//strategyPanel.setLayout(null);
		jSPMyStrategy.setPreferredSize(new Dimension(430,190));
		strategyPanel.add(jSPMyStrategy);
		
		m_popupMenu.add(JMI_runStrategy);
		m_popupMenu.add(JMI_stopStrategy);
		m_popupMenu.add(JMI_deleteStrategy);
		m_popupMenu.add(JMI_setupEquityStrategy);
		m_popupMenu.add(JMI_setupOptionStrategy);
		m_popupMenu.add(JMI_test);
		
		
		legsPanel.setBounds(460, 60, 440, 230);
		legsPanel.setBorder(new TitledBorder("Combo Legs"));
		jSPLegs.setPreferredSize(new Dimension(430,190));
		legsPanel.add(jSPLegs);
		
		mSTabbedPanel.setBounds(10,300,900,220);
		mSTabbedPanel.setBorder((Border) new EtchedBorder(EtchedBorder.RAISED));
		//newTabbedPanel.setLayout(null);
		//mSTabbedPanel.addTab("new tab",mSTabbedPanel);
		add(buttonPanel);
		add(strategyPanel);
		add(legsPanel);
		add(mSTabbedPanel);
		assignListeners();
	}

	@Override
	public void activated() {
		// TODO Auto-generated method stub
	}

	@Override
	public void closed() {
		// TODO Auto-generated method stub
	}
	
	// AddStrategyFromExcel button Action1 chooseConfigFile
	public File chooseConfigFile() {
		JFileChooser jFileChooser = new JFileChooser("Title...");
		FileNameExtensionFilter filefilter = new FileNameExtensionFilter("Excel","xls");
		jFileChooser.setFileFilter(filefilter);
		File file1 = new File("C:\\");
		jFileChooser.setCurrentDirectory(file1);
		jFileChooser.setDialogTitle("Chose Your Strategy excel File");
		int result = jFileChooser.showOpenDialog(null);
		jFileChooser.setVisible(true);
		File selectedFile = jFileChooser.getSelectedFile();
		return selectedFile;
	}
	
	// AddStrategyFromeExcel button Action2 readConfigExcel
	public Object[][] readConfigFile(File f) {
		String[][] string_array = null;
		String s="";
		ArrayList<String> string_list=new ArrayList<String>();
		try {
			Workbook wb = Workbook.getWorkbook(f); // WorkBook
            Sheet sheet = wb.getSheet(0); //Sheet
            int row=0;
            for(int r=0;r<1000;r+=2) {
            	try {
            		s=sheet.getCell(0, r).getContents();
            	} catch(ArrayIndexOutOfBoundsException e) {
            		s="";
            	}
            	
                if(s=="") {
                	row=r-1;
                	break;
                }
            }
            string_array=new String[row][];
            s="";
            for(int r=0;r<row;r++) {
            	for(int c=0;c<26;c++) {
            		try {
            			s=sheet.getCell(c, r).getContents();
            		} catch(ArrayIndexOutOfBoundsException e) {
            			//e.printStackTrace();
            			s="";
            		}
            		if(s!="") {
            			string_list.add(s);
            		} else{
            			int size = string_list.size();
            			string_array[r]=(String[]) string_list.toArray(new String[size]);
            			string_list.clear();
            			break;
            		}
            	}
            }
            /* 输出二维数组
            System.out.println(Arrays.deepToString(string_array));
            string_array[0]=new String[]{"r1_c1","r1_c2"};
            for(int j=0;j<string_array.length;j++) {
            	System.out.println(Arrays.toString(string_array[j]));
            }
            */

            wb.close();
            
		} catch (IOException | BiffException e) {
            e.printStackTrace();
        }
		return string_array;
	}
	
	// 根据读出来的excel文件生成相应的MSConfigClass
	public void generateConfigClass(Object[][] cellData) {
		int l=cellData.length;
		int s=0,e=0;
		String tag="";
		for(int i=0;i<l;i++) {
			try {
    			tag=cellData[i][0].toString();
    		} catch(ArrayIndexOutOfBoundsException e1) {
    			tag="";
    		}
			if(tag.equals("StrategyID")) {
				s=i;
			} else if(tag.equals("EndStrat")) {
				e=i;
				String[][] strategy=new String[e-s][];
				for(int j=s;j<e;j++) {
					strategy[j-s]=(String[])cellData[j];
				}
				MSConfigClass strategyConfig=new MSConfigClass();
				strategyConfig.addAll(strategy);
				int id=Integer.parseInt(strategy[1][0].toString());
				hM_MSConfigClass.put(id, strategyConfig);
			}
		}
	}
	
	// 更新MyStrategies
	protected void updateMyStrategiesPanel() {
		int strategyID;
		Set<String> symbol=new HashSet<String>();
		Set<String> type=new HashSet<String>();
		// TODO Auto-generated method stub
		for(Integer i:hM_MSConfigClass.keySet()) {
			strategyID=i;
			MSConfigClass strategyConfig=hM_MSConfigClass.get(i);
			symbol=new HashSet<String>();
			type=new HashSet<String>();
			for(String key:strategyConfig.hM_legsConfig.keySet()) {
				Leg l=strategyConfig.hM_legsConfig.get(key);
				symbol.add(l.hM_legConfig.get("Symbol"));
				type.add(l.hM_legConfig.get("SecType"));
			}
			String strategySymbols=symbol.toString();
			String strategyTypes=type.toString();
			m_strategyModel.addStrategy(strategyID, strategySymbols, strategyTypes);
		}
		MSTabbedPanel.fitTableColumns(jTMyStrategy);
	}
	
	protected void generateComboLegs() {
		int strategyID;
		String temp="";
		int legId=-1;
		for(Integer i:hM_MSConfigClass.keySet()) {
			strategyID=i;
			MSConfigClass strategyConfig=hM_MSConfigClass.get(i);
			MSStrategyInfo strategyInfo=new MSStrategyInfo();
			strategyInfo.strategyParam=strategyConfig.hM_param;
			for(String key:strategyConfig.hM_legsConfig.keySet()) {
				legId=Integer.parseInt(key);
				Leg l=strategyConfig.hM_legsConfig.get(key);
				HashMap<String,String> legConfig=l.hM_legConfig;
				
				NewContract contract=new NewContract();
				NewComboLeg comboLeg=new NewComboLeg();
				
				contract.symbol(legConfig.get("Symbol"));
				String secType=legConfig.get("SecType");
				contract.secType(SecType.valueOf(SecType.class,secType));
				if(secType.equals("OPT")) {
					contract.right(Right.valueOf(Right.class,legConfig.get("OptType")));
					contract.strike(Double.parseDouble(legConfig.get("OptStrike")));
				}
				
				contract.expiry(legConfig.get("OptExpiry"));
				contract.multiplier(legConfig.get("Multiplier"));
				contract.currency(legConfig.get("CURRENCY"));
				contract.exchange(legConfig.get("Exchange"));
				
				contract.localSymbol("");
				contract.tradingClass("");
				
				temp=legConfig.get("Action");
				if(temp.equals("BUY")||temp.equals("SELL")) {
					comboLeg.action(Action.valueOf(Action.class, temp));
				} else {
					comboLeg.action(Action.Unkown);
				}
				
				temp=legConfig.get("Ratio");
				if(temp!="") {
					comboLeg.ratio(Integer.parseInt(temp));
				}
				MSComboLeg leg = new MSComboLeg(legId, contract, comboLeg, l.hM_legParam);
				strategyInfo.addLeg(legId,leg);
			}
			hM_MSStrategyInfo.put(strategyID, strategyInfo);
		}
	}
	
	// 更新ComboLegsTable
	protected synchronized void updateComboPanel(int strategyID) {
		if(strategiesStart==true) {
			MSStrategyThread mSStrategyThread=mSPortfolio.getStrategy(strategyID);
			ArrayList<MSComboLeg> comboLegs=mSStrategyThread.getComboLegs();
			m_comboLegModel.updateTable(comboLegs);
			//MSTabbedPanel.fitTableColumns(jTLegs);
		}
	}
	
	// 更新MSMarketBookOfLegPanel
	public synchronized void updateMSMarketBookPanel(int strategyID) {
		if(strategiesStart==true) {
			MSStrategyThread mSStrategyThread=mSPortfolio.getStrategy(strategyID);
			MSMarketBooksModel topModel=mSStrategyThread.getMarketBooksModel();
			mSTabbedPanel.updateMarketBookOfLegPanel(topModel);
		}
	}
	
	// 更新StrategyLiveOrderPanel
	public void updateMSStrategyLiveOrder(int strategyID) {
		if(strategiesStart==true) {
			MSStrategyThread strategyThread=mSPortfolio.getStrategy(strategyID);
			mSTabbedPanel.updateStrategyLiveOrder(strategyThread.strategyOrdersModel);
		}
	}
	
	// 更新
	public void updateMSPositionPanel(int strategyID) {
		if(strategiesStart==true) {
			MSStrategyThread strategyThread=mSPortfolio.getStrategy(strategyID);
			mSTabbedPanel.updatePositionPanel(strategyThread.m_positionModel);
		}
	}
	
	protected void addComboLeg(NewContractDetails contractDetails, NewComboLeg leg, int legID, HashMap<String,String> legConfig) {
    	NewContract c = contractDetails.contract();
    	
		leg.conid( c.conid() );
		leg.exchange( c.exchange() );

		MSComboLeg row = new MSComboLeg(legID, c, leg, legConfig);
		//m_comboLegModel.addLeg(row);
	}
	
	private void assignListeners() {
		
		ActionListener aLAddStrategyFromExcel=new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				File configFile=chooseConfigFile();
				if(configFile==null) {
					return;
				}
				cellData=readConfigFile(configFile);
				//String[] s=new String[]{"table name","table value"};
				mSTabbedPanel.addConfigTable(cellData);
				generateConfigClass(cellData);
				updateMyStrategiesPanel();
				generateComboLegs();
			}
		};
		jBAddStrategyFromExcel.addActionListener(aLAddStrategyFromExcel);
		
		ActionListener aLStartStrategy=new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				for(int i=0;i<m_strategyModel.getList().size();i++) {
					StrategyRow strategyRow=(StrategyRow) m_strategyModel.getList().get(i);
					int strategyID=strategyRow.getStrategyID();
					MSStrategyInfo strategyInfo=hM_MSStrategyInfo.get(strategyID);
					if(strategyRow.m_selected==true) {
						runStrategy.add(i);
						mSPortfolio.addStrategyInfo(strategyID, strategyInfo);
					}
				}
				Thread portfolioThread=new Thread(mSPortfolio,"PortfolioThread");
				portfolioThread.start();
				mSTabbedPanel.mSOrdersPanel.activated();
				strategiesStart=true;
				m_strategyModel.setEditable(false);
			}
		};
		jBStartStrategy.addActionListener(aLStartStrategy);
		
		ActionListener aLTest=new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			}
		};
		jBTest.addActionListener(aLTest);
		
		jTMyStrategy.addMouseListener(new MouseAdapter() {
			@Override
			public synchronized void mouseClicked(MouseEvent e) {
				int i=e.getButton();
				int selected=jTMyStrategy.rowAtPoint(e.getPoint());
				if(i==3) {
					jTMyStrategy.setRowSelectionInterval(selected, selected);
					m_popupMenu.show(jTMyStrategy, e.getX(), e.getY());
				} else if(i==1) {
					int strategyID=m_strategyModel.getRowAt(selected).getStrategyID();
					if(runStrategy.contains(strategyID)) {
						updateComboPanel(strategyID);
						updateMSMarketBookPanel(strategyID);
						updateMSStrategyLiveOrder(strategyID);
						updateMSPositionPanel(strategyID);
					}
				}
			}
		});
		
		JMI_runStrategy.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("JMI_runStrategy.performed");
			}
		});
		
		JMI_stopStrategy.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("JMI_stopStrategy.performed");
			}
		});
		
		JMI_deleteStrategy.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("JMI_deleteStrategy.performed");
			}
		});
		
		JMI_setupEquityStrategy.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				MSEquityPairsDlg setupEquityDlg=new MSEquityPairsDlg();
				setupEquityDlg.setVisible(true);
			}
		});
		
		JMI_setupOptionStrategy.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				MSOptionPairsDlg setupOptionDlg=new MSOptionPairsDlg();
				setupOptionDlg.setVisible(true);
			}
		});
		
		JMI_test.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				test();
			}
		});
	}
	
	protected void test() {
		
	}
	
}