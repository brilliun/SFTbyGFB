package model;

public class Orientation {
	
	private double slantD;
	
	private double tiltD;
	
	private double slantR;
	
	private double tiltR;
	
	

	public static Orientation orientationInDegree(double slantD, double tiltD){
		
		Orientation orient = new Orientation();
		
		orient.slantD = slantD;
		orient.tiltD = tiltD;
		
		orient.slantR = degreeToRadian(slantD);
		orient.tiltR = degreeToRadian(tiltD);
		
		return orient;
		
	}
	
	public void setInRadian(double slantR, double tiltR){
		this.slantR = slantR;
		this.tiltR = tiltR;
	}
	
	public double getSlantD(){
		return slantD;
	}
	
	public double getTiltD(){
		return tiltD;
	}
	
	public double getSlantR(){
		return slantR;
	}
	
	public double getTiltR(){
		return tiltR;
	}

	
	private static double degreeToRadian(double degree){
		
		return degree * Math.PI / 180.0;
	}
	
	private static double radianToDegree(double radian){
		
		return radian * 180 / Math.PI;
	}
	
	public String toString(){
		
		return "slant = " + slantD + ", tilt = " + tiltD;
	}
}
