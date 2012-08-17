package sft;

import static java.lang.Math.toRadians;
import imgUtil.Spectrum;
import mathUtil.Coordinate2D;
import model.Orientation;

public class ShapeEstimation_FFT {

	private double viewAngleD;
	
	private int dimension;
	
	private static final double SLANT_MAX = 90.0;
	
	private static final double SLANT_MIN = 0.0;
	
	private static final double TILT_MAX = 360.0;
	
	private static final double TILT_MIN = 0.0;
	
	
	
	public ShapeEstimation_FFT(double viewAngleD, int dimension){
		this.viewAngleD = viewAngleD;
		this.dimension = dimension;
		
	}
	
	public Orientation estimateShape(Spectrum spectrumA, Spectrum spectrumB, Coordinate2D pointA, Coordinate2D pointB){
		
		
		double slantEstimatedD = 0.0;
		double tiltEstimatedD = 0.0;
		
		
		double slantGapD = 0.1;
		double tiltGapD = 0.1;
		
		
		double minDiff = Double.MAX_VALUE;
		
		
		double slantD = 0.0;
		double tiltD = 0.0;
		
		
		
		
		
		for(slantD = 0.0; slantD < SLANT_MAX; slantD += slantGapD){
			
			for(tiltD = 0.0; tiltD < TILT_MAX; tiltD += tiltGapD){
				
				
				DistortionMatrix phi = new DistortionMatrix(viewAngleD, dimension, toRadians(slantD), toRadians(tiltD), pointA, pointB);
				
				
				double responseDiff = ResponseDiff_FFT.computeResponseDiff(phi, spectrumA, spectrumB);
				
				if(responseDiff < minDiff){
					
					slantEstimatedD = slantD;
					tiltEstimatedD = tiltD;
					
					minDiff = responseDiff;
					
					
				}
				
			}
		}
		
		if(tiltEstimatedD > 180.0)
			tiltEstimatedD -= 360.0;
		
		return Orientation.orientationInDegree(slantEstimatedD, tiltEstimatedD);
	}	
}
