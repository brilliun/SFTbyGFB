package sft;

import static java.lang.Math.PI;
import static java.lang.Math.ceil;
import static java.lang.Math.floor;

public class InterpolationUtil {
	
	

	public static double linearInterpolation(double thetaA, int orientations, double[] responses){
		
		double dTheta = PI / orientations;

		int n0 = (int) (floor(thetaA / dTheta) % orientations);
		
		int n1 = (int) (ceil(thetaA / dTheta) % orientations);
		
		double theta0 = n0 * dTheta;
		
		double response0 = responses[n0];
		
		double response1 = responses[n1];
		
		
		double response_interpolation = response0 + (thetaA - theta0) * (response1 - response0) / dTheta;
		
		return response_interpolation;
		
		
	}
	
	
	
	public static double gaussInterpolation(double thetaA, int orientations, double[] responses){
		
		double dTheta = PI / orientations;
		
		int n_left = (int) Math.floor(thetaA / dTheta);
		int n_right = (int) Math.ceil(thetaA / dTheta);
		
		n_right = n_left == n_right ? (n_left+1) : n_right;
		
		double leftDist = Math.abs(n_left * dTheta - thetaA) / dTheta;
		double rightDist = Math.abs(n_right * dTheta - thetaA) / dTheta;

		int n3 = mod(n_left, orientations);
		int n4 = mod(n_right, orientations);
       int n2 = mod(n3 - 1, orientations);
       int n1 = mod(n2 - 1, orientations);
       int n5 = mod(n4 + 1, orientations);
       int n6 = mod(n5 + 1, orientations);
       
       	
       double[] samples = {responses[n1], responses[n2], responses[n3], responses[n4], responses[n5], responses[n6]};
       
       
       return gaussFiltering(leftDist, rightDist, samples);
	}
	

	
	private static double gaussFiltering(double leftDist, double rightDist, double[] samples){
		
		
		double basis3 = gaussInterFunc(-leftDist) * samples[2];
		
		double basis4 = gaussInterFunc(rightDist) * samples[3];


		double basis2 = gaussInterFunc(-leftDist - 1) * samples[1];

		double basis5 = gaussInterFunc(rightDist + 1) * samples[4];


		double basis1 = gaussInterFunc(-leftDist - 2) * samples[0];

		double basis6 = gaussInterFunc(rightDist + 2) * samples[5];
		
		
		return basis1 + basis2 +basis3 + basis4 + basis5 + basis6;
		
	}
	
	
	private static double gaussInterFunc(double x){
		
		double gamma = 0.4638;
		
		double g0 = 1/Math.sqrt(2*Math.PI*2*gamma) * Math.exp(-x*x/(4*gamma));

		double g2 = 1/(gamma*gamma) * (x*x - gamma) * 1/Math.sqrt(2*Math.PI*gamma) * Math.exp(-x*x/(2*gamma));

		return g0 - gamma * g2;
	}
	
	
	private static int mod(int x, int y){
		
		return (int) (x - y * Math.floor((double)x/y));
	}
	
	
}
