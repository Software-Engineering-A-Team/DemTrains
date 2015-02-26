package ctcOffice;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

import java.awt.GridLayout;

import javax.swing.border.LineBorder;

import java.awt.Font;

public class CTCOfficeGUIImplementation extends JFrame {

    /**
     * Create the panel.
     */
	private TrackJPanel trackLayoutPanel;
	private JPanel trackInfoPanel = new JPanel();
	private TrackLayout trackLayout;
	
    public CTCOfficeGUIImplementation(TrackLayout blueLineTrackLayout) { //Takes a trackLayout and will draw the track
    	trackLayout = blueLineTrackLayout;
    	setResizable(false);
        getContentPane().setBackground(Color.WHITE);
        setBackground(Color.WHITE);

        JPanel toolbar = new JPanel();
        toolbar.setBorder(new LineBorder(new Color(0, 0, 0)));
        toolbar.setBackground(Color.WHITE);        

        toolbar.setLayout(null);

        JScrollPane trackLayoutScrollPane = new JScrollPane();
        
        trackLayoutPanel = new TrackJPanel(trackLayout);
        trackLayoutPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
        trackLayoutPanel.setBackground(Color.WHITE);
        trackLayoutScrollPane.setViewportView(trackLayoutPanel);
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
    
    public TrackJPanel getTrackLayoutPanel(){
    	return trackLayoutPanel;
    }

}
