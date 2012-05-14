package model;

import imgUtil.Spectrum;

import java.awt.image.BufferedImage;

import mathUtil.Coordinate2D;

import controller.IMyController;

public interface IFilterBankModel {
	
	
	public void setController(IMyController controller);
	
	
	public void init();
	
	
	public int doFiltering(Spectrum srcImg, Coordinate2D coord);
	
	public int doFilteringConcurrent(Spectrum srcImg, Coordinate2D coord);
	
	public int doFilteringConcurrentEnergy(Spectrum srcImg, Coordinate2D coord);
	
	public void printCurrentResult();
	
	public double[] getCurrentResult();
	
	public double[] getFilterResultConcurrent();
	
	public double[] getFilterResultConcurrentEnergy();
}
