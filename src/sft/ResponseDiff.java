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
			
			
			double responseInter = linearInterpolation(thetaA, orientations, responseA);
			
			
			double responseB_recovered = phi.determinate() / pow(lambda, 2) * responseInter;
			
			
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
			
			
			double responseInter = linearInterpolation(thetaA, orientations, responseA);
			
			
			double responseB_recovered = pow(phi.determinate(), 2) / pow(lambda, 2) * responseInter;
			
			
			totalDiff += abs(responseB_recovered - responseB[n]) / responseB[n];
		}
		
		return totalDiff;
		
		
	}
	
	
	private static double linearInterpolation(double thetaA, int orientations, double[] responses){
		
		double dTheta = PI / orientations;

		int n0 = (int) (floor(thetaA / dTheta) % orientations);
		
		int n1 = (int) (ceil(thetaA / dTheta) % orientations);
		
		double theta0 = n0 * dTheta;
		
		double response0 = responses[n0];
		
		double response1 = responses[n1];
		
		
		double response_interpolation = response0 + (thetaA - theta0) * (response1 - response0) / dTheta;
		
		return response_interpolation;
		
		
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
