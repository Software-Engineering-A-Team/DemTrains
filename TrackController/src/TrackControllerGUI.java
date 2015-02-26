import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTabbedPane;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;

import java.awt.Color;

import javax.swing.ButtonGroup;
import javax.swing.JSlider;
import javax.swing.JFormattedTextField;
import javax.swing.JToggleButton;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.AbstractAction;

import java.awt.event.ActionEvent;

import javax.swing.Action;

import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class TrackControllerGUI extends JFrame {
	private JPanel contentPane;
	private JTextField txtTracklayoutcsv;
	private JTable table;
	private TrackLayout blueLine = new TrackLayout();
	private TrackController tContr;
	private List<String> lines = new ArrayList<String>();
	private List<Integer> blockNums = new ArrayList<Integer>();
	private List<TrackBlock> trackIterator;
	private JComboBox<Integer> comboBoxBlock;
	private JComboBox<String> comboBoxLine;
	private boolean broken = false;
	private boolean occupied = false;
	private int light = 0;
	private boolean open = true;
	private List<TrackBlock> route = new ArrayList<TrackBlock>();
	private JTextField switchOut;
	private JTextField closedOut;
	private JTextField lightsOut;
	private JTextField brokenOut;
	private JTextField speedOut;
	private JTextField occOut;
	private JTextField blockOut;
	private JTextField lineOut;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TrackControllerGUI frame = new TrackControllerGUI();
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
	public TrackControllerGUI() {
		setTitle("Track Controller");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 349);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		txtTracklayoutcsv = new JTextField();
		txtTracklayoutcsv.setForeground(Color.BLACK);
		txtTracklayoutcsv.setText("track_layout.csv");
		txtTracklayoutcsv.setBounds(10, 11, 120, 20);
		contentPane.add(txtTracklayoutcsv);
		txtTracklayoutcsv.setColumns(10);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.BOTTOM);
		tabbedPane.setBounds(10, 42, 414, 268);
		contentPane.add(tabbedPane);
		
		JPanel panel = new JPanel();
		tabbedPane.addTab("Input", null, panel, null);
		panel.setLayout(null);
		
		JLabel lblLine = new JLabel("Line:");
		lblLine.setBounds(10, 11, 46, 14);
		lblLine.setVerticalAlignment(SwingConstants.TOP);
		lblLine.setHorizontalAlignment(SwingConstants.LEFT);
		panel.add(lblLine);
		
		JLabel lblBlock = new JLabel("Block:");
		lblBlock.setBounds(10, 36, 46, 14);
		panel.add(lblBlock);
		
		comboBoxLine = new JComboBox<String>();
		lblLine.setLabelFor(comboBoxLine);
		comboBoxLine.setBounds(58, 8, 52, 20);
		panel.add(comboBoxLine);
		
		
		comboBoxBlock = new JComboBox<Integer>();
		lblBlock.setLabelFor(comboBoxBlock);
		comboBoxBlock.setBounds(58, 33, 52, 20);
		panel.add(comboBoxBlock);
		
		JButton btnImport = new JButton("Import");
		btnImport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				//get trackLayout file name and parse the csv
				String fileName = txtTracklayoutcsv.getText().toString();
				try {
					blueLine.parseCsvFile(fileName);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				//if successful iterate track layout and add items to drop downs
				trackIterator = blueLine.createIterator();
				for(TrackBlock nextBlock: trackIterator){
					String line = nextBlock.getLine();
					if(!lines.contains(line)){
						lines.add(line);
						comboBoxLine.addItem(line);
					}
					
					Integer blockNum = nextBlock.getBlockNumber(); 
					comboBoxBlock.addItem(blockNum);
					blockNums.add(blockNum);
					if(blockNum!=5){
						route.add(nextBlock);
					}
				}
			}
		});
		btnImport.setBounds(140, 10, 89, 23);
		contentPane.add(btnImport);
		
		JLabel lblSuggestedSpeed = new JLabel("Suggested Speed:");
		lblSuggestedSpeed.setEnabled(false);
		lblSuggestedSpeed.setBounds(134, 11, 131, 14);
		panel.add(lblSuggestedSpeed);
		
		JFormattedTextField SugSpeed = new JFormattedTextField();
		SugSpeed.setEnabled(false);
		SugSpeed.setEditable(false);
		SugSpeed.setBounds(263, 8, 31, 20);
		panel.add(SugSpeed);
		
		JLabel lblMph = new JLabel("miles");
		lblMph.setEnabled(false);
		lblMph.setBounds(304, 36, 46, 14);
		panel.add(lblMph);
		
		JLabel lblSuggestedAuthority = new JLabel("Suggested Authority:");
		lblSuggestedAuthority.setEnabled(false);
		lblSuggestedAuthority.setBounds(134, 36, 131, 14);
		panel.add(lblSuggestedAuthority);
		
		JFormattedTextField formattedTextField = new JFormattedTextField();
		formattedTextField.setEditable(false);
		formattedTextField.setEnabled(false);
		formattedTextField.setBounds(263, 33, 31, 20);
		panel.add(formattedTextField);
		
		JLabel label = new JLabel("mph");
		label.setEnabled(false);
		label.setBounds(304, 11, 46, 14);
		panel.add(label);
		
		JLabel lblOccupancy = new JLabel("Occupancy:");
		lblOccupancy.setBounds(134, 61, 95, 14);
		panel.add(lblOccupancy);
		
		JToggleButton tglbtnOccupied = new JToggleButton("Occupied");
		tglbtnOccupied.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(occupied==false) occupied = true;
				else occupied = false;
			}
		});
		lblOccupancy.setLabelFor(tglbtnOccupied);
		tglbtnOccupied.setBounds(229, 57, 121, 23);
		panel.add(tglbtnOccupied);
		
		JLabel lblLights = new JLabel("Lights:");
		lblLights.setBounds(134, 86, 90, 14);
		panel.add(lblLights);
		
		
		JRadioButton rdbtnRed = new JRadioButton("Red");
		rdbtnRed.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				light = 1;
			}
		});
		rdbtnRed.setBounds(230, 82, 52, 23);
		panel.add(rdbtnRed);
		
		JRadioButton rdbtnGreen = new JRadioButton("Green");
		rdbtnGreen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				light = 0;
			}
		});
		rdbtnGreen.setSelected(true);
		rdbtnGreen.setBounds(284, 82, 66, 23);
		panel.add(rdbtnGreen);
		
		ButtonGroup lights = new ButtonGroup();
		lights.add(rdbtnGreen);
		lights.add(rdbtnRed);
		
		JLabel lblBroken = new JLabel("Broken?:");
		lblBroken.setBounds(134, 111, 90, 14);
		panel.add(lblBroken);
		
		JToggleButton tglbtnBroken = new JToggleButton("Broken");
		tglbtnBroken.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(broken==true) broken = false;
				else broken = true;
			}
		});
		lblBroken.setLabelFor(tglbtnBroken);
		tglbtnBroken.setBounds(229, 107, 121, 23);
		panel.add(tglbtnBroken);
		
		JLabel lblClosed = new JLabel("Closed?:");
		lblClosed.setBounds(134, 136, 90, 14);
		panel.add(lblClosed);
		
		JToggleButton tglbtnClosed = new JToggleButton("Closed");
		tglbtnClosed.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (open == true) open = false;
				else open = true;
			}
		});
		lblClosed.setLabelFor(tglbtnClosed);
		tglbtnClosed.setBounds(229, 132, 121, 23);
		panel.add(tglbtnClosed);
		
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//create TrackController for selected block
				int lineChosen = comboBoxLine.getSelectedIndex();
				if(lineChosen==-1){
					return;
				}
				String l = lines.get(lineChosen); 
				int chosen = comboBoxBlock.getSelectedIndex();
				int blockNum = blockNums.get(chosen);
				for (TrackBlock next: trackIterator){
					if(next.getBlockNumber()==blockNum){
						TrackBlock chosenBlock = next;
						tContr = new TrackController(chosenBlock);
						//System.out.println("TrackController created.");
						
						tContr.setTrackSig(occupied);
						tContr.setLights(light);
						tContr.setOpen(open);
						tContr.setBroken(broken);
						tContr.checkLights();
						TrackBlock pt = tContr.chkSwitch(route);
						
						//update outputs
						lineOut.setText(l);
						blockOut.setText(Integer.toString(blockNum));
						if(tContr.isOccupied()) occOut.setText("Occupied");
						else occOut.setText("Free");
						if(tContr.isOpen()) closedOut.setText("Open");
						else closedOut.setText("Closed");
						if(tContr.getLights() == 0) lightsOut.setText("Green");
						else lightsOut.setText("Red");
						speedOut.setText(Integer.toString(tContr.getSpeed()));
						if(tContr.isBroken()) brokenOut.setText("Broken");
						else brokenOut.setText("Normal");
						if(pt!=null) switchOut.setText("Block "+pt.getBlockNumber());
						else switchOut.setText("-");
						
						
						
						
					} 
				}
				
				
				
				
			}
		});
		btnUpdate.setBounds(310, 206, 89, 23);
		panel.add(btnUpdate);
				
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("Output", null, panel_1, null);
		panel_1.setLayout(null);
		
		table = new JTable();
		table.setBounds(204, 5, 0, 0);
		panel_1.add(table);
		
		JLabel lblLine_1 = new JLabel("Line:");
		lblLine_1.setBounds(10, 5, 105, 14);
		panel_1.add(lblLine_1);
		
		JLabel lblBlock_1 = new JLabel("Block:");
		lblBlock_1.setBounds(10, 30, 105, 14);
		panel_1.add(lblBlock_1);
		
		JLabel lblOccupancy_1 = new JLabel("Occupancy:");
		lblOccupancy_1.setBounds(10, 55, 105, 14);
		panel_1.add(lblOccupancy_1);
		
		JLabel lblSpeedLimit = new JLabel("Speed Limit:");
		lblSpeedLimit.setBounds(10, 76, 105, 14);
		panel_1.add(lblSpeedLimit);
		
		JLabel lblBrokenStatus = new JLabel("Broken Status:");
		lblBrokenStatus.setBounds(10, 101, 105, 14);
		panel_1.add(lblBrokenStatus);
		
		JLabel lblLightStatus = new JLabel("Light Status:");
		lblLightStatus.setBounds(10, 126, 116, 14);
		panel_1.add(lblLightStatus);
		
		JLabel lblClosedStatus = new JLabel("Closed Status:");
		lblClosedStatus.setBounds(10, 151, 105, 14);
		panel_1.add(lblClosedStatus);
		
		JLabel lblSwitchPointsTo = new JLabel("Switch points to:");
		lblSwitchPointsTo.setBounds(10, 176, 116, 14);
		panel_1.add(lblSwitchPointsTo);
		
		switchOut = new JTextField();
		switchOut.setEditable(false);
		switchOut.setBounds(111, 173, 86, 20);
		panel_1.add(switchOut);
		switchOut.setColumns(10);
		
		closedOut = new JTextField();
		closedOut.setEditable(false);
		closedOut.setBounds(111, 148, 86, 20);
		panel_1.add(closedOut);
		closedOut.setColumns(10);
		
		lightsOut = new JTextField();
		lightsOut.setEditable(false);
		lightsOut.setBounds(111, 120, 86, 20);
		panel_1.add(lightsOut);
		lightsOut.setColumns(10);
		
		brokenOut = new JTextField();
		brokenOut.setEditable(false);
		brokenOut.setBounds(111, 98, 86, 20);
		panel_1.add(brokenOut);
		brokenOut.setColumns(10);
		
		speedOut = new JTextField();
		speedOut.setEditable(false);
		speedOut.setBounds(111, 73, 86, 20);
		panel_1.add(speedOut);
		speedOut.setColumns(10);
		
		occOut = new JTextField();
		occOut.setEditable(false);
		occOut.setBounds(111, 52, 86, 20);
		panel_1.add(occOut);
		occOut.setColumns(10);
		
		blockOut = new JTextField();
		blockOut.setEditable(false);
		blockOut.setBounds(111, 28, 86, 20);
		panel_1.add(blockOut);
		blockOut.setColumns(10);
		
		lineOut = new JTextField();
		lineOut.setEditable(false);
		lineOut.setBounds(111, 5, 86, 20);
		panel_1.add(lineOut);
		lineOut.setColumns(10);
		tabbedPane.setEnabledAt(1, true);
	}
	private class SwingAction extends AbstractAction {
		public SwingAction() {
			putValue(NAME, "SwingAction");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
		}
	}
}
