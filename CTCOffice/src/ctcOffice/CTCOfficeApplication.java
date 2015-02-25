package ctcOffice;


import java.util.List;

import javax.swing.JFrame;

public class CTCOfficeApplication {

	private JFrame frame;

	/**
	 * Create the application.
	 */
	public CTCOfficeApplication() {
		initialize();
		frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new CTCOfficeGUIImplementation();
		frame.setResizable(false);
		frame.setBounds(100, 100, 836, 530);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void drawTrackLayout(TrackLayout blueLineTrackLayout) {
		// TODO Auto-generated method stub
		List<Infrastructure> trackIterator = blueLineTrackLayout.createIterator();
		for (Infrastructure nextBlock : trackIterator){
			
		}
	}

}
