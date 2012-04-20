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
	
	

	@Override
	public void triggerShapeEstimation(Coordinate2D coordA, Coordinate2D coordB) {
		
		
		double[] anglesEstimated = sftModel.doShapeEstimation(filterBankModel, filterImage.getSrcImg(), coordA, coordB);
		
		appendNormalNeedle(anglesEstimated[0], anglesEstimated[1]);
	}
	
	
	public void triggerSlantTiltMap(Coordinate2D startCoord, int rangeX, int rangeY){
		
		
		
	}
	
	
	public void appendNormalNeedle(double slant, double tilt){
		
		mainView.addNormalNeedle(slant, tilt);
		
	}
	
	@Override
	public void setView(IMyView view) {
		
		this.mainView = view;
		
	}

	@Override
	public void setModel(IFilterBankModel model) {
		
		this.filterBankModel = model;
		
	}

	public void setModel(ISFTModel model){
		this.sftModel = model;
	}


	@Override
	public void init() {
	
		initModel();
		
		initView();
		
		
		
	}
	
	
	private void initModel(){
		
		filterImage = new SrcImage("final/11_s25t160.bmp");
		
		
		filterBankModel.init();
		
		sftModel.init();
	}

	
	private void initView(){
		
		
		mainView.init();
	}
	
	@Override
	public void start() {
		mainView.start();
		
	}
	
	
	
	@Override
	public void updateView() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateModel() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public BufferedImage getSrcImage() {
		// TODO Auto-generated method stub
		
		return filterImage.getSrcImg();
	}

	@Override
	public int getPatchWidth() {
		// TODO Auto-generated method stub
		return filterBankModel.getPatchWidth();
	}

	@Override
	public int getPatchHeight() {
		// TODO Auto-generated method stub
		return filterBankModel.getPatchHeight();
	}





	
}
