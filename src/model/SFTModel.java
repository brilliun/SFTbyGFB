package model;

import imgUtil.ImgCommonUtil;
import imgUtil.Spectrum;

import java.awt.image.BufferedImage;

import mathUtil.Coordinate2D;
import sft.ShapeEstimation;
import sft.ShapeEstimation_FFT;
import controller.IMyController;

public class SFTModel implements ISFTModel{

	
	private double viewAngleD;
	
	private int dimension;
	
	private static int patchWidth = 64;
	
	private static int patchHeight = 64;
	
	
	private IMyController controller;
	
	private ShapeEstimation shapeEstimation;
	
	private ShapeEstimation_FFT shapeEstimation_FFT;
	
	
	
	public SFTModel(double viewAngleD, int dimension){
	
		this.viewAngleD = viewAngleD;
		
		this.dimension = dimension;
	}
	
	
	
	public void setController(IMyController controller) {

		this.controller = controller;
		
	}
	
	
	public int getPatchWidth(){
		return patchWidth;
	}
	
	public int getPatchHeight(){
		return patchHeight;
	}

	
	public void init() {
		
		this.shapeEstimation = new ShapeEstimation(viewAngleD, dimension);
		this.shapeEstimation_FFT = new ShapeEstimation_FFT(viewAngleD, dimension);
	}
	
	
	
	public Orientation doShapeEstimation(IFilterBankModel filterBankModel, Spectrum srcImg, Coordinate2D pointA, Coordinate2D pointB){
		
		filterBankModel.doFilteringConcurrent(srcImg, pointA);
		
		double[] responseA = filterBankModel.getFilterResultConcurrent();
		
		filterBankModel.doFilteringConcurrent(srcImg, pointB);
		
		double[] responseB = filterBankModel.getFilterResultConcurrent();
		
//		System.out.println("start searching");
		
		Orientation estimatedOrientation = shapeEstimation.coarseToFineEstimate(responseA, responseB, pointA.shiftedCoord(), pointB.shiftedCoord());  
		
		
		
		
		
		System.out.println(estimatedOrientation.toString());
		
		
		return estimatedOrientation;
	}
	
	
	public Orientation doShapeEstimationFFT(SrcImage srcImg, Coordinate2D pointA, Coordinate2D pointB){
		
		BufferedImage subImageA = ImgCommonUtil.getSubImage(pointA.getX() - patchWidth/2, pointA.getY() - patchHeight/2, patchWidth, patchHeight, srcImg.getBufferedImg());
		
		BufferedImage subImageB = ImgCommonUtil.getSubImage(pointB.getX() - patchWidth/2, pointB.getY() - patchHeight/2, patchWidth, patchHeight, srcImg.getBufferedImg());
		
		
		Spectrum spectrumA = ImgCommonUtil.FFT2D(subImageA, false);
		
		Spectrum spectrumB = ImgCommonUtil.FFT2D(subImageB, false);
		
		
		System.out.println("Shape Estimation FFT start");
		
		Orientation estimatedOrientation = shapeEstimation_FFT.coarseToFineEstimate(spectrumA, spectrumB, pointA.shiftedCoord(), pointB.shiftedCoord());
		
		
		System.out.println(estimatedOrientation.toString());
		
		return estimatedOrientation;
	}
	
//	public Orientation doShapeEstimationEnergy(IFilterBankModel filterBankModel, Spectrum srcImg, Coordinate2D pointA, Coordinate2D pointB){
//		
//		filterBankModel.doFilteringConcurrentEnergy(srcImg, pointA);
//		
//		double[] responseA = filterBankModel.getFilterResultConcurrentEnergy();
//		
//		filterBankModel.doFilteringConcurrentEnergy(srcImg, pointB);
//		
//		double[] responseB = filterBankModel.getFilterResultConcurrentEnergy();
//		
//		
//		Orientation estimatedOrientation = shapeEstimation.estimateShapeEnergy(responseA, responseB, pointA.shiftedCoord(), pointB.shiftedCoord());  
//		
//		
//		System.out.println(estimatedOrientation.toString());
//		
//		
//		return estimatedOrientation;
//	}
	
	
	public void doSlantTiltMapGeneration(IFilterBankModel filterBankModel, Coordinate2D startCoord, int rangeX, int rangeY){
		
	}

}
