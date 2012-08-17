package imgUtil;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

public class TextureGenerator {
	
	
	
	public static void main(String[] args){
		
		
		int textureSize = 512;
		int density = 32;
		double variance = 0;
		
		
		
		TextureGenerator textureGenerator = new TextureGenerator();
		
		
		TexturePattern pattern = textureGenerator.new TexturePattern(TexturePattern.SQUARE, 8, 250);
		
		
		BufferedImage textureImg = generateTexture(textureSize, density, variance, pattern);
		
		
		try {
			   
		    File outputfile = new File("texture_v0.png");
		    ImageIO.write(textureImg, "png", outputfile);
		} catch (IOException e) {
		    
		}
	}
	
	
	
	public static BufferedImage generateTexture(int size, int density, double variance, TexturePattern pattern){
		
		BufferedImage textureImg = new BufferedImage(size, size, BufferedImage.TYPE_BYTE_GRAY);
		
		WritableRaster raster = textureImg.getRaster();
		
		
		int gap = size / density;
		Random random = new Random();
		
		for(int x = 1; x < density; x++){
			for(int y = 1; y < density; y++){
				
				int aliasX = (int)Math.round(gaussianRandom(random, variance));
				int aliasY = (int)Math.round(gaussianRandom(random, variance));
				
				
				
				pattern.drawPattern(raster, x * gap + aliasX, y * gap + aliasY);
				
				
			}
			
		}
		
		
		textureImg.setData(raster);
		
		
		
		return textureImg;
	}
	
	
	private static double gaussianRandom(Random random, double variance){
		
		if(variance == 0)
			return 0;
		
		double k = Math.sqrt(2*variance*Math.PI);
		
		double y = random.nextDouble() / k;
		
		double x = Math.sqrt(-2 * variance * Math.log(y * k));
		
		return random.nextBoolean() ? x : -x;
		
		
	}
	

	private class TexturePattern{
	
		public static final int SQUARE = 1;
		
		private int size;
		
		private int intensity;
		
		private int patternType;
		
		private double variance;
		
		
		public TexturePattern(int patternType, int size, int intensity){
			
			this.patternType = patternType;
			this.size = size;
			this.intensity = intensity;
		}
		
		
		
		public void drawPattern(WritableRaster raster, int posX, int posY){
			
			if(patternType == SQUARE){
				
				for(int x = posX - size/2; x < posX + size/2; x++){
					for(int y = posY - size/2; y < posY + size/2; y++){
						
						int[] pixel = {intensity};
						
						if(x < 0 || y < 0 || x >= raster.getWidth() || y >= raster.getHeight())
							continue;
						
						try{
							raster.setPixel(x, y, pixel);
						}catch(Exception e){
							System.out.println("x="+ x +", y="+y);
						}
					}
										
				}
				
				
			}
			
		}
		
		
	}
}
