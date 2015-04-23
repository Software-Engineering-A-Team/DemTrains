package ctc_office;


import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import java.awt.BorderLayout;


public class CTCStandaloneGUI extends JFrame {

  /**
	 * 
	 */
	private static final long serialVersionUID = -8917096951256318886L;

public static void main(String[] args) {
    EventQueue.invokeLater(new Runnable() {
      public void run() {
        try {
          CTCStandaloneGUI window = new CTCStandaloneGUI();
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
            	CTCWrapper.ctcOffice.runCTC();

            	// Run TrackController
            	CTCWrapper.trackController.runTrackController();
            	
            	// Sleep for the appropriate amount of time
            	long elapsedTime = System.nanoTime() - time;
            	long desiredElapsedTime = (long) Math.ceil(CTCWrapper.perceivedTimeMultiplier * 10000000);
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
  
  public CTCStandaloneGUI() {

    this.setBounds(100, 100, 1100, 900);
    
    JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
    getContentPane().add(tabbedPane, BorderLayout.CENTER);

    tabbedPane.addTab("CTC Office", null, CTCWrapper.ctcGUI.getContentPane(), null);
  }
}
