package model;

import imgUtil.Spectrum;

import java.awt.image.BufferedImage;

import mathUtil.Coordinate2D;
import controller.IMyController;

public interface ISFTModel {
	
	public void setController(IMyController controller);
	
	public void init();
	
	public double[] doShapeEstimation(IFilterBankModel filterBankModel, Spectrum srcImg, Coordinate2D coordA, Coordinate2D coordB);
	
	public double[] doShapeEstimationEnergy(IFilterBankModel filterBankModel, Spectrum srcImg, Coordinate2D coordA, Coordinate2D coordB);

}
