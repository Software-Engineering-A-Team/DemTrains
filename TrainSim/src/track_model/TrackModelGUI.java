package track_model;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import net.miginfocom.swing.MigLayout;

import javax.swing.JComboBox;
import javax.swing.JLabel;

import java.awt.Component;

import javax.swing.Box;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;

import java.awt.Color;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DirectedMultigraph;

public class TrackModelGUI extends JFrame {

	public TrackModel trackModel;
	
	private Map<String, DefaultComboBoxModel<Object>> blockNumberModel;

	private JPanel contentPane;
	
	private JComboBox<String> weatherSelect;
	private JComboBox<String> statusSelect;
	private JComboBox<Object> trackLineSelect;
	private JComboBox<Object> trackBlockSelect;
	private JComboBox<Object> lightsSelect;
	
	private JCheckBox occupiedCheckbox;
	private JCheckBox heaterCheckbox;
	
	private JTextField commandedSpeedTextfield;
	private JTextField commandedAuthorityTextfield;
	
	private JTable table;
	private JTabbedPane tabbedPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TrackModelGUI frame = new TrackModelGUI();
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
	public TrackModelGUI() {
		
		trackModel = new TrackModel();
		
		blockNumberModel = new HashMap<String, DefaultComboBoxModel<Object>>();
		for (String lineName : trackModel.trackLayouts.keySet()) {
			blockNumberModel.put(lineName, new DefaultComboBoxModel<Object>(trackModel.trackLayouts.get(lineName).layout.vertexSet().toArray()));
		}
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[grow][][grow]", "[][][][][][grow]"));
		
		JLabel lblTrackLine = new JLabel("Track line:");
		contentPane.add(lblTrackLine, "flowx,cell 0 0");
		
		DefaultComboBoxModel<Object> trackLineModel = new DefaultComboBoxModel<Object>(trackModel.trackLayouts.keySet().toArray());
		trackLineSelect = new JComboBox<Object>(trackLineModel);
		contentPane.add(trackLineSelect, "cell 0 0,growx");
		trackLineSelect.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	JComboBox<Object> source = (JComboBox<Object>)e.getSource();
		    	trackBlockSelect.setModel(blockNumberModel.get(source.getSelectedItem()));
		    	TrackBlock block = trackModel.trackLayouts.get(source.getSelectedItem()).blocks.get((Integer)trackBlockSelect.getSelectedItem());
		    	table.setModel(createTableModel(block, (String)trackLineSelect.getSelectedItem()));
		    	updateSimulationPane(block);
		    }
		});
		
		Component horizontalStrut = Box.createHorizontalStrut(20);
		contentPane.add(horizontalStrut, "cell 1 0");
		
		JLabel lblTrackBlock = new JLabel("Track block:");
		contentPane.add(lblTrackBlock, "flowx,cell 2 0");
		
		trackBlockSelect = new JComboBox<Object>(blockNumberModel.get(trackLineSelect.getSelectedItem()));
		contentPane.add(trackBlockSelect, "cell 2 0,growx");
		trackBlockSelect.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	JComboBox<Object> source = (JComboBox<Object>)e.getSource();
		    	TrackBlock block = trackModel.trackLayouts.get(trackLineSelect.getSelectedItem()).blocks.get((Integer)source.getSelectedItem());
		    	table.setModel(createTableModel(block, (String)trackLineSelect.getSelectedItem()));
		    	updateSimulationPane(block);
		    }
		});
		
		Component verticalStrut = Box.createVerticalStrut(20);
		contentPane.add(verticalStrut, "cell 1 1");
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane, "cell 0 2 3 4,grow");
		
		JPanel dataPanel = new JPanel();
		tabbedPane.addTab("Block Data", null, dataPanel, null);
		dataPanel.setLayout(new MigLayout("", "[grow]", "[grow]"));
		dataPanel.setOpaque(false);
		
		JScrollPane scrollPane = new JScrollPane();
		dataPanel.add(scrollPane, "cell 0 0,grow");
		
		TrackBlock block = trackModel.trackLayouts.get(trackLineSelect.getSelectedItem()).blocks.get((Integer)trackBlockSelect.getSelectedItem());
		
		table = new JTable(createTableModel(block, (String)trackLineSelect.getSelectedItem()));
		scrollPane.setViewportView(table);
		
		JPanel simulationPanel = new JPanel();
		simulationPanel.setOpaque(false);
		tabbedPane.addTab("Simulate Inputs", null, simulationPanel, null);
		simulationPanel.setLayout(new MigLayout("", "[][grow]", "[][][][][][][][][]"));
		
		JLabel lblWeather = new JLabel("Weather");
		simulationPanel.add(lblWeather, "cell 0 0,alignx trailing");
		
		weatherSelect = new JComboBox<String>(new String[] {"Clear", "Rain", "Snow", "Ice"});
		simulationPanel.add(weatherSelect, "cell 1 0,growx");
		
		JLabel lblStatusfailuremaintenance = new JLabel("Status (Failure/Maintenance)");
		simulationPanel.add(lblStatusfailuremaintenance, "cell 0 1,alignx trailing");
		
		statusSelect = new JComboBox<String>(new String[] {"Good", "Maintenance", "Power Failure", "Circuit Failure", "Broken Track"});
		simulationPanel.add(statusSelect, "cell 1 1,growx");
		
		JLabel lblNewLabel = new JLabel("Occupancy");
		simulationPanel.add(lblNewLabel, "cell 0 2,alignx right");
		
		occupiedCheckbox = new JCheckBox("Block occupied");
		simulationPanel.add(occupiedCheckbox, "cell 1 2");
		
		JLabel lblCommandedSpeed = new JLabel("Commanded Speed");
		simulationPanel.add(lblCommandedSpeed, "cell 0 3,alignx trailing");
		
		commandedSpeedTextfield = new JTextField();
		simulationPanel.add(commandedSpeedTextfield, "cell 1 3,growx");
		commandedSpeedTextfield.setColumns(10);
		
		JLabel lblCommandedAuthority = new JLabel("Commanded Authority");
		simulationPanel.add(lblCommandedAuthority, "cell 0 4,alignx trailing");
		
		commandedAuthorityTextfield = new JTextField();
		simulationPanel.add(commandedAuthorityTextfield, "cell 1 4,growx");
		commandedAuthorityTextfield.setColumns(10);
		
		JLabel lblLights = new JLabel("Lights");
		simulationPanel.add(lblLights, "cell 0 5,alignx trailing");
		
		lightsSelect = new JComboBox<Object>(new String[] {"Green", "Red"});
		simulationPanel.add(lightsSelect, "cell 1 5,growx");
		
		JLabel lblHeater = new JLabel("Heater");
		simulationPanel.add(lblHeater, "cell 0 6,alignx right");
		
		heaterCheckbox = new JCheckBox("Heater on");
		simulationPanel.add(heaterCheckbox, "cell 1 6");
		
		Component verticalStrut_1 = Box.createVerticalStrut(20);
		simulationPanel.add(verticalStrut_1, "cell 1 7");
		
		updateSimulationPane(block);
		
		JButton simulateButton = new JButton("Simulate Inputs");
		simulationPanel.add(simulateButton, "cell 1 8");
		simulateButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	JButton source = (JButton)e.getSource();
		    	TrackBlock block = trackModel.trackLayouts.get(trackLineSelect.getSelectedItem()).blocks.get((Integer)trackBlockSelect.getSelectedItem());
		    	updateModel(block);
		    	table.setModel(createTableModel(block, (String)trackLineSelect.getSelectedItem()));
		    	tabbedPane.setSelectedIndex(0);
		    }
		});
		
	}
	
	private void updateModel(TrackBlock block) {
		block.weather = weatherSelect.getSelectedItem().equals("Clear") ? null : (String)weatherSelect.getSelectedItem();
		block.failure = statusSelect.getSelectedItem().equals("Good") ? null : (String)statusSelect.getSelectedItem();
		block.occupancy = occupiedCheckbox.isSelected();
		String commandedSpeedText = commandedSpeedTextfield.getText();
		String commandedAuthorityText = commandedAuthorityTextfield.getText();
		double commandedSpeed = 0;
		double commandedAuthority = 0;
		try {
			if (!commandedSpeedText.isEmpty()) {
				commandedSpeed = Double.parseDouble(commandedSpeedText);
			}
			if (!commandedAuthorityText.isEmpty()) {
				commandedAuthority = Double.parseDouble(commandedAuthorityText);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		block.commandedSpeed = commandedSpeed;
		block.commandedAuthority = commandedAuthority;
		block.lights = lightsSelect.getSelectedItem().equals("Red") ? true : false;
		block.heater = heaterCheckbox.isSelected();
	}
	
	private void updateSimulationPane(TrackBlock block) {
		weatherSelect.setSelectedItem(block.weather == null ? "Clear" : block.weather);
    	statusSelect.setSelectedItem(block.failure == null ? "Good" : block.failure);
    	occupiedCheckbox.setSelected(block.occupancy);
    	commandedSpeedTextfield.setText(String.valueOf(block.commandedSpeed));
    	commandedAuthorityTextfield.setText(String.valueOf(block.commandedAuthority));
    	lightsSelect.setSelectedItem(block.lights ? "Red" : "Green");
    	heaterCheckbox.setSelected(block.heater);
	}
	
	private DefaultTableModel createTableModel(TrackBlock block, String line) {
		DefaultTableModel model = new DefaultTableModel(new String[][] {
			{"Section", block.section != null ? block.section : "No section"},
			{"Number", String.valueOf(block.number)},
			{"Length (yards)", String.valueOf(block.length)},
			{"Speed Limit (mph)", String.valueOf(block.speedLimit)},
			{"Grade (%)", String.valueOf(block.grade)},
			{"Elevation (yards)", String.valueOf(block.elevation)},
			{"Cumulative Elevation (yards)", String.valueOf(block.cumulativeElevation)},
			{"Underground", block.underground ? "Yes" : "No" },
			{"Infrastructure", block.infrastructure != null ? block.infrastructure : ""},
			{"Occupancy", block.occupancy ? "Occupied" : "Not occupied"},
			{"Commanded Speed (mph)", String.valueOf(block.commandedSpeed)},
			{"Commanded Authority (yards)", String.valueOf(block.commandedAuthority)},
			{"Heater", block.heater ? "On" : "Off"},
			{"Weather", block.weather != null ? String.valueOf(block.weather) : "Clear"},
			{"Lights", block.lights ? "Red" : "Green"},
			{"Failure", block.failure != null ? String.valueOf(block.failure) : "None"},
			{"Beacon Message", "None"}
		}, new String[] {"Attribute", "Value"});
		DirectedMultigraph<Integer, DefaultEdge> layout = trackModel.trackLayouts.get(line).layout;
		Set<DefaultEdge> inEdges = layout.incomingEdgesOf(block.number);
		Set<DefaultEdge> outEdges = layout.outgoingEdgesOf(block.number);
		StringBuilder inBuilder = new StringBuilder();
		StringBuilder outBuilder = new StringBuilder();
		int i = 0;
		for (DefaultEdge edge : inEdges) {
			if (i++ > 0) {
				inBuilder.append(", ");
			}
			inBuilder.append(layout.getEdgeSource(edge));
		}
		i = 0;
		for (DefaultEdge edge : outEdges) {
			if (i++ > 0) {
				outBuilder.append(", ");
			}
			outBuilder.append(layout.getEdgeTarget(edge));
		}
		model.addRow(new String[] {"Tail Blocks", inBuilder.toString()});
		model.addRow(new String[] {"Head Blocks", outBuilder.toString()});
		if (block.infrastructure != null && block.infrastructure.equals("station")) {
			TrackStation trackStation = (TrackStation)block;
			model.addRow(new String[] {"Passengers", String.valueOf(trackStation.passengersWaiting)});
		}
		if (block.infrastructure != null && block.infrastructure.equals("switch")) {
			TrackSwitch trackSwitch = (TrackSwitch)block;
			model.addRow(new String[] {"Switch Position", String.valueOf(trackSwitch.state ? trackSwitch.out[0] : trackSwitch.out[1])});
		}
		return model;
	}

}
