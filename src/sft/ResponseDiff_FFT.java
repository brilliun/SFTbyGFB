package sft;

import imgUtil.Spectrum;

public class ResponseDiff_FFT {
	
	
	public static double computeResponseDiff(DistortionMatrix Phi, Spectrum spectrumA, Spectrum spectrumB){
		
		if(spectrumA == null || spectrumB == null || spectrumA.getDimX() != spectrumB.getDimX() || spectrumA.getDimY() != spectrumB.getDimY())
			throw new IllegalArgumentException();
		
		int width = spectrumA.getDimX();
		int height = spectrumA.getDimY();
		
		int halfWidth = width/2;
		int halfHeight = height/2;
		
		double gap = 1.0 / width;
		double halfGap = 1.0 / (2 * width);
		
		double phi_11 = Phi.get(1, 1);
		double phi_12 = Phi.get(1, 2);
		double phi_21 = Phi.get(2, 1);
		double phi_22 = Phi.get(2, 2);
		
		
		double totalDiff = 0.0;
		
		for(int m = 0; m < width; m++){
			for(int n = 0; n < height; n++){
				
				
				double u = (m+1 - halfWidth)/width - halfGap;
				double v = -((n+1 - halfHeight)/height - halfGap);
				
				double u_shift = phi_11 * u + phi_12 * v;
				double v_shift = phi_21 * u + phi_22 * v;
				
				int posU, posV;
				
				if(u_shift > 0){
					
					posU = (int) Math.floor(u_shift / gap) + halfWidth;
					
				}
				else{
					posU = (int)Math.ceil(u_shift / gap) + halfWidth - 1;
					
				}
				
				if(v_shift > 0){
					
					posV = halfHeight - (int) Math.floor(v_shift / gap) - 1;
					
				}
				else{
					posV = halfHeight - (int) Math.floor(v_shift / gap);
					
				}
				
				
				
				
				
				double responseB = spectrumB.getPointData(m, n).getAmplitude() ;
				
				if(posU < 0 || posV< 0 || posU>= width || posV>= height){
					totalDiff += responseB;
					continue;
				}
				double responseA = Phi.determinate() * spectrumA.getPointData(posU, posV).getAmplitude();
				
				totalDiff += Math.abs(responseB - responseA);
				
			}
		}
		
		
		return totalDiff;
		
	}

}
