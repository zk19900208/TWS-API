package apidemo;

import java.awt.BorderLayout;
import java.util.Enumeration;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;

public class MSPositionPanel extends JPanel {
	public MSPositionModel m_positionModel=new MSPositionModel();
	private JTable m_table=new JTable(m_positionModel);
	
	MSPositionPanel() {
		JScrollPane m_scrollPane=new JScrollPane(m_table);
		MSTabbedPanel.fitTableColumns(m_table);
		setLayout(new BorderLayout());
		add(m_scrollPane);
	}

	public void updatePositionModel(MSPositionModel positionModel) {
		// TODO Auto-generated method stub
		m_table.setModel(positionModel);
	}
	
	
}
