package controller;

import java.awt.image.BufferedImage;


import mathUtil.Coordinate2D;
import model.IFilterBankModel;
import model.ISFTModel;
import model.SrcImage;
import gui.IMyView;

public interface IMyController {
	
	
	public void setView(IMyView view);
	
	public void setModel(IFilterBankModel model);
	
	public void setModel(ISFTModel model);
	
	public void init();
	
	public void start();
	
	public void updateView();
	
	
	public void updateModel();
	
	public void appendNormalNeedle(double slant, double tilt);
	
	
	public SrcImage getSrcImage();
	
	
	public int getPatchWidth();
	
	public int getPatchHeight();
	
	public void triggerFiltering(Coordinate2D centerCoord);
	
	public void triggerShapeEstimation(Coordinate2D coordA, Coordinate2D coordB);
	
	public void triggerShapeEstimationEnergy(Coordinate2D coordA, Coordinate2D coordB);
}
