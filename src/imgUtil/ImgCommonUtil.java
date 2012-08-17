package imgUtil;


import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import javax.imageio.ImageIO;

import mathUtil.Complex;

public class ImgCommonUtil {
	
	private static final int GRAY = 1;
	private static final int RGB = 3;
	
	
	
	public static void main(String[] args){
		
		BufferedImage srcImg = readImageFile("test.bmp");
		
		writeToImgFile("subImg", "png", ImgCommonUtil.getSubImage(368, 430, 64, 64, srcImg));
		
		
	}
	
	
	public static BufferedImage readImageFile(String filename){
		
		BufferedImage srcImg = null;

		try {
			srcImg = ImageIO.read(new File(filename));
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		
		return srcImg;
	}


	public static double[][][] readImageData(BufferedImage img){
		
		int width, height, band;
        
        width = img.getWidth();
        height = img.getHeight();
        
        
        band = img.getRaster().getNumBands();

		
		double data[][][] = new double[width][height][band];
		
		Raster raster = img.getRaster();
		
		for(int x = 0; x < width; x++)
			for(int y = 0; y < height; y++){
				
				double[] pixel = raster.getPixel(x, y, (double[])null);
				
				for(int b = 0; b < band; b++){
					data[x][y][b] = pixel[b];
				}
			}		
		
		return data;
	}

	
	public static double[][] readGrayscaleImageData(BufferedImage img){
		
		int width, height, band;
        
       width = img.getWidth();
       height = img.getHeight();
        
       Raster raster = img.getRaster();
        
       band = raster.getNumBands();
       
       double[][] data = new double[width][height];
        
       if(band == RGB){
    	   
    	   for(int x = 0; x < width; x++){
   				for(int y = 0; y < height; y++){

   					double[] pixel = raster.getPixel(x, y, (double[])null);
        	
   					data[x][y] = convertRGBtoGrayscale(pixel[0], pixel[1], pixel[2]);
   				}
    	   }
       
        }
       else if(band == GRAY){
    	   
    	   for(int x = 0; x < width; x++){
  				for(int y = 0; y < height; y++){

  					double[] pixel = raster.getPixel(x, y, (double[])null);
       	
  					data[x][y] = pixel[0];
  				}
    	   }
    	   
       }
       
		
				
		
		return data;
		
		
		
	}
	
	
	
	public static BufferedImage getSubImage(int startX, int startY, int width, int height, BufferedImage entireImg){
		
		
		BufferedImage subImg = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
		
		WritableRaster raster = subImg.getRaster();
		
		
		double[][] entirePixel = readGrayscaleImageData(entireImg);
		
		
		for(int x = 0; x < width; x++){
			for(int y = 0; y < height; y++){
				
				double[] pixel = {entirePixel[startX + x][startY + y]};
				
				raster.setPixel(x, y, pixel);
			}
		}
		
		return subImg;
	}
	
	
	public static BufferedImage writeToImage(int width, int height, double[][] data){
		
		BufferedImage resultImg = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
		
		WritableRaster raster = resultImg.getRaster();
		
		
		for(int x = 0; x < width; x++){
			for(int y = 0; y < height; y++){
				
				int[] pixel = new int[1];
				
				pixel[0] = round((int)data[x][y], 0, 255);
				
				
				raster.setPixel(x, y, pixel);
			}
		}
		
		
		resultImg.setData(raster);
		
		return resultImg;
	}
	
	
	public static void writeToImgFile(String filename, String format, BufferedImage img){
		

		try {
			   
		    File outputfile = new File(filename + "." + format);
		    ImageIO.write(img, format, outputfile);
		} catch (IOException e) {
		    
		}
	}
	
	
	public static void writeToImgFile(int width, int height, double[][] data, double lowerBound, String fileName){
		
		
		BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
		
		WritableRaster raster = img.getRaster();
		
		
		for(int x = 0; x < width; x++){
			for(int y = 0; y < height; y++){
				
				int[] pixel = new int[1];
				
				pixel[0] = round((int)(data[x][y] - lowerBound), 0, 255);
				
				
				raster.setPixel(x, y, pixel);
			}
		}
		
		
		img.setData(raster);
		
		try {
		   
		    File outputfile = new File(fileName + ".png");
		    ImageIO.write(img, "png", outputfile);
		} catch (IOException e) {
		    
		}
		
	}
	

	public static boolean isSingleBand(BufferedImage img){
		
		int band = img.getRaster().getNumBands();
		
		return (band==1);
	}



	public static double convertRGBtoGrayscale(double red, double green, double blue){
		
		return 0.21*red + 0.71*green + 0.07*blue;
	}
	
	
	public static Spectrum FFT2D(BufferedImage img, boolean inverse){
		
		int width = img.getWidth();
		int height = img.getHeight();
		
		return FFT2D(width, height, inverse, new Spectrum(width, height, readGrayscaleImageData(img)));
		
	}
	
	
	public static Spectrum FFT2D(int n, int m, boolean inverse, Spectrum inputSpectrum)
	{ 
		//System.out.println("FFT!!!");
		
		
		double[][] GRe = new double[n][m];
		double[][] GIm = new double[n][m];
		
	  int l2n = 0, p = 1; //l2n will become log_2(n)
	  while(p < n) {p *= 2; l2n++;}
	  int l2m = 0; p = 1; //l2m will become log_2(m)
	  while(p < m) {p *= 2; l2m++;}
	    
	  m= 1<<l2m; n= 1<<l2n; //Make sure m and n will be powers of 2, otherwise you'll get in an infinite loop
	  
	  //Erase all history of this array
	  for(int x = 0; x <m; x++) //for each column
	  for(int y = 0; y < m; y++) //for each row
	  {
	    GRe[x][y] = inputSpectrum.getPointData(x, y).getRe();
	    GIm[x][y] = inputSpectrum.getPointData(x, y).getIm();
	  } 
	  
	  //Bit reversal of each row
	 
	  for(int y = 0; y < m; y++) //for each row
	  {
	   int j = 0;
	    for(int i = 0; i < n - 1; i++)
	    {
	      GRe[i][y] = inputSpectrum.getPointData(j, y).getRe();
	      GIm[i][y] = inputSpectrum.getPointData(j, y).getIm();
	      int k = n / 2;
	      while (k <= j) {j -= k; k/= 2;}
	      j += k;
	    }
	  }
	  //Bit reversal of each column
	  double tx = 0, ty = 0; 
	  for(int x = 0; x < n; x++) //for each column
	  {
	    int j = 0;
	    for(int i = 0; i < m - 1; i++)
	    {
	      if(i < j)
	      {
	        tx = GRe[x][i];
	        ty = GIm[x][i];
	        GRe[x][i] = GRe[x][j];
	        GIm[x][i] = GIm[x][j];
	        GRe[x][j] = tx;
	        GIm[x][j] = ty;            
	      }  
	      int k = m / 2;
	      while (k <= j) {j -= k; k/= 2;}
	      j += k;
	    }
	  }     

	  //Calculate the FFT of the columns
	  for(int x = 0; x < n; x++) //for each column
	  {  
	    //This is the 1D FFT:
	    double ca = -1.0;
	    double sa = 0.0;
	    int l1 = 1, l2 = 1;
	    for(int l=0;l<l2n;l++)
	    {
	      l1 = l2;
	      l2 *= 2;
	      double u1 = 1.0;
	      double u2 = 0.0;
	      for(int j = 0; j < l1; j++)
	      {
	        for(int i = j; i < n; i += l2)
	        {
	          int i1 = i + l1;
	          double t1 = u1 * GRe[x][i1] - u2 * GIm[x][i1];
	          double t2 = u1 * GIm[x][i1] + u2 * GRe[x][i1];
	          GRe[x][i1] = GRe[x][i] - t1;
	          GIm[x][i1] = GIm[x][i] - t2;
	          GRe[x][i] += t1;
	          GIm[x][i] += t2;
	        }
	        double z =  u1 * ca - u2 * sa;
	        u2 = u1 * sa + u2 * ca;
	        u1 = z;
	      }
	      sa = Math.sqrt((1.0 - ca) / 2.0);
	      if(!inverse) sa = -sa;
	      ca = Math.sqrt((1.0 + ca) / 2.0);
	    }
	  }
	  //Calculate the FFT of the rows
	  for(int y = 0; y < m; y++) //for each row
	  {  
	    //This is the 1D FFT:
	    double ca = -1.0;
	    double sa = 0.0;
	    int l1= 1, l2 = 1;
	    for(int l = 0; l < l2m; l++)
	    {
	      l1 = l2;
	      l2 *= 2;
	      double u1 = 1.0;
	      double u2 = 0.0;
	      for(int j = 0; j < l1; j++)
	      {
	        for(int i = j; i < n; i += l2)
	        {
	          int i1 = i + l1;
	          double t1 = u1 * GRe[i1][y] - u2 * GIm[i1][y];
	          double t2 = u1 * GIm[i1][y] + u2 * GRe[i1][y];
	          GRe[i1][y] = GRe[i][y] - t1;
	          GIm[i1][y] = GIm[i][y] - t2;
	          GRe[i][y] += t1;
	          GIm[i][y] += t2;
	        }
	        double z =  u1 * ca - u2 * sa;
	        u2 = u1 * sa + u2 * ca;
	        u1 = z;
	      }
	      sa = Math.sqrt((1.0 - ca) / 2.0);
	      if(!inverse) sa = -sa;
	      ca = Math.sqrt((1.0 + ca) / 2.0);
	    }
	  }
	 
	  int d;
	  if(inverse) d = n; else d = m;
	  for(int x = 0; x < n; x++) for(int y = 0; y < m; y++) //for every value of the buffers
	  {
	    GRe[x][y] /= d;
	    GIm[x][y] /= d;
	  }  
	  
	  
	  return new Spectrum(n, m, GRe, GIm);
	}
	
	private static int round(int v, int l, int h){
    	
    	if(v < l)
    		return l;
    	if(v > h)
    		return h;
    	
    	return v;
    	
    	
    }
}
