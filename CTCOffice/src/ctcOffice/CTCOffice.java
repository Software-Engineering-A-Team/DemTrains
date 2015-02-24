package ctcOffice;

import java.io.FileNotFoundException;
import java.io.IOException;


public class CTCOffice {

	/**
	 * Launch the application.
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		

		String excelFilePointer = "";
		TrackLayout blueLineTrackLayout = new TrackLayout();
		// Draw the GUI
		CTCOfficeApplication window = new CTCOfficeApplication();
		
		blueLineTrackLayout.parseCsvFile(excelFilePointer);
		
		

	}

}
