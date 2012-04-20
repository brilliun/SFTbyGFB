package controller;

import java.awt.image.BufferedImage;


import gui.IMyView;
import mathUtil.Coordinate2D;
import model.ISFTModel;
import model.SrcImage;
import model.IFilterBankModel;

public class MainController implements IMyController {

	
	private IMyView mainView;
	
	private IFilterBankModel filterBankModel;
	
	private ISFTModel sftModel;
	
	
	private SrcImage filterImage; 
	
	
	
	public void triggerFiltering(Coordinate2D centerCoord){
		
		filterBankModel.doFiltering(filterImage.getSrcImg(), centerCoord);
		
		filterBankModel.printCurrentResult();
		
	}
	
	

	
	public void triggerShapeEstimation(Coordinate2D coordA, Coordinate2D coordB) {
		
		
		double[] anglesEstimated = sftModel.doShapeEstimation(filterBankModel, filterImage.getSrcImg(), coordA, coordB);
		
		appendNormalNeedle(anglesEstimated[0], anglesEstimated[1]);
	}
	
	
	public void triggerSlantTiltMap(Coordinate2D startCoord, int rangeX, int rangeY){
		
		
		
	}
	
	
	public void appendNormalNeedle(double slant, double tilt){
		
		mainView.addNormalNeedle(slant, tilt);
		
	}
	
	
	public void setView(IMyView view) {
		
		this.mainView = view;
		
	}

	
	public void setModel(IFilterBankModel model) {
		
		this.filterBankModel = model;
		
	}

	public void setModel(ISFTModel model){
		this.sftModel = model;
	}


	
	public void init() {
	
		initModel();
		
		initView();
		
		
		
	}
	
	
	private void initModel(){
		
		filterImage = new SrcImage("test/11_s25t160.bmp");
		
		
		filterBankModel.init();
		
		sftModel.init();
	}

	
	private void initView(){
		
		
		mainView.init();
	}
	
	
	public void start() {
		mainView.start();
		
	}
	
	
	
	
	public void updateView() {
		// TODO Auto-generated method stub
		
	}

	
	public void updateModel() {
		// TODO Auto-generated method stub
		
	}

	
	public BufferedImage getSrcImage() {
		// TODO Auto-generated method stub
		
		return filterImage.getSrcImg();
	}

	
	public int getPatchWidth() {
		// TODO Auto-generated method stub
		return filterBankModel.getPatchWidth();
	}

	
	public int getPatchHeight() {
		// TODO Auto-generated method stub
		return filterBankModel.getPatchHeight();
	}





	
}
