package model;

import java.awt.image.BufferedImage;

import mathUtil.Coordinate2D;

import controller.IMyController;

public interface IFilterBankModel {
	
	
	public void setController(IMyController controller);
	
	
	public void init();
	
	public int getPatchWidth();
	
	public int getPatchHeight();
	
	public int doFiltering(BufferedImage srcImg, Coordinate2D coord);
	
	public int doFilteringConcurrent(BufferedImage srcImg, Coordinate2D coord);
	
	public int doFilteringConcurrentEnergy(BufferedImage srcImg, Coordinate2D coord);
	
	public void printCurrentResult();
	
	public double[] getCurrentResult();
	
	public double[] getCurrentResultConcurrent();
	
	public double[] getCurrentResultConcurrentEnergy();
}
