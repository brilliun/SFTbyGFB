package sft;

import static java.lang.Math.*;

public class ResponseDiff {
	
	
	
	public static double computeResponseDiff(DistortionMatrix phi, double[] responseA, double[] responseB){
		
		if(responseA == null || responseB == null || responseA.length != responseB.length)
			throw new IllegalArgumentException();
		
		
		int orientations = responseA.length; 
		
		
		double dTheta = PI / orientations;
		
		double totalDiff = 0.0;
		
//		double[] responseB_recovered = new double[orientations];
		
		for(int n = 0; n < orientations; n++){
			
			double thetaB = n * dTheta;
			
			double thetaA = thetaRotated(phi, thetaB);
			
			double lambda = lambdaScaling(phi, thetaB);
			
			if(thetaA < 0)
				thetaA += PI;
			
			
			double responseInter = InterpolationUtil.gaussInterpolation(thetaA, orientations, responseA);
			
			double coef = phi.determinate();
//			System.out.println(coef);
			
			double responseB_recovered = coef * responseInter;
			
			
			totalDiff += abs(responseB_recovered - responseB[n]) / responseB[n];
		}
		
		return totalDiff;
		
		
	}
	
	public static double computeResponseDiffEnergy(DistortionMatrix phi, double[] responseA, double[] responseB){
		
		if(responseA == null || responseB == null || responseA.length != responseB.length)
			throw new IllegalArgumentException();
		
		
		int orientations = responseA.length; 
		
		
		double dTheta = PI / orientations;
		
		double totalDiff = 0.0;
		
//		double[] responseB_recovered = new double[orientations];
		
		for(int n = 0; n < orientations; n++){
			
			double thetaB = n * dTheta;
			
			double thetaA = thetaRotated(phi, thetaB);
			
			double lambda = lambdaScaling(phi, thetaB);
			
			if(thetaA < 0)
				thetaA += PI;
			
			
			double responseInter = InterpolationUtil.linearInterpolation(thetaA, orientations, responseA);
			
			
			double responseB_recovered = phi.determinate() * responseInter;
			
			
			totalDiff += abs(responseB_recovered - responseB[n]) / responseB[n];
		}
		
		return totalDiff;
		
		
	}
	
	
	
	
	private static double thetaRotated(DistortionMatrix phi, double thetaB){
		
		double thetaA = atan((phi.get(2, 1) * cos(thetaB) + phi.get(2, 2) * sin(thetaB))/
				(phi.get(1, 1) * cos(thetaB) + phi.get(1, 2) * sin(thetaB)));
		
		return thetaA;
	}

	
	private static double lambdaScaling(DistortionMatrix phi, double thetaB){
		
		double lambda = sqrt(pow((phi.get(1,1) * cos(thetaB) + phi.get(1, 2) * sin(thetaB)), 2) + 
				pow((phi.get(2, 1) * cos(thetaB) + phi.get(2,2) * sin(thetaB)), 2));
		
		return lambda;
	}
}
