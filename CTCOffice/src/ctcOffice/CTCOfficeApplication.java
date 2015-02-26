package ctcOffice;


import java.awt.EventQueue;
import java.io.IOException;

import javax.swing.JFrame;

public class CTCOfficeApplication {

	private CTCOfficeGUIImplementation frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CTCOfficeApplication window = new CTCOfficeApplication();
					window.frame.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @throws IOException 
	 */
	public CTCOfficeApplication() throws IOException {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws IOException 
	 */
	private void initialize() throws IOException {
		// Create the track Layout
		String excelFilePointer = "track_layout.csv";
		TrackLayout blueLineTrackLayout = new TrackLayout();
		blueLineTrackLayout.parseCsvFile(excelFilePointer);
		frame = new CTCOfficeGUIImplementation(blueLineTrackLayout);
		frame.setResizable(false);
		frame.setBounds(100, 100, 902, 388);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
	}



}
