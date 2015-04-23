package mbo;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import java.awt.BorderLayout;

public class mboStandAloneGUI extends JFrame{

	private static final long serialVersionUID = -8917096951256318886L;
	static MovingBlockOverlay mbo;
	MBOGUI mboGUI;

	public static void main(String[] args) {
	    EventQueue.invokeLater(new Runnable() {
	      public void run() {
	        try {
	          mboStandAloneGUI window = new mboStandAloneGUI();
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
	            	// Run Mbo
	            	//mbo.run();
	            	
	            	// Run TrackController
	            	//CTCWrapper.trackController.runTrackController();
	            	
	            	// Sleep for the appropriate amount of time
	            	long elapsedTime = System.nanoTime() - time;
	            	long desiredElapsedTime = (long) Math.ceil(1 * 10000000);
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
	  
	  public mboStandAloneGUI() {
		
		mbo = new MovingBlockOverlay();
		mboGUI = new MBOGUI();

	    this.setBounds(100, 100, 1300, 900);
	    
	    JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
	    getContentPane().add(tabbedPane, BorderLayout.CENTER);

	    tabbedPane.addTab("MBO", null, mboGUI.getContentPane(), null);
	  }
	}
	


