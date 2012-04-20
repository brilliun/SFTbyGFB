package filter;
import java.awt.image.BufferedImage;


public interface IFilter {

	
	public void buildKernel(int width, int height);
	
	public double pointConvolve(BufferedImage srcImg, int posX, int posY, int edgeAction);
	
	public double patchConvolve(BufferedImage srcImg, int posX, int posY, int width, int height, int edgeAction);
	
	public BufferedImage filterEntireImage(BufferedImage srcImg, int edgeAction);
	
	
	
	public int getKernelWidth();
	
	public int getKernelHeight();
	
	public void setTag(String tag);
	
	public String getTag();
	
	
	
}
