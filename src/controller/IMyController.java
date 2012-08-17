package controller;

import java.awt.image.BufferedImage;


import mathUtil.Coordinate2D;
import model.IFilterBankModel;
import model.ISFTModel;
import model.Orientation;
import model.SrcImage;
import gui.IMyView;

public interface IMyController {
	
	
	public void setView(IMyView view);
	
	public void setModel(IFilterBankModel model);
	
	public void setModel(ISFTModel model);
	
	public void init();
	
	public void start();
	
	public void updateView();
	
	public void updateSrcImage(SrcImage srcImg);
	
	
	public void updateModel();
	
//	public void appendNormalNeedle(Orientation orientationEstimated);
	
	
	public SrcImage getSrcImage();
	
	
	public int getPatchWidth();
	
	public int getPatchHeight();
	
	public int getSrcImgWidth();
	
	public int getSrcImgHeight();
	
	public void triggerFiltering(Coordinate2D centerCoord);
	
	public Orientation triggerShapeEstimation(Coordinate2D coordA, Coordinate2D coordB);
	
	public Orientation triggerShapeEstimation_FFT(Coordinate2D coordA, Coordinate2D coordB);
	
//	public Orientation triggerShapeEstimationEnergy(Coordinate2D coordA, Coordinate2D coordB);
}
