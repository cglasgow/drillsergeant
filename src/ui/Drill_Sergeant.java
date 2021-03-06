package ui;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Point;

import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.AbstractListModel;
import javax.swing.border.BevelBorder;
import javax.swing.UIManager;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;
import javax.swing.DefaultListModel;
import javax.swing.JSeparator;
import javax.swing.JComboBox;
import javax.swing.JSpinner;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.JOptionPane; //Added manually.
import javax.swing.JDialog;		//Added manually.
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JRadioButton;
import java.awt.Component;
import javax.swing.JToggleButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;
import javax.swing.JTextPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.DropMode;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JInternalFrame;
import javax.swing.JDesktopPane;
import javax.swing.JLayeredPane;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import util.Format;

import data.Exercise;
import data.Workout;
import data.ActiveWorkout;
import data.XMLSaxParser;
import data.XMLWriter;

public class Drill_Sergeant {
	protected JFrame 			frmDrillSergeant;
	private Preview 			frmPreview = new Preview();
	private JList 				listWorkout;
	private JTextField 			txtCurrent;
	private JTextField 			txtCurrentSet;
	private JTextField 			txtTotalSets;
	private JTextField 			txtRepCount;
	private JTextField 			txtSetTimeLeft;
	private JTextField 			txtTotalTimeLeft;
	private JTextField 			txtNext;
	private JTextField 			txtWorkout;
	private JProgressBar 		progressBar;
	private JLabel 				lblNextSetStarts;
	private JLabel 				lblWorkoutTitle;
	private JLabel 				dlblTotalWorkoutTime;
	private JButton				btnPreview;
	private JButton 			btnStart;
	private JButton				btnDelete2;
	private JComboBox 			cbName;
	private JComboBox 			cbSets;
	private JComboBox 			cbReps;
	private JComboBox 			cbBetweenMin;
	private JComboBox 			cbBetweenSec;
	private JComboBox 			cbAfterMin;
	private JComboBox 			cbAfterSec;
	private CardLayout 			cardlayout = new CardLayout();
	private JPanel 				cards = new JPanel(cardlayout);
	private ImageIcon 			dialogIcon;
	private	ImageIcon 			okIcon = new ImageIcon(Drill_Sergeant.class.getResource("/ui/resources/dialog-ok-apply-5_32x32.png"));;
	private Workout 			newWorkout;
	private ActiveWorkout 		activeWorkout;
	private String 				workoutName = new String();
	private XMLSaxParser 		handler = new XMLSaxParser();
	private Boolean				xmlNeedsParsing = true;
	private DefaultListModel 	listModel = new DefaultListModel();
	private Timer 				masterTimer;
	private Timer 				setTimer;
	private int 				timeOfPauseSet;
	private int 				timeOfPauseMaster;
	private WorkoutTimerTask 	masterTimeCountdownTask;
	private WorkoutTimerTask 	setTimeCountdownTask;
	private Boolean 			isWorkoutRunning = false;
	private Boolean 			isWorkoutPaused = false;
	private Boolean 			isWorkoutComplete = false;
	private final Color			DS_BLUE = new Color(51, 153, 255);
	private Boolean				isCreatingWorkout;
	private int					currentWorkoutIndex;						//Keeps track of the index of an existing workout being edited, so that when it's saved, it can overwrite itself.
	
	//************************************************************
	// Drill_Sergeant
	//		Initialize the contents of the frame.
	//************************************************************
	public Drill_Sergeant() {
		//Set UI look and feel.
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
		//Main Application Frame
		frmDrillSergeant = new JFrame();
		frmDrillSergeant.setName("DrillSergeant");
		frmDrillSergeant.setIconImage(Toolkit.getDefaultToolkit().getImage(Drill_Sergeant.class.getResource("/ui/resources/stopwatch.png")));
		frmDrillSergeant.setResizable(false);
		frmDrillSergeant.setTitle("Drill Sergeant");
		frmDrillSergeant.setBounds(100, 100, 520, 600);
		frmDrillSergeant.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmDrillSergeant.setLocationRelativeTo(null);
		frmDrillSergeant.getContentPane().setLayout(null);
		
		//The main card panel containing all of the "cards" (screens) in the main application frame.
		cards.setBounds(0, 0, 514, 551);
		frmDrillSergeant.getContentPane().add(cards);
		
		//=============================================================================================================
		// Card 1 - Title Screen
		//=============================================================================================================
		JPanel card1 = new JPanel();
		card1.setBackground(UIManager.getColor("Tree.dropLineColor"));
		cards.add(card1, "card1");
		card1.setLayout(null);

		//-------------
		// Images
		//-------------
		JLabel lblTitleImage = new JLabel("");
		lblTitleImage.setIcon(new ImageIcon(Drill_Sergeant.class.getResource("/ui/resources/dstitle2.png")));
		lblTitleImage.setBounds(109, 35, 300, 301);
		card1.add(lblTitleImage);
		
		//-------------
		// Buttons
		//-------------
		//Open
		JButton btnOpen = new JButton("Open Workout");
		btnOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (xmlNeedsParsing) {
						handler.clearWorkoutList();
						parseXML("config/user_config.xml");
						xmlNeedsParsing = false;
					}
					clearWorkoutList();
					displayWorkoutList(handler);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				swapView("card2");
			}
		});
		btnOpen.setIcon(new ImageIcon(Drill_Sergeant.class.getResource("/ui/resources/document-open-folder_24x24.png")));
		btnOpen.setBackground(UIManager.getColor("Tree.dropLineColor"));
		btnOpen.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnOpen.setBounds(109, 360, 132, 74);
		card1.add(btnOpen);
		
		//New
		JButton btnNew = new JButton("New Workout");
		btnNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//swapView("card3");
				dialogIcon = new ImageIcon(Drill_Sergeant.class.getResource("/ui/resources/document-new-8_24x24.png"));
				String temp = (String) JOptionPane.showInputDialog(frmDrillSergeant, "Please enter a name for the workout:", "Name", JOptionPane.INFORMATION_MESSAGE, dialogIcon, null, workoutName);
				newWorkout = new Workout(temp);
				String workoutName = newWorkout.getName();
				if (workoutName != null) {
					isCreatingWorkout = true;
					currentWorkoutIndex = -1;		//Set to -1 since the workout is new and does not yet exist in the Workout list.
					txtWorkout.setText(workoutName);
					swapView("card3");
					togglePreviewWindow();
				}
			}
		});
		btnNew.setIcon(new ImageIcon(Drill_Sergeant.class.getResource("/ui/resources/document-new-8_24x24.png")));
		btnNew.setBackground(UIManager.getColor("Tree.dropLineColor"));
		btnNew.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnNew.setBounds(277, 360, 132, 74);
		card1.add(btnNew);
		
		//View Journal
		JButton btnJournal = new JButton("View Journal");
		btnJournal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		btnJournal.setIcon(new ImageIcon(Drill_Sergeant.class.getResource("/ui/resources/accessories-dictionary_24x24.png")));
		btnJournal.setBackground(UIManager.getColor("Tree.dropLineColor"));
		btnJournal.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnJournal.setBounds(109, 458, 132, 74);
		card1.add(btnJournal);
		
		//Settings
		JButton btnSettings = new JButton("Settings");
		btnSettings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				swapView("card5");
			}
		});
		btnSettings.setIcon(new ImageIcon(Drill_Sergeant.class.getResource("/ui/resources/configure-3_24x24.png")));
		btnSettings.setBackground(UIManager.getColor("Tree.dropLineColor"));
		btnSettings.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnSettings.setBounds(277, 458, 132, 74);
		card1.add(btnSettings);
		
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(Drill_Sergeant.class.getResource("/ui/resources/textureAlum.jpg")));
		label.setBounds(0, 0, 514, 551);
		card1.add(label);
		
		//=============================================================================================================
		// Card 2 - Select Workout
		//=============================================================================================================
		JPanel card2 = new JPanel();
		card2.setLayout(null);
		cards.add(card2, "card2");
		
		//-------------
		// Borders
		//-------------
		JPanel panelTitleBorderCard2 = new JPanel();
		panelTitleBorderCard2.setFont(new Font("Tahoma", Font.PLAIN, 11));
		panelTitleBorderCard2.setToolTipText("");
		panelTitleBorderCard2.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Select a Workout", TitledBorder.CENTER, TitledBorder.TOP, null, UIManager.getColor("windowBorder")));
		panelTitleBorderCard2.setBounds(10, 27, 496, 355);
		card2.add(panelTitleBorderCard2);
		panelTitleBorderCard2.setLayout(null);
		
		JScrollPane scrollWorkout = new JScrollPane();
		scrollWorkout.setBounds(6, 16, 484, 332);
		panelTitleBorderCard2.add(scrollWorkout);
		
		//-------------
		// List
		//-------------
		listWorkout = new JList();
		listWorkout.setDragEnabled(true);
		scrollWorkout.setViewportView(listWorkout);
		listWorkout.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listWorkout.setFont(new Font("Tahoma", Font.PLAIN, 12));
		listWorkout.setBorder(new BevelBorder(BevelBorder.LOWERED, UIManager.getColor("InternalFrame.resizeIconShadow"), UIManager.getColor("InternalFrame.borderLight"), null, null));
		listWorkout.setModel(listModel);
		
		//-------------
		// Buttons
		//-------------
		//Open
		JButton btnOpen2 = new JButton("   Open");
		btnOpen2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int selectedIndex = listWorkout.getSelectedIndex();
				if (selectedIndex != -1) {
					selectWorkout(handler, selectedIndex, "btnOpen2");
				}
			}
		});
		btnOpen2.setIcon(new ImageIcon(Drill_Sergeant.class.getResource("/ui/resources/document-open-folder_32x32.png")));
		btnOpen2.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnOpen2.setBounds(10, 408, 132, 74);
		card2.add(btnOpen2);
		
		//Delete
		JButton btnDelete = new JButton("   Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedIndex = listWorkout.getSelectedIndex();
				if (selectedIndex != -1) {
					int choice = JOptionPane.showConfirmDialog(cards, "Are you sure you want to delete this workout?", "Delete", JOptionPane.YES_NO_OPTION);
					if (choice == JOptionPane.YES_OPTION) {
						//Remove the selected workout from the workoutList ArrayList.
						handler.removeFromWorkoutList(selectedIndex);
						//Rewrite the xml with the updated workoutList.
						XMLWriter theXMLWriter = new XMLWriter();
						theXMLWriter.writeXML(handler);
						//Now, wipeout the entire workoutList ArrayList since it is about to be re-created from the new xml file.
						handler.clearWorkoutList();
						//Parse the newly written xml.
						try {
							parseXML("config/user_config.xml");
							clearWorkoutList(); //Clear the JList containing the workouts.
							displayWorkoutList(handler);
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					} else {
						//Do nothing except close dialog box.
					}
				}
			}
		});
		btnDelete.setIcon(new ImageIcon(Drill_Sergeant.class.getResource("/ui/resources/edit-delete-2.png")));
		btnDelete.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnDelete.setBounds(372, 408, 132, 74);
		card2.add(btnDelete);
		
		//Edit
		JButton btnEdit = new JButton("   Edit");
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedIndex = listWorkout.getSelectedIndex();
				if (selectedIndex !=-1) {
					currentWorkoutIndex = selectedIndex;
					selectWorkout(handler, currentWorkoutIndex, "btnEdit");
				}
			}
		});
		btnEdit.setIcon(new ImageIcon(Drill_Sergeant.class.getResource("/ui/resources/edit-4.png")));
		btnEdit.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnEdit.setBounds(192, 408, 132, 74);
		card2.add(btnEdit);
		
		//Back
		JButton btnBack = new JButton("  Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				swapView("card1");
			}
		});
		btnBack.setIcon(new ImageIcon(Drill_Sergeant.class.getResource("/ui/resources/go-previous-7_32x32.png")));
		btnBack.setBounds(10, 505, 100, 35);
		card2.add(btnBack);
		
		//=============================================================================================================
		// Card 3 - Edit Workout
		//=============================================================================================================
		JPanel card3 = new JPanel();
		cards.add(card3, "card3");
		card3.setLayout(null);
		
		//-------------
		// Borders
		//-------------
		JPanel panelTitleBorderCard3 = new JPanel();
		panelTitleBorderCard3.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Edit Workout", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		panelTitleBorderCard3.setBounds(10, 11, 494, 392);
		card3.add(panelTitleBorderCard3);
		panelTitleBorderCard3.setLayout(null);
		
		//-------------
		// Combo Boxes
		//-------------
		//Name
		cbName = new JComboBox();
		cbName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JComboBox cbExercises = (JComboBox)arg0.getSource();
				String selected = (String)cbExercises.getSelectedItem();
				System.out.print(selected);
				if (selected == "**Custom**") {
					frmDrillSergeant.requestFocus(); //Make main application window request focus, so user doesn't have to click on input dialog before typing.
					String customName = (String) JOptionPane.showInputDialog(cards, "Please enter a name for the custom exercise:", "Name", JOptionPane.INFORMATION_MESSAGE);
					//Add the custom exercise if user doesn't cancel at input prompt.
					if (customName != null) {
						if (customName.length() > 0) {
							System.out.print(customName);
							cbExercises.setEditable(true);
							cbExercises.setSelectedItem(customName);
							cbExercises.addItem(customName);
							cbExercises.setEditable(false);
						} else {
							JOptionPane.showMessageDialog(cards, "The name entered was blank.  Please try again.", "Notification", JOptionPane.ERROR_MESSAGE);
							cbName.setSelectedIndex(0);
						}
					} else {
						//Reset the combo box.
						cbName.setSelectedIndex(0);
					}
					
				}
			}
		});
		cbName.setModel(new DefaultComboBoxModel(new String[] {"--------------------------Select an exercise to add-------------------------", "**Custom**", "Crunches", "Lunges", "Pushups", "Pullups", "Situps"}));
		cbName.setName("");
		cbName.setBounds(78, 96, 352, 34);
		panelTitleBorderCard3.add(cbName);
		
		//Sets
		cbSets = new JComboBox();
		cbSets.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if ((String)cbSets.getSelectedItem() == "1") {
					cbBetweenMin.setEnabled(false);
					cbBetweenSec.setEnabled(false);
					cbBetweenMin.setSelectedItem("0");
					cbBetweenSec.setSelectedItem("0");
				} else {
					cbBetweenMin.setEnabled(true);
					cbBetweenSec.setEnabled(true);
				}
			}
		});
		cbSets.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20"}));
		cbSets.setEditable(true);
		cbSets.setBounds(212, 160, 50, 20);
		panelTitleBorderCard3.add(cbSets);
		
		//Reps
		cbReps = new JComboBox();
		cbReps.setModel(new DefaultComboBoxModel(new String[] {"5", "10", "15", "20", "25", "30", "35", "40", "45", "50"}));
		cbReps.setEditable(true);
		cbReps.setBounds(212, 219, 50, 20);
		panelTitleBorderCard3.add(cbReps);
		
		//Time between sets
		cbBetweenMin = new JComboBox();
		cbBetweenMin.setModel(new DefaultComboBoxModel(new String[] {"0", "1", "2", "3", "4", "5"}));
		cbBetweenMin.setEditable(true);
		cbBetweenMin.setEnabled(false); //Initialize to false since "Sets" is initialized to 1.
		cbBetweenMin.setBounds(212, 281, 40, 20);
		panelTitleBorderCard3.add(cbBetweenMin);
		
		cbBetweenSec = new JComboBox();
		cbBetweenSec.setModel(new DefaultComboBoxModel(new String[] {"0", "5", "10", "15", "20", "25", "30", "35", "40", "45", "50", "55"}));
		cbBetweenSec.setEditable(true);
		cbBetweenSec.setEnabled(false); //Initialize to false since "Sets" is initialized to 1.
		cbBetweenSec.setBounds(282, 281, 40, 20);
		panelTitleBorderCard3.add(cbBetweenSec);
		
		//Rest time after exercise
		cbAfterMin = new JComboBox();
		cbAfterMin.setModel(new DefaultComboBoxModel(new String[] {"0", "1", "2", "3", "4", "5"}));
		cbAfterMin.setEditable(true);
		cbAfterMin.setBounds(212, 347, 40, 20);
		panelTitleBorderCard3.add(cbAfterMin);
		
		cbAfterSec = new JComboBox();
		cbAfterSec.setModel(new DefaultComboBoxModel(new String[] {"0", "5", "10", "15", "20", "25", "30", "35", "40", "45", "50", "55"}));
		cbAfterSec.setEditable(true);
		cbAfterSec.setBounds(282, 347, 40, 20);
		panelTitleBorderCard3.add(cbAfterSec);
		
		//-------------
		// Text
		//-------------
		JLabel lblNameOfWorkout = new JLabel("Name:");
		lblNameOfWorkout.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNameOfWorkout.setBounds(10, 44, 59, 14);
		panelTitleBorderCard3.add(lblNameOfWorkout);
		
		//Workout Name
		txtWorkout = new JTextField();
		txtWorkout.setText("My_Workout_A");
		txtWorkout.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtWorkout.setBounds(78, 34, 352, 34);
		panelTitleBorderCard3.add(txtWorkout);
		txtWorkout.setColumns(10);
		
		//Exercises
		JLabel lblExercise = new JLabel("Exercises:");
		lblExercise.setBounds(10, 105, 58, 14);
		panelTitleBorderCard3.add(lblExercise);
		lblExercise.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		//Sets
		JLabel lblSets = new JLabel("Sets:");
		lblSets.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSets.setBounds(133, 162, 69, 14);
		panelTitleBorderCard3.add(lblSets);
		lblSets.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		//Reps/Set
		JLabel lblNewLabel = new JLabel("Reps/Set:");
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel.setBounds(133, 221, 69, 14);
		panelTitleBorderCard3.add(lblNewLabel);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		//Time Between Sets
		JLabel lblTimeBetweenSets = new JLabel("Time Between Sets:");
		lblTimeBetweenSets.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTimeBetweenSets.setBounds(43, 281, 159, 14);
		panelTitleBorderCard3.add(lblTimeBetweenSets);
		lblTimeBetweenSets.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		JLabel lblBetweenMin = new JLabel("min");
		lblBetweenMin.setBounds(256, 284, 16, 14);
		panelTitleBorderCard3.add(lblBetweenMin);
		
		JLabel lblBetweenSec = new JLabel("sec");
		lblBetweenSec.setBounds(326, 284, 16, 14);
		panelTitleBorderCard3.add(lblBetweenSec);
		
		//Rest Time After Exercise
		JLabel lblRestTime = new JLabel("Rest Time After This Exercise:");
		lblRestTime.setHorizontalAlignment(SwingConstants.RIGHT);
		lblRestTime.setBounds(22, 347, 180, 14);
		panelTitleBorderCard3.add(lblRestTime);
		lblRestTime.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		JLabel lblAfterMin = new JLabel("min");
		lblAfterMin.setBounds(256, 354, 16, 14);
		panelTitleBorderCard3.add(lblAfterMin);
		
		JLabel lblAfterSec = new JLabel("sec");
		lblAfterSec.setBounds(326, 354, 16, 14);
		panelTitleBorderCard3.add(lblAfterSec);
		
		//Total Workout Time (static label)
		JLabel lblTotalWorkoutTime = new JLabel("Total Workout Time");
		lblTotalWorkoutTime.setBounds(386, 347, 98, 14);
		panelTitleBorderCard3.add(lblTotalWorkoutTime);
		
		//Total Workout Time (dynamic label)
		dlblTotalWorkoutTime = new JLabel("00:00:00");
		dlblTotalWorkoutTime.setFont(new Font("Tahoma", Font.PLAIN, 18));
		dlblTotalWorkoutTime.setHorizontalAlignment(SwingConstants.CENTER);
		dlblTotalWorkoutTime.setBounds(386, 358, 98, 23);
		panelTitleBorderCard3.add(dlblTotalWorkoutTime);
		
		//-------------
		// Buttons
		//-------------
		//Add
		JButton btnAdd = new JButton(" Add");
		btnAdd.setBounds(386, 233, 98, 39);
		panelTitleBorderCard3.add(btnAdd);
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Exercise newExercise = new Exercise();
				if (cbName.getSelectedIndex() == 0) {
					JOptionPane.showMessageDialog(cards, "Please select an exercise.", "Notification", JOptionPane.ERROR_MESSAGE);
					cbName.requestFocus();
					return;
				}
				newExercise.setName((String)cbName.getSelectedItem());
				newExercise.setGraphicURL("URL GOES HERE");
				newExercise.setSets((String)cbSets.getSelectedItem());
				newExercise.setReps((String)cbReps.getSelectedItem());
				newExercise.setTimeBetween((String)cbBetweenMin.getSelectedItem(), (String)cbBetweenSec.getSelectedItem());
				newExercise.setRestAfter((String)cbAfterMin.getSelectedItem(), (String)cbAfterSec.getSelectedItem());
				newExercise.setTotalTime();
				if (newWorkout.addExercise(newExercise) != -1) {
					//newWorkout.setLengthInSecs(Integer.toString(newExercise.getTotalTime()));
					dlblTotalWorkoutTime.setText( Format.toHHMMSS(newWorkout.calculateLengthInSecs()) );
					int previewIndex = frmPreview.add(newExercise);
					newExercise.setPosition(previewIndex);
					//ImageIcon okIcon = new ImageIcon(Drill_Sergeant.class.getResource("/ui/resources/dialog-ok-apply-5_32x32.png"));
					JOptionPane.showMessageDialog(cards, "Exercise added!", "Notification", JOptionPane.INFORMATION_MESSAGE, okIcon);
				} else {
					JOptionPane.showMessageDialog(cards, "Cannot add - Exercise limit reached for this workout!", "Notification", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnAdd.setIcon(new ImageIcon(Drill_Sergeant.class.getResource("/ui/resources/edit-add-2_16x16.png")));
		btnAdd.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		//Delete
		btnDelete2 = new JButton("Delete");
		btnDelete2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int index = frmPreview.delete();
				if (index != -1) {
					newWorkout.deleteExercise(index);
					dlblTotalWorkoutTime.setText( Format.toHHMMSS(newWorkout.calculateLengthInSecs()) );
				} else {
					JOptionPane.showMessageDialog(cards, "Please select an exercise to delete from the Workout Preview window.", "Notification", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnDelete2.setIcon(new ImageIcon(Drill_Sergeant.class.getResource("/ui/resources/edit-delete-2_16x16.png")));
		btnDelete2.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnDelete2.setBounds(386, 159, 98, 39);
		panelTitleBorderCard3.add(btnDelete2);
		
		//Cancel
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int choice = JOptionPane.showConfirmDialog(cards, "Are you sure you want to cancel? All unsaved work will be lost.", "Cancel", JOptionPane.YES_NO_OPTION);
				if (choice == JOptionPane.YES_OPTION) {
					resetEditScreen();
					resetPreview();
					frmPreview.setVisible(false);
					btnDelete2.setVisible(false);
					swapView("card1");
				} else {
					//Do nothing except close dialog box.
				}
			}
		});
		btnCancel.setIcon(new ImageIcon(Drill_Sergeant.class.getResource("/ui/resources/dialog-cancel-3.png")));
		btnCancel.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnCancel.setBounds(10, 439, 132, 74);
		card3.add(btnCancel);
		
		//Preview
		btnPreview = new JButton("Preview");
		btnPreview.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//swapView("card6");
				togglePreviewWindow();
			}
		});
		btnPreview.setIcon(new ImageIcon(Drill_Sergeant.class.getResource("/ui/resources/format-list-ordered_24x24.png")));
		btnPreview.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnPreview.setBounds(193, 439, 132, 74);
		card3.add(btnPreview);
		
		//Finished
		JButton btnSave = new JButton("Finished");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Object[] options = { "Save and Exit", "Save and Begin Workout", "Cancel" };
				int choice = JOptionPane.showOptionDialog(cards, 
						"Do you want to exit to the Main screen or begin this workout?\n(Press Cancel to stay on this screen)", 
						null, 
						JOptionPane.YES_NO_CANCEL_OPTION, 
						JOptionPane.QUESTION_MESSAGE, 
						null, options, options[0]);
				
				if (choice == JOptionPane.NO_OPTION) {
					if (newWorkout.getExerciseListSize() > 0) {
						newWorkout.setName(txtWorkout.getText());
						newWorkout.setLengthInSecs(Integer.toString(newWorkout.calculateLengthInSecs()));
						newWorkout.save(handler, currentWorkoutIndex);
						xmlNeedsParsing = true; //Set to true so that the xml will be re-parsed before the Workout List is opened again.
						loadWorkout();
						resetEditScreen();
						resetPreview();
						frmPreview.setVisible(false);
						btnDelete2.setVisible(false);
						swapView("card4");
					} else {
						JOptionPane.showMessageDialog(cards, "Cannot save - The workout doesn't contain any exercises.  Please add exercises and try again.", "Notification", JOptionPane.ERROR_MESSAGE);
					}
				} else if (choice == JOptionPane.YES_OPTION) {
					if (newWorkout.getExerciseListSize() > 0) {
						newWorkout.setName(txtWorkout.getText());
						newWorkout.setLengthInSecs(Integer.toString(newWorkout.calculateLengthInSecs()));
						newWorkout.save(handler, currentWorkoutIndex);
						resetEditScreen();
						resetPreview();
						frmPreview.setVisible(false);
						btnDelete2.setVisible(false);
						try {
							handler.clearWorkoutList();
							parseXML("config/user_config.xml");
							xmlNeedsParsing = false;
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						clearWorkoutList();
						displayWorkoutList(handler);
						swapView("card1");
					} else {
						JOptionPane.showMessageDialog(cards, "Cannot save.  The workout doesn't contain any exercises.   Please add exercises and try again.", "Notification", JOptionPane.ERROR_MESSAGE);
					}
				} else if (choice == JOptionPane.CANCEL_OPTION) {
					//do nothing except close dialog box
				}
			}
		});
		btnSave.setIcon(new ImageIcon(Drill_Sergeant.class.getResource("/ui/resources/document-save-3_24x24.png")));
		btnSave.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnSave.setBounds(372, 439, 132, 74);
		card3.add(btnSave);
		
		//=============================================================================================================
		// Card 4 - Active Workout
		//=============================================================================================================
		JPanel card4 = new JPanel();
		cards.add(card4, "card4");
		card4.setLayout(null);
		
		//-------------
		// Text
		//-------------
		//Workout Name
		lblWorkoutTitle = new JLabel("");
		lblWorkoutTitle.setForeground(new Color(102, 102, 102));
		lblWorkoutTitle.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblWorkoutTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblWorkoutTitle.setBounds(10, 11, 487, 26);
		card4.add(lblWorkoutTitle);
		
		//Current Exercise
		JLabel lblCurrentExercise = new JLabel("Current:");
		lblCurrentExercise.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblCurrentExercise.setHorizontalAlignment(SwingConstants.LEFT);
		lblCurrentExercise.setBounds(10, 72, 52, 17);
		card4.add(lblCurrentExercise);
		
		txtCurrent = new JTextField();
		txtCurrent.setHorizontalAlignment(SwingConstants.LEFT);
		txtCurrent.setForeground(new Color(0, 153, 204));
		txtCurrent.setFont(new Font("Tahoma", Font.BOLD, 30));
		txtCurrent.setText("");
		txtCurrent.setEditable(false);
		txtCurrent.setBounds(75, 48, 429, 57);
		card4.add(txtCurrent);
		txtCurrent.setColumns(10);
		
		//Next Exercise
		JLabel lblUpNext = new JLabel("Up Next:");
		lblUpNext.setHorizontalAlignment(SwingConstants.LEFT);
		lblUpNext.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblUpNext.setBounds(12, 123, 60, 17);
		card4.add(lblUpNext);
		
		txtNext = new JTextField();
		txtNext.setHorizontalAlignment(SwingConstants.LEFT);
		txtNext.setEnabled(false);
		txtNext.setText("");
		txtNext.setForeground(new Color(102, 102, 102));
		txtNext.setFont(new Font("Tahoma", Font.BOLD, 14));
		txtNext.setEditable(false);
		txtNext.setColumns(10);
		txtNext.setBounds(75, 116, 253, 32);
		card4.add(txtNext);
		
		//Current Set
		JLabel lblSet = new JLabel("Set:");
		lblSet.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblSet.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSet.setBounds(117, 192, 46, 14);
		card4.add(lblSet);
		
		txtCurrentSet = new JTextField();
		txtCurrentSet.setEditable(false);
		txtCurrentSet.setForeground(new Color(0, 153, 204));
		txtCurrentSet.setFont(new Font("Tahoma", Font.BOLD, 32));
		txtCurrentSet.setHorizontalAlignment(SwingConstants.CENTER);
		txtCurrentSet.setText("");
		txtCurrentSet.setBounds(173, 167, 60, 57);
		card4.add(txtCurrentSet);
		txtCurrentSet.setColumns(10);
		
		JLabel lblOf = new JLabel("of");
		lblOf.setHorizontalAlignment(SwingConstants.CENTER);
		lblOf.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblOf.setBounds(248, 182, 20, 27);
		card4.add(lblOf);
		
		//Total Sets
		txtTotalSets = new JTextField();
		txtTotalSets.setForeground(new Color(0, 153, 204));
		txtTotalSets.setFont(new Font("Tahoma", Font.BOLD, 32));
		txtTotalSets.setHorizontalAlignment(SwingConstants.CENTER);
		txtTotalSets.setText("");
		txtTotalSets.setEditable(false);
		txtTotalSets.setColumns(10);
		txtTotalSets.setBounds(279, 167, 60, 57);
		card4.add(txtTotalSets);
		
		//Rep Count
		JLabel lblRepCount = new JLabel("Rep Count:");
		lblRepCount.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblRepCount.setBounds(89, 233, 80, 20);
		card4.add(lblRepCount);
		
		txtRepCount = new JTextField();
		txtRepCount.setForeground(new Color(0, 153, 204));
		txtRepCount.setHorizontalAlignment(SwingConstants.CENTER);
		txtRepCount.setText("");
		txtRepCount.setFont(new Font("Tahoma", Font.BOLD, 54));
		txtRepCount.setEditable(false);
		txtRepCount.setBounds(44, 253, 155, 52);
		card4.add(txtRepCount);
		txtRepCount.setColumns(10);
		
		//Set Time Left
		lblNextSetStarts = new JLabel("Next Set In:");
		lblNextSetStarts.setHorizontalAlignment(SwingConstants.CENTER);
		lblNextSetStarts.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNextSetStarts.setBounds(337, 233, 120, 20);
		card4.add(lblNextSetStarts);
		
		txtSetTimeLeft = new JTextField();
		txtSetTimeLeft.setForeground(new Color(0, 153, 204));
		txtSetTimeLeft.setHorizontalAlignment(SwingConstants.CENTER);
		txtSetTimeLeft.setText("");
		txtSetTimeLeft.setFont(new Font("Tahoma", Font.BOLD, 54));
		txtSetTimeLeft.setEditable(false);
		txtSetTimeLeft.setColumns(10);
		txtSetTimeLeft.setBounds(317, 253, 155, 52);
		card4.add(txtSetTimeLeft);
		
		//Full Workout Progress
		JLabel lblProgress = new JLabel("Full Workout Progress");
		lblProgress.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblProgress.setHorizontalAlignment(SwingConstants.CENTER);
		lblProgress.setHorizontalTextPosition(SwingConstants.CENTER);
		lblProgress.setBounds(175, 316, 164, 20);
		card4.add(lblProgress);
		
		//Total Time Remaining
		JLabel lblTimeRemaining = new JLabel("Total Time Remaining:");
		lblTimeRemaining.setHorizontalTextPosition(SwingConstants.CENTER);
		lblTimeRemaining.setHorizontalAlignment(SwingConstants.LEFT);
		lblTimeRemaining.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblTimeRemaining.setBounds(270, 380, 105, 20);
		card4.add(lblTimeRemaining);
		
		txtTotalTimeLeft = new JTextField();
		txtTotalTimeLeft.setForeground(new Color(0, 153, 204));
		txtTotalTimeLeft.setHorizontalAlignment(SwingConstants.CENTER);
		txtTotalTimeLeft.setFont(new Font("Tahoma", Font.BOLD, 16));
		txtTotalTimeLeft.setText("");
		txtTotalTimeLeft.setEditable(false);
		txtTotalTimeLeft.setBounds(376, 380, 96, 26);
		card4.add(txtTotalTimeLeft);
		txtTotalTimeLeft.setColumns(10);
		
		//-------------
		// Progress Bars
		//-------------
		progressBar = new JProgressBar();
		progressBar.setFont(new Font("Tahoma", Font.BOLD, 14));
		progressBar.setStringPainted(true);
		progressBar.setBounds(44, 341, 428, 37);
		card4.add(progressBar);
		
		//-------------
		// Buttons
		//-------------
		//Start
		btnStart = new JButton("START");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (isWorkoutRunning) {
					if (isWorkoutPaused) {
						resumeWorkout();
					} else {
						pauseWorkout();
					}
				} else {
					runWorkout();
				}
				System.out.println("isWorkoutRunning = " + isWorkoutRunning);
				System.out.println("isWorkoutPaused = " + isWorkoutPaused);
			}
		});
		btnStart.setIcon(new ImageIcon(Drill_Sergeant.class.getResource("/ui/resources/stopwatch_start.png")));
		btnStart.setForeground(new Color(102, 102, 102));
		btnStart.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnStart.setBounds(175, 417, 164, 123);
		card4.add(btnStart);
		
		//End Workout
		JButton btnEnd = new JButton("End Workout");
		btnEnd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int choice = JOptionPane.showConfirmDialog(cards, "Are you sure you want to end this workout?", "End Workout", JOptionPane.YES_NO_OPTION);
				if (choice == JOptionPane.YES_OPTION) {
					if (isWorkoutRunning || isWorkoutComplete) {
						resetWorkout();
					}
					swapView("card1");
				} else {
					//Do nothing except close dialog box.
				}
			}
		});
		btnEnd.setIcon(new ImageIcon(Drill_Sergeant.class.getResource("/ui/resources/dialog-disable_16x16.png")));
		btnEnd.setBounds(384, 503, 120, 37);
		card4.add(btnEnd);
		
		//Reset Workout
		JButton btnReset = new JButton("Reset");
		btnReset.setIcon(new ImageIcon(Drill_Sergeant.class.getResource("/ui/resources/arrow-rotate-clockwise_16x16.png")));
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int choice = JOptionPane.showConfirmDialog(cards, "Are you sure you want to reset this workout?", "Reset Workout", JOptionPane.YES_NO_OPTION);
				if (choice == JOptionPane.YES_OPTION) {
					if (isWorkoutRunning || isWorkoutComplete) {
						resetWorkout();
					}
				} else {
					//Do nothing except close dialog box.
				}
			}
		});
		btnReset.setBounds(10, 503, 120, 37);
		card4.add(btnReset);
		
		//=============================================================================================================
		// Card 5 - Settings
		//=============================================================================================================
		JPanel card5 = new JPanel();
		card5.setName("card5");
		cards.add(card5, "card5");
		card5.setLayout(null);
		
		JPanel panelTitleBorderCard5 = new JPanel();
		panelTitleBorderCard5.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Settings", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		panelTitleBorderCard5.setBounds(41, 33, 434, 439);
		card5.add(panelTitleBorderCard5);
		panelTitleBorderCard5.setLayout(null);
		
		//-------------
		// Text
		//-------------
		JLabel lblSound = new JLabel("Sound:");
		lblSound.setIcon(new ImageIcon(Drill_Sergeant.class.getResource("/ui/resources/player-volume_32x32.png")));
		lblSound.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSound.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblSound.setBounds(113, 29, 88, 44);
		panelTitleBorderCard5.add(lblSound);
		
		//-------------
		// Buttons
		//-------------
		//Sound On/Off radio toggle
		JRadioButton rdbtnOn = new JRadioButton("On");
		rdbtnOn.setSelected(true);
		rdbtnOn.setBounds(216, 40, 39, 23);
		panelTitleBorderCard5.add(rdbtnOn);
		
		JRadioButton rdbtnOff = new JRadioButton("Off");
		rdbtnOff.setBounds(257, 40, 46, 23);
		panelTitleBorderCard5.add(rdbtnOff);
		
		//Back
		JButton btnBack_card5 = new JButton("  Back");
		btnBack_card5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				swapView("card1");
			}
		});
		btnBack_card5.setIcon(new ImageIcon(Drill_Sergeant.class.getResource("/ui/resources/go-previous-7_32x32.png")));
		btnBack_card5.setBounds(41, 494, 100, 35);
		card5.add(btnBack_card5);
		
		//=============================================================================================================
		// Card 6 - Preview
		//=============================================================================================================
		JPanel card6 = new JPanel();
		card6.setName("");
		cards.add(card6, "card6");
		card6.setLayout(null);
		
		//-------------
		// Borders
		//-------------
		JScrollPane scrollExercises = new JScrollPane();
		scrollExercises.setBorder(new TitledBorder(null, "Preview", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		scrollExercises.setBounds(10, 30, 494, 380);
		card6.add(scrollExercises);

		//-------------
		// Lists
		//-------------
		JList listExercises = new JList();
		scrollExercises.setViewportView(listExercises);
		
		//-------------
		// Buttons
		//-------------
		//Back
		JButton button = new JButton("  Back");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				swapView("card3");
			}
		});
		button.setIcon(new ImageIcon(Drill_Sergeant.class.getResource("/ui/resources/go-previous-7_32x32.png")));
		button.setBounds(10, 444, 132, 74);
		card6.add(button);
		
		//Delete
		JButton button_1 = new JButton("   Delete");
		button_1.setIcon(new ImageIcon(Drill_Sergeant.class.getResource("/ui/resources/edit-delete-2.png")));
		button_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		button_1.setBounds(372, 444, 132, 74);
		card6.add(button_1);
		
		//=============================================================================================================
		// Menu Bar
		//=============================================================================================================
		JMenuBar menuBar = new JMenuBar();
		frmDrillSergeant.setJMenuBar(menuBar);
		
		JMenu mFile = new JMenu("File");
		menuBar.add(mFile);
		
		JMenu mHelp = new JMenu("Help");
		menuBar.add(mHelp);
		
		JMenuItem mitemUserGuide = new JMenuItem("User Guide");
		mHelp.add(mitemUserGuide);
		
		JMenuItem mitemAbout = new JMenuItem("About");
		mHelp.add(mitemAbout);
		
		//=============================================================================================================
		//Other initialization
		//=============================================================================================================
		//Parse user_config.xml
		try {
			parseXML("config/user_config.xml");
			xmlNeedsParsing = false;
			displayWorkoutList(handler);					
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	} 
	//End of initialize()
	
	
	//************************************************************
	// swapView
	//		Change the current screen of the program.
	// 		Param: cardName - the name of the card to be switched to
	//************************************************************
	public void swapView(String cardName) {
	      cardlayout.show(cards, cardName);
	}
	
	
	//************************************************************
	// resetEditScreen
	//		Clear all fields on the Edit Workout screen.
	//************************************************************
	public void resetEditScreen() {
		txtWorkout.setText("");
		cbName.setSelectedIndex(0);
		cbSets.setSelectedIndex(0);
		cbReps.setSelectedIndex(0);
		cbBetweenMin.setSelectedIndex(0);
		cbBetweenSec.setSelectedIndex(0);
		cbAfterMin.setSelectedIndex(0);
		cbAfterSec.setSelectedIndex(0);
		dlblTotalWorkoutTime.setText("00:00:00");
	}
	
	
	//************************************************************
	// resetPreview
	//		Clear all exercises stored in the Preview pop-up window.
	//************************************************************
	public void resetPreview() {
		
		frmPreview.model = (DefaultTableModel)frmPreview.table.getModel();
		int rowCount = frmPreview.model.getRowCount();
		System.out.println("Number of rows to clear: " + rowCount);
		for (int i = 0; i < rowCount; i++) {
			frmPreview.model.removeRow(0);
		}
	}
	
	
	//************************************************************
	// togglePreviewWindow
	//		Show/hide the workout preview window.
	//************************************************************
	public void togglePreviewWindow() {
		if (frmPreview.isVisible() == false) {
			//First, center the frame on the main application window.
			frmPreview.setLocationRelativeTo(frmDrillSergeant);
			Point location = new Point(frmPreview.getLocation());
			location.x += 500;
			frmPreview.setLocation(location);
			frmPreview.setVisible(true);
			btnDelete2.setVisible(true);
		} else {
			frmPreview.setVisible(false);
			btnDelete2.setVisible(false);
		}
	}
	
	
	//************************************************************
	// log
	//		Wrapper for System.out.println()
	//************************************************************
	public void log(String logString) {
		System.out.println(logString);
	}
	
	//************************************************************
	// mdPrint
	//		Print any string output to a message dialog.
	//		Param: 	outputString - the string to be displayed in the message box
	//				component - the component on which to display the message box. 
	//************************************************************
	public void mdPrint(String outputString, Component component) {
		JOptionPane.showMessageDialog(component, outputString, "Debug", JOptionPane.INFORMATION_MESSAGE, null);
	}
	
	
	//************************************************************
	// parseXML
	//		Parse the xml file containing data for all the workouts.
	//		Param: 	uri - the URI of the xml file.
	//************************************************************
	public void parseXML(String uri) throws Exception {
    	//Create a "parser factory" for creating SAX parsers
    	SAXParserFactory spfac = SAXParserFactory.newInstance();
	
    	//Now use the parser factory to create a SAXParser object
    	SAXParser sp = spfac.newSAXParser();
	
    	//Create an instance of this class; it defines all the handler methods
    	//handler = new XMLSaxParser();
	
    	//Finally, tell the parser to parse the input and notify the handler
    	sp.parse(uri, handler);
    	handler.readList();
    	//displayWorkoutList(handler);
	}
	
	//************************************************************
	// displayWorkoutList
	//		Display the list of workouts parsed from the xml file
	//		in the list object on the Workout Select screen.
	//************************************************************
	public void displayWorkoutList(XMLSaxParser handler) {
		ArrayList<String> listItems = new ArrayList<String>(handler.getListItems());
		int listItemsSize = listItems.size();
		
		System.out.println("listItemsSize = " + listItemsSize);
//		DefaultListModel listModel = new DefaultListModel();
//		listModel.clear();
		for (int i=0; i<listItemsSize; i++) {
			listModel.addElement(listItems.get(i));
		}
		System.out.println(listModel.size());
//		listWorkout.setModel(listModel);
		listWorkout.setSelectedIndex(0);
	}
	
	//************************************************************
	// clearWorkoutList
	//		Clear the list of workouts from the list object 
	//		on the Workout Select screen.
	//************************************************************
	public void clearWorkoutList() {
		System.out.println("clearWorkoutList!!!");
//		DefaultListModel listModel = new DefaultListModel();
		listModel.clear();
		System.out.println(listModel.size());
		//listWorkout.setModel(listModel);
	
//		for (int i=0; i<listItemsSize; i++) {
//			listModel.(listItems.get(i));
//		}
	}

	//************************************************************
	// selectWorkout
	//		This method is called when the user highlights a workout
	//		from the list of workouts and presses either the "Open"
	//		or "Edit" button.
	//************************************************************
	public void selectWorkout(XMLSaxParser theHandler, int index, String callingBtnName) {
		//Load the workout.
		System.out.println(index);
		newWorkout = new Workout(""); 				//Create a new empty workout so the selected saved workout can be copied into it.
		newWorkout = theHandler.getWorkout(index);	//Copy the saved workout into the new workout.
		System.out.println(newWorkout.toString());
		txtWorkout.setText(newWorkout.getName());
		
		//Switch to either the "Active Workout" or "Edit Workout" screen.
		if (callingBtnName == "btnOpen2") {
			loadWorkout();
			swapView("card4");
		} else if (callingBtnName == "btnEdit") {
			isCreatingWorkout = false;
			dlblTotalWorkoutTime.setText( Format.toHHMMSS(Integer.parseInt(newWorkout.getLengthInSecs())) );
			swapView("card3");
			//Populate the Preview window.
			for (int i=0; i<newWorkout.getExerciseListSize(); i++) {
				int previewIndex = frmPreview.add(newWorkout.getExercise(i));
				newWorkout.getExercise(i).setPosition(previewIndex);
			}
			togglePreviewWindow();
		}
	}
	
	//************************************************************
	// loadWorkout
	//		This method is called when the user opens a workout 
	//		(via the "Open") button from the list on the 
	//		"Select Workout" screen.  Alternatively, it is called
	//		when the user presses the "Finished" button on the 
	//		"Edit Workout" screen and subsequently chooses to begin
	//		the workout.
	//************************************************************
	public void loadWorkout() {
		activeWorkout = new ActiveWorkout(newWorkout);
		lblWorkoutTitle.setText(activeWorkout.getName());
		System.out.println(activeWorkout.toString());
		
	}
	
	//************************************************************
	// runWorkout
	//************************************************************
	public void runWorkout() {
		isWorkoutRunning = true;
		Boolean areExercisesRemaining = true;
		//int totalTimeLeft = 43; //just for testing for now...
		//int setTimeLeft = 3; //FOR TESTING
		int totalTimeLeft = Integer.parseInt(activeWorkout.getLengthInSecs());
		int setTimeLeft = activeWorkout.getExercise(0).getTimeBetween();
		
		masterTimer = new Timer();
		setTimer = new Timer();
		this.masterTimeCountdownTask = new WorkoutTimerTask(this, totalTimeLeft, "MASTER");
		//this.setTimeCountdownTask = new WorkoutTimerTask(this, setTimeLeft, "SET");
		masterTimer.schedule(masterTimeCountdownTask, 0, 1000);
        //setTimer.schedule(setTimeCountdownTask, 0, 1000);
		btnStart.setIcon(new ImageIcon(Drill_Sergeant.class.getResource("/ui/resources/media-playback-pause.png")));
		btnStart.setText("PAUSE");
		
		loadNextExercise();
	}
	
	//************************************************************
	// pauseWorkout
	//************************************************************
	public void pauseWorkout() {
		timeOfPauseMaster = masterTimeCountdownTask.getTimeLeft();
		timeOfPauseSet = setTimeCountdownTask.getTimeLeft();
		masterTimer.cancel();
		setTimer.cancel();
		isWorkoutPaused = true;
		btnStart.setIcon(new ImageIcon(Drill_Sergeant.class.getResource("/ui/resources/stopwatch_start.png")));
		btnStart.setText("RESUME");
	}
	
	//************************************************************
	// resumeWorkout
	//************************************************************
	public void resumeWorkout() {
		masterTimer = new Timer();
		setTimer	= new Timer();
		this.masterTimeCountdownTask = new WorkoutTimerTask(this, timeOfPauseMaster, "MASTER");
		this.setTimeCountdownTask = new WorkoutTimerTask(this, timeOfPauseSet, "SET");
		masterTimer.schedule(masterTimeCountdownTask, 0, 1000);
		setTimer.schedule(setTimeCountdownTask, 0, 1000);
		isWorkoutPaused = false;
		btnStart.setIcon(new ImageIcon(Drill_Sergeant.class.getResource("/ui/resources/media-playback-pause.png")));
		btnStart.setText("PAUSE");
	}
	
	//************************************************************
	// resetWorkout
	//************************************************************
	public void resetWorkout() {
		//Kill timers.
		masterTimer.cancel();
		setTimer.cancel();
		
		//Reset GUI.
		isWorkoutRunning = false;
		isWorkoutPaused = false;
		btnStart.setIcon(new ImageIcon(Drill_Sergeant.class.getResource("/ui/resources/stopwatch_start.png")));
		btnStart.setText("START");
		btnStart.setEnabled(true);
		txtCurrent.setForeground(DS_BLUE);
		txtCurrent.setText("");
		txtNext.setText("");
		txtCurrentSet.setText("");
		txtTotalSets.setText("");
		txtRepCount.setText("");
		txtSetTimeLeft.setForeground(DS_BLUE);
		txtSetTimeLeft.setText("");
		txtTotalTimeLeft.setForeground(DS_BLUE);
		txtTotalTimeLeft.setText("");
		lblNextSetStarts.setText("Next Set Starts In:");
		progressBar.setValue(0);
		
		//Re-instantiate the activeWorkout Object.
		activeWorkout = new ActiveWorkout(newWorkout);
		
		isWorkoutComplete = false;
	}
		
		
	//************************************************************
	// loadNextExercise
	//************************************************************
	public Boolean loadNextExercise() {
		int index = activeWorkout.getCurrentExerciseIndex() + 1;
		Boolean areExercisesRemaining;
		
		if (index < activeWorkout.getExerciseListSize()) {
			System.out.println("Exercise Index = " + index);
			activeWorkout.setCurrentExerciseIndex(index);
			
			//Reset the Set timer.
			int setTimeLeft = activeWorkout.getExercise(index).getTimeBetween();
			System.out.println("setTimeLeft = " + setTimeLeft);
			setTimer.cancel();
			setTimer = new Timer();
			setTimeCountdownTask = new WorkoutTimerTask(this, setTimeLeft, "SET");
	        setTimer.schedule(setTimeCountdownTask, 0, 1000);
			
			activeWorkout.setCurrentExerciseIndex(index);
			activeWorkout.setCurrentSet(1);
			//Set GUI text fields...
			txtCurrent.setText(activeWorkout.getExercise(index).getName());
			if ( index < (activeWorkout.getExerciseListSize() - 1) ) {
				txtNext.setText(activeWorkout.getExercise(index+1).getName());
			} else {
				txtNext.setText("");
			}
			txtCurrent.setForeground(DS_BLUE);
			txtCurrentSet.setText(Integer.toString(activeWorkout.getCurrentSet()));
			txtTotalSets.setText(Integer.toString(activeWorkout.getExercise(index).getSets()));
			txtRepCount.setText(Integer.toString(activeWorkout.getExercise(index).getReps()));
			txtSetTimeLeft.setForeground(DS_BLUE);
			areExercisesRemaining = true;
		} else {
				areExercisesRemaining = false;
				setTimer.cancel();
				masterTimer.cancel();
				txtTotalTimeLeft.setForeground(Color.RED);
				txtCurrent.setForeground(DS_BLUE);
				txtCurrent.setText("Workout Completed!");
				isWorkoutRunning = false;
				isWorkoutComplete = true;
				btnStart.setEnabled(false);
		}
		return areExercisesRemaining;
	}
	
	//************************************************************
	// loadNextSet
	//************************************************************
	public void loadNextSet() {
		int index = activeWorkout.getCurrentExerciseIndex();
		//Check if the current set is the final set and load the next exercise if so.  Otherwise, continue loading the next set.
		if (activeWorkout.getCurrentSet() == activeWorkout.getExercise(index).getSets()) {
			if (activeWorkout.getIsResting() == true) {
				loadRestPeriod(activeWorkout.getExercise(index).getRestAfter());
			} else {
				lblNextSetStarts.setText("Next Set Starts In:");
				loadNextExercise();
			}
		} else {
			int currentSet = activeWorkout.getCurrentSet();
			activeWorkout.setCurrentSet(currentSet+1);
			System.out.println("Set Index = " + currentSet);
			
			//Set GUI textfield.
			txtCurrentSet.setText(Integer.toString(activeWorkout.getCurrentSet()));
			
			//Reset the Set timer.
			int setTimeLeft = activeWorkout.getExercise(index).getTimeBetween();
			setTimeCountdownTask = new WorkoutTimerTask(this, setTimeLeft, "SET");
			setTimer.schedule(setTimeCountdownTask, 0, 1000);
			
			//Enable post-exercise resting period if applicable.
			if (activeWorkout.getCurrentSet() == activeWorkout.getExercise(index).getSets()) {
				if (activeWorkout.getExercise(index).getTimeBetween() > 0) {
					activeWorkout.setIsResting(true);
				}
			}
		}
	}
	
	//************************************************************
	// loadRestPeriod
	//************************************************************
	public void loadRestPeriod(int restTime) {
		//Reset the Set timer.
		setTimeCountdownTask = new WorkoutTimerTask(this, restTime, "REST");
		setTimer.schedule(setTimeCountdownTask, 0, 1000);
		txtSetTimeLeft.setForeground(Color.RED);
		txtCurrent.setForeground(Color.RED);
		txtCurrent.setText("RESTING...");
		txtCurrentSet.setText("");
		txtTotalSets.setText("");
		txtRepCount.setText("");
		lblNextSetStarts.setText("Next Exercise Starts In:");
		activeWorkout.setIsResting(false);
	}
	
	//************************************************************
	// setTxtSetTimeLeft
	//************************************************************
	public void setTxtSetTimeLeft(String theText) {
		txtSetTimeLeft.setText(theText);
	}
	
	//************************************************************
	// setTxtTotalTimeLeft
	//************************************************************
	public void setTxtTotalTimeLeft(String theText) {
		txtTotalTimeLeft.setText(theText);
	}
	
	//************************************************************
	// updateProgressBar
	//************************************************************
	public void updateProgressBar(int timeLeft) {
		double dblPercentLeft = ((float)timeLeft / (float)Integer.parseInt(activeWorkout.getLengthInSecs()) * 100.0);
		int intPercentLeft = (int) dblPercentLeft;
		int currentPercent = 100 - intPercentLeft;
		System.out.println("Current Progress: " + currentPercent + "%");
		progressBar.setValue(currentPercent);
	}
}
