package ctcOfficeGUI;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import java.awt.Canvas;
import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

import java.awt.GridLayout;

import javax.swing.border.LineBorder;
import javax.swing.JSlider;

import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CTCOfficeGUIImplementation extends JFrame {

    /**
     * Create the panel.
     */
    public CTCOfficeGUIImplementation() {
    	setResizable(false);
        getContentPane().setBackground(Color.WHITE);
        setBackground(Color.WHITE);

        JPanel toolbar = new JPanel();
        toolbar.setBorder(new LineBorder(new Color(0, 0, 0)));
        toolbar.setBackground(Color.WHITE);

        JPanel simulationSpeed = new JPanel();
        simulationSpeed.setBounds(24, 2, 175, 29);
        simulationSpeed.setBackground(Color.WHITE);
        simulationSpeed.setLayout(null);

        JLabel simulationSpeedLabel = new JLabel("Simulation Speed");
        simulationSpeedLabel.setBounds(10, 8, 81, 14);
        simulationSpeed.add(simulationSpeedLabel);
        
        JSlider slider = new JSlider();
        slider.setForeground(Color.BLACK);
        slider.setBounds(101, 9, 50, 10);
        simulationSpeed.add(slider);
        toolbar.setLayout(null);
        toolbar.add(simulationSpeed);

        JScrollPane trackLayoutScrollPane = new JScrollPane();
        
        JPanel trackLayoutPanel = new JPanel();
        trackLayoutPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
        trackLayoutPanel.setBackground(Color.WHITE);
        trackLayoutScrollPane.setViewportView(trackLayoutPanel);
        trackLayoutPanel.add(new Canvas());
        GroupLayout gl_trackLayoutPanel = new GroupLayout(trackLayoutPanel);
        gl_trackLayoutPanel.setHorizontalGroup(
        	gl_trackLayoutPanel.createParallelGroup(Alignment.LEADING)
        		.addGap(0, 650, Short.MAX_VALUE)
        );
        gl_trackLayoutPanel.setVerticalGroup(
        	gl_trackLayoutPanel.createParallelGroup(Alignment.LEADING)
        		.addGap(0, 495, Short.MAX_VALUE)
        );
        trackLayoutPanel.setLayout(gl_trackLayoutPanel);

        JScrollPane trackInfoScrollPane = new JScrollPane();
        trackInfoScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        JPanel trackInfoPanel = new JPanel();
        trackInfoPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
        trackInfoPanel.setBackground(Color.WHITE);
        trackInfoScrollPane.setViewportView(trackInfoPanel);
        trackInfoPanel.setLayout(new GridLayout(1, 0, 0, 0));
        
        JPanel CTCOfficeLabel = new JPanel();
        CTCOfficeLabel.setBorder(new LineBorder(new Color(0, 0, 0)));
        CTCOfficeLabel.setBackground(Color.WHITE);
        GroupLayout groupLayout = new GroupLayout(getContentPane());
        groupLayout.setHorizontalGroup(
        	groupLayout.createParallelGroup(Alignment.LEADING)
        		.addComponent(toolbar, GroupLayout.PREFERRED_SIZE, 654, GroupLayout.PREFERRED_SIZE)
        		.addComponent(trackLayoutScrollPane, GroupLayout.PREFERRED_SIZE, 654, GroupLayout.PREFERRED_SIZE)
        		.addGroup(groupLayout.createSequentialGroup()
        			.addGap(653)
        			.addComponent(trackInfoScrollPane, GroupLayout.PREFERRED_SIZE, 197, GroupLayout.PREFERRED_SIZE))
        		.addGroup(groupLayout.createSequentialGroup()
        			.addGap(653)
        			.addComponent(CTCOfficeLabel, GroupLayout.PREFERRED_SIZE, 197, GroupLayout.PREFERRED_SIZE))
        );
        groupLayout.setVerticalGroup(
        	groupLayout.createParallelGroup(Alignment.LEADING)
        		.addComponent(toolbar, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
        		.addGroup(groupLayout.createSequentialGroup()
        			.addGap(33)
        			.addComponent(trackLayoutScrollPane, GroupLayout.PREFERRED_SIZE, 499, GroupLayout.PREFERRED_SIZE))
        		.addGroup(groupLayout.createSequentialGroup()
        			.addGap(33)
        			.addComponent(trackInfoScrollPane, GroupLayout.PREFERRED_SIZE, 499, GroupLayout.PREFERRED_SIZE))
        		.addComponent(CTCOfficeLabel, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
        );
        
        JLabel lblCtcOffice = new JLabel("CTC Office");
        lblCtcOffice.setFont(new Font("Tahoma", Font.BOLD, 14));
        CTCOfficeLabel.add(lblCtcOffice);
        getContentPane().setLayout(groupLayout);

    }
}
