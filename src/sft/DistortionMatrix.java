package sft;

import mathUtil.Coordinate2D;

import static java.lang.Math.*;

public class DistortionMatrix {
	
	private double elements[][];
	
	private double focal;
	
	private double slant;
	
	private double tilt;
	
	private Coordinate2D pointA;
	
	private Coordinate2D pointB;
	
	
	
	
	public DistortionMatrix(double viewAngleD, int dimension, double slant, double tilt, Coordinate2D pointA, Coordinate2D pointB){
		
		this.slant = slant;
		this.tilt = tilt;
		this.pointA = pointA;
		this.pointB = pointB;
		
		
		this.focal = computeFocal(viewAngleD, dimension);
		
		constructDistortionMatrix();
		
	}

	
	
	private void constructDistortionMatrix(){
		
		elements = new double[2][2];
		
		int x1 = pointA.getX();
		int y1 = pointA.getY();
		
		int x2 = pointB.getX();
		int y2 = pointB.getY();
		
		int dx = x2 - x1;
		int dy = y2 - y1;
		
		
		double omegaA = focal * cos(slant) + (x1 * cos(tilt) + y1 * sin(tilt)) * sin(slant);
		
		elements[0][0] = (omegaA + dx * sin(slant) * cos(tilt)) / omegaA;
		
		elements[0][1] = (dy * sin(slant) * cos(tilt)) / omegaA;
		
		elements[1][0] = (dx * sin(slant) * sin(tilt)) / omegaA;
		
		elements[1][1] = (omegaA + dy * sin(slant) * sin(tilt)) / omegaA;
		
		
//		System.out.println(elements[0][0] + ", " + elements[0][1]);
//		
//		System.out.println(elements[1][0] + ", " + elements[1][1]);
		
	}
	
	
	public double determinate(){
		
		return (elements[0][0] * elements[1][1] - elements[0][1] * elements[1][0]);
		
	}
	
	
	private static double computeFocal(double viewAngleD, int dimension){
		
		double halfAngle = toRadians(viewAngleD / 2.0);
		
		double focal = -dimension / (2.0 * tan(halfAngle));
		
//		focal = -1836;
		return focal;
		
	}
	
	
	public double get(int row, int col){
		
		return elements[row-1][col-1];
	}
	
	
	
}
