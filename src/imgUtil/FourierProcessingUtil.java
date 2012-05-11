package imgUtil;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import javax.imageio.ImageIO;

import mathUtil.Complex;

public class FourierProcessingUtil {
	
	
	
	public static Spectrum highpassFiltering(BufferedImage inputImg, double cutoffFreq){
		
		
		
		Spectrum spectrum = ImgCommonUtil.FFT2D(inputImg, false); 
		
		
		int width = spectrum.getDimX();
		int height = spectrum.getDimY();
		
		Spectrum filteredSpectrum = new Spectrum(width, height);
		
		for(int x = 0; x < width; x++){
			for(int y = 0; y < height; y++){
				
				int u = x < width/2 ? x: x - width;
				int v = y < height/2 ? -y : height - y;
				
				double freqDistPow2 = u * u + v * v; 
				
				double cutoffDistPow2 = Math.pow(cutoffFreq * width, 2);
				
				double filterFunc = freqDistPow2 < cutoffDistPow2 ? 0 : 1;
				
				filteredSpectrum.setPointData(x, y, spectrum.getPointData(x, y).mul(filterFunc));
				
				
			}
		}
		
		
		Spectrum filteredSpectrumData = ImgCommonUtil.FFT2D(width, height, true, filteredSpectrum);
		
		return filteredSpectrumData;
		
		
//		
//		
//		try {
//			System.out.println("filtered!");
//			ImageIO.write(resultImg, "bmp", new File("output.bmp"));
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
		
		
		
		
	}

	
public static Spectrum bandpassFiltering(BufferedImage inputImg, double lowFreq, double highFreq){
		
		
		
		Spectrum spectrum = ImgCommonUtil.FFT2D(inputImg, false); 
		
		
		int width = spectrum.getDimX();
		int height = spectrum.getDimY();
		
		Spectrum filteredSpectrum = new Spectrum(width, height);
		
		for(int x = 0; x < width; x++){
			for(int y = 0; y < height; y++){
				
				int u = x < width/2 ? x: x - width;
				int v = y < height/2 ? -y : height - y;
				
				int freqDistPow2 = u * u + v * v; 
				
				
				
				
				
				double lowDistPow2 = Math.pow(lowFreq * width, 2);
				
				double highDistPow2 = Math.pow(highFreq * width, 2);
				
				
				double filterFunc = 0;
				
				if(freqDistPow2 > lowDistPow2 && freqDistPow2 < highDistPow2)
					filterFunc = 1;
				
				filteredSpectrum.setPointData(x, y, spectrum.getPointData(x, y).mul(filterFunc));
				
			}
		}
		
		
		Spectrum filteredSpectrumData = ImgCommonUtil.FFT2D(width, height, true, filteredSpectrum);
		
		return filteredSpectrumData;
		
		
//		
//		
//		try {
//			System.out.println("filtered!");
//			ImageIO.write(resultImg, "bmp", new File("output.bmp"));
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
		
		
		
		
	}
}
