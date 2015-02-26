package ctcOffice;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.Timer;


@SuppressWarnings("serial")
public class TrackJPanel extends JPanel {
		private TrackLayout trackLayout;
		private HashMap<Infrastructure, GeneralPath> renderedObjects = new HashMap<Infrastructure, GeneralPath>();
		private HashMap<Switch, HashMap<String, Line2D>> renderedSwitches = new HashMap<Switch, HashMap<String, Line2D>>();
		private Graphics2D drawer;
		private HashMap<Infrastructure, Double> TrainLocation;
		
		TrackJPanel(TrackLayout tLayout) {
            // set a preferred size for the custom panel.
            setPreferredSize(new Dimension(2000,400));
            trackLayout = tLayout;
            final Timer timer = new Timer(1000, new ActionListener() {
                @Override
                public void actionPerformed(final ActionEvent e) {
                	// It looks shitty as hell, but I can call call methods from here to update the track and it will
                	// update the GUI with anything I draw
                	
                	
                	
                	
                	
                    repaint();
                }
            });
            timer.start();
        }
		
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            
            TrackLayout tLayout = trackLayout;
            drawer = (Graphics2D) g;
            
            List<Infrastructure[]> trackIterator = tLayout.createIterator();
            HashMap<Infrastructure, Double[]> blockEndCoordinates = new HashMap<Infrastructure, Double[]>();
            // Create a dashed line stroke
            // Draw the yard
            // If I use shape then I can use default methods like draw rectangle.
            // I think generalpath supports collision detection though so its probably fine.
            GeneralPath yard = drawYard(100, 100);
            GeneralPath currentComponent = yard;
            drawer.draw(yard);
            drawer.drawString("YARD", 110, 130);
            Double[] nextCoordinates = {160.0, 125.0};
            Double[] endCoordinates = Arrays.copyOf(nextCoordinates, nextCoordinates.length);
            for (int i = 0; i<trackIterator.size();i++){
            	Infrastructure[] trackBlock = trackIterator.get(i);
            	double blockLength = trackBlock[1].getBlockLength();
            	Color paintColor;
            	if (trackBlock[1].isOpen()){
            		paintColor = Color.GREEN;
            	}
            	else{
            		paintColor = Color.RED;
            	}
            	if (trackBlock[0] != null){
            		// If it is not the first track block, the coordinates will
            		// be drawn based on the coordinates of the previous block
            		nextCoordinates = Arrays.copyOf(blockEndCoordinates.get(trackBlock[0]), 2);
            		if (trackBlock[0].getSection() != trackBlock[1].getSection()){
            			drawer.draw(new Line2D.Double(nextCoordinates[0], nextCoordinates[1] + 5, nextCoordinates[0] + 20, nextCoordinates[1] + 5));
            			nextCoordinates[0] = nextCoordinates[0] + 20;
            		}
            	}
            	else {
            		// Draw a line from the Yard to the first track segment
        			drawer.draw(new Line2D.Double(nextCoordinates[0] - 10, nextCoordinates[1] + 5, nextCoordinates[0], nextCoordinates[1] + 5));
            	}
            	if (trackBlock[1].containsSwitch()){
            		//Draw the switch
            		HashMap<String, Line2D> switchMap = new HashMap<String, Line2D>();
            		Switch switchBlock = (Switch) trackBlock[1];
            		Line2D switchLine;
            		currentComponent = drawSwitch(nextCoordinates[0], nextCoordinates[1], blockLength);
            		if (currentComponent == null){
            			// Drawing failed! Backtrack 
            		}
                	drawer.setPaint(paintColor);
                	drawer.fill(currentComponent);
                	drawer.setPaint(Color.BLACK);
            		drawer.draw(currentComponent);
            		endCoordinates[0] = (int) (Math.ceil(blockLength)) + nextCoordinates[0];
            		blockEndCoordinates.put(trackBlock[1], Arrays.copyOf(endCoordinates,2));
            		renderedObjects.put(trackBlock[1], currentComponent);
            		// It is a switch draw the next two;
            		// Get the first connected block
            		trackBlock = trackIterator.get(++i);
                	if (trackBlock[1].isOpen()){
                		paintColor = Color.GREEN;
                	}
                	else{
                		paintColor = Color.RED;
                	}
                	blockLength = trackBlock[1].getBlockLength();
                	nextCoordinates[0] = endCoordinates[0] + 20;
                	nextCoordinates[1] = endCoordinates[1] - 10;
                	switchLine = new Line2D.Double(endCoordinates[0], endCoordinates[1] + 5, nextCoordinates[0], nextCoordinates[1] + 5);
                	switchMap.put("Enabled", switchLine);
                	renderedSwitches.put(switchBlock, switchMap);
        			drawer.draw(switchLine);
                	
            		// Draw the first one
            		currentComponent = drawBlock(nextCoordinates[0], nextCoordinates[1], blockLength);
            		if (currentComponent == null){
            			// Drawing failed! Backtrack 
            		}
                	drawer.setPaint(paintColor);
                	drawer.fill(currentComponent);
                	drawer.setPaint(Color.BLACK);
            		drawer.draw(currentComponent);
            		endCoordinates[0] =  blockLength + nextCoordinates[0];
            		endCoordinates[1] = (double) nextCoordinates[1];
            		blockEndCoordinates.put(trackBlock[1], Arrays.copyOf(endCoordinates,2));
            		renderedObjects.put(trackBlock[1], currentComponent);
            		// Get the second block
            		trackBlock = trackIterator.get(++i);
                	if (trackBlock[1].isOpen()){
                		paintColor = Color.GREEN;
                	}
                	else{
                		paintColor = Color.RED;
                	}
                	blockLength = trackBlock[1].getBlockLength();
                	nextCoordinates[1] = nextCoordinates[1] + 20;
                	switchLine = new Line2D.Double(endCoordinates[0], endCoordinates[1] + 5, nextCoordinates[0], nextCoordinates[1] + 5);
                	switchMap.put("Disabled", switchLine);
                	renderedSwitches.put(switchBlock, switchMap);
            		//Draw the second block
            		currentComponent = drawBlock(nextCoordinates[0], nextCoordinates[1], blockLength);
            		if (currentComponent == null){
            			// Drawing failed! Backtrack 
            		}
                	drawer.setPaint(paintColor);
                	drawer.fill(currentComponent);
                	drawer.setPaint(Color.BLACK);
            		drawer.draw(currentComponent);
            		endCoordinates[0] =  blockLength + nextCoordinates[0];
            		endCoordinates[1] = (double) nextCoordinates[1];
            		blockEndCoordinates.put(trackBlock[1], Arrays.copyOf(endCoordinates,2));
            		renderedObjects.put(trackBlock[1], currentComponent);
            		
            		
            	}
            	else{ // It is a station or a normal block
            		
            		if (trackBlock[1].containsStation()){
            			currentComponent = drawStation(nextCoordinates[0], nextCoordinates[1], blockLength);
            		}
            		else{
                		currentComponent = drawBlock(nextCoordinates[0], nextCoordinates[1], blockLength);
            		}
            		if (currentComponent == null){
            			// Drawing failed! Backtrack 
            		}
            		endCoordinates[0] = blockLength + nextCoordinates[0];
            		endCoordinates[1] = (double) nextCoordinates[1];
            		blockEndCoordinates.put(trackBlock[1], Arrays.copyOf(endCoordinates,2));
            		for (Infrastructure connection : trackBlock[1].getConnectedBlocks()){
            			if (connection != trackBlock[0] && blockEndCoordinates.containsKey(connection)){
            				Double[] currentEndCoordinates = blockEndCoordinates.get(trackBlock[1]);
            				Double[] otherEndCoordinates = blockEndCoordinates.get(connection);
                			drawer.draw(new Line2D.Double(otherEndCoordinates[0], otherEndCoordinates[1] + 5, currentEndCoordinates[0], currentEndCoordinates[1] + 5));
            			}
            		}

                	drawer.setPaint(paintColor);
                	drawer.fill(currentComponent);
                	drawer.setPaint(Color.BLACK);
            		drawer.draw(currentComponent);
            		renderedObjects.put(trackBlock[1], currentComponent);           		
            		
            	}
            	
            }
        }
        public void repaint(Graphics g) {
            super.paintComponent(g);
            
            TrackLayout tLayout = trackLayout;
            drawer = (Graphics2D) g;
            
            List<Infrastructure[]> trackIterator = tLayout.createIterator();
            HashMap<Infrastructure, Double[]> blockEndCoordinates = new HashMap<Infrastructure, Double[]>();
            // Create a dashed line stroke
            // Draw the yard
            // If I use shape then I can use default methods like draw rectangle.
            // I think generalpath supports collision detection though so its probably fine.
            GeneralPath yard = drawYard(100, 100);
            GeneralPath currentComponent = yard;
            drawer.draw(yard);
            drawer.drawString("YARD", 110, 130);
            Double[] nextCoordinates = {160.0, 125.0};
            Double[] endCoordinates = Arrays.copyOf(nextCoordinates, nextCoordinates.length);
            for (int i = 0; i<trackIterator.size();i++){
            	Infrastructure[] trackBlock = trackIterator.get(i);
            	double blockLength = trackBlock[1].getBlockLength();
            	Color paintColor;
            	if (trackBlock[1].isOpen()){
            		paintColor = Color.GREEN;
            	}
            	else{
            		paintColor = Color.RED;
            	}
            	if (trackBlock[0] != null){
            		// If it is not the first track block, the coordinates will
            		// be drawn based on the coordinates of the previous block
            		nextCoordinates = Arrays.copyOf(blockEndCoordinates.get(trackBlock[0]), 2);
            		if (trackBlock[0].getSection() != trackBlock[1].getSection()){
            			drawer.draw(new Line2D.Double(nextCoordinates[0], nextCoordinates[1] + 5, nextCoordinates[0] + 20, nextCoordinates[1] + 5));
            			nextCoordinates[0] = nextCoordinates[0] + 20;
            		}
            	}
            	else {
            		// Draw a line from the Yard to the first track segment
        			drawer.draw(new Line2D.Double(nextCoordinates[0] - 10, nextCoordinates[1] + 5, nextCoordinates[0], nextCoordinates[1] + 5));
            	}
            	if (trackBlock[1].containsSwitch()){
            		//Draw the switch
            		HashMap<String, Line2D> switchMap = new HashMap<String, Line2D>();
            		Switch switchBlock = (Switch) trackBlock[1];
            		Line2D switchLine;
            		currentComponent = drawSwitch(nextCoordinates[0], nextCoordinates[1], blockLength);
            		if (currentComponent == null){
            			// Drawing failed! Backtrack 
            		}
                	drawer.setPaint(paintColor);
                	drawer.fill(currentComponent);
                	drawer.setPaint(Color.BLACK);
            		drawer.draw(currentComponent);
            		endCoordinates[0] = (int) (Math.ceil(blockLength)) + nextCoordinates[0];
            		blockEndCoordinates.put(trackBlock[1], Arrays.copyOf(endCoordinates,2));
            		renderedObjects.put(trackBlock[1], currentComponent);
            		// It is a switch draw the next two;
            		// Get the first connected block
            		trackBlock = trackIterator.get(++i);
                	if (trackBlock[1].isOpen()){
                		paintColor = Color.GREEN;
                	}
                	else{
                		paintColor = Color.RED;
                	}
                	blockLength = trackBlock[1].getBlockLength();
                	nextCoordinates[0] = endCoordinates[0] + 20;
                	nextCoordinates[1] = endCoordinates[1] - 10;
                	switchLine = new Line2D.Double(endCoordinates[0], endCoordinates[1] + 5, nextCoordinates[0], nextCoordinates[1] + 5);
                	switchMap.put("Enabled", switchLine);
                	renderedSwitches.put(switchBlock, switchMap);
        			drawer.draw(switchLine);
                	
            		// Draw the first one
            		currentComponent = drawBlock(nextCoordinates[0], nextCoordinates[1], blockLength);
            		if (currentComponent == null){
            			// Drawing failed! Backtrack 
            		}
                	drawer.setPaint(paintColor);
                	drawer.fill(currentComponent);
                	drawer.setPaint(Color.BLACK);
            		drawer.draw(currentComponent);
            		endCoordinates[0] =  blockLength + nextCoordinates[0];
            		endCoordinates[1] = (double) nextCoordinates[1];
            		blockEndCoordinates.put(trackBlock[1], Arrays.copyOf(endCoordinates,2));
            		renderedObjects.put(trackBlock[1], currentComponent);
            		// Get the second block
            		trackBlock = trackIterator.get(++i);
                	if (trackBlock[1].isOpen()){
                		paintColor = Color.GREEN;
                	}
                	else{
                		paintColor = Color.RED;
                	}
                	blockLength = trackBlock[1].getBlockLength();
                	nextCoordinates[1] = nextCoordinates[1] + 20;
                	switchLine = new Line2D.Double(endCoordinates[0], endCoordinates[1] + 5, nextCoordinates[0], nextCoordinates[1] + 5);
                	switchMap.put("Disabled", switchLine);
                	renderedSwitches.put(switchBlock, switchMap);
            		//Draw the second block
            		currentComponent = drawBlock(nextCoordinates[0], nextCoordinates[1], blockLength);
            		if (currentComponent == null){
            			// Drawing failed! Backtrack 
            		}
                	drawer.setPaint(paintColor);
                	drawer.fill(currentComponent);
                	drawer.setPaint(Color.BLACK);
            		drawer.draw(currentComponent);
            		endCoordinates[0] =  blockLength + nextCoordinates[0];
            		endCoordinates[1] = (double) nextCoordinates[1];
            		blockEndCoordinates.put(trackBlock[1], Arrays.copyOf(endCoordinates,2));
            		renderedObjects.put(trackBlock[1], currentComponent);
            		
            		
            	}
            	else{ // It is a station or a normal block
            		
            		if (trackBlock[1].containsStation()){
            			currentComponent = drawStation(nextCoordinates[0], nextCoordinates[1], blockLength);
            		}
            		else{
                		currentComponent = drawBlock(nextCoordinates[0], nextCoordinates[1], blockLength);
            		}
            		if (currentComponent == null){
            			// Drawing failed! Backtrack 
            		}
            		endCoordinates[0] = blockLength + nextCoordinates[0];
            		endCoordinates[1] = (double) nextCoordinates[1];
            		blockEndCoordinates.put(trackBlock[1], Arrays.copyOf(endCoordinates,2));
            		for (Infrastructure connection : trackBlock[1].getConnectedBlocks()){
            			if (connection != trackBlock[0] && blockEndCoordinates.containsKey(connection)){
            				Double[] currentEndCoordinates = blockEndCoordinates.get(trackBlock[1]);
            				Double[] otherEndCoordinates = blockEndCoordinates.get(connection);
                			drawer.draw(new Line2D.Double(otherEndCoordinates[0], otherEndCoordinates[1] + 5, currentEndCoordinates[0], currentEndCoordinates[1] + 5));
            			}
            		}

                	drawer.setPaint(Color.GREEN);
                	drawer.fill(currentComponent);
                	drawer.setPaint(Color.BLACK);
            		drawer.draw(currentComponent);
            		renderedObjects.put(trackBlock[1], currentComponent);
            	}
            	
            }
        }
        private GeneralPath drawYard(double x, double y){
        	GeneralPath yard = new GeneralPath(GeneralPath.WIND_EVEN_ODD, 4);
        	double width = 50;
        	int height = 50;
        	yard.moveTo(x, y);
        	yard.lineTo (x + width, y);
        	yard.lineTo(x + width, y + height);
        	yard.lineTo(x, y + height);
        	yard.lineTo(x, y);
        	
        	return yard;
        }
        private GeneralPath drawBlock(double x, double y, double width){
        	GeneralPath block = new GeneralPath(GeneralPath.WIND_EVEN_ODD, 4);
        	int height = 10;
        	block.moveTo(x, y);
        	block.lineTo (x + width, y);
        	block.lineTo(x + width, y + height);
        	block.lineTo(x, y + height);
        	block.lineTo(x, y);
        	
        	return block;
        }
        private GeneralPath drawStation(double x, double y, double width){
        	GeneralPath block = new GeneralPath(GeneralPath.WIND_EVEN_ODD, 4);
        	int height = 10;
        	block.moveTo(x, y);
        	block.lineTo (x + width, y);
        	block.lineTo(x + width, y + height);
        	block.lineTo(x, y + height);
        	block.lineTo(x, y);
        	
        	return block;
        }
        private GeneralPath drawSwitch(double x, double y, double width){
        	GeneralPath block = new GeneralPath(GeneralPath.WIND_EVEN_ODD, 4);
        	int height = 10;
        	block.moveTo(x, y);
        	block.lineTo (x + width, y);
        	block.lineTo(x + width, y + height);
        	block.lineTo(x, y + height);
        	block.lineTo(x, y);
        	
        	return block;
        }
        public void closeBlock(int blockNum){
        	Block block = trackLayout.getBlockAt(blockNum);
        	block.closeBlock();
        	GeneralPath blockRendering = renderedObjects.get(block);
        	drawer.setPaint(Color.RED);
        	drawer.fill(blockRendering);
        	drawer.setPaint(Color.BLACK);
    		drawer.draw(blockRendering);        	
        }
        /*
        private GeneralPath drawPreviousPointedBlock(double x, double y, double width){
        	GeneralPath block = new GeneralPath(GeneralPath.WIND_EVEN_ODD, 4);
        	int height = 10;
        	block.moveTo(x, y);
        	block.lineTo (x + width, y);
        	block.lineTo(x + width, y + height);
        	block.lineTo(x, y + height);
        	block.lineTo(x, y);
        	
        	return block;
        }
        private GeneralPath drawNextPointedBlock(double x, double y, double width){
        	GeneralPath block = new GeneralPath(GeneralPath.WIND_EVEN_ODD, 4);
        	int height = 10;
        	block.moveTo(x, y);
        	block.lineTo (x + width, y);
        	block.lineTo(x + width, y + height);
        	block.lineTo(x, y + height);
        	block.lineTo(x, y);
        	
        	return block;
        }
        private GeneralPath drawDoublePointedBlock(double x, double y, double width){
        	GeneralPath block = new GeneralPath(GeneralPath.WIND_EVEN_ODD, 4);
        	int height = 10;
        	block.moveTo(x, y);
        	block.lineTo (x + width, y);
        	block.lineTo(x + width, y + height);
        	block.lineTo(x, y + height);
        	block.lineTo(x, y);
        	
        	return block;
        }
        private GeneralPath drawTopPointedSwitch(double x, double y, double width){
        	GeneralPath block = new GeneralPath(GeneralPath.WIND_EVEN_ODD, 4);
        	int height = 10;
        	block.moveTo(x, y);
        	block.lineTo (x + width, y);
        	block.lineTo(x + width, y + height);
        	block.lineTo(x, y + height);
        	block.lineTo(x, y);
        	
        	return block;
        }
        private GeneralPath drawBottomPointedSwitch(double x, double y, double width){
        	GeneralPath block = new GeneralPath(GeneralPath.WIND_EVEN_ODD, 4);
        	int height = 10;
        	block.moveTo(x, y);
        	block.lineTo (x + width, y);
        	block.lineTo(x + width, y + height);
        	block.lineTo(x, y + height);
        	block.lineTo(x, y);
        	
        	return block;
        }
        private GeneralPath drawDoublePointedSwitch(double x, double y, double width){
        	GeneralPath block = new GeneralPath(GeneralPath.WIND_EVEN_ODD, 4);
        	int height = 10;
        	block.moveTo(x, y);
        	block.lineTo (x + width, y);
        	block.lineTo(x + width, y + height);
        	block.lineTo(x, y + height);
        	block.lineTo(x, y);
        	
        	return block;
        }*/
    }