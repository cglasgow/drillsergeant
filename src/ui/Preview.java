package ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Rectangle;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.AbstractListModel;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.UIManager;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;
import java.awt.Window.Type;
import data.Exercise;
import data.Workout;
import javax.swing.table.DefaultTableModel;
import javax.swing.ListSelectionModel;
import javax.swing.DropMode;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseEvent;
import java.awt.ComponentOrientation;


public class Preview extends JFrame {
	
	private JPanel contentPane;
	protected JTable table;
	protected DefaultTableModel model;
	
	//************************************************************
	// initialize
	//		Initialize the contents of the frame.
	//************************************************************
	public Preview() {
		setType(Type.POPUP);
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Throwable e) {
			e.printStackTrace();
		}
		setTitle("Workout Preview");
		setIconImage(Toolkit.getDefaultToolkit().getImage(Preview.class.getResource("/ui/resources/format-list-ordered_24x24.png")));
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(0, 0, 450, 380);
		contentPane = new JPanel();
//		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
//		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 414, 326);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		table.setDropMode(DropMode.INSERT_ROWS);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table.setDragEnabled(true);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"#", "Name", "Sets", "Reps/Set", "Time Between", "Rest After"
			}
		) {
			Class[] columnTypes = new Class[] {
				Integer.class, String.class, Integer.class, Integer.class, Integer.class, Integer.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			boolean[] columnEditables = new boolean[] {
				false, false, false, false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table.getColumnModel().getColumn(0).setResizable(false);
		table.getColumnModel().getColumn(0).setPreferredWidth(21);
		table.getColumnModel().getColumn(1).setResizable(false);
		table.getColumnModel().getColumn(1).setPreferredWidth(150);
		table.getColumnModel().getColumn(2).setResizable(false);
		table.getColumnModel().getColumn(2).setPreferredWidth(37);
		table.getColumnModel().getColumn(3).setResizable(false);
		table.getColumnModel().getColumn(3).setPreferredWidth(57);
		table.getColumnModel().getColumn(4).setResizable(false);
		table.getColumnModel().getColumn(4).setPreferredWidth(79);
		table.getColumnModel().getColumn(5).setResizable(false);
		table.getColumnModel().getColumn(5).setPreferredWidth(60);
		scrollPane.setViewportView(table);
	}
		
	public int add(Exercise theExercise) {
		String name = theExercise.getName();
		int sets = theExercise.getSets();
		int reps = theExercise.getReps();
		int timeBetweenSets = theExercise.getTimeBetween();
		int restAfterExercise = theExercise.getRestAfter();
		
		model = (DefaultTableModel)table.getModel();
		int index = model.getRowCount();
		int position = index + 1;
		model.addRow(new Object[]{position, name, sets, reps, timeBetweenSets, restAfterExercise});
		return index;
	}
	
	public int delete() {
		int selectedIndex = table.getSelectedRow();
		if (selectedIndex != -1) {
			model = (DefaultTableModel)table.getModel();
			model.removeRow(selectedIndex);
			//Set the new position for each row, so they remain in order.
			for (int i=0; i < model.getRowCount(); i++) {
				model.setValueAt(i+1, i, 0);
			}
		}
		return selectedIndex;
	}
}
