package model;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import controller.IMyController;

public class SrcImage {
	
	
	private BufferedImage srcImg;

	
	
	public SrcImage(){
		
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
		
		
		
	}
	
	
	
	public BufferedImage getSrcImg(){
		return srcImg;
	}

	
	
}
