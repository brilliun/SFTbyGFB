package gui;

import mathUtil.Coordinate2D;
import model.Orientation;
import model.SrcImage;
import controller.IMyController;

public interface IMyView {

	public void setController(IMyController controller);

	public void init();

	public void start();

	public int getPatchWidth();

	public int getPatchHeight();
	
	public int getSrcImgWidth();
	
	public int getSrcImgHeight();
	
	public SrcImage getSrcImg();
	
	public void updateSrcImg(SrcImage srcImg);
	
	
//	public void patchSelected(Coordinate2D coord);
	
	public Orientation shapeEstimate(Coordinate2D coordA, Coordinate2D coordB);
	
	public Orientation shapeEstimateEnergy(Coordinate2D coordA, Coordinate2D coordB);
	
//	public void addNormalNeedle(Orientation orientationEstimated);

}
