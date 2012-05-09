package model;

import java.awt.image.BufferedImage;

import mathUtil.Coordinate2D;
import controller.IMyController;

public interface ISFTModel {
	
	public void setController(IMyController controller);
	
	public void init();
	
	public double[] doShapeEstimation(IFilterBankModel filterBankModel, BufferedImage srcImg, Coordinate2D coordA, Coordinate2D coordB);
	
	public double[] doShapeEstimationEnergy(IFilterBankModel filterBankModel, BufferedImage srcImg, Coordinate2D coordA, Coordinate2D coordB);

}
