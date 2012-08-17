package sft;

import imgUtil.Spectrum;

public class ResponseDiff_FFT {
	
	
	public static double computeResponseDiff(DistortionMatrix Phi, Spectrum spectrumA, Spectrum spectrumB){
		
		if(spectrumA == null || spectrumB == null || spectrumA.getDimX() != spectrumB.getDimX() || spectrumA.getDimY() != spectrumB.getDimY())
			throw new IllegalArgumentException();
		
		int width = spectrumA.getDimX();
		int height = spectrumA.getDimY();
		
		double phi_11 = Phi.get(1, 1);
		double phi_12 = Phi.get(1, 2);
		double phi_21 = Phi.get(2, 1);
		double phi_22 = Phi.get(2, 2);
		
		
		double totalDiff = 0.0;
		
		for(int u = 0; u < width; u++){
			for(int v = 0; v < height; v++){
				
				int u_shift = (int) Math.round(phi_11 * u + phi_12 * v);
				int v_shift = (int) Math.round(phi_21 * u + phi_22 * v);
				
				double responseB = spectrumB.getPointData(u, v).getAmplitude() ;
				double responseA = spectrumA.getPointData(u_shift, v_shift).getAmplitude();
				
				if(responseB != 0)
					totalDiff += Math.abs(responseB - responseA) / responseB;
				else if(responseA != 0)
					totalDiff += Math.abs(responseB - responseA) / responseA;
				
			}
		}
		
		
		return totalDiff;
		
	}

}
