package sft;

import mathUtil.Coordinate2D;
import static java.lang.Math.*;

public class ShapeEstimation {
	
	
	private double viewAngleD;
	
	private int dimension;
	
	
	public ShapeEstimation(double viewAngleD, int dimension){
		this.viewAngleD = viewAngleD;
		this.dimension = dimension;
	}
	
	
	public double[] estimateShape(double[] responseA, double[] responseB, Coordinate2D pointA, Coordinate2D pointB){
		
		
		double slantEstimatedD = 0.0;
		double tiltEstimatedD = 0.0;
		
		
		double slantGapD = 1.0;
		double tiltGapD = 1.0;
		
		
		double minDiff = 0.0;
		
		
		double slantD = 0.0;
		double tiltD = 0.0;
		
		DistortionMatrix phi = new DistortionMatrix(viewAngleD, dimension, toRadians(slantD), toRadians(tiltD), pointA, pointB);
		
		
		double responseDiff = ResponseDiff.computeResponseDiff(phi, responseA, responseB);
		
			
//		slantEstimatedD = slantD;
//		tiltEstimatedD = tiltD;
			
		minDiff = responseDiff;
			
			
		
		
		
		
		for(slantD = slantGapD; slantD < 90.0; slantD += slantGapD)
			
			for(tiltD = tiltGapD; tiltD < 360.0; tiltD += tiltGapD){
				
				
				phi = new DistortionMatrix(viewAngleD, dimension, toRadians(slantD), toRadians(tiltD), pointA, pointB);
				
				
				responseDiff = ResponseDiff.computeResponseDiff(phi, responseA, responseB);
				
				if(responseDiff < minDiff){
					
					slantEstimatedD = slantD;
					tiltEstimatedD = tiltD;
					
					minDiff = responseDiff;
					
					
				}
				
			}
			
			
		double[] resultAngles = {slantEstimatedD, tiltEstimatedD};
		
		return resultAngles;
		
		
	}

	public double[] estimateShapeEnergy(double[] responseA, double[] responseB, Coordinate2D pointA, Coordinate2D pointB){
		
		
		double slantEstimatedD = 0.0;
		double tiltEstimatedD = 0.0;
		
		
		double slantGapD = 1.0;
		double tiltGapD = 1.0;
		
		
		double minDiff = 0.0;
		
		
		double slantD = 0.0;
		double tiltD = 0.0;
		
		DistortionMatrix phi = new DistortionMatrix(viewAngleD, dimension, toRadians(slantD), toRadians(tiltD), pointA, pointB);
		
		
		double responseDiff = ResponseDiff.computeResponseDiffEnergy(phi, responseA, responseB);
		
			
//		slantEstimatedD = slantD;
//		tiltEstimatedD = tiltD;
			
		minDiff = responseDiff;
			
			
		
		
		
		
		for(slantD = slantGapD; slantD < 90.0; slantD += slantGapD)
			
			for(tiltD = tiltGapD; tiltD < 360.0; tiltD += tiltGapD){
				
				
				phi = new DistortionMatrix(viewAngleD, dimension, toRadians(slantD), toRadians(tiltD), pointA, pointB);
				
				
				responseDiff = ResponseDiff.computeResponseDiffEnergy(phi, responseA, responseB);
				
				if(responseDiff < minDiff){
					
					slantEstimatedD = slantD;
					tiltEstimatedD = tiltD;
					
					minDiff = responseDiff;
					
					
				}
				
			}
			
			
		double[] resultAngles = {slantEstimatedD, tiltEstimatedD};
		
		return resultAngles;
		
		
	}

}
