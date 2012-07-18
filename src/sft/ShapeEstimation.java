package sft;

import imgUtil.ImgCommonUtil;
import mathUtil.Coordinate2D;
import model.Orientation;
import static java.lang.Math.*;

public class ShapeEstimation {
	
	
	private double viewAngleD;
	
	private int dimension;
	
	private static final double SLANT_MAX = 90.0;
	
	private static final double SLANT_MIN = 0.0;
	
	private static final double TILT_MAX = 360.0;
	
	private static final double TILT_MIN = 0.0;
	
	
	
	public ShapeEstimation(double viewAngleD, int dimension){
		this.viewAngleD = viewAngleD;
		this.dimension = dimension;
		
	}
	
	
	
	public Orientation coarseToFineEstimate(double[] responseA, double[] responseB, Coordinate2D pointA, Coordinate2D pointB){
		
		double[] slantGap = {5.0, 1.0, 0.1};
		
		double[] tiltGap = {10.0, 2.0, 0.5};
		
		int iterationRounds = 3;
		
		
		double slantStart = SLANT_MIN;
		double slantEnd = SLANT_MAX;
		
		double tiltStart = TILT_MIN;
		double tiltEnd = TILT_MAX;
		
		Orientation tempResult = null;
		
		for(int iteration = 0; iteration < iterationRounds; iteration++){
			
			tempResult = findMinDiff(slantStart, slantEnd, slantGap[iteration], tiltStart, tiltEnd, tiltGap[iteration], responseA, responseB, pointA, pointB);
			
			slantStart = tempResult.getSlantD() - slantGap[iteration];
			slantEnd = tempResult.getSlantD() + slantGap[iteration];
			
			tiltStart = tempResult.getTiltD() - tiltGap[iteration];
			tiltEnd = tempResult.getTiltD() + tiltGap[iteration];
			
			slantStart = slantStart < SLANT_MIN ? SLANT_MIN: slantStart;
			slantEnd = slantEnd > SLANT_MAX ? SLANT_MAX : slantEnd;
			
			tiltStart = tiltStart < TILT_MIN ? TILT_MIN : tiltStart;
			tiltEnd = tiltEnd > TILT_MAX ? TILT_MAX : tiltEnd;
		}
		
		
		if(tempResult.getTiltD() >= 180.0)
			tempResult = Orientation.orientationInDegree(tempResult.getSlantD(), tempResult.getTiltD()-360.0);
			
		
		return tempResult;
		
		
		
	}
	
	
	
	
	
	private Orientation findMinDiff(double slantStart, double slantEnd, double slantGap, double tiltStart, double tiltEnd, double tiltGap, double[] responseA, double[] responseB, Coordinate2D pointA, Coordinate2D pointB){
		
		
		double minDiff = Double.MAX_VALUE;
		
		double slantEstimated = 0.0;
		double tiltEstimated = 0.0;
		
		for(double slant = slantStart; slant < slantEnd; slant += slantGap){
			
			for(double tilt = tiltStart; tilt < tiltEnd; tilt += tiltGap){
				
				
				DistortionMatrix phi = new DistortionMatrix(viewAngleD, dimension, toRadians(slant), toRadians(tilt), pointA, pointB);
				
				
				double responseDiff = ResponseDiff.computeResponseDiff(phi, responseA, responseB);
				
				if(responseDiff < minDiff){
					
					slantEstimated = slant;
					tiltEstimated = tilt;
					
					minDiff = responseDiff;
					
					
				}
				
			}
			
			
		}
		
		return Orientation.orientationInDegree(slantEstimated, tiltEstimated);
	}
	
	
	
	public Orientation estimateShape(double[] responseA, double[] responseB, Coordinate2D pointA, Coordinate2D pointB){
		
		
		double slantEstimatedD = 0.0;
		double tiltEstimatedD = 0.0;
		
		
		double slantGapD = 0.1;
		double tiltGapD = 0.1;
		
		
		double minDiff = Double.MAX_VALUE;
		
		
		double slantD = 0.0;
		double tiltD = 0.0;
		
		
		
		
		
		for(slantD = 0.0; slantD < 90.0; slantD += slantGapD){
			
			for(tiltD = 0.0; tiltD < 360.0; tiltD += tiltGapD){
				
				
				DistortionMatrix phi = new DistortionMatrix(viewAngleD, dimension, toRadians(slantD), toRadians(tiltD), pointA, pointB);
				
				
				double responseDiff = ResponseDiff.computeResponseDiff(phi, responseA, responseB);
				
				if(responseDiff < minDiff){
					
					slantEstimatedD = slantD;
					tiltEstimatedD = tiltD;
					
					minDiff = responseDiff;
					
					
				}
				
			}
		}
		
		if(tiltEstimatedD > 180.0)
			tiltEstimatedD -= 360.0;
		
//		double slantTruthD = 30;
//		double tiltTruthD = 250;
//		
//		
//		DistortionMatrix phi = new DistortionMatrix(viewAngleD, dimension, toRadians(slantTruthD), toRadians(tiltTruthD), pointA, pointB);
//		
//		
//		double responseDiffTruth = ResponseDiff.computeResponseDiff(phi, responseA, responseB);
		
//		System.out.println("EstimatedDiff=" + minDiff);
//		System.out.println("TruthDiff=" + responseDiffTruth);
		
		return Orientation.orientationInDegree(slantEstimatedD, tiltEstimatedD);
		
		
		
		
	}
}
