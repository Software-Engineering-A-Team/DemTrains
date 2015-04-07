package Prototype;
import java.awt.event.ActionEvent;





import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;


public class MBODistancePanel extends JPanel{

	JLabel value1;
	JLabel value2;
	int i;
	int j;

	public MBODistancePanel(final JLabel value1, final JLabel value2){

		JPanel trackInfoPanel = new JPanel();
		final String[] distances = {"3 miles","2 miles", "1.4 miles","3 miles","2.3 miles", "1.6 miles",".8 miles","2.2 miles", "1.2 miles","1 mile"};
		i = 0;
		j = 3;
		
		
		final Timer timer = new Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
			
				
				

				value1.setText(distances[i]);
				value2.setText(distances[j]);
				i++;
				j++;
				if (j>9) j=0;
				if (i>9) i=0;

				repaint();
				
			}
			});
			timer.start();
		
		
		
		
	}

}
