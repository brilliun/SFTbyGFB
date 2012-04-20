package imgUtil;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import javax.imageio.ImageIO;

public class ImgProcessingUtil {
	
	
	
	public static BufferedImage highpassFiltering(BufferedImage inputImg, double cutoffFreq){
		
		
		
		LinkedList<double[][]> spectrumData = ImgCommonUtil.FFT2D(inputImg, false); 
		
		Spectrum spectrum = new Spectrum(spectrumData.getFirst(), spectrumData.getLast());
		
		int width = spectrum.getWidth();
		int height = spectrum.getHeight();
		
		double[][] re = spectrum.getReal();
		double[][] im = spectrum.getImaginary();
		
		
		for(int x = 0; x < width; x++){
			for(int y = 0; y < height; y++){
				
				int u = x < width/2 ? x: x - width;
				int v = y < height/2 ? -y : height - y;
				
				int freqDistPow2 = u * u + v * v; 
				
				double cutoffDistPow2 = Math.pow(cutoffFreq * width, 2);
				
				double filterFunc = freqDistPow2 == 0 ? 0 : 1 / (1 + cutoffDistPow2 / freqDistPow2);
				
				re[x][y] = re[x][y] * filterFunc;
				im[x][y] = im[x][y] * filterFunc;
				
			}
		}
		
		
		LinkedList<double[][]> filteredSpectrumData = ImgCommonUtil.FFT2D(width, height, true, re, im);
		
		BufferedImage resultImg = ImgCommonUtil.writeToImage(width, height, filteredSpectrumData.getFirst());
		
		
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
		
		
		return resultImg;
		
		
	}

	
public static BufferedImage bandpassFiltering(BufferedImage inputImg, double lowFreq, double highFreq){
		
		
		
		LinkedList<double[][]> spectrumData = ImgCommonUtil.FFT2D(inputImg, false); 
		
		Spectrum spectrum = new Spectrum(spectrumData.getFirst(), spectrumData.getLast());
		
		int width = spectrum.getWidth();
		int height = spectrum.getHeight();
		
		double[][] re = spectrum.getReal();
		double[][] im = spectrum.getImaginary();
		
		
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
				
				re[x][y] = re[x][y] * filterFunc;
				im[x][y] = im[x][y] * filterFunc;
				
			}
		}
		
		
		LinkedList<double[][]> filteredSpectrumData = ImgCommonUtil.FFT2D(width, height, true, re, im);
		
		BufferedImage resultImg = ImgCommonUtil.writeToImage(width, height, filteredSpectrumData.getFirst());
		
		
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
		
		
		return resultImg;
		
		
	}
}
