package track_controller;
import track_model.*;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JFileChooser;
import javax.swing.JTabbedPane;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.Color;

import javax.swing.JSlider;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.ButtonGroup;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.filechooser.*;
import java.awt.GridLayout;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;

public class TrackControllerGUI extends JFrame {

	private JPanel contentPane;
	private JTextField PLCFileName;
	private JTextField sugSpeed;
	private JTextField sugAuthority;
	public static WaysideSystem wContrl;
	public static boolean plcUploadSuccess;
	private TrackBlock currentBlock;
	private String currentLine;
	private WaysideController currentController;
	private JTable table;
	private String switchStatus = "No information available.";
	private String crossingStatus = "No information available.";
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
	  track_model.TrackModel t = new track_model.TrackModel();
	  final WaysideSystem ws = new WaysideSystem(t);
	  
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TrackControllerGUI frame = new TrackControllerGUI(ws);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TrackControllerGUI(WaysideSystem w) {
		wContrl = w;
		initialize();
	}
	
	public void initialize() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 562, 367);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.BOTTOM);
		tabbedPane.setBounds(10, 11, 526, 306);
		contentPane.add(tabbedPane);
		
		JPanel inputPanel = new JPanel();
		tabbedPane.addTab("Inputs", null, inputPanel, null);
		inputPanel.setLayout(null);
		
		JLabel lblLine = new JLabel("Line: ");
		lblLine.setBounds(10, 40, 46, 14);
		inputPanel.add(lblLine);
		
		JLabel lblBlock = new JLabel("Block: ");
		lblBlock.setBounds(10, 65, 56, 14);
		inputPanel.add(lblBlock);
		
		final JComboBox<Integer> blockPicker = new JComboBox<Integer>();
		blockPicker.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(blockPicker.getSelectedItem()!=null) {
					currentBlock = wContrl.tracks.getLine(currentLine).blocks.get(Integer.parseInt(blockPicker.getSelectedItem().toString())-1);
					if(currentLine!=null && currentLine.equals("Green")) {
						currentController = wContrl.blockControllerMapGreen.get(currentBlock.number);	
					}
				}
			}
		});
		blockPicker.setBounds(51, 65, 65, 20);
		inputPanel.add(blockPicker);
		
		final JComboBox<String> linePicker = new JComboBox<String>();
		linePicker.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				currentLine = linePicker.getSelectedItem().toString();
				//dynamically add block numbers
				if (currentLine.equals("Red")) {
					track_model.TrackLayout temp = wContrl.tracks.getLine("Red");
					blockPicker.removeAllItems();
					for (TrackBlock b: temp.blocks) {
	                    blockPicker.addItem(b.number);
	                  }
				}
				else if (currentLine.equals("Green")) {
				  track_model.TrackLayout temp = wContrl.tracks.getLine("Green");
				  blockPicker.removeAllItems();
				  for (TrackBlock b: temp.blocks) {
				    blockPicker.addItem(b.number);
				  }
				}
			}
		});
		linePicker.setBounds(51, 40, 65, 20);
		inputPanel.add(linePicker);
		linePicker.addItem("Green");
		linePicker.addItem("Red");
		
		PLCFileName = new JTextField();
		PLCFileName.setBounds(58, 12, 129, 20);
		inputPanel.add(PLCFileName);
		PLCFileName.setColumns(10);
		
		JLabel lblPlcFile = new JLabel("PLC file:");
		lblPlcFile.setBounds(10, 15, 89, 14);
		inputPanel.add(lblPlcFile);
		
		JLabel lblSuggestSpeed = new JLabel("Suggest speed:");
		lblSuggestSpeed.setBounds(126, 40, 120, 14);
		inputPanel.add(lblSuggestSpeed);
		
		JLabel label = new JLabel("Suggest authority:");
		label.setBounds(126, 65, 129, 14);
		inputPanel.add(label);
		
		JLabel label_1 = new JLabel("");
		label_1.setBounds(420, 40, 46, 14);
		inputPanel.add(label_1);
		
		sugSpeed = new JTextField();
		sugSpeed.setBounds(258, 37, 39, 20);
		inputPanel.add(sugSpeed);
		sugSpeed.setColumns(10);
		
		sugAuthority = new JTextField();
		sugAuthority.setBounds(257, 62, 40, 20);
		inputPanel.add(sugAuthority);
		sugAuthority.setColumns(10);
		
		JLabel lblMph = new JLabel("mph");
		lblMph.setBounds(306, 40, 46, 14);
		inputPanel.add(lblMph);
		
		JLabel lblMiles = new JLabel("miles");
		lblMiles.setBounds(302, 65, 46, 14);
		inputPanel.add(lblMiles);
		
		JComboBox<String> weatherPicker = new JComboBox<String>();
		weatherPicker.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				currentBlock.weather = weatherPicker.getSelectedItem().toString();
				System.out.println("block "+ currentBlock.number+ " weather set to "+ currentBlock.weather);
			}
		});
		weatherPicker.setBounds(223, 89, 76, 20);
		inputPanel.add(weatherPicker);
		weatherPicker.addItem("Clear");
		weatherPicker.addItem("Rain");
		weatherPicker.addItem("Snow");
		weatherPicker.addItem("Ice");
		
		JLabel lblWeather = new JLabel("Weather:");
		lblWeather.setBounds(126, 92, 87, 14);
		inputPanel.add(lblWeather);
		
		JLabel lblOccupancy = new JLabel("Occupancy:");
		lblOccupancy.setBounds(126, 117, 100, 14);
		inputPanel.add(lblOccupancy);
		
		
		JRadioButton rdbtnOccupied = new JRadioButton("occupied");
		rdbtnOccupied.setBounds(223, 113, 89, 23);
		inputPanel.add(rdbtnOccupied);
		
		JRadioButton rdbtnFree = new JRadioButton("free");
		rdbtnFree.setSelected(true);
		rdbtnFree.setBounds(314, 113, 67, 23);
		inputPanel.add(rdbtnFree);
		
		ButtonGroup occupancyBtns = new ButtonGroup();
		occupancyBtns.add(rdbtnOccupied);
		occupancyBtns.add(rdbtnFree);
		
		
		JLabel lblBrokenStatus = new JLabel("Broken status: ");
		lblBrokenStatus.setBounds(126, 147, 100, 14);
		inputPanel.add(lblBrokenStatus);
		
		JRadioButton rdbtnBroken = new JRadioButton("broken");
		rdbtnBroken.setBounds(223, 143, 67, 23);
		inputPanel.add(rdbtnBroken);
		
		JRadioButton rdbtnOperational = new JRadioButton("operational");
		rdbtnOperational.setSelected(true);
		rdbtnOperational.setBounds(314, 143, 120, 23);
		inputPanel.add(rdbtnOperational);
		
		ButtonGroup brokenStatusBtns = new ButtonGroup();
		brokenStatusBtns.add(rdbtnBroken);
		brokenStatusBtns.add(rdbtnOperational);
		
		JLabel lblLightStatus = new JLabel("Light status:");
		lblLightStatus.setBounds(126, 173, 100, 14);
		inputPanel.add(lblLightStatus);
		
		JRadioButton rdbtnGreen = new JRadioButton("green");
		rdbtnGreen.setSelected(true);
		rdbtnGreen.setBounds(223, 169, 63, 23);
		inputPanel.add(rdbtnGreen);
		
		JRadioButton rdbtnRed = new JRadioButton("red");
		rdbtnRed.setBounds(314, 169, 109, 23);
		inputPanel.add(rdbtnRed);
		
		ButtonGroup lightStatusBtns = new ButtonGroup();
		lightStatusBtns.add(rdbtnGreen);
		lightStatusBtns.add(rdbtnRed);
		
		JLabel lblClosedStatus = new JLabel("Closed status:");
		lblClosedStatus.setBounds(126, 198, 120, 14);
		inputPanel.add(lblClosedStatus);
		
		JRadioButton rdbtnOpen = new JRadioButton("open");
		rdbtnOpen.setSelected(true);
		rdbtnOpen.setBounds(223, 194, 63, 23);
		inputPanel.add(rdbtnOpen);
		
		JRadioButton rdbtnClosed = new JRadioButton("closed");
		rdbtnClosed.setBounds(314, 194, 63, 23);
		inputPanel.add(rdbtnClosed);
		
		ButtonGroup closedStatusBtns = new ButtonGroup();
		closedStatusBtns.add(rdbtnOpen);
		closedStatusBtns.add(rdbtnClosed);
		
		JLabel lblHeaterStatus = new JLabel("Heater status:");
		lblHeaterStatus.setBounds(126, 226, 100, 14);
		inputPanel.add(lblHeaterStatus);
		
		JRadioButton rdbtnOff = new JRadioButton("off");
		rdbtnOff.setSelected(true);
		rdbtnOff.setBounds(223, 222, 63, 23);
		inputPanel.add(rdbtnOff);
		
		JRadioButton rdbtnOn = new JRadioButton("on");
		rdbtnOn.setBounds(314, 222, 109, 23);
		inputPanel.add(rdbtnOn);
		
		ButtonGroup heaterStatusBtns = new ButtonGroup();
		heaterStatusBtns.add(rdbtnOff);
		heaterStatusBtns.add(rdbtnOn);
		
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
		inputPanel.add(btnChooseFile);

		JLabel lblCompilationSuccessful = new JLabel("No file selected.");
		lblCompilationSuccessful.setForeground(Color.BLACK);
		lblCompilationSuccessful.setBounds(399, 15, 120, 14);
		inputPanel.add(lblCompilationSuccessful);
		
		JButton btnUploadPlc = new JButton("Upload PLC");
		btnUploadPlc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String path = PLCFileName.getText().toString();
				String line = linePicker.getSelectedItem().toString();
				int block = Integer.parseInt(blockPicker.getSelectedItem().toString());
				
				plcUploadSuccess = wContrl.updatePLC(line, block, path);
				
				if (plcUploadSuccess) {
					lblCompilationSuccessful.setText("Compiled!");
					lblCompilationSuccessful.setForeground(Color.GREEN);
				}
				else {
					lblCompilationSuccessful.setText("Not compiled.");
					lblCompilationSuccessful.setForeground(Color.RED);
				}
			}
		});
		btnUploadPlc.setBounds(292, 11, 100, 23);
		inputPanel.add(btnUploadPlc);
		
		
		JPanel outputPanel = new JPanel();
		tabbedPane.addTab("Outputs", null, outputPanel, null);
		outputPanel.setLayout(null);
		
		
		
		
		
		DefaultTableModel model = new DefaultTableModel(new Object[][] {
			      { "Line", currentLine }, { "Block", currentBlock.number }, { "Occupancy", currentBlock.occupancy},
			      { "Weather", weatherPicker.getSelectedItem().toString() }, { "Speed limit (mph)", currentBlock.speedLimit }, { "Commanded speed (mph)", currentBlock.commandedSpeed },
			      { "Commanded authority (miles)", currentBlock.commandedAuthority }, { "Broken status", null }, { "Light status", currentBlock.lights }, {"Closed status", null},
			      { "Heater status", currentBlock.heater }, { "Switch position", switchStatus }, {"Crossing status", crossingStatus}}, 
			      new Object[] { "Attribute", "Value" });
		
		table = new JTable(model);
		table.setBounds(10, 10, 501, 258);
		outputPanel.add(table);
		
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(10, 10, 501, 258);
		outputPanel.add(scrollPane);
		
		JButton btnUpdateBlock = new JButton("Update block");
		btnUpdateBlock.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				currentController.runPLC();
				model.setValueAt(currentLine, 0, 1);
				model.setValueAt(currentBlock.number, 1, 1);
				model.setValueAt(currentBlock.occupancy, 2, 1);
				model.setValueAt(weatherPicker.getSelectedItem().toString(), 3, 1);
				model.setValueAt(currentBlock.speedLimit, 4, 1);
				model.setValueAt(currentBlock.commandedSpeed, 5, 1);
				model.setValueAt(currentBlock.commandedAuthority, 6, 1);
				model.setValueAt(null, 7, 1);
				model.setValueAt(currentBlock.lights, 8, 1);
				model.setValueAt(null, 9, 1);
				model.setValueAt(currentBlock.heater, 10, 1);
				model.setValueAt(switchStatus, 11, 1);
				model.setValueAt(crossingStatus, 12, 1);
				table.setModel(model);
				table.repaint();
			}
		});
		btnUpdateBlock.setBounds(399, 244, 112, 23);
		inputPanel.add(btnUpdateBlock);
	}
	
	public void updateData() {
		
	}
}
