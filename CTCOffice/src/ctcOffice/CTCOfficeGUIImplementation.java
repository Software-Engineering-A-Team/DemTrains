package ctcOffice;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

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
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{700, 202};
		gridBagLayout.rowHeights = new int[]{30, 358};
		gridBagLayout.columnWeights = new double[]{1.0, 0.0};
		gridBagLayout.rowWeights = new double[]{0.0, 1.0};
		getContentPane().setLayout(gridBagLayout);
		
		JPanel toolbar = new JPanel();
		toolbar.setBackground(Color.WHITE);
		toolbar.setBorder(new LineBorder(new Color(0, 0, 0)));
		GridBagConstraints gbc_toolbar = new GridBagConstraints();
		gbc_toolbar.fill = GridBagConstraints.BOTH;
		gbc_toolbar.insets = new Insets(0, 0, 5, 5);
		gbc_toolbar.gridx = 0;
		gbc_toolbar.gridy = 0;
		getContentPane().add(toolbar, gbc_toolbar);
		
		JPanel CTCOfficeLabel = new JPanel();
		CTCOfficeLabel.setBackground(Color.WHITE);
		CTCOfficeLabel.setBorder(new LineBorder(new Color(0, 0, 0)));
		GridBagConstraints gbc_CTCOfficeLabel = new GridBagConstraints();
		gbc_CTCOfficeLabel.fill = GridBagConstraints.BOTH;
		gbc_CTCOfficeLabel.insets = new Insets(0, 0, 5, 0);
		gbc_CTCOfficeLabel.gridx = 1;
		gbc_CTCOfficeLabel.gridy = 0;
		getContentPane().add(CTCOfficeLabel, gbc_CTCOfficeLabel);
		
		JLabel lblCtcOffice = new JLabel("CTC Office");
		CTCOfficeLabel.add(lblCtcOffice);
		
		JScrollPane trackLayoutScrollPane = new JScrollPane();
		GridBagConstraints gbc_trackLayoutScrollPane = new GridBagConstraints();
		gbc_trackLayoutScrollPane.fill = GridBagConstraints.BOTH;
		gbc_trackLayoutScrollPane.insets = new Insets(0, 0, 0, 5);
		gbc_trackLayoutScrollPane.gridx = 0;
		gbc_trackLayoutScrollPane.gridy = 1;
		getContentPane().add(trackLayoutScrollPane, gbc_trackLayoutScrollPane);
		
		trackLayoutPanel = new TrackJPanel(trackLayout, trackInfoPanel);
		trackLayoutPanel.setBackground(Color.WHITE);
		trackLayoutScrollPane.setViewportView(trackLayoutPanel);
		trackLayoutPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		

		trackInfoPanel.setBackground(Color.WHITE);
		trackInfoPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		GridBagConstraints gbc_trackInfoPanel = new GridBagConstraints();
		gbc_trackInfoPanel.fill = GridBagConstraints.BOTH;
		gbc_trackInfoPanel.gridx = 1;
		gbc_trackInfoPanel.gridy = 1;
		getContentPane().add(trackInfoPanel, gbc_trackInfoPanel);
		GridBagLayout gbl_trackInfoPanel = new GridBagLayout();
		trackInfoPanel.setLayout(gbl_trackInfoPanel);
		
    }

}
