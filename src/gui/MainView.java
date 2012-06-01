package gui;

import java.awt.Dimension;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import mathUtil.Coordinate2D;
import model.Orientation;
import model.SrcImage;


import controller.IMyController;

public class MainView implements IMyView {

	
	// Layout Constants
	
	public static final int FRAME_WIDTH = 1200;

	public static final int FRAME_HEIGHT = 1200;

	private static Dimension frameSize = new Dimension(FRAME_WIDTH,
			FRAME_HEIGHT);

	
//	private static final int CHART_LEFT = 100;
//
//	private static final int CHART_WIDTH = 600;
//
//	private static final int CHART_HEIGHT = 600;

	
	
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

		
		
		SrcImage srcImg = controller.getSrcImage();
		
		
		
		srcImgPanel = new SrcImgPanel();
		
		srcImgPanel.setMainView(this);
		
		srcImgPanel.init();
		
		frame.add(srcImgPanel);
		
		srcImgPanel.requestFocus();
		
	}
	
	
	/*
	public void patchSelected(Coordinate2D coord){
		
		controller.triggerFiltering(coord);
		
	}
	
	*/
	
	public void updateSrcImg(SrcImage srcImg){
		controller.updateSrcImage(srcImg);
		
	}
	
	
	public int getPatchWidth(){
		return controller.getPatchWidth();
	}
	
	public int getPatchHeight(){
		return controller.getPatchHeight();
	}
	
	
	public int getSrcImgWidth(){
		return controller.getSrcImgWidth();
	}
	
	public int getSrcImgHeight(){
		return controller.getSrcImgHeight();
	}
	
	public SrcImage getSrcImg(){
		return controller.getSrcImage();
	}
	
	

	public void setController(IMyController controller) {

		this.controller = controller;

	}

	public void start() {
		
		frame.setVisible(true);
	}



	
	public Orientation shapeEstimate(Coordinate2D coordA, Coordinate2D coordB) {
		
		return controller.triggerShapeEstimation(coordA, coordB);
		
	}
	
	public Orientation shapeEstimateEnergy(Coordinate2D coordA, Coordinate2D coordB) {
		
		return controller.triggerShapeEstimationEnergy(coordA, coordB);
		
	}
	
	
}
