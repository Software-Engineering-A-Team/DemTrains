package ctcOfficeGUI;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

public class CTCOfficeGUIImplementation extends JFrame {
	private JTextField zoomInput;
	private JTextField simulationSpeedInput;

	/**
	 * Create the panel.
	 */
	public CTCOfficeGUIImplementation() {
		getContentPane().setBackground(Color.WHITE);
		setBackground(Color.WHITE);
		getContentPane().setLayout(null);
		
		JPanel toolbar = new JPanel();
		toolbar.setBounds(0, 0, 654, 34);
		toolbar.setBackground(Color.WHITE);
		getContentPane().add(toolbar);
		
		JPanel zoomPanel = new JPanel();
		
		JLabel zoomLabel = new JLabel("Zoom");
		zoomPanel.add(zoomLabel);
		
		zoomInput = new JTextField();
		zoomInput.setText("100");
		zoomInput.setColumns(10);
		zoomPanel.add(zoomInput);
		
		JLabel zoomPercentLabel = new JLabel("%");
		zoomPanel.add(zoomPercentLabel);
		
		JPanel simulationSpeed = new JPanel();
		simulationSpeed.setLayout(null);
		
		JLabel simulationSpeedLabel = new JLabel("Simulation Speed");
		simulationSpeedLabel.setBounds(22, 8, 81, 14);
		simulationSpeed.add(simulationSpeedLabel);
		
		simulationSpeedInput = new JTextField();
		simulationSpeedInput.setBounds(108, 5, 86, 20);
		simulationSpeedInput.setText("100");
		simulationSpeedInput.setColumns(10);
		simulationSpeed.add(simulationSpeedInput);
		
		JLabel simulationPercentLabel = new JLabel("%");
		simulationPercentLabel.setBounds(199, 8, 11, 14);
		simulationSpeed.add(simulationPercentLabel);
		GroupLayout gl_toolbar = new GroupLayout(toolbar);
		gl_toolbar.setHorizontalGroup(
			gl_toolbar.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_toolbar.createSequentialGroup()
					.addGap(28)
					.addComponent(zoomPanel, GroupLayout.PREFERRED_SIZE, 165, GroupLayout.PREFERRED_SIZE)
					.addGap(44)
					.addComponent(simulationSpeed, GroupLayout.PREFERRED_SIZE, 232, GroupLayout.PREFERRED_SIZE))
		);
		gl_toolbar.setVerticalGroup(
			gl_toolbar.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_toolbar.createSequentialGroup()
					.addGap(5)
					.addGroup(gl_toolbar.createParallelGroup(Alignment.LEADING)
						.addComponent(zoomPanel, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
						.addComponent(simulationSpeed, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)))
		);
		toolbar.setLayout(gl_toolbar);
		
		JScrollPane trackLayoutScrollPane = new JScrollPane();
		trackLayoutScrollPane.setBounds(0, 33, 654, 486);
		getContentPane().add(trackLayoutScrollPane);
		
		JPanel trackLayoutPanel = new JPanel();
		trackLayoutPanel.setBackground(Color.WHITE);
		trackLayoutScrollPane.setViewportView(trackLayoutPanel);
		
		JButton btnNewButton = new JButton("YARD");
		GroupLayout gl_trackLayoutPanel = new GroupLayout(trackLayoutPanel);
		gl_trackLayoutPanel.setHorizontalGroup(
			gl_trackLayoutPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_trackLayoutPanel.createSequentialGroup()
					.addGap(261)
					.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(311, Short.MAX_VALUE))
		);
		gl_trackLayoutPanel.setVerticalGroup(
			gl_trackLayoutPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_trackLayoutPanel.createSequentialGroup()
					.addGap(195)
					.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 74, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(215, Short.MAX_VALUE))
		);
		trackLayoutPanel.setLayout(gl_trackLayoutPanel);
		
		JScrollPane trackInfoScrollPane = new JScrollPane();
		trackInfoScrollPane.setBounds(653, 0, 184, 519);
		trackInfoScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		getContentPane().add(trackInfoScrollPane);
		
		JPanel trackInfoPanel = new JPanel();
		trackInfoPanel.setBackground(Color.WHITE);
		trackInfoScrollPane.setViewportView(trackInfoPanel);
		trackInfoPanel.setLayout(new GridLayout(1, 0, 0, 0));

	}
}
