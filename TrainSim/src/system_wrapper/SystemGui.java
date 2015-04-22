package system_wrapper;

import track_controller.*;
import train_model.TrainModel;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import java.awt.BorderLayout;
import javax.swing.JPanel;

public class SystemGui extends JFrame {

  /**
	 * 
	 */
	private static final long serialVersionUID = -8917096951256318886L;

public static void main(String[] args) {
    EventQueue.invokeLater(new Runnable() {
      public void run() {
        try {
          SystemGui window = new SystemGui();
          window.setDefaultCloseOperation(EXIT_ON_CLOSE);
          window.setVisible(true);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });
    Thread t = new Thread(new Runnable() {
        public void run() {
            long time = System.nanoTime();
            while (true) {
            	// Run CTC
            	SystemWrapper.ctcOffice.runCTC();

            	// Run TrackController
            	boolean manual =  (((TrackControllerGUI) SystemWrapper.trackControllerGui).inManualMode());
            	for (WaysideController wcGreen : SystemWrapper.waysideSys.blockControllerMapGreen.values()) {
                	SystemWrapper.waysideSys.runPLC(wcGreen, manual);
            	}
            	for (WaysideController wcRed : SystemWrapper.waysideSys.blockControllerMapRed.values()) {
                	SystemWrapper.waysideSys.runPLC(wcRed, manual);
            	}
            	
            	// Run all Train Models
            	for (TrainModel train : SystemWrapper.trainModels) {
            		train.run();
    			}
            	
            	// Run MBO
            	SystemWrapper.mbo.run();
    
            	// Sleep for the appropriate amount of time
            	long elapsedTime = System.nanoTime() - time;
            	long desiredElapsedTime = (long) Math.ceil(SystemWrapper.perceivedTimeMultiplier * 1000000000);
            	long timeDifferenceMS = (desiredElapsedTime - elapsedTime) / 1000000;
            	try {
            	  if (timeDifferenceMS > 0) {
					Thread.sleep(timeDifferenceMS);
            	  }
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
            	time = System.nanoTime();
            	
            	
            }
        }
      });
    
    t.start();
  }
  
  public SystemGui() {

    this.setBounds(100, 100, 1100, 900);
    
    JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
    getContentPane().add(tabbedPane, BorderLayout.CENTER);

    tabbedPane.addTab("CTC Office", null, SystemWrapper.ctcGUI.getContentPane(), null);
    
    tabbedPane.addTab("MBO", null, SystemWrapper.mboGUI.getContentPane(), null);
    
    tabbedPane.addTab("Track Model", null, SystemWrapper.trackModelGUI.getContentPane(), null);
    
    tabbedPane.addTab("Track Controller", null, SystemWrapper.trackControllerGui.getContentPane(), null);
    
    tabbedPane.addTab("Train Model", null, SystemWrapper.trainModelGUI.getContentPane(), null);
    
    tabbedPane.addTab("Train Controller", null, SystemWrapper.trainControllerGui.getContentPane(), null);
  }
}
