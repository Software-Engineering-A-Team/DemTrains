package ctcOffice;

import java.io.IOException;


public class CTCOffice {

	/**
	 * Launch the application.
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		

		String excelFilePointer = "";
		TrackLayout blueLineTrackLayout = new TrackLayout();
		// Create the track Layout
		blueLineTrackLayout.parseCsvFile(excelFilePointer);
		// Draw the GUI
		CTCOfficeApplication window = new CTCOfficeApplication();
		window.drawTrackLayout(blueLineTrackLayout);
		
	}

}
