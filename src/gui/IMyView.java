package gui;

import mathUtil.Coordinate2D;
import controller.IMyController;

public interface IMyView {

	public void setController(IMyController controller);

	public void init();

	public void start();

	public int getPatchWidth();

	public int getPatchHeight();
	
	public void patchSelected(Coordinate2D coord);
	
	public void shapeEstimate(Coordinate2D coordA, Coordinate2D coordB);
	
	public void addNormalNeedle(double slant, double tilt);

}
