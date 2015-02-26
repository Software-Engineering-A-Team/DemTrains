package ctcOffice;

import javax.swing.JPanel;

import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.BorderLayout;
import java.awt.CardLayout;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

import net.miginfocom.swing.MigLayout;

import java.awt.GridLayout;

import javax.swing.JComboBox;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;
import java.awt.Color;

public class temp extends JFrame {

	/**
	 * Create the panel.
	 */
	public temp() {
		getContentPane().setBackground(Color.WHITE);
		setBackground(Color.WHITE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{550, 152};
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
		
		JPanel trackLayoutPanel = new JPanel();
		trackLayoutPanel.setBackground(Color.WHITE);
		trackLayoutScrollPane.setViewportView(trackLayoutPanel);
		trackLayoutPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		JPanel trackInfoPanel = new JPanel();
		trackInfoPanel.setBackground(Color.WHITE);
		trackInfoPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		GridBagConstraints gbc_trackInfoPanel = new GridBagConstraints();
		gbc_trackInfoPanel.fill = GridBagConstraints.BOTH;
		gbc_trackInfoPanel.gridx = 1;
		gbc_trackInfoPanel.gridy = 1;
		getContentPane().add(trackInfoPanel, gbc_trackInfoPanel);
		GridBagLayout gbl_trackInfoPanel = new GridBagLayout();
		gbl_trackInfoPanel.columnWidths = new int[]{89, 89, 0};
		gbl_trackInfoPanel.rowHeights = new int[]{227, 227, 0};
		gbl_trackInfoPanel.columnWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		gbl_trackInfoPanel.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		trackInfoPanel.setLayout(gbl_trackInfoPanel);
		
		JLabel lblNewLabel = new JLabel("New label");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.fill = GridBagConstraints.BOTH;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		trackInfoPanel.add(lblNewLabel, gbc_lblNewLabel);
		
		JComboBox comboBox = new JComboBox();
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.fill = GridBagConstraints.BOTH;
		gbc_comboBox.insets = new Insets(0, 0, 5, 0);
		gbc_comboBox.gridx = 1;
		gbc_comboBox.gridy = 0;
		trackInfoPanel.add(comboBox, gbc_comboBox);
		
		JButton btnNewButton = new JButton("New button");
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.gridwidth = 2;
		gbc_btnNewButton.fill = GridBagConstraints.BOTH;
		gbc_btnNewButton.insets = new Insets(0, 0, 0, 5);
		gbc_btnNewButton.gridx = 0;
		gbc_btnNewButton.gridy = 1;
		trackInfoPanel.add(btnNewButton, gbc_btnNewButton);
		
	}

}
