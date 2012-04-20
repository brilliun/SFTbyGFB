package gui;

import java.awt.Dimension;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import mathUtil.Coordinate2D;


import controller.IMyController;

public class MainView implements IMyView {

	
	// Layout Constants
	
	private static final int FRAME_WIDTH = 1800;

	private static final int FRAME_HEIGHT = 1100;

	private static Dimension frameSize = new Dimension(FRAME_WIDTH,
			FRAME_HEIGHT);

	
	private static final int CHART_LEFT = 100;

	private static final int CHART_WIDTH = 600;

	private static final int CHART_HEIGHT = 600;

	
	
	// GUI Components
	
	private JFrame frame;
	
	private SrcImgPanel srcImgPanel;
	
	
	
	
	
	
	
	
	

	private IMyController controller;

	public void init() {

		// Initialize GUI components

		frame = new JFrame("Filter Bank Monitor");

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.setSize(frameSize);

		frame.setLocationRelativeTo(null);

		
		
		BufferedImage srcImg = controller.getSrcImage();
		
		srcImgPanel = new SrcImgPanel(srcImg);
		
		srcImgPanel.setMainView(this);
		
		srcImgPanel.init();
		
		frame.add(srcImgPanel);
		
		srcImgPanel.requestFocus();
		
	}
	
	
	
	public void patchSelected(Coordinate2D coord){
		
		controller.triggerFiltering(coord);
		
	}
	
	
	
	
	public int getPatchWidth(){
		return controller.getPatchWidth();
	}
	
	public int getPatchHeight(){
		return controller.getPatchHeight();
	}
	
	
	

	public void setController(IMyController controller) {

		this.controller = controller;

	}

	public void start() {
		
		frame.setVisible(true);
	}



	
	public void shapeEstimate(Coordinate2D coordA, Coordinate2D coordB) {
		
		controller.triggerShapeEstimation(coordA, coordB);
		
	}
	
	public void addNormalNeedle(double slant, double tilt){
		srcImgPanel.attatchNormalNeedle(slant, tilt);
	}
	
}
