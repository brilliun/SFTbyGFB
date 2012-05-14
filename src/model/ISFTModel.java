package model;

import imgUtil.Spectrum;

import java.awt.image.BufferedImage;

import mathUtil.Coordinate2D;
import controller.IMyController;

public interface ISFTModel {
	
	public void setController(IMyController controller);
	
	public void init();
	
	public int getPatchWidth();
		
	public int getPatchHeight();
					
	public Orientation doShapeEstimation(IFilterBankModel filterBankModel, Spectrum srcImg, Coordinate2D coordA, Coordinate2D coordB);
	
	public Orientation doShapeEstimationEnergy(IFilterBankModel filterBankModel, Spectrum srcImg, Coordinate2D coordA, Coordinate2D coordB);

}
