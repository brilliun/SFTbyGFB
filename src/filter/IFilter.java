package filter;
import imgUtil.Spectrum;

import java.awt.image.BufferedImage;

import mathUtil.Complex;
import mathUtil.Coordinate2D;


public interface IFilter {
	
	public static final int CONTINUOUS = 0;
	
	public static final int ZERO_PADDING = 1;
	
	public static final int REPLICATE = 2;
	
	public static final int SYMMETRIC = 3;
	
	
	
	public void buildKernel(int width, int height);
	
	public Complex dotProduct(Spectrum srcImg, int posX, int posY, Coordinate2D coordA, Coordinate2D coordB, int edgeAction);
	
	public double patchConvolve(Spectrum srcImg, Coordinate2D patchCenterCoord, int width, int height, int edgeAction);
	
	public double patchConvolveEnergy(Spectrum srcImg, Coordinate2D patchCenterCoord, int width, int height, int edgeAction);
	
	public BufferedImage filterEntireImage(Spectrum srcImg, int edgeAction);
	
	
	
	public int getKernelWidth();
	
	public int getKernelHeight();
	
	public void setTag(String tag);
	
	public String getTag();
	
	
	
}
