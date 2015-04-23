package track_controller;
import system_wrapper.SystemWrapper;
import track_model.*;
import ctc_office.TrainRoute;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.Color;

import javax.swing.JTable;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JComponent;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.ArrayList;

import javax.swing.Timer;
import javax.swing.JTextPane;
import javax.swing.JTextArea;
import javax.swing.JCheckBox;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;

public class TrackControllerGUI extends JFrame {

	private JPanel contentPane;
	private JTextField PLCFileName;
	public static WaysideSystem wContrl;
	public static boolean plcUploadSuccess;
	private TrackBlock currentBlock;
	private String currentLine;
	private WaysideController currentController;
	private String switchStatus = "No information available.";
	private String crossingStatus = "No information available.";
	private boolean sysMode = false;
	
	private JComboBox<Integer> blockPicker;
	private JComboBox<String> linePicker;
	private static WaysideSystem ws;
	private DefaultTableModel model;
	private JLabel lblCompilationSuccessful;
	private JTextField speedSug;
	private JTextField sugAuth;
	private JButton btnFlipSwitch;
	private JTable table_1;
	private JComboBox<String> blockStatusPicker;
	private JComboBox<String> weatherPicker;
	private static Timer displayTimer;
	private JTextArea CTCInfoPane;
	private static boolean manualOverride = false;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
	  track_model.TrackModel t = new track_model.TrackModel();
	  ws = new WaysideSystem(t);
	  List<Integer> route = new ArrayList<Integer>();
	  route.add(14);
	  route.add(13);
	  route.add(12);
	  route.add(11);
	  route.add(10);
	  route.add(9);
	  route.add(8);
	  route.add(7);
	  route.add(6);
	  route.add(5);
	  route.add(4);
	  route.add(3);
	  route.add(2);
	  route.add(1);
	  route.add(12);
	  route.add(13);
	  TrainRoute r = new TrainRoute("Green", 14, route, 22.0, 1910);
	  WaysideController routed = ws.blockControllerMapGreen.get(1);
	  routed.addRoute(r);
	  ws.setBeacon("g12testbeaconstring/", 31);
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TrackControllerGUI frame = new TrackControllerGUI(ws, false);
					frame.setVisible(true);
					frame.updateData();
					displayTimer.start();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TrackControllerGUI(WaysideSystem w, boolean m) {
		wContrl = w;
		sysMode = m;
		if (sysMode) SystemWrapper.ctcOffice.setTrackLayout("Green", wContrl.tracks.trackLayouts.get("Green").layout,wContrl.tracks.trackLayouts.get("Green").blocks, wContrl.blockControllerMapGreen);
		initialize();
	}
	
	public void initialize() {
		
		displayTimer = new Timer(100, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0){
				updateData();
			}
		});
		

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 830, 382);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblLine = new JLabel("Line: ");
		lblLine.setBounds(10, 40, 46, 14);
		contentPane.add(lblLine);
		
		JLabel lblBlock = new JLabel("Block: ");
		lblBlock.setBounds(10, 65, 56, 14);
		contentPane.add(lblBlock);
		
		blockPicker = new JComboBox<Integer>();
		blockPicker.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(blockPicker.getSelectedItem()!=null) {
					currentBlock = wContrl.tracks.trackLayouts.get(currentLine).blocks.get(Integer.parseInt(blockPicker.getSelectedItem().toString()));
					if(currentLine!=null && currentLine.equals("Green")) {
						currentController = wContrl.blockControllerMapGreen.get(currentBlock.number);
					}
					if(currentLine!=null && currentLine.equals("Red")) {
						currentController = wContrl.blockControllerMapRed.get(currentBlock.number);
					}
				}
			}
		});
		blockPicker.setBounds(51, 65, 65, 20);
		contentPane.add(blockPicker);
		
		linePicker = new JComboBox<String>();
		linePicker.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				currentLine = linePicker.getSelectedItem().toString();
				//dynamically add block numbers
				if (currentLine.equals("Red")) {
					track_model.TrackLayout temp = wContrl.tracks.trackLayouts.get("Red");
					blockPicker.removeAllItems();
					for (TrackBlock b: temp.blocks) {
	                    blockPicker.addItem(b.number);
	                  }
				}
				else if (currentLine.equals("Green")) {
				  track_model.TrackLayout temp = wContrl.tracks.trackLayouts.get("Green");
				  blockPicker.removeAllItems();
				  for (TrackBlock b: temp.blocks) {
				    blockPicker.addItem(b.number);
				  }
				}
			}
		});
		linePicker.setBounds(51, 40, 65, 20);
		contentPane.add(linePicker);
		linePicker.addItem("Green");
		linePicker.addItem("Red");
		
		PLCFileName = new JTextField();
		PLCFileName.setBounds(58, 12, 129, 20);
		contentPane.add(PLCFileName);
		PLCFileName.setColumns(10);
		
		JLabel lblPlcFile = new JLabel("PLC file:");
		lblPlcFile.setBounds(10, 15, 89, 14);
		contentPane.add(lblPlcFile);
		
		JLabel label_1 = new JLabel("");
		label_1.setBounds(420, 40, 46, 14);
		contentPane.add(label_1);
		
		JButton btnChooseFile = new JButton("Browse");
		btnChooseFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				int returnVal = chooser.showOpenDialog(getParent());
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					String path = chooser.getSelectedFile().getPath();
					PLCFileName.setText(path);
				}
			}
		});
		
		btnChooseFile.setBounds(197, 11, 89, 23);
		contentPane.add(btnChooseFile);
		
				lblCompilationSuccessful = new JLabel("No file selected.");
				lblCompilationSuccessful.setForeground(Color.BLACK);
				lblCompilationSuccessful.setBounds(399, 15, 120, 14);
				contentPane.add(lblCompilationSuccessful);
				
				JButton btnUploadPlc = new JButton("Upload PLC");
				btnUploadPlc.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						String path = PLCFileName.getText().toString();
						String line = linePicker.getSelectedItem().toString();
						int block = Integer.parseInt(blockPicker.getSelectedItem().toString());
						
						try {
							plcUploadSuccess = wContrl.updatePLC(line, block, path);
						} catch (NoSuchMethodException | SecurityException
								| IllegalArgumentException
								| InvocationTargetException e) {
							e.printStackTrace();
						}
						
						if (plcUploadSuccess) {
							lblCompilationSuccessful.setText("Loaded!");
							lblCompilationSuccessful.setForeground(Color.GREEN);
						}
						else {
							lblCompilationSuccessful.setText("Compilation error.");
							lblCompilationSuccessful.setForeground(Color.RED);
						}
					}
				});
				btnUploadPlc.setBounds(292, 11, 100, 23);
				contentPane.add(btnUploadPlc);
				
				JScrollPane ctcInfoScroll = new JScrollPane();
				ctcInfoScroll.setBounds(10, 117, 183, 209);
				contentPane.add(ctcInfoScroll);
				
				CTCInfoPane = new JTextArea();
				ctcInfoScroll.setViewportView(CTCInfoPane);
				
				
				model = new DefaultTableModel(new Object[][] {
					      { "Line", currentLine }, { "Block", currentBlock.number }, { "Occupancy", currentBlock.occupancy},
					      { "Weather", currentBlock.weather},{"Infrastructure", currentBlock.infrastructure},{"Length (yards)", currentBlock.length} ,{ "Speed limit (mph)", currentBlock.speedLimit }, { "Cmd speed (mph)", currentBlock.commandedSpeed },
					      { "Cmd authority (yards)", currentBlock.commandedAuthority }, { "Failure mode", currentBlock.failure }, { "Light status", currentBlock.lights }, 
					      { "Heater status", currentBlock.heater }, { "Switch position", switchStatus }, {"Crossing status", crossingStatus}}, 
					      new Object[] { "Attribute", "Value" });
				
				table_1 = new JTable(model);
				table_1.getColumnModel().getColumn(0).setPreferredWidth(130);
				table_1.getColumnModel().getColumn(1).setMinWidth(260);
				table_1.setFillsViewportHeight(true);
				table_1.setRowSelectionAllowed(false);
				table_1.setSize(310, 269);
				table_1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);	
				JScrollPane blockInfoScroll = new JScrollPane(table_1);
				blockInfoScroll.setBounds(207, 57, 365, 269);
				contentPane.add(blockInfoScroll);
				
				
				JLabel lblInformationFromCtc = new JLabel("Information from CTC:");
				lblInformationFromCtc.setBounds(10, 96, 129, 14);
				contentPane.add(lblInformationFromCtc);
				
				JLabel lblBlockStatusFrom = new JLabel("Block status from Track Model:");
				lblBlockStatusFrom.setBounds(207, 40, 259, 14);
				contentPane.add(lblBlockStatusFrom);
				
				JLabel lblManualControls = new JLabel("Manual Controls: ");
				lblManualControls.setBounds(582, 40, 100, 14);
				contentPane.add(lblManualControls);
				
				btnFlipSwitch = new JButton("Move Switch");
				btnFlipSwitch.setEnabled(false);
				btnFlipSwitch.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						if(currentBlock.infrastructure != null){
							if(currentBlock.infrastructure.contains("switch")){
								TrackSwitch tempSwitch = (TrackSwitch)currentBlock;
								if (tempSwitch.out.length != 0){
									tempSwitch.state = !tempSwitch.state;
									if(tempSwitch.state){
										System.out.println("points to block " + tempSwitch.out[0]);
									}
									else System.out.println("points to block " + tempSwitch.out[1]);
								}
							}
						}
					}
				});
				btnFlipSwitch.setBounds(606, 104, 175, 23);
				contentPane.add(btnFlipSwitch);
				
				JButton btnActivateCrossing = new JButton("Toggle Crossing State");
				btnActivateCrossing.setEnabled(false);
				btnActivateCrossing.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(currentBlock.infrastructure != null){
							if (currentBlock.infrastructure.contains("crossing")){
								TrackCrossing tempCrossing = (TrackCrossing) currentBlock;
								System.out.println("Crossing is active: " + tempCrossing.state);
								tempCrossing.state = !tempCrossing.state;
								System.out.println("Crossing is active: " + tempCrossing.state);
							}
						}
					}
				});
				btnActivateCrossing.setBounds(606, 138, 175, 23);
				contentPane.add(btnActivateCrossing);
				
				JLabel lblStandaloneTestInputs = new JLabel("Standalone Mode Test Inputs:");
				lblStandaloneTestInputs.setBounds(582, 172, 201, 14);
				contentPane.add(lblStandaloneTestInputs);
				
				JLabel lblWeather = new JLabel("Weather: ");
				lblWeather.setBounds(606, 197, 87, 14);
				contentPane.add(lblWeather);
				
				weatherPicker = new JComboBox<String>();
				weatherPicker.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if (weatherPicker.getSelectedItem().toString().equals("Clear")){
							currentBlock.weather = null;
						}
						else currentBlock.weather = weatherPicker.getSelectedItem().toString();
					}
				});
				weatherPicker.setBounds(703, 194, 94, 20);
				contentPane.add(weatherPicker);
				weatherPicker.addItem("Clear");
				weatherPicker.addItem("Rain");
				weatherPicker.addItem("Snow");
				weatherPicker.addItem("Ice");
				
				JLabel lblSuggestSpeed = new JLabel("Suggest Speed: ");
				lblSuggestSpeed.setBounds(606, 222, 107, 14);
				contentPane.add(lblSuggestSpeed);
				
				JLabel lblSuggestAuthority = new JLabel("Suggest Authority:");
				lblSuggestAuthority.setBounds(606, 250, 107, 14);
				contentPane.add(lblSuggestAuthority);
				
				JLabel lblBlockStatus = new JLabel("Block Status:");
				lblBlockStatus.setBounds(606, 275, 100, 14);
				contentPane.add(lblBlockStatus);
				
				blockStatusPicker = new JComboBox<String>();
				blockStatusPicker.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(blockStatusPicker.getSelectedItem().toString().equals("Good")){
							currentBlock.failure = null;
						}
						else if (blockStatusPicker.getSelectedItem().toString().equals("Maintenance")) {
							wContrl.setBlockClosed(currentLine, currentBlock.number);							 
						}
						else if (blockStatusPicker.getSelectedItem().toString().equals("Broken Track")) {
							wContrl.setBlockBroken(currentLine, currentBlock.number);
						}
						else {
							wContrl.setFailureMode(currentLine, currentBlock.number, blockStatusPicker.getSelectedItem().toString());
						}
					}
				});
				blockStatusPicker.setBounds(703, 272, 94, 20);
				contentPane.add(blockStatusPicker);
				blockStatusPicker.addItem("Good");
				blockStatusPicker.addItem("Maintenance");
				blockStatusPicker.addItem("Broken Track");
				blockStatusPicker.addItem("Power Failure");
				blockStatusPicker.addItem("Circuit Failure");
				
				speedSug = new JTextField();
				speedSug.setBounds(726, 222, 34, 20);
				contentPane.add(speedSug);
				speedSug.setColumns(10);
				
				sugAuth = new JTextField();
				sugAuth.setColumns(10);
				sugAuth.setBounds(726, 247, 34, 20);
				contentPane.add(sugAuth);
				
				JLabel lblMph = new JLabel("mph");
				lblMph.setBounds(770, 225, 34, 14);
				contentPane.add(lblMph);
				
				JLabel lblMiles = new JLabel("miles");
				lblMiles.setBounds(770, 250, 46, 14);
				contentPane.add(lblMiles);
				
				JButton btnToggleOccupancy = new JButton("Toggle Occupancy");
				btnToggleOccupancy.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						wContrl.setOccupancy(currentLine, currentBlock.number);
					}
				});
				btnToggleOccupancy.setBounds(606, 303, 191, 23);
				contentPane.add(btnToggleOccupancy);
				
				JToggleButton tglbtnOverride = new JToggleButton("Override");
				tglbtnOverride.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						manualOverride = !manualOverride;
						if(manualOverride) {
							tglbtnOverride.setText("In manual mode");
							btnFlipSwitch.setEnabled(true);
							btnActivateCrossing.setEnabled(true);
						}
						else {
							tglbtnOverride.setText("Override");
							btnFlipSwitch.setEnabled(false);
							btnActivateCrossing.setEnabled(false);
						}
					}
				});
				tglbtnOverride.setBounds(606, 64, 175, 23);
				contentPane.add(tglbtnOverride);
	}
	
	/*
	 * Update the table data in the GUI
	 */
	public void updateData() {
		//on updates change picker drop downs to whatever the current block has
		if(currentBlock.weather == null) weatherPicker.setSelectedItem("Clear");
		else weatherPicker.setSelectedItem(currentBlock.weather);
		if(currentBlock.failure == null) blockStatusPicker.setSelectedItem("Good");
		else blockStatusPicker.setSelectedItem(currentBlock.failure);
		
		
		
		if(currentController != null){
			wContrl.runPLC(currentController, manualOverride);
		
			if(currentController.containsSwitch & !currentController.containsCrossing) {
				switchStatus = "";
				crossingStatus = "";
				ArrayList<TrackSwitch> s = currentController.findSwitches(currentLine);
				if(!s.isEmpty()){
					for (TrackSwitch t: s) {
						boolean pTB = t.state;
						int index;
						if (pTB) index = 0;
						else index = 1;
						switchStatus = switchStatus +"Switch on block " + t.number + " pointing to block " +t.out[index]+ ".\n";
					}
					crossingStatus = "No crossings in this section.";
				}
			}
			if(currentLine.equals("Green")){
				if(currentController.containsCrossing & !currentController.containsSwitch) {
					TrackCrossing t = (TrackCrossing) currentController.blockMap.get(19);
					switchStatus = "No switches in this section.";
					if(t.state) {
						crossingStatus = "Crossing on block " + 19 + " is active.";
					}
					else {
						crossingStatus = "Crossing on block " + 19 + " is inactive.";
					}
				}
				else if(currentController.containsCrossing & currentController.containsSwitch) {
					switchStatus = "";
					TrackCrossing tc = (TrackCrossing) currentController.blockMap.get(19);
					ArrayList<TrackSwitch> s = currentController.findSwitches(currentLine);
					for (TrackSwitch t: s) {
						boolean pTB = t.state;
						int index;
						if (pTB) index = 0;
						else index = 1;
						switchStatus = switchStatus + "Switch on block " +t.number+ " pointing to block " + t.out[index]+ ".\n";
					}
					if(tc.state) {
						crossingStatus = "Crossing on block " + 19 + " is active.";
					}
					else {
						crossingStatus = "Crossing on block " + 19 + " is inactive.";
					}
				}
				else if (!currentController.containsCrossing & !currentController.containsSwitch) {
					switchStatus = "No switches in this section.";
					crossingStatus = "No crossings in this section.";
				}
			}
			
			else if(currentLine.equals("Red")){
				if(currentController.containsCrossing & !currentController.containsSwitch) {
					TrackCrossing t = (TrackCrossing) currentController.blockMap.get(47);
					switchStatus = "No switches in this section.";
					if(t.state) {
						crossingStatus = "Crossing on block " + 47 + " is active.";
					}
					else {
						crossingStatus = "Crossing on block " + 47 + " is inactive.";
					}
				}
				else if(currentController.containsCrossing & currentController.containsSwitch) {
					switchStatus = "";
					TrackCrossing tc = (TrackCrossing) currentController.blockMap.get(47);
					ArrayList<TrackSwitch> s = currentController.findSwitches(currentLine);
					for (TrackSwitch t: s) {
						boolean pTB = t.state;
						int index;
						if (pTB) index = 0;
						else index = 1;
						switchStatus = switchStatus + "Switch on block " +t.number+ " pointing to block " + t.out[index]+ ".\n";
					}
					if(tc.state) {
						crossingStatus = "Crossing on block " + 47 + " is active.";
					}
					else {
						crossingStatus = "Crossing on block " + 47 + " is inactive.";
					}
				}
				else if (!currentController.containsCrossing & !currentController.containsSwitch) {
					switchStatus = "No switches in this section.";
					crossingStatus = "No crossings in this section.";
				}
			}
		}
		model.setValueAt(currentLine, 0, 1);
		model.setValueAt(currentBlock.number, 1, 1);
		model.setValueAt(currentBlock.occupancy, 2, 1);
		model.setValueAt(currentBlock.weather, 3, 1);
		model.setValueAt(currentBlock.infrastructure, 4,1);
		model.setValueAt(currentBlock.length, 5, 1);
		model.setValueAt(currentBlock.speedLimit, 6, 1);
		model.setValueAt(currentBlock.commandedSpeed, 7, 1);
		model.setValueAt(currentBlock.commandedAuthority, 8, 1);
		model.setValueAt(currentBlock.failure, 9, 1);
		if(currentBlock.lights){
			model.setValueAt("red", 10, 1);
		}
		else model.setValueAt("green", 10, 1);
		model.setValueAt(currentBlock.heater, 11, 1);
		model.setValueAt(switchStatus, 12, 1);
		model.setValueAt(crossingStatus, 13, 1);
		
		//display routes added for all controllers
		for(WaysideController c : wContrl.blockControllerMapGreen.values()){
			if(c.ctcInfo != null){
				CTCInfoPane.append(c.ctcInfo);
				c.ctcInfo = null;
			}
		}
		
		for( WaysideController c : wContrl.blockControllerMapRed.values()){
			if(c.ctcInfo != null){
				CTCInfoPane.append(c.ctcInfo);
				c.ctcInfo = null;
			}
		}
		
		if(wContrl.ctcBeaconUpdate != null){
			CTCInfoPane.append(wContrl.ctcBeaconUpdate);
			wContrl.ctcBeaconUpdate = null;
		}
		
		table_1.setModel(model);
		table_1.repaint();
	}
	
	public static boolean inManualMode(){
		return manualOverride;
	}
}
