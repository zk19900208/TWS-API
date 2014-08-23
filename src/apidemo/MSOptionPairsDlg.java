package apidemo;

import javax.swing.JDialog;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JLabel;

import java.awt.Font;
import java.awt.Color;

import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.SwingConstants;
import javax.swing.JToggleButton;
import javax.swing.JFormattedTextField;
import javax.swing.JList;
import javax.swing.JProgressBar;
import javax.swing.JSpinner;
import javax.swing.JEditorPane;
import javax.swing.JRadioButton;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import javax.swing.DefaultComboBoxModel;

public class MSOptionPairsDlg extends JDialog{
	private JTextField textField;
	private JTable table;
	private JTextField textField_1;
	public MSOptionPairsDlg() {
		setTitle("Equity/Option Pair Strategy");
		this.setSize(850,660);
		getContentPane().setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.BOTTOM);
		tabbedPane.setBounds(0, 0, 835, 600);
		getContentPane().add(tabbedPane);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.BLACK);
		tabbedPane.addTab("Option Parameter", null, panel, null);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Name");
		lblNewLabel.setForeground(Color.RED);
		lblNewLabel.setFont(new Font("宋体", Font.PLAIN, 20));
		lblNewLabel.setBounds(10, 0, 100, 30);
		panel.add(lblNewLabel);
		
		JLabel lblAlgorithms = new JLabel("Algorithms");
		lblAlgorithms.setForeground(Color.RED);
		lblAlgorithms.setFont(new Font("宋体", Font.PLAIN, 20));
		lblAlgorithms.setBounds(10, 30, 100, 30);
		panel.add(lblAlgorithms);
		
		textField = new JTextField();
		textField.setFont(new Font("宋体", Font.PLAIN, 18));
		textField.setBackground(new Color(255, 102, 51));
		textField.setBounds(120, 0, 200, 28);
		panel.add(textField);
		textField.setColumns(10);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"PegTo Volatility", "PegTo Spread", "PegTo Vol/Delta", "PegTo Spr/Delta"}));
		comboBox.setFont(new Font("宋体", Font.PLAIN, 15));
		comboBox.setBounds(120, 30, 200, 28);
		panel.add(comboBox);
		
		JLabel lblQuantity = new JLabel("Quantity");
		lblQuantity.setForeground(Color.RED);
		lblQuantity.setFont(new Font("宋体", Font.PLAIN, 20));
		lblQuantity.setBounds(10, 60, 100, 30);
		panel.add(lblQuantity);
		
		JComboBox comboBox_2 = new JComboBox();
		comboBox_2.setModel(new DefaultComboBoxModel(new String[] {"IVol Bid/Ask"}));
		comboBox_2.setFont(new Font("宋体", Font.PLAIN, 18));
		comboBox_2.setBounds(650, 60, 150, 28);
		panel.add(comboBox_2);
				
		OptionLegsModel optionLegsModel=new OptionLegsModel();
		table = new JTable(optionLegsModel);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(0, 100, 830, 130);
		panel.add(scrollPane);
		/*
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{new Double(1.0), null, null, null, null, null, null, null, null, null, null, null, null},
				{new Double(2.0), null, null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null, null, null},
			},
			new String[] {
				"Leg", "U/Tickers", "Expiry", "C/P", "Strike", "Side", "Ratio", "IVBid", "Qty", "Bid", "Ask", "Qty", "IVAsk"
			}
		) {
			Class[] columnTypes = new Class[] {
				Double.class, Object.class, Object.class, Object.class, Object.class, Object.class, Object.class, Object.class, Object.class, Object.class, Object.class, Object.class, Object.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		scrollPane.setColumnHeaderView(table);
		*/
		//scrollPane.setc
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.BLACK);
		panel_1.setBounds(0, 230, 830, 300);
		panel.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblVolitality = new JLabel("Volitality");
		lblVolitality.setBounds(50, 0, 100, 28);
		panel_1.add(lblVolitality);
		lblVolitality.setHorizontalAlignment(SwingConstants.CENTER);
		lblVolitality.setForeground(Color.RED);
		lblVolitality.setFont(new Font("宋体", Font.PLAIN, 20));
		
		JSpinner spinner = new JSpinner();
		spinner.setFont(new Font("宋体", Font.PLAIN, 18));
		spinner.setBounds(40, 30, 120, 28);
		panel_1.add(spinner);
		
		JLabel lblNetlimit = new JLabel("Net$Limit");
		lblNetlimit.setHorizontalAlignment(SwingConstants.CENTER);
		lblNetlimit.setForeground(Color.RED);
		lblNetlimit.setFont(new Font("宋体", Font.PLAIN, 20));
		lblNetlimit.setBounds(210, 0, 100, 28);
		panel_1.add(lblNetlimit);
		
		JSpinner spinner_1 = new JSpinner();
		spinner_1.setFont(new Font("宋体", Font.PLAIN, 18));
		spinner_1.setBounds(200, 30, 120, 28);
		panel_1.add(spinner_1);
		
		JLabel lblNewLabel_1 = new JLabel("Leg");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setForeground(Color.WHITE);
		lblNewLabel_1.setFont(new Font("宋体", Font.PLAIN, 20));
		lblNewLabel_1.setBounds(10, 60, 40, 30);
		panel_1.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("1");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setForeground(Color.RED);
		lblNewLabel_2.setFont(new Font("宋体", Font.PLAIN, 18));
		lblNewLabel_2.setBounds(10, 90, 40, 30);
		panel_1.add(lblNewLabel_2);
		
		JLabel label = new JLabel("2");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setForeground(Color.RED);
		label.setFont(new Font("宋体", Font.PLAIN, 18));
		label.setBounds(10, 120, 40, 30);
		panel_1.add(label);
		
		JLabel lblShort = new JLabel("SHORT");
		lblShort.setHorizontalAlignment(SwingConstants.CENTER);
		lblShort.setForeground(Color.RED);
		lblShort.setFont(new Font("宋体", Font.PLAIN, 18));
		lblShort.setBounds(60, 120, 50, 30);
		panel_1.add(lblShort);
		
		JLabel lblBuy = new JLabel("BUY");
		lblBuy.setHorizontalAlignment(SwingConstants.CENTER);
		lblBuy.setForeground(Color.RED);
		lblBuy.setFont(new Font("宋体", Font.PLAIN, 18));
		lblBuy.setBounds(60, 90, 50, 30);
		panel_1.add(lblBuy);
		
		JLabel lblEquity = new JLabel("Equity");
		lblEquity.setHorizontalAlignment(SwingConstants.CENTER);
		lblEquity.setForeground(Color.RED);
		lblEquity.setFont(new Font("宋体", Font.PLAIN, 18));
		lblEquity.setBounds(120, 90, 60, 30);
		panel_1.add(lblEquity);
		
		JLabel lblOption = new JLabel("Option");
		lblOption.setHorizontalAlignment(SwingConstants.CENTER);
		lblOption.setForeground(Color.RED);
		lblOption.setFont(new Font("宋体", Font.PLAIN, 18));
		lblOption.setBounds(120, 120, 60, 30);
		panel_1.add(lblOption);
		
		JLabel lblReferencePrice = new JLabel("Reference Price");
		lblReferencePrice.setHorizontalAlignment(SwingConstants.CENTER);
		lblReferencePrice.setForeground(Color.WHITE);
		lblReferencePrice.setFont(new Font("宋体", Font.PLAIN, 15));
		lblReferencePrice.setBounds(190, 60, 140, 30);
		panel_1.add(lblReferencePrice);
		
		JSpinner spinner_2 = new JSpinner();
		spinner_2.setFont(new Font("宋体", Font.PLAIN, 18));
		spinner_2.setBounds(200, 90, 120, 28);
		panel_1.add(spinner_2);
		
		JSpinner spinner_3 = new JSpinner();
		spinner_3.setFont(new Font("宋体", Font.PLAIN, 18));
		spinner_3.setBounds(200, 120, 120, 28);
		panel_1.add(spinner_3);
		
		JCheckBox chckbxNewCheckBox = new JCheckBox("5800");
		chckbxNewCheckBox.setFont(new Font("宋体", Font.PLAIN, 18));
		chckbxNewCheckBox.setBounds(330, 90, 120, 28);
		panel_1.add(chckbxNewCheckBox);
		
		JLabel lblQuantity_1 = new JLabel("Quantity");
		lblQuantity_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblQuantity_1.setForeground(Color.WHITE);
		lblQuantity_1.setFont(new Font("宋体", Font.PLAIN, 15));
		lblQuantity_1.setBounds(330, 60, 120, 30);
		panel_1.add(lblQuantity_1);
		
		JCheckBox checkBox = new JCheckBox("5800");
		checkBox.setFont(new Font("宋体", Font.PLAIN, 18));
		checkBox.setBounds(330, 120, 120, 28);
		panel_1.add(checkBox);
		
		JLabel lblNewLabel_3 = new JLabel("Post");
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_3.setFont(new Font("宋体", Font.PLAIN, 15));
		lblNewLabel_3.setForeground(Color.WHITE);
		lblNewLabel_3.setBounds(470, 60, 50, 30);
		panel_1.add(lblNewLabel_3);
		
		JLabel lblMaxSlice = new JLabel("Max Slice");
		lblMaxSlice.setHorizontalAlignment(SwingConstants.CENTER);
		lblMaxSlice.setForeground(Color.WHITE);
		lblMaxSlice.setFont(new Font("宋体", Font.PLAIN, 15));
		lblMaxSlice.setBounds(530, 60, 120, 30);
		panel_1.add(lblMaxSlice);
		
		JCheckBox checkBox_1 = new JCheckBox("5800");
		checkBox_1.setFont(new Font("宋体", Font.PLAIN, 18));
		checkBox_1.setBounds(530, 90, 120, 28);
		panel_1.add(checkBox_1);
		
		JCheckBox checkBox_2 = new JCheckBox("5800");
		checkBox_2.setFont(new Font("宋体", Font.PLAIN, 18));
		checkBox_2.setBounds(530, 120, 120, 28);
		panel_1.add(checkBox_2);
		
		JLabel lblNewLabel_4 = new JLabel("    Additional Field");
		lblNewLabel_4.setForeground(Color.WHITE);
		lblNewLabel_4.setFont(new Font("宋体", Font.PLAIN, 20));
		lblNewLabel_4.setBackground(Color.DARK_GRAY);
		lblNewLabel_4.setOpaque(true);
		lblNewLabel_4.setBounds(0, 150, 830, 30);
		panel_1.add(lblNewLabel_4);
		
		JLabel label_2 = new JLabel("Leg");
		label_2.setHorizontalAlignment(SwingConstants.CENTER);
		label_2.setForeground(Color.WHITE);
		label_2.setFont(new Font("宋体", Font.PLAIN, 20));
		label_2.setBounds(10, 180, 40, 30);
		panel_1.add(label_2);
		
		JLabel label_3 = new JLabel("1");
		label_3.setHorizontalAlignment(SwingConstants.CENTER);
		label_3.setForeground(Color.RED);
		label_3.setFont(new Font("宋体", Font.PLAIN, 18));
		label_3.setBounds(10, 210, 40, 30);
		panel_1.add(label_3);
		
		JLabel label_4 = new JLabel("2");
		label_4.setHorizontalAlignment(SwingConstants.CENTER);
		label_4.setForeground(Color.RED);
		label_4.setFont(new Font("宋体", Font.PLAIN, 18));
		label_4.setBounds(10, 240, 40, 30);
		panel_1.add(label_4);
		
		JLabel label_5 = new JLabel("BUY");
		label_5.setHorizontalAlignment(SwingConstants.CENTER);
		label_5.setForeground(Color.RED);
		label_5.setFont(new Font("宋体", Font.PLAIN, 18));
		label_5.setBounds(60, 210, 50, 30);
		panel_1.add(label_5);
		
		JLabel label_6 = new JLabel("SHORT");
		label_6.setHorizontalAlignment(SwingConstants.CENTER);
		label_6.setForeground(Color.RED);
		label_6.setFont(new Font("宋体", Font.PLAIN, 18));
		label_6.setBounds(60, 240, 50, 30);
		panel_1.add(label_6);
		
		JLabel label_7 = new JLabel("Equity");
		label_7.setHorizontalAlignment(SwingConstants.CENTER);
		label_7.setForeground(Color.RED);
		label_7.setFont(new Font("宋体", Font.PLAIN, 18));
		label_7.setBounds(120, 210, 60, 30);
		panel_1.add(label_7);
		
		JLabel label_8 = new JLabel("Option");
		label_8.setHorizontalAlignment(SwingConstants.CENTER);
		label_8.setForeground(Color.RED);
		label_8.setFont(new Font("宋体", Font.PLAIN, 18));
		label_8.setBounds(120, 240, 60, 30);
		panel_1.add(label_8);
		
		JLabel label_9 = new JLabel("Reference Price");
		label_9.setHorizontalAlignment(SwingConstants.CENTER);
		label_9.setForeground(Color.WHITE);
		label_9.setFont(new Font("宋体", Font.PLAIN, 15));
		label_9.setBounds(190, 180, 140, 30);
		panel_1.add(label_9);
		
		JSpinner spinner_4 = new JSpinner();
		spinner_4.setFont(new Font("宋体", Font.PLAIN, 18));
		spinner_4.setBounds(200, 210, 120, 28);
		panel_1.add(spinner_4);
		
		JSpinner spinner_5 = new JSpinner();
		spinner_5.setFont(new Font("宋体", Font.PLAIN, 18));
		spinner_5.setBounds(200, 240, 120, 28);
		panel_1.add(spinner_5);
		
		JLabel label_10 = new JLabel("Quantity");
		label_10.setHorizontalAlignment(SwingConstants.CENTER);
		label_10.setForeground(Color.WHITE);
		label_10.setFont(new Font("宋体", Font.PLAIN, 15));
		label_10.setBounds(330, 180, 120, 30);
		panel_1.add(label_10);
		
		JSpinner spinner_6 = new JSpinner();
		spinner_6.setFont(new Font("宋体", Font.PLAIN, 18));
		spinner_6.setBounds(330, 210, 120, 28);
		panel_1.add(spinner_6);
		
		JSpinner spinner_7 = new JSpinner();
		spinner_7.setFont(new Font("宋体", Font.PLAIN, 18));
		spinner_7.setBounds(330, 240, 120, 28);
		panel_1.add(spinner_7);
		
		JLabel lblAccountid = new JLabel("AccountID");
		lblAccountid.setHorizontalAlignment(SwingConstants.CENTER);
		lblAccountid.setForeground(Color.WHITE);
		lblAccountid.setFont(new Font("宋体", Font.PLAIN, 15));
		lblAccountid.setBounds(530, 180, 120, 30);
		panel_1.add(lblAccountid);
		
		JComboBox comboBox_3 = new JComboBox();
		comboBox_3.setBounds(650, 180, 120, 28);
		panel_1.add(comboBox_3);
		
		JLabel lblAccountorigin = new JLabel("AccountOrigin");
		lblAccountorigin.setHorizontalAlignment(SwingConstants.CENTER);
		lblAccountorigin.setForeground(Color.WHITE);
		lblAccountorigin.setFont(new Font("宋体", Font.PLAIN, 15));
		lblAccountorigin.setBounds(530, 210, 120, 30);
		panel_1.add(lblAccountorigin);
		
		JComboBox comboBox_4 = new JComboBox();
		comboBox_4.setBounds(650, 210, 120, 28);
		panel_1.add(comboBox_4);
		
		JLabel lblPrimebroker = new JLabel("PrimeBroker");
		lblPrimebroker.setHorizontalAlignment(SwingConstants.CENTER);
		lblPrimebroker.setForeground(Color.WHITE);
		lblPrimebroker.setFont(new Font("宋体", Font.PLAIN, 15));
		lblPrimebroker.setBounds(530, 240, 120, 30);
		panel_1.add(lblPrimebroker);
		
		JComboBox comboBox_5 = new JComboBox();
		comboBox_5.setBounds(650, 240, 120, 28);
		panel_1.add(comboBox_5);
		
		JSpinner spinner_8 = new JSpinner();
		spinner_8.setFont(new Font("宋体", Font.PLAIN, 18));
		spinner_8.setBounds(200, 270, 120, 28);
		panel_1.add(spinner_8);
		
		JSpinner spinner_9 = new JSpinner();
		spinner_9.setFont(new Font("宋体", Font.PLAIN, 18));
		spinner_9.setBounds(330, 270, 120, 28);
		panel_1.add(spinner_9);
		
		JLabel lblDelta = new JLabel("Delta");
		lblDelta.setHorizontalAlignment(SwingConstants.CENTER);
		lblDelta.setForeground(Color.RED);
		lblDelta.setFont(new Font("宋体", Font.PLAIN, 18));
		lblDelta.setBounds(120, 270, 60, 30);
		panel_1.add(lblDelta);
		
		JButton btnNewButton = new JButton("6)Add");
		btnNewButton.setFont(new Font("宋体", Font.PLAIN, 20));
		btnNewButton.setBounds(400, 535, 100, 30);
		panel.add(btnNewButton);
		
		JButton btnactivate = new JButton("7)Activate");
		btnactivate.setFont(new Font("宋体", Font.PLAIN, 20));
		btnactivate.setBounds(520, 535, 150, 30);
		panel.add(btnactivate);
		
		JButton btnClose = new JButton("Close");
		btnClose.setFont(new Font("宋体", Font.PLAIN, 20));
		btnClose.setBounds(690, 535, 100, 30);
		panel.add(btnClose);
		
		JLabel lblSharescontracts = new JLabel("Shares/Contracts");
		lblSharescontracts.setForeground(Color.RED);
		lblSharescontracts.setFont(new Font("宋体", Font.PLAIN, 20));
		lblSharescontracts.setBounds(120, 60, 200, 30);
		panel.add(lblSharescontracts);
		
		textField_1 = new JTextField();
		textField_1.setHorizontalAlignment(SwingConstants.CENTER);
		textField_1.setText("06/06/11 13:51:13");
		textField_1.setFont(new Font("宋体", Font.PLAIN, 18));
		textField_1.setColumns(10);
		textField_1.setBackground(new Color(255, 102, 51));
		textField_1.setBounds(620, 0, 200, 28);
		panel.add(textField_1);
	}
	
	public class OptionLegsModel extends AbstractTableModel {
		String[] columnNames=new String[] {
				"Leg", "U/Tickers", "Expiry", "C/P", "Strike", "Side", "Ratio", "IVBid", "Qty", "Bid", "Ask", "Qty", "IVAsk"
				};
		@Override
		public int getColumnCount() {
			// TODO Auto-generated method stub
			return columnNames.length;
		}

		@Override
		public String getColumnName(int col) {
			return columnNames[col];
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