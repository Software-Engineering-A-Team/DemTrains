package ctcOffice;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import com.ibm.icu.util.BytesTrie.Iterator;


@SuppressWarnings("serial")
public class TrackJPanel extends JPanel {
		private TrackLayout trackLayout;
		private HashMap<Infrastructure, GeneralPath> renderedObjects = new HashMap<Infrastructure, GeneralPath>();
		private HashMap<Switch, HashMap<String, Line2D>> renderedSwitches = new HashMap<Switch, HashMap<String, Line2D>>();
		private Graphics2D drawer;
		private Ellipse2D trainDrawing = null;
		private double distanceTraveledOnCurrentBlock = 0;
		private Train train;
		private GeneralPath yard;
		private JPanel infoPanel;
		private JComboBox<String> blocksComboBox;
        private HashMap<Infrastructure, Double[]> blockEndCoordinates = new HashMap<Infrastructure, Double[]>();
        private String launchTrain = null;
        private String routeTrain = null;
        private boolean trainMoving = false;
		
		TrackJPanel(TrackLayout tLayout, JPanel trackInfoPanel) {
			infoPanel = trackInfoPanel;
            // set a preferred size for the custom panel.
            setPreferredSize(new Dimension(2000,400));
            trackLayout = tLayout;
            final Timer timer = new Timer(100, new ActionListener() {
                @Override
                public void actionPerformed(final ActionEvent e) {
                	// It looks shitty as hell, but I can call call methods from here to update the track and it will
                	
                	// Update the trains and track
                	if ((trainMoving) &&(train != null) && (train.getRoute().size() > 0)){
                		// Update switches is handled by the draw function
                		
                		// Move the train along its route
                		List<Infrastructure> trainRoute= train.getRoute();
                		Infrastructure currentBlock = trainRoute.get(0);
                		// Calculate the distance the train will travel
                		double distance = train.getSpeed() / 10; // how far the train will travel in a millisecond
                		double currentBlockLength = currentBlock.getBlockLength();
                		if (trainRoute.size() == 1){
                			currentBlockLength = currentBlockLength/2;
                		}
            			// It is on the last block, only travel halfway.
                		double distanceLeftOnBlock = currentBlockLength - distanceTraveledOnCurrentBlock - distance;
                		if (distanceLeftOnBlock < 0){
                    		if (train.getRoute().size() == 1){
                        		distanceTraveledOnCurrentBlock += distance;
                        		trainMoving = false;
                    		}
                			// change which block the train is on
                    		else{
                    			trainRoute.remove(0);
                    			currentBlock = trainRoute.get(0);
                    			train.setCurrentBlock(currentBlock);
                    			// Update the distanceTraveledOnCurrentBlock
                    			distanceTraveledOnCurrentBlock = -1 * distanceLeftOnBlock;
                    		}
                		}
                		else {
                    		distanceTraveledOnCurrentBlock +=  distance; 
                		}
                	}
                	
                	
                	if (launchTrain != null){
                		launchNewTrain(launchTrain);
                		launchTrain = null;
                	}
                	else if (routeTrain != null){
                		reRouteTrain(routeTrain);
                		routeTrain = null;
                	}
                	
                	
                	// Repaint the track
                    repaint();
                }
            });
            timer.start();
            
            addMouseListener(new MouseAdapter(){
            	@Override
        		public void mouseClicked(MouseEvent e) {
        			// Of mouseClick is in the yard
        			double x = e.getX();
        			double y = e.getY();
        			// If it is in one of the blocks.
        			if (yard.contains(x, y)){
        				yardClicked();
        			}
        			else if((trainDrawing != null) && (trainDrawing.contains(x,y))){
        				trainClicked();
        			}
        			else{
        				java.util.Iterator<Entry<Infrastructure, GeneralPath>> shapeIterator = renderedObjects.entrySet().iterator();
        				for (Infrastructure key : renderedObjects.keySet()){
        					if (renderedObjects.get(key).contains(x,y)){
        						displayBlockInformation(key);
        						break;
        					}
        				}
        			}
        		}
            });
            
        }
		
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            
            repaint(g);
        }
        public void repaint(Graphics g) {
            super.paintComponent(g);
            
            TrackLayout tLayout = trackLayout;
            drawer = (Graphics2D) g;
            
            List<Infrastructure[]> trackIterator = tLayout.createIterator();
            blockEndCoordinates = new HashMap<Infrastructure, Double[]>();
            // Create a dashed line stroke
            // Draw the yard
            // If I use shape then I can use default methods like draw rectangle.
            // I think generalpath supports collision detection though so its probably fine.
            yard = drawYard(100, 100);
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
        	
        	if (train != null){
        		// the train is on the track
        		Infrastructure currentBlock = train.getCurrentBlock();
        		double radius = 3;
        		double blockLength = currentBlock.getBlockLength();
        		Double[] blockCoordinates = blockEndCoordinates.get(currentBlock);
        		double blockCenterX = blockCoordinates[0] - (blockLength/2);
        		double blockCenterY = blockCoordinates[1] + 5;
        		trainDrawing = new Ellipse2D.Double(blockCenterX - radius, blockCenterY - radius, 2.0 * radius, 2.0 * radius);
        		drawer.setPaint(Color.BLUE);
            	drawer.fill(trainDrawing);
            	drawer.setPaint(Color.BLACK);
        		
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

        public void yardClicked(){
        	infoPanel.removeAll();
			infoPanel.revalidate();
			infoPanel.repaint();
        	JLabel comboBoxLabel = new JLabel("Select Track Block");
    		GridBagConstraints gbc_ComboBoxLabel = new GridBagConstraints();
    		gbc_ComboBoxLabel.fill = GridBagConstraints.BOTH;
    		gbc_ComboBoxLabel.insets = new Insets(0, 0, 5, 5);
    		gbc_ComboBoxLabel.gridx = 0;
    		gbc_ComboBoxLabel.gridy = 0;
    		infoPanel.add(comboBoxLabel, gbc_ComboBoxLabel);
    		
    		blocksComboBox = new JComboBox<String>();
    		GridBagConstraints gbc_comboBox = new GridBagConstraints();
    		gbc_comboBox.fill = GridBagConstraints.BOTH;
    		gbc_comboBox.insets = new Insets(0, 0, 5, 0);
    		gbc_comboBox.gridx = 1;
    		gbc_comboBox.gridy = 0;
    		infoPanel.add(blocksComboBox, gbc_comboBox);
    		for (Infrastructure track : trackLayout.iterateSequentially()){
    			blocksComboBox.addItem("Block " + track.getBlockNumber());
    		}
    		
    		JButton createTrain = new JButton("Send Train");
    		GridBagConstraints gbc_CreateTrain = new GridBagConstraints();
    		gbc_CreateTrain.gridwidth = 2;
    		gbc_CreateTrain.fill = GridBagConstraints.BOTH;
    		gbc_CreateTrain.insets = new Insets(0, 0, 0, 5);
    		gbc_CreateTrain.gridx = 0;
    		gbc_CreateTrain.gridy = 1;
    		
    		createTrain.setPreferredSize(new Dimension(60,20));
    		createTrain.addMouseListener(new MouseAdapter() {
    			@Override
    			public void mouseClicked(MouseEvent e) {
    				String selectedBlock = String.valueOf(blocksComboBox.getSelectedItem());
    				selectedBlock = selectedBlock.substring(6);
    				launchTrain = selectedBlock;
    	        	infoPanel.removeAll();
    	    		infoPanel.revalidate();
    	    		infoPanel.repaint();
    			}
    		});

    		infoPanel.add(createTrain, gbc_CreateTrain);
    		infoPanel.revalidate();
        }
        
        private void trainClicked(){
        	infoPanel.removeAll();
			infoPanel.revalidate();
			infoPanel.repaint();
        	JLabel comboBoxLabel = new JLabel("Select Track Block");
    		GridBagConstraints gbc_ComboBoxLabel = new GridBagConstraints();
    		gbc_ComboBoxLabel.fill = GridBagConstraints.BOTH;
    		gbc_ComboBoxLabel.insets = new Insets(0, 0, 5, 5);
    		gbc_ComboBoxLabel.gridx = 0;
    		gbc_ComboBoxLabel.gridy = 0;
    		infoPanel.add(comboBoxLabel, gbc_ComboBoxLabel);
    		
    		blocksComboBox = new JComboBox<String>();
    		GridBagConstraints gbc_comboBox = new GridBagConstraints();
    		gbc_comboBox.fill = GridBagConstraints.BOTH;
    		gbc_comboBox.insets = new Insets(0, 0, 5, 0);
    		gbc_comboBox.gridx = 1;
    		gbc_comboBox.gridy = 0;
    		infoPanel.add(blocksComboBox, gbc_comboBox);
    		for (Infrastructure track : trackLayout.iterateSequentially()){
    			blocksComboBox.addItem("Block " + track.getBlockNumber());
    		}
    		
    		JButton createTrain = new JButton("Send Train");
    		GridBagConstraints gbc_CreateTrain = new GridBagConstraints();
    		gbc_CreateTrain.gridwidth = 2;
    		gbc_CreateTrain.fill = GridBagConstraints.BOTH;
    		gbc_CreateTrain.insets = new Insets(0, 0, 0, 5);
    		gbc_CreateTrain.gridx = 0;
    		gbc_CreateTrain.gridy = 1;
    		
    		createTrain.setPreferredSize(new Dimension(60,20));
    		createTrain.addMouseListener(new MouseAdapter() {
    			@Override
    			public void mouseClicked(MouseEvent e) {
    				String selectedBlock = String.valueOf(blocksComboBox.getSelectedItem());
    				selectedBlock = selectedBlock.substring(6);
    				routeTrain = selectedBlock;
    	        	infoPanel.removeAll();
    	    		infoPanel.revalidate();
    	    		infoPanel.repaint();
    			}
    		});

    		infoPanel.add(createTrain, gbc_CreateTrain);
    		infoPanel.revalidate();
        }
        
        private void displayBlockInformation(Infrastructure block){
	        infoPanel.removeAll();
			infoPanel.revalidate();
			infoPanel.repaint();
	    	JLabel tempLabel1 = new JLabel("");
			GridBagConstraints gbc_ComboBoxLabel = new GridBagConstraints();
			gbc_ComboBoxLabel.fill = GridBagConstraints.BOTH;
			gbc_ComboBoxLabel.insets = new Insets(0, 0, 5, 5);
			gbc_ComboBoxLabel.gridx = 0;
			gbc_ComboBoxLabel.gridy = 0;
			infoPanel.add(tempLabel1, gbc_ComboBoxLabel);
			
			JLabel tempLabel2 = new JLabel("");
			GridBagConstraints gbc_comboBox = new GridBagConstraints();
			gbc_comboBox.fill = GridBagConstraints.BOTH;
			gbc_comboBox.insets = new Insets(0, 0, 5, 0);
			gbc_comboBox.gridx = 1;
			gbc_comboBox.gridy = 0;
			infoPanel.add(tempLabel2, gbc_comboBox);
			
			JLabel displayBlockInfo = new JLabel(block.toString());
			GridBagConstraints gbc_CreateTrain = new GridBagConstraints();
			gbc_CreateTrain.gridwidth = 2;
			gbc_CreateTrain.fill = GridBagConstraints.BOTH;
			gbc_CreateTrain.insets = new Insets(0, 0, 0, 5);
			gbc_CreateTrain.gridx = 0;
			gbc_CreateTrain.gridy = 1;
    		infoPanel.add(displayBlockInfo, gbc_CreateTrain);
			
	
			infoPanel.revalidate();
			infoPanel.repaint();
        }
        
        private void launchNewTrain(String blockNum){
        	Infrastructure firstBlock = trackLayout.createIterator().get(0)[1];
        	Infrastructure endBlock = trackLayout.getBlockAt(Integer.parseInt(blockNum));
        	
        	train = new Train(firstBlock);
        	
        	List<Infrastructure> route = TrackLayout.generatePath(firstBlock, endBlock);
        	train.setRoute(route);
    		trainMoving = true;
        }
        private void reRouteTrain(String blockNum){
        	Infrastructure endBlock = trackLayout.getBlockAt(Integer.parseInt(blockNum));
        	
        	List<Infrastructure> route = TrackLayout.generatePath(train.getCurrentBlock(), endBlock);
        	train.setRoute(route);
    		trainMoving = true;
        	
        }
        
        
        
    }