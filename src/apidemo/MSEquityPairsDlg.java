package apidemo;

import javax.swing.JDialog;
import javax.swing.JLabel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;
import javax.swing.SwingConstants;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.JSpinner;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;
import javax.swing.JButton;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JScrollPane;
import javax.swing.table.TableModel;

public class MSEquityPairsDlg extends JDialog{
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTextField textField_6;
	private JTextField textField_7;
	private JTextField textField_8;
	private JTextField textField_9;
	private JTextField txtMarketable;
	private JTextField textField_10;
	private JTable table;
	private JTable table_1;
	public MSEquityPairsDlg() {
		setTitle("Equity Pair Strategy");
		this.setSize(750,660);
		getContentPane().setBackground(Color.BLACK);
		getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Pair Name");
		lblNewLabel.setForeground(Color.RED);
		lblNewLabel.setFont(new Font("宋体", Font.PLAIN, 20));
		lblNewLabel.setBackground(Color.BLACK);
		lblNewLabel.setBounds(10, 0, 110, 30);
		getContentPane().add(lblNewLabel);
		
		textField = new JTextField();
		textField.setFont(new Font("宋体", Font.PLAIN, 20));
		textField.setBackground(new Color(255, 102, 51));
		textField.setBounds(120, 0, 200, 30);
		getContentPane().add(textField);
		textField.setColumns(10);
		
		JPanel setupPanel = new JPanel();
		setupPanel.setBackground(Color.BLACK);
		setupPanel.setBounds(0, 30, 735, 270);
		getContentPane().add(setupPanel);
		setupPanel.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel(" Strategy Setup");
		lblNewLabel_1.setForeground(Color.WHITE);
		lblNewLabel_1.setFont(new Font("宋体", Font.PLAIN, 20));
		lblNewLabel_1.setBackground(Color.DARK_GRAY);
		lblNewLabel_1.setOpaque(true);
		lblNewLabel_1.setBounds(0, 0, 735, 30);
		setupPanel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Price Convention");
		lblNewLabel_2.setForeground(Color.RED);
		lblNewLabel_2.setFont(new Font("宋体", Font.PLAIN, 18));
		lblNewLabel_2.setBounds(10, 30, 180, 30);
		setupPanel.add(lblNewLabel_2);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setFont(new Font("宋体", Font.PLAIN, 18));
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Spread"}));
		comboBox.setBounds(200, 30, 100, 28);
		setupPanel.add(comboBox);
		
		JLabel lblQuantityConvention = new JLabel("Quantity Convention");
		lblQuantityConvention.setForeground(Color.RED);
		lblQuantityConvention.setFont(new Font("宋体", Font.PLAIN, 18));
		lblQuantityConvention.setBounds(10, 60, 180, 30);
		setupPanel.add(lblQuantityConvention);
		
		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setFont(new Font("宋体", Font.PLAIN, 18));
		comboBox_1.setModel(new DefaultComboBoxModel(new String[] {"Shares", "Contracts", "Notional"}));
		comboBox_1.setBounds(200, 60, 115, 28);
		setupPanel.add(comboBox_1);
		
		JLabel lblMaxUnhedgeAmt = new JLabel("Max Unhedge Amt");
		lblMaxUnhedgeAmt.setForeground(Color.RED);
		lblMaxUnhedgeAmt.setFont(new Font("宋体", Font.PLAIN, 18));
		lblMaxUnhedgeAmt.setBounds(10, 90, 180, 30);
		setupPanel.add(lblMaxUnhedgeAmt);
		
		JCheckBox chckbxNewCheckBox = new JCheckBox("Flip");
		chckbxNewCheckBox.setForeground(Color.RED);
		chckbxNewCheckBox.setBackground(Color.BLACK);
		chckbxNewCheckBox.setFont(new Font("宋体", Font.PLAIN, 18));
		chckbxNewCheckBox.setBounds(310, 30, 70, 28);
		setupPanel.add(chckbxNewCheckBox);
		
		JLabel lblShares = new JLabel("Shares");
		lblShares.setForeground(Color.RED);
		lblShares.setFont(new Font("宋体", Font.PLAIN, 18));
		lblShares.setBounds(310, 90, 70, 30);
		setupPanel.add(lblShares);
		
		JLabel lblLeg = new JLabel(" Leg 1");
		lblLeg.setOpaque(true);
		lblLeg.setForeground(Color.WHITE);
		lblLeg.setFont(new Font("宋体", Font.PLAIN, 20));
		lblLeg.setBackground(Color.DARK_GRAY);
		lblLeg.setBounds(0, 120, 365, 30);
		setupPanel.add(lblLeg);
		
		JLabel lblSide = new JLabel("Side");
		lblSide.setForeground(Color.RED);
		lblSide.setFont(new Font("宋体", Font.PLAIN, 18));
		lblSide.setBounds(10, 150, 70, 30);
		setupPanel.add(lblSide);
		
		JComboBox comboBox_3 = new JComboBox();
		comboBox_3.setFont(new Font("宋体", Font.PLAIN, 18));
		comboBox_3.setModel(new DefaultComboBoxModel(new String[] {"BUY", "SHORT"}));
		comboBox_3.setBounds(90, 150, 100, 28);
		setupPanel.add(comboBox_3);
		
		JLabel lblTicker = new JLabel("Ticker");
		lblTicker.setForeground(Color.RED);
		lblTicker.setFont(new Font("宋体", Font.PLAIN, 18));
		lblTicker.setBounds(10, 180, 70, 30);
		setupPanel.add(lblTicker);
		
		JLabel lblWeight = new JLabel("Weight");
		lblWeight.setForeground(Color.RED);
		lblWeight.setFont(new Font("宋体", Font.PLAIN, 18));
		lblWeight.setBounds(10, 210, 70, 30);
		setupPanel.add(lblWeight);
		
		textField_1 = new JTextField();
		textField_1.setFont(new Font("宋体", Font.PLAIN, 20));
		textField_1.setColumns(10);
		textField_1.setBackground(new Color(255, 102, 51));
		textField_1.setBounds(90, 180, 275, 28);
		setupPanel.add(textField_1);
		
		textField_2 = new JTextField();
		textField_2.setFont(new Font("宋体", Font.PLAIN, 20));
		textField_2.setColumns(10);
		textField_2.setBackground(new Color(255, 102, 51));
		textField_2.setBounds(90, 210, 100, 28);
		setupPanel.add(textField_2);
		
		JLabel lblLeg_1 = new JLabel(" Leg 2");
		lblLeg_1.setOpaque(true);
		lblLeg_1.setForeground(Color.WHITE);
		lblLeg_1.setFont(new Font("宋体", Font.PLAIN, 20));
		lblLeg_1.setBackground(Color.DARK_GRAY);
		lblLeg_1.setBounds(370, 120, 365, 30);
		setupPanel.add(lblLeg_1);
		
		JLabel label_1 = new JLabel("Side");
		label_1.setForeground(Color.RED);
		label_1.setFont(new Font("宋体", Font.PLAIN, 18));
		label_1.setBounds(380, 150, 70, 30);
		setupPanel.add(label_1);
		
		JComboBox comboBox_4 = new JComboBox();
		comboBox_4.setModel(new DefaultComboBoxModel(new String[] {"BUY", "SHORT"}));
		comboBox_4.setFont(new Font("宋体", Font.PLAIN, 18));
		comboBox_4.setBounds(460, 150, 100, 28);
		setupPanel.add(comboBox_4);
		
		JLabel label_2 = new JLabel("Ticker");
		label_2.setForeground(Color.RED);
		label_2.setFont(new Font("宋体", Font.PLAIN, 18));
		label_2.setBounds(380, 180, 70, 30);
		setupPanel.add(label_2);
		
		textField_3 = new JTextField();
		textField_3.setFont(new Font("宋体", Font.PLAIN, 20));
		textField_3.setColumns(10);
		textField_3.setBackground(new Color(255, 102, 51));
		textField_3.setBounds(460, 180, 275, 28);
		setupPanel.add(textField_3);
		
		textField_4 = new JTextField();
		textField_4.setFont(new Font("宋体", Font.PLAIN, 20));
		textField_4.setColumns(10);
		textField_4.setBackground(new Color(255, 102, 51));
		textField_4.setBounds(460, 210, 100, 28);
		setupPanel.add(textField_4);
		
		JLabel label_3 = new JLabel("Weight");
		label_3.setForeground(Color.RED);
		label_3.setFont(new Font("宋体", Font.PLAIN, 18));
		label_3.setBounds(380, 210, 70, 30);
		setupPanel.add(label_3);
		
		JLabel lblAccount = new JLabel("Account");
		lblAccount.setForeground(Color.RED);
		lblAccount.setFont(new Font("宋体", Font.PLAIN, 18));
		lblAccount.setBounds(380, 240, 70, 30);
		setupPanel.add(lblAccount);
		
		JComboBox comboBox_5 = new JComboBox();
		comboBox_5.setBounds(460, 240, 100, 28);
		setupPanel.add(comboBox_5);
		
		JLabel lblBroker = new JLabel("Broker");
		lblBroker.setForeground(Color.RED);
		lblBroker.setFont(new Font("宋体", Font.PLAIN, 18));
		lblBroker.setBounds(567, 240, 60, 30);
		setupPanel.add(lblBroker);
		
		JComboBox comboBox_6 = new JComboBox();
		comboBox_6.setBounds(630, 240, 100, 28);
		setupPanel.add(comboBox_6);
		
		textField_10 = new JTextField();
		textField_10.setFont(new Font("宋体", Font.PLAIN, 20));
		textField_10.setColumns(10);
		textField_10.setBackground(new Color(255, 102, 51));
		textField_10.setBounds(200, 90, 100, 28);
		setupPanel.add(textField_10);
		
		JLabel lblInitiate = new JLabel("Initiate");
		lblInitiate.setHorizontalAlignment(SwingConstants.CENTER);
		lblInitiate.setForeground(Color.RED);
		lblInitiate.setFont(new Font("宋体", Font.PLAIN, 18));
		lblInitiate.setBounds(370, 90, 100, 30);
		setupPanel.add(lblInitiate);
		
		JComboBox comboBox_2 = new JComboBox();
		comboBox_2.setModel(new DefaultComboBoxModel(new String[] {"Leg1 First", "Leg2 First", "Both legs", "Hide and Sweep", "Least liquid leg"}));
		comboBox_2.setFont(new Font("宋体", Font.PLAIN, 18));
		comboBox_2.setBounds(480, 90, 180, 28);
		setupPanel.add(comboBox_2);
		
		JPanel marketPanel = new JPanel();
		marketPanel.setBackground(Color.BLACK);
		marketPanel.setBounds(0, 300, 735, 270);
		getContentPane().add(marketPanel);
		marketPanel.setLayout(null);
		
		JLabel lblMarket = new JLabel(" Market");
		lblMarket.setHorizontalAlignment(SwingConstants.CENTER);
		lblMarket.setBounds(0, 0, 100, 30);
		lblMarket.setOpaque(true);
		lblMarket.setForeground(Color.WHITE);
		lblMarket.setFont(new Font("宋体", Font.PLAIN, 20));
		lblMarket.setBackground(Color.DARK_GRAY);
		marketPanel.add(lblMarket);
		
		JLabel lblCurrency = new JLabel("Currency ");
		lblCurrency.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCurrency.setOpaque(true);
		lblCurrency.setForeground(Color.WHITE);
		lblCurrency.setFont(new Font("宋体", Font.PLAIN, 20));
		lblCurrency.setBackground(Color.DARK_GRAY);
		lblCurrency.setBounds(101, 0, 100, 30);
		marketPanel.add(lblCurrency);
		
		JComboBox comboBox_7 = new JComboBox();
		comboBox_7.setFont(new Font("宋体", Font.PLAIN, 18));
		comboBox_7.setModel(new DefaultComboBoxModel(new String[] {"USD", "HKD"}));
		comboBox_7.setBounds(201, 0, 100, 30);
		marketPanel.add(comboBox_7);
		
		JCheckBox chckbxNewCheckBox_1 = new JCheckBox("Local Bid Ask");
		chckbxNewCheckBox_1.setForeground(Color.WHITE);
		chckbxNewCheckBox_1.setBackground(Color.DARK_GRAY);
		chckbxNewCheckBox_1.setFont(new Font("宋体", Font.PLAIN, 20));
		chckbxNewCheckBox_1.setBounds(305, 0, 155, 30);
		marketPanel.add(chckbxNewCheckBox_1);
		
		JLabel lblLastTradeTime = new JLabel("Last Trade Time");
		lblLastTradeTime.setOpaque(true);
		lblLastTradeTime.setHorizontalAlignment(SwingConstants.CENTER);
		lblLastTradeTime.setForeground(Color.RED);
		lblLastTradeTime.setFont(new Font("宋体", Font.PLAIN, 20));
		lblLastTradeTime.setBackground(Color.DARK_GRAY);
		lblLastTradeTime.setBounds(465, 0, 160, 30);
		marketPanel.add(lblLastTradeTime);
		
		textField_5 = new JTextField();
		textField_5.setForeground(Color.RED);
		textField_5.setFont(new Font("宋体", Font.PLAIN, 15));
		textField_5.setBackground(Color.DARK_GRAY);
		textField_5.setBounds(630, 0, 100, 30);
		marketPanel.add(textField_5);
		textField_5.setColumns(10);
		
		ComboModel comboModel=new ComboModel();
		
		table = new JTable(comboModel);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(0, 35, 735, 60);
		marketPanel.add(scrollPane);
		/*
		table.setFillsViewportHeight(true);
		table.setForeground(Color.WHITE);
		table.getColumnModel().getColumn(0).setPreferredWidth(126);
		scrollPane.setRowHeaderView(table);
		table.setFont(new Font("宋体", Font.PLAIN, 15));
		table.setBackground(Color.DARK_GRAY);
		*/
		
		EquityLegsModel equityLegsModel=new EquityLegsModel();
		table_1 = new JTable(equityLegsModel);
		table_1.setFont(new Font("宋体", Font.PLAIN, 15));
		table_1.setBackground(Color.DARK_GRAY);
		JScrollPane scrollPane_1 = new JScrollPane(table_1);
		//scrollPane_1.setRowHeaderView(table_1);
		scrollPane_1.setBounds(0, 100, 735, 60);
		marketPanel.add(scrollPane_1);		
		
		JCheckBox chckbxNewCheckBox_2 = new JCheckBox("Link Setup & Unwind");
		chckbxNewCheckBox_2.setForeground(Color.RED);
		chckbxNewCheckBox_2.setBackground(Color.BLACK);
		chckbxNewCheckBox_2.setFont(new Font("宋体", Font.PLAIN, 18));
		chckbxNewCheckBox_2.setBounds(10, 160, 200, 30);
		marketPanel.add(chckbxNewCheckBox_2);
		
		JCheckBox chckbxSetup = new JCheckBox("Setup");
		chckbxSetup.setForeground(Color.RED);
		chckbxSetup.setFont(new Font("宋体", Font.PLAIN, 18));
		chckbxSetup.setBackground(Color.BLACK);
		chckbxSetup.setBounds(10, 190, 80, 30);
		marketPanel.add(chckbxSetup);
		
		JCheckBox chckbxUnwind = new JCheckBox("Unwind");
		chckbxUnwind.setForeground(Color.RED);
		chckbxUnwind.setFont(new Font("宋体", Font.PLAIN, 18));
		chckbxUnwind.setBackground(Color.BLACK);
		chckbxUnwind.setBounds(10, 220, 80, 30);
		marketPanel.add(chckbxUnwind);
		
		JComboBox comboBox_8 = new JComboBox();
		comboBox_8.setModel(new DefaultComboBoxModel(new String[] {"SELL, BUY"}));
		comboBox_8.setBounds(100, 220, 100, 28);
		marketPanel.add(comboBox_8);
		
		JLabel lblSpread = new JLabel("Spread");
		lblSpread.setHorizontalAlignment(SwingConstants.CENTER);
		lblSpread.setForeground(Color.RED);
		lblSpread.setFont(new Font("宋体", Font.PLAIN, 18));
		lblSpread.setBounds(230, 160, 100, 30);
		marketPanel.add(lblSpread);
		
		JSpinner spinner = new JSpinner();
		spinner.setFont(new Font("宋体", Font.PLAIN, 15));
		spinner.setBounds(230, 190, 100, 28);
		marketPanel.add(spinner);
		
		JLabel lblLegShares = new JLabel("Leg 1 Shares");
		lblLegShares.setHorizontalAlignment(SwingConstants.CENTER);
		lblLegShares.setForeground(Color.RED);
		lblLegShares.setFont(new Font("宋体", Font.PLAIN, 18));
		lblLegShares.setBounds(340, 160, 120, 30);
		marketPanel.add(lblLegShares);
		
		textField_6 = new JTextField();
		textField_6.setFont(new Font("宋体", Font.PLAIN, 20));
		textField_6.setColumns(10);
		textField_6.setBackground(new Color(255, 102, 51));
		textField_6.setBounds(340, 190, 120, 28);
		marketPanel.add(textField_6);
		
		JLabel label = new JLabel("Leg 1 Shares");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setForeground(Color.RED);
		label.setFont(new Font("宋体", Font.PLAIN, 18));
		label.setBounds(470, 160, 120, 30);
		marketPanel.add(label);
		
		textField_7 = new JTextField();
		textField_7.setFont(new Font("宋体", Font.PLAIN, 20));
		textField_7.setColumns(10);
		textField_7.setBackground(new Color(255, 102, 51));
		textField_7.setBounds(470, 190, 120, 28);
		marketPanel.add(textField_7);
		
		JSpinner spinner_1 = new JSpinner();
		spinner_1.setFont(new Font("宋体", Font.PLAIN, 15));
		spinner_1.setBounds(230, 222, 100, 28);
		marketPanel.add(spinner_1);
		
		textField_8 = new JTextField();
		textField_8.setFont(new Font("宋体", Font.PLAIN, 20));
		textField_8.setColumns(10);
		textField_8.setBackground(new Color(255, 102, 51));
		textField_8.setBounds(340, 222, 120, 28);
		marketPanel.add(textField_8);
		
		textField_9 = new JTextField();
		textField_9.setFont(new Font("宋体", Font.PLAIN, 20));
		textField_9.setColumns(10);
		textField_9.setBackground(new Color(255, 102, 51));
		textField_9.setBounds(470, 222, 120, 28);
		marketPanel.add(textField_9);
		
		JCheckBox chckbxOverride = new JCheckBox("Override");
		chckbxOverride.setForeground(Color.RED);
		chckbxOverride.setFont(new Font("宋体", Font.PLAIN, 18));
		chckbxOverride.setBackground(Color.BLACK);
		chckbxOverride.setBounds(610, 160, 100, 30);
		marketPanel.add(chckbxOverride);
		
		txtMarketable = new JTextField();
		txtMarketable.setText("Marketable");
		txtMarketable.setForeground(Color.RED);
		txtMarketable.setEditable(false);
		txtMarketable.setFont(new Font("宋体", Font.PLAIN, 20));
		txtMarketable.setColumns(10);
		txtMarketable.setBackground(Color.BLACK);
		txtMarketable.setBounds(600, 190, 120, 28);
		marketPanel.add(txtMarketable);
		
		JButton btnNewButton = new JButton("1)Pair Setup");
		btnNewButton.setBackground(Color.BLACK);
		btnNewButton.setBounds(10, 580, 105, 20);
		getContentPane().add(btnNewButton);
		
		JButton btnadd = new JButton("6)Add");
		btnadd.setBackground(Color.BLACK);
		btnadd.setBounds(400, 590, 100, 30);
		getContentPane().add(btnadd);
		
		JButton btnadd_1 = new JButton("7)Activate");
		btnadd_1.setBackground(Color.BLACK);
		btnadd_1.setBounds(510, 590, 100, 30);
		getContentPane().add(btnadd_1);
		
		JButton btnClose = new JButton("Close");
		btnClose.setBackground(Color.BLACK);
		btnClose.setBounds(620, 590, 100, 30);
		getContentPane().add(btnClose);
	}
	
	public class ComboModel extends AbstractTableModel {
		String[] names=new String[] {"Security", "Last", "Setup Size", "Setup", "Unwind", "Unwind Size"};
		@Override
		public int getColumnCount() {
			// TODO Auto-generated method stub
			return names.length;
		}
		
		@Override
		public String getColumnName(int col) {
			return names[col];
		}

		@Override
		public int getRowCount() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public Object getValueAt(int arg0, int arg1) {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
	
	public class EquityLegsModel extends AbstractTableModel {
		String[] names=new String[] {"", "Side", "Last", "Bid Local", "Bid", "Ask", "Ask Local"};
		@Override
		public int getColumnCount() {
			// TODO Auto-generated method stub
			return names.length;
		}
		
		@Override
		public String getColumnName(int col) {
			return names[col];
		}

		@Override
		public int getRowCount() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public Object getValueAt(int arg0, int arg1) {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
}