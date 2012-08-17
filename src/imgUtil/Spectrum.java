package imgUtil;

import mathUtil.Complex;

public class Spectrum {
	
	private Complex[][] data;
	
//	private double[][] amplitudeSpectrum;
	
	
	private int dimX;
	
	private int dimY;
	
	
	
	public Spectrum(int dimX, int dimY){
		
		this.dimX = dimX;
		this.dimY = dimY;
		
		data = new Complex[dimX][dimY];
	}
	
	
	
	public Spectrum(int dimX, int dimY, double[][] real){
		
		if(real != null){
			
			if(dimX < 1 || real.length != dimX)
				throw new IllegalArgumentException();
			
				
			this.dimX= dimX;
				
			for(int x = 0; x < dimX; x++){
					
				if(dimY < 1 || real[x].length != dimY)
					throw new IllegalArgumentException();
					
					
			}
				
			this.dimY = dimY;
				
			
			
			this.data = new Complex[dimX][dimY];
			
			for(int x = 0; x < dimX; x++){
				for(int y = 0; y < dimY; y++){
					
					this.data[x][y] = new Complex(real[x][y], 0.0);
										
				}
				
			}
			
		}
		
	}
	
	
	
	public Spectrum(int dimX, int dimY, double[][] real, double[][] imaginary){
		
		if(real != null && imaginary != null){
			
			if(dimX < 1 || real.length != dimX || imaginary.length != dimX)
				throw new IllegalArgumentException();
			
				
			this.dimX= dimX;
				
			for(int x = 0; x < dimX; x++){
					
				if(dimY < 1 || real[x].length != dimY || imaginary[x].length != dimY)
					throw new IllegalArgumentException();
					
					
			}
				
			this.dimY = dimY;
				
			
			
			this.data = new Complex[dimX][dimY];
			
			for(int x = 0; x < dimX; x++){
				for(int y = 0; y < dimY; y++){
					
					this.data[x][y] = new Complex(real[x][y], imaginary[x][y]);
										
				}
				
			}
			
			
			
			
		}
		
		
		
		
		
		
	}
	
	
	
	
	
	
	
	
	public Spectrum(int dimX, int dimY, Complex[][] complex){
		
		
		
		if(complex != null){
			
			if(dimX < 1 || complex.length != dimX)
				throw new IllegalArgumentException();
			
				
			this.dimX= dimX;
				
			for(int x = 0; x < dimX; x++){
					
				if(dimY < 1 || complex[x].length != dimY)
					throw new IllegalArgumentException();
					
					
			}
				
			this.dimY = dimY;
				
			
			
			this.data = complex;
			
		}
		
	}
	
	public Complex getPointData(int x, int y){
		
		if(x < 0 || y < 0 || x >= dimX || y >= dimY)
			return new Complex();
				
		if(data[x][y] == null)
			return data[x][y] = new Complex();
		
		
		return data[x][y];
	}

	public void setPointData(int x, int y, Complex pointData){
		
		this.data[x][y] = pointData;
	}
	
	public double[][] getAmplitudeSpectrum(){
		
		
		double[][] amplitudeSpectrum = new double[dimX][dimY];
		
		for(int x = 0; x < dimX; x++){
			for(int y = 0; y < dimY; y++){
				
				amplitudeSpectrum[x][y] = this.getPointData(x, y).getAmplitude();
				
				
				
			}
		}
		
		
		
		
		return amplitudeSpectrum;
	}

	public double getAmplitudeSum(){
		
		double sum = 0;
		
		for(int x = 0; x < dimX; x++){
			for(int y = 0; y < dimY; y++){
				
				sum += this.getPointData(x, y).getAmplitude();
				
				
			}
		}
		
		return sum;
	}
	
	
	public int getDimX(){
		return dimX;
	}
	
	public int getDimY(){
		return dimY;
	}
	
	
	
}
