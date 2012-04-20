package gui;

import imgUtil.ImgProcessingUtil;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

import javax.swing.JPanel;

import mathUtil.Coordinate2D;


import controller.IMyController;

public class SrcImgPanel extends JPanel implements MouseListener, KeyListener{
	
	private static final int UP_MARGIN = 0;

	private static final int LEFT_MARGIN = 0;
	
	private static final Color POINT_COLOR = Color.GREEN;
	
	private static final Color FORMER_COLOR = Color.RED;
	
	private static final Color LATTER_COLOR = Color.BLUE;

	private int width;
	
	private int height;
	
	
	private static final int NEEDLE_LENGTH = 80;
	
	private static final int GRID_GAP = 32;
	
	private IMyView mainView;
	
	private BufferedImage srcImg;
	
	private int patchWidth;
	
	private int patchHeight;
	
	
	
	
	private Coordinate2D formerCoord;
	
	private Coordinate2D latterCoord;
	
	private Coordinate2D centerCoord;
	
	
	private boolean leftPicked;
	
	private boolean rightPicked;
	
	private boolean centerPicked;
	
	private boolean gridSelected;
	
	
	
	private static final int FORMER = 1;
	
	private static final int LATTER = 2;
	
	private int active;
	
	
	private LinkedList<double[]> angles;
	
	private LinkedList<int[]> positions;
	
	private LinkedList<Color> needleColor;
	
	
	
	public SrcImgPanel(){
		
	}
	
	public SrcImgPanel(BufferedImage srcImg){
		this.srcImg = srcImg;
		
		if(srcImg != null){
		
			width = srcImg.getWidth();
		
			height = srcImg.getHeight();
		}
		
		
	
	}

	

	@Override
	public void mousePressed(MouseEvent e) {
		this.requestFocus();
		
		if(javax.swing.SwingUtilities.isLeftMouseButton(e)){
			
			formerCoord.setCoordinate(e.getX(), e.getY(), srcImg.getWidth());
			
			leftPicked = true;
			
			active = FORMER;
		}
		else if(javax.swing.SwingUtilities.isRightMouseButton(e)){
			
			latterCoord.setCoordinate(e.getX(), e.getY(), srcImg.getWidth());
			
			rightPicked = true;
			
			active = LATTER;
		}
		else if(javax.swing.SwingUtilities.isMiddleMouseButton(e)){
			
			centerCoord.setCoordinate(e.getX(), e.getY(), srcImg.getWidth());
			
			System.out.println("Center: " + centerCoord.toString());
			
			centerPicked = true;
			
			gridSelected = true;
		}
		
		this.repaint();
		
	}
	
	
	@Override
	public void keyPressed(KeyEvent e) {
		
		if(!leftPicked && !rightPicked && !centerPicked)
			return;
		
		
		if(e.getKeyChar() == 'f'){
			
			if(leftPicked){
				System.out.println("Former:");
				mainView.patchSelected(formerCoord);
				
			}if(rightPicked){
				System.out.println("Latter:");
				mainView.patchSelected(latterCoord);
				
			}
			
			
		}
		else if(e.getKeyChar() == 'r'){
		
			if(leftPicked && rightPicked){
				
				centerPicked = true;
				needleColor.add(Color.GREEN);
				
				int centerX = (formerCoord.getX() + latterCoord.getX())/2;
				int centerY = (formerCoord.getY() + latterCoord.getY())/2;
				
				centerCoord.setCoordinate(centerX, centerY, srcImg.getWidth());
				
				
				mainView.shapeEstimate(formerCoord, latterCoord);
				
				
			}
		}
		/*
		else if(e.getKeyChar() == 'n'){
			
			
			
			int[][] pos = {{-146, 422}, {-118, 382}, {-85, 338}, {-49, 289}, {-8, 235}, {40, 172}, {81, 113}, 
							 {-215, 415}, {-189, 373}, {-157, 334}, {-126, 282}, {-90, 225}, {-46, 162}, {-3, 95},
							 {-283, 388}, {-254, 340}, {-226, 291}, {-196, 240}, {-162, 180}, {-127, 119}, {-88, 51},
							 {-352, 354}, {-330, 311}, {-305, 266}, {-279, 213}, {-252, 159}, {-220, 95}, {-184, 25},
							 {-404, 307}, {-385, 263}, {-363,213}, {-330, 133}, {-305, 78}, {-287, 38}, {-258, -32}};
			
			double[][] angs = {{52, 78}, {41, 86}, {44, 71}, {48, 67}, {48, 72}, {41, 90}, {34, 67},
								  {45, 97}, {54, 98}, {78, 89}, {55, 87}, {25, 94}, {68, 95}, {73, 110},
								  {45, 107}, {54, 107}, {39, 115}, {33, 125}, {50, 118}, {43, 120}, {45, 115},
								  {57, 118}, {51, 129}, {37, 124}, {56, 121}, {55, 137}, {34, 135}, {36, 121},
								  {46, 139}, {53, 130}, {58, 129}, {49, 142}, {40, 134}, {58, 146}, {45, 140}};
			
			
			for(int i = 0; i < pos.length; i++){
				
				pos[i][0] = pos[i][0] + 512;
				pos[i][1] = 512 - pos[i][1];
				
				positions.add(pos[i]);
				angles.add(angs[i]);
				
			}
			this.repaint();
		}*/
		
		else if(e.getKeyChar() == 'd'){
			System.out.println("diagonal");
			if(centerPicked){

				
				
				
				int topLeftX = centerCoord.getX() - patchWidth/2;
				int topLeftY = centerCoord.getY() - patchHeight/2;
				
				int bottomRightX = centerCoord.getX() + patchWidth/2;
				int bottomRightY = centerCoord.getY() + patchHeight/2;
						
//				needleColor = Color.RED;
				needleColor.add(Color.GREEN);
				
				mainView.shapeEstimate(new Coordinate2D(topLeftX, topLeftY, srcImg.getWidth()), new Coordinate2D(bottomRightX, bottomRightY, srcImg.getWidth()));
				
				
				int topRightX = centerCoord.getX() + patchWidth/2;
				int topRightY = centerCoord.getY() + patchHeight/2;
				
				int bottomLeftX = centerCoord.getX() - patchWidth/2;
				int bottomLeftY = centerCoord.getY() - patchHeight/2;
						
				needleColor.add(Color.GREEN);
				
				mainView.shapeEstimate(new Coordinate2D(topRightX, topRightY, srcImg.getWidth()), new Coordinate2D(bottomLeftX, bottomLeftY, srcImg.getWidth()));
				
				
			}
			
			
		}
		else if(e.getKeyChar() == 'c'){
			System.out.println("cross");
			if(centerPicked){

				
				
				
				int leftX = centerCoord.getX() - patchWidth/2;
				int leftY = centerCoord.getY();
				
				int rightX = centerCoord.getX() + patchWidth/2;
				int rightY = centerCoord.getY();
						
//				needleColor = Color.RED;
				needleColor.add(Color.GREEN);
				
				mainView.shapeEstimate(new Coordinate2D(leftX, leftY, srcImg.getWidth()), new Coordinate2D(rightX, rightY, srcImg.getWidth()));
				
				
				int topX = centerCoord.getX();
				int topY = centerCoord.getY() + patchHeight/2;
				
				int bottomX = centerCoord.getX();
				int bottomY = centerCoord.getY() - patchHeight/2;
						
				needleColor.add(Color.GREEN);
				
				mainView.shapeEstimate(new Coordinate2D(topX, topY, srcImg.getWidth()), new Coordinate2D(bottomX, bottomY, srcImg.getWidth()));
				
				
			}
			
			
		}
		else if(e.getKeyChar() == 'z'){
			
			System.out.println("Zhankai");
			if(centerPicked){

				
				
				
				double[] avgAngle = estimateGrid(GRID_GAP);
				
				
				System.out.println(avgAngle[0]);
				System.out.println(avgAngle[1]);
			}
			
		}
		
		else if(e.getKeyChar() == '1'){
			needleColor.add(Color.RED);
			attatchNormalNeedle(62, 349);
			
		}
		else if(e.getKeyChar() == '2'){
			needleColor.add(Color.RED);
			attatchNormalNeedle(70, 90);
			
		}
		else if(e.getKeyChar() == '3'){
			needleColor.add(Color.RED);
			attatchNormalNeedle(35.5, 210.6);
			
		}
		else if(e.getKeyChar() == 'p'){
//			System.out.println("preprocessing");
			
			
			this.srcImg = ImgProcessingUtil.bandpassFiltering(srcImg, 0.1, 0.45);
			
			this.repaint();
		}
		else{
		
			Coordinate2D activeCoord = null;
			
			if(active == FORMER)
				activeCoord = formerCoord;
			else if(active == LATTER)
				activeCoord = latterCoord;
			
			
			moveCoordinate(activeCoord, e.getKeyCode(), 1);
		
			
			this.repaint();
		}
		
	}
	
	
	private double[] estimateGrid(int gridGap){
		
		int centerX = centerCoord.getX();
		int centerY = centerCoord.getY();
		
		double slantAll = 0;
		double tiltAll = 0;
		
		LinkedList<double[]> resultList = new LinkedList<double[]>();
		
		System.out.println("#1");
		resultList.add(estimateSingle(centerX, centerY)); //#1
		System.out.println("#2");
		resultList.add(estimateSingle(centerX - gridGap, centerY)); //#2
		System.out.println("#3");
		resultList.add(estimateSingle(centerX + gridGap, centerY)); //#3
		System.out.println("#4");
		resultList.add(estimateSingle(centerX, centerY - gridGap)); //#4
		System.out.println("#5");
		resultList.add(estimateSingle(centerX, centerY + gridGap)); //#5
		System.out.println("#6");
		resultList.add(estimateSingle(centerX - gridGap, centerY - gridGap)); //#6
		System.out.println("#7");
		resultList.add(estimateSingle(centerX + gridGap, centerY - gridGap)); //#7
		System.out.println("#8");
		resultList.add(estimateSingle(centerX - gridGap, centerY + gridGap)); //#8
		System.out.println("#9");
		resultList.add(estimateSingle(centerX + gridGap, centerY + gridGap)); //#9
		
		for(int i = 0; i < resultList.size(); i++){
			double[] result = resultList.get(i);
			
			slantAll += result[0];
			tiltAll += result[1];
			
			
		}
		
		double[] avgAngle = {slantAll/resultList.size(), tiltAll/resultList.size()};
		
		return avgAngle;
		
	}
	
	
	private double[] estimateSingle(int centerX, int centerY){
		

		//Diagonal
		int topLeftX = centerX - patchWidth/2;
		int topLeftY = centerY - patchHeight/2;
		
		int bottomRightX = centerX + patchWidth/2;
		int bottomRightY = centerY + patchHeight/2;
				

//		needleColor.add(Color.GREEN);
		
		mainView.shapeEstimate(new Coordinate2D(topLeftX, topLeftY, srcImg.getWidth()), new Coordinate2D(bottomRightX, bottomRightY, srcImg.getWidth()));
		
		
		int topRightX = centerX + patchWidth/2;
		int topRightY = centerY + patchHeight/2;
		
		int bottomLeftX = centerX - patchWidth/2;
		int bottomLeftY = centerY - patchHeight/2;
				
//		needleColor.add(Color.GREEN);
		
		mainView.shapeEstimate(new Coordinate2D(topRightX, topRightY, srcImg.getWidth()), new Coordinate2D(bottomLeftX, bottomLeftY, srcImg.getWidth()));
		
		
		
		//Cross
		int leftX = centerX - patchWidth/2;
		int leftY = centerY;
		
		int rightX = centerX + patchWidth/2;
		int rightY = centerY;
				

//		needleColor.add(Color.GREEN);
		
		mainView.shapeEstimate(new Coordinate2D(leftX, leftY, srcImg.getWidth()), new Coordinate2D(rightX, rightY, srcImg.getWidth()));
		
		
		int topX = centerX;
		int topY = centerY + patchHeight/2;
		
		int bottomX = centerX;
		int bottomY = centerY - patchHeight/2;
				
//		needleColor.add(Color.GREEN);
		
		mainView.shapeEstimate(new Coordinate2D(topX, topY, srcImg.getWidth()), new Coordinate2D(bottomX, bottomY, srcImg.getWidth()));
		
		double slantAll = 0;
		double tiltAll = 0;
		
		for(int i = 0; i < angles.size(); i++){
			
			double[] angle = angles.get(i);
			
			slantAll += angle[0];
			tiltAll += angle[1];
			
		}
		double[] avgAngle = {slantAll/angles.size(), tiltAll/angles.size()};
		
		angles.clear();
		
		return avgAngle;
	}
	
	
	public void attatchNormalNeedle(double slant, double tilt){
		
		double[] estimatedAngles = {slant, tilt}; 
		
		angles.add(estimatedAngles);
		
		int[] position = {centerCoord.getX(), centerCoord.getY()};
		
		positions.add(position);
		
		this.repaint();
	}
	
	
	
	
	protected void paintComponent(Graphics g) {
		if(srcImg != null)
			g.drawImage(srcImg, 0, 0, this);
        
        
        drawBoundary(g);
        
        drawNeedle(g);
        
//        drawGrid(g);
	}
	
	
	
	private void drawGrid(Graphics g){
		
		if(gridSelected){
			
			
			g.setColor(POINT_COLOR);
        	
        	g.drawRect(centerCoord.getX() - 1, centerCoord.getY() - 1, 2, 2);
        	
        	g.drawRect(centerCoord.getX() - GRID_GAP - 1, centerCoord.getY() - 1, 2, 2);
        	
        	g.drawRect(centerCoord.getX() + GRID_GAP - 1, centerCoord.getY() - 1, 2, 2);
        	
        	g.drawRect(centerCoord.getX() - 1, centerCoord.getY() - GRID_GAP - 1, 2, 2);
        	
        	g.drawRect(centerCoord.getX() - 1, centerCoord.getY() + GRID_GAP - 1, 2, 2);
        	
        	g.drawRect(centerCoord.getX() - GRID_GAP - 1, centerCoord.getY() - GRID_GAP - 1, 2, 2);
        	
        	g.drawRect(centerCoord.getX() + GRID_GAP - 1, centerCoord.getY() - GRID_GAP - 1, 2, 2);
        	
        	g.drawRect(centerCoord.getX() - GRID_GAP - 1, centerCoord.getY() + GRID_GAP - 1, 2, 2);
        	
        	g.drawRect(centerCoord.getX() + GRID_GAP - 1, centerCoord.getY() + GRID_GAP - 1, 2, 2);
			
		}
		
	}
	
	
	

	private void drawNeedle(Graphics g){
		
		if(!centerPicked)
			return;
		((Graphics2D)g).setStroke(new BasicStroke(3.0f));
		
		for(int i = 0; i < angles.size(); i++){
			
			int[] position = positions.get(i); 
		
			double[] angle = angles.get(i);
			
			double slant = Math.toRadians(angle[0]);
			
			double tilt = Math.toRadians(angle[1]);
			
			
			int x1 = position[0];
			
			int y1 = position[1];
			
			
			
			
			int x2 = (int) Math.round(x1 + NEEDLE_LENGTH * Math.sin(slant) * Math.cos(tilt));
			
			int y2 = (int) Math.round(y1 - NEEDLE_LENGTH * Math.sin(slant) * Math.sin(tilt));
			
			g.setColor(Color.GREEN);
			
			g.drawLine(x1, y1, x2, y2);
			
			g.setColor(Color.RED);
			
			g.drawLine(x1, y1, x1, y1);
			
		}
		System.out.println("Center" + centerCoord.toString());
		
	}
	
	private void drawBoundary(Graphics g){
		
		if(leftPicked){
        	
        	System.out.println("Former" + formerCoord.toString());
        	
        	g.setColor(POINT_COLOR);
        	
        	g.drawRect(formerCoord.getX() - 1, formerCoord.getY() - 1, 2, 2);

        	g.setColor(FORMER_COLOR);
        	
        	g.drawRect(formerCoord.getX() - (patchWidth - 1) / 2, formerCoord.getY() - (patchHeight - 1) / 2, patchWidth, patchHeight);
        	
        	
        	
//        	g.drawLine(formerCoord.getX(), formerCoord.getY(), formerCoord.getX(), formerCoord.getY());
        	
        }
       if(rightPicked){

       	System.out.println("Latter" + latterCoord.toString());
       	
       	g.setColor(POINT_COLOR);
       	
       	g.drawRect(latterCoord.getX() - 1, latterCoord.getY() - 1, 2, 2);
       	
       	g.setColor(LATTER_COLOR);
       	
       	g.drawRect(latterCoord.getX() - (patchWidth - 1) / 2, latterCoord.getY() - (patchHeight - 1) / 2, patchWidth, patchHeight);
       	
    	   
        }
		
	}
	
	
	private void moveCoordinate(Coordinate2D activeCoord, int direction, int step){
		
		
		
		if(direction == KeyEvent.VK_UP){
			
			activeCoord.moveUp(step);
			
		}
		else if(direction == KeyEvent.VK_DOWN){
			
			activeCoord.moveDown(step);
			
		}
		else if(direction == KeyEvent.VK_LEFT){
			
			activeCoord.moveLeft(step);
			
		}
		else if(direction == KeyEvent.VK_RIGHT){
			
			activeCoord.moveRight(step);
			
		}
		
	}
	
	

	public void init() {
		// TODO Auto-generated method stub
		
		this.setLayout(null);
		
		this.setBounds(LEFT_MARGIN, UP_MARGIN, width, height);
		
		this.setFocusable(true);
		
		
				
		this.addMouseListener(this);
		
		this.addKeyListener(this);
		
		
		formerCoord = new Coordinate2D();
		latterCoord = new Coordinate2D();
		centerCoord = new Coordinate2D();
		
		angles = new LinkedList<double[]>();
		
		positions = new LinkedList<int[]>();
		
		needleColor = new LinkedList<Color>();
		
		patchWidth = mainView.getPatchWidth();
		
		patchHeight = mainView.getPatchHeight();
		
	}

	
	public void setMainView(IMyView myView){
		
		this.mainView = myView;
		
	}
	

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	

}
