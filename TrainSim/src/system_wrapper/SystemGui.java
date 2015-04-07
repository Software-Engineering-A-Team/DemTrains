package system_wrapper;

import track_controller.TrackControllerGUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JTextField;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.JToggleButton;
import javax.swing.JRadioButton;
import javax.swing.JProgressBar;
import javax.swing.JSlider;
import javax.swing.JCheckBox;
import javax.swing.ButtonGroup;
import javax.swing.JTabbedPane;

import java.awt.BorderLayout;

import javax.swing.JPanel;

public class SystemGui extends JFrame {

  public static void main(String[] args) {
    
    
    EventQueue.invokeLater(new Runnable() {
      public void run() {
        try {
          SystemGui window = new SystemGui();
          window.setVisible(true);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });
  }
  
  public SystemGui() {

    this.setBounds(100, 100, 500, 569);
    
    JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
    getContentPane().add(tabbedPane, BorderLayout.CENTER);
    
    JPanel panelCtc = new JPanel();
    tabbedPane.addTab("CTC", null, panelCtc, null);
    
    JPanel panelMbo = new JPanel();
    tabbedPane.addTab("MBO", null, panelMbo, null);
    
    JPanel panelTrackModel = new JPanel();
    tabbedPane.addTab("Track Model", null, panelTrackModel, null);
    
    JPanel panelTrackController = new JPanel();
    tabbedPane.addTab("Track Controller", null, panelTrackController, null);
    
    JPanel panelTrainModel = new JPanel();
    tabbedPane.addTab("Train Model", null, panelTrainModel, null);
    
    tabbedPane.addTab("Train Controller", null, SystemWrapper.trainControllerGui.getContentPane(), null);
  }
}
