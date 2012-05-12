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
	
	
	private SrcImage srcImg; 
	
	
	
	public void triggerFiltering(Coordinate2D centerCoord){
		
		filterBankModel.doFiltering(srcImg.getSpectrum(), centerCoord);
		
		filterBankModel.printCurrentResult();
		
	}
	
	

	
	public void triggerShapeEstimation(Coordinate2D coordA, Coordinate2D coordB) {
		
		
		double[] anglesEstimated = sftModel.doShapeEstimation(filterBankModel, srcImg.getSpectrum(), coordA, coordB);
		
		appendNormalNeedle(anglesEstimated[0], anglesEstimated[1]);
	}
	
	public void triggerShapeEstimationEnergy(Coordinate2D coordA, Coordinate2D coordB) {
		
		
		double[] anglesEstimated = sftModel.doShapeEstimationEnergy(filterBankModel, srcImg.getSpectrum(), coordA, coordB);
		
		appendNormalNeedle(anglesEstimated[0], anglesEstimated[1]);
	}
	
	
	public void triggerSlantTiltMap(Coordinate2D startCoord, int rangeX, int rangeY){
		
		
		
	}
	
	
	public void appendNormalNeedle(double slant, double tilt){
		
		mainView.addNormalNeedle(slant, tilt);
		
	}
	
	
	
	public void updateSrcImage(SrcImage srcImg){
		this.srcImg = srcImg;
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
		
		this.srcImg = new SrcImage("test/5_s30t250.bmp");
		
		
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

	
	public SrcImage getSrcImage() {
		// TODO Auto-generated method stub
		
		return srcImg;
	}

	
	public int getSrcImgWidth(){
		return srcImg.getWidth();
	}
	
	public int getSrcImgHeight(){
		return srcImg.getHeight();
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
