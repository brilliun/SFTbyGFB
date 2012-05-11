package filter;
import imgUtil.Spectrum;

import java.awt.image.BufferedImage;

import mathUtil.Complex;


public interface IFilter {
	
	public static final int ZERO_PADDING = 1;
	
	public static final int REPLICATE = 2;
	
	public static final int SYMMETRIC = 3;
	
	
	
	public void buildKernel(int width, int height);
	
	public Complex pointConvolve(Spectrum srcImg, int posX, int posY, int edgeAction);
	
	public Complex patchConvolve(Spectrum srcImg, int posX, int posY, int width, int height, int edgeAction);
	
	public double patchConvolveEnergy(Spectrum srcImg, int posX, int posY, int width, int height, int edgeAction);
	
	public BufferedImage filterEntireImage(Spectrum srcImg, int edgeAction);
	
	
	
	public int getKernelWidth();
	
	public int getKernelHeight();
	
	public void setTag(String tag);
	
	public String getTag();
	
	
	
}
