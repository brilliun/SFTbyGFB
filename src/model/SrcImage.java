package model;

import imgUtil.ImgCommonUtil;
import imgUtil.Spectrum;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import controller.IMyController;

public class SrcImage {
	
	
	private BufferedImage srcImg;
	
	private Spectrum srcImgSpectrum;

	
	
	
	public SrcImage(){
		
	}
	
	public SrcImage(BufferedImage srcImg){
		
		this.srcImg = srcImg;
		
		this.srcImgSpectrum = new Spectrum(srcImg.getWidth(), srcImg.getHeight(), ImgCommonUtil.readGrayscaleImageData(srcImg));
	}
	
	
	public SrcImage(Spectrum srcImgSpectrum){
		
		this.srcImgSpectrum = srcImgSpectrum;
		
		this.srcImg = ImgCommonUtil.writeToImage(srcImgSpectrum.getDimX(), srcImgSpectrum.getDimY(), srcImgSpectrum.getAmplitudeSpectrum());
		
	}
	
	public SrcImage(String fileName){
		
		loadImg(fileName);
	}
	
	
	public void loadImg(String fileName){
		
		
		
		try {
			srcImg = ImageIO.read(new File(fileName));
			System.out.println("Load Image [band = " + srcImg.getData().getNumBands() + "]");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		
		if(srcImg == null)
			System.out.println("Source Image is NULL!!!");
		
		
		this.srcImgSpectrum = new Spectrum(srcImg.getWidth(), srcImg.getHeight(), ImgCommonUtil.readGrayscaleImageData(srcImg));
		
		
	}
	
	
	
	public BufferedImage getSrcImg(){
		return srcImg;
	}

	public Spectrum getSpectrum(){
		return this.srcImgSpectrum;
	}
	
	
	public int getWidth(){
		return this.srcImg.getWidth();
	}
	
	
	public int getHeight(){
		return this.srcImg.getHeight();
	}
}
