package model;

import java.awt.image.BufferedImage;

import mathUtil.Coordinate2D;
import sft.ShapeEstimation;
import controller.IMyController;

public class SFTModel implements ISFTModel{

	
	private double viewAngleD;
	
	private int dimension;
	
	
	private IMyController controller;
	
	private ShapeEstimation shapeEstimation;
	
	
	
	public SFTModel(double viewAngleD, int dimension){
	
		this.viewAngleD = viewAngleD;
		
		this.dimension = dimension;
	}
	
	
	
	public void setController(IMyController controller) {

		this.controller = controller;
		
	}
	

	
	public void init() {
		
		this.shapeEstimation = new ShapeEstimation(viewAngleD, dimension);
		
	}
	
	
	
	public double[] doShapeEstimation(IFilterBankModel filterBankModel, BufferedImage srcImg, Coordinate2D pointA, Coordinate2D pointB){
		
		filterBankModel.doFilteringConcurrent(srcImg, pointA);
		
		double[] responseA = filterBankModel.getCurrentResultConcurrent();
		
		filterBankModel.doFilteringConcurrent(srcImg, pointB);
		
		double[] responseB = filterBankModel.getCurrentResultConcurrent();
		
		
		double[] anglesEstimated = shapeEstimation.estimateShape(responseA, responseB, pointA.centerCoord(), pointB.centerCoord());  
		
		System.out.println("slant = " + anglesEstimated[0]);
		
		System.out.println("tilt = " + anglesEstimated[1]);
		
		return anglesEstimated;
	}
	
	public double[] doShapeEstimationEnergy(IFilterBankModel filterBankModel, BufferedImage srcImg, Coordinate2D pointA, Coordinate2D pointB){
		
		filterBankModel.doFilteringConcurrentEnergy(srcImg, pointA);
		
		double[] responseA = filterBankModel.getCurrentResultConcurrentEnergy();
		
		filterBankModel.doFilteringConcurrentEnergy(srcImg, pointB);
		
		double[] responseB = filterBankModel.getCurrentResultConcurrentEnergy();
		
		
		double[] anglesEstimated = shapeEstimation.estimateShapeEnergy(responseA, responseB, pointA.centerCoord(), pointB.centerCoord());  
		
		System.out.println("slant = " + anglesEstimated[0]);
		
		System.out.println("tilt = " + anglesEstimated[1]);
		
		return anglesEstimated;
	}
	
	
	public void doSlantTiltMapGeneration(IFilterBankModel filterBankModel, Coordinate2D startCoord, int rangeX, int rangeY){
		
	}

}
