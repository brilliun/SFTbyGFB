package filter;

import imgUtil.Spectrum;

import java.awt.image.BufferedImage;
import java.util.LinkedHashMap;
import java.util.Iterator;
import java.util.LinkedList;

import mathUtil.Complex;
import mathUtil.Coordinate2D;

public class FilterBank implements IFilter{
	
	private String tag;
	
	private LinkedList<IFilter> filterBank;
	
	private LinkedHashMap<IFilter, Double> responseMap;
	
	
	public FilterBank(){
		this(null);
	}
	
	public FilterBank(String tag){
		this.filterBank = new LinkedList<IFilter>();
		
		this.tag = tag;
	}
	
	
	public void addFilter(IFilter filter){
		filterBank.add(filter);
		
	}
	
	public int getFilterBankSize(){
		return filterBank.size();
	}
	

	
	public void buildKernel(int width, int height) {
		
		Iterator<IFilter> iter = filterBank.iterator();
		
		while(iter.hasNext()){
			iter.next().buildKernel(width, height);
		}
		
	}

	
	public Complex patchConvolve(Spectrum srcImg, Coordinate2D patchCenterCoord,	int width, int height, int edgeAction) {
		
		Complex totalResponse = new Complex();
		
		Iterator<IFilter> iter = filterBank.iterator();
		
		while(iter.hasNext()){
			totalResponse = totalResponse.add(iter.next().patchConvolve(srcImg, patchCenterCoord, width, height, edgeAction));
		}
		
				
		
		return totalResponse;
	}
	public double patchConvolveEnergy(Spectrum srcImg, Coordinate2D patchCenterCoord, int width, int height, int edgeAction) {
		// TODO Auto-generated method stub
		double totalResponse = 0;
		
		Iterator<IFilter> iter = filterBank.iterator();
		
		while(iter.hasNext()){
			totalResponse += iter.next().patchConvolveEnergy(srcImg, patchCenterCoord, width, height, edgeAction);
		}
		
		return totalResponse;
	}
	
	public LinkedHashMap<IFilter, Double> patchConvolveResultMap(Spectrum srcImg, Coordinate2D patchCenterCoord, int patchWidth, int patchHeight, int edgeAction){
		
		
		LinkedHashMap<IFilter, Double> resultMap = new LinkedHashMap<IFilter, Double>();
		
		Iterator<IFilter> iter = filterBank.iterator();
		double response = 0.0;
		
		while(iter.hasNext()){
			
			IFilter filter = iter.next();
			
			response = filter.patchConvolve(srcImg, patchCenterCoord, patchWidth, patchHeight, edgeAction).getAmplitude();
			
			resultMap.put(filter, response);
			
		}
		
		return resultMap;
		
		
	}
	
	public LinkedHashMap<IFilter, Double> patchConvolveResultMapEnergy(Spectrum srcImg, Coordinate2D patchCenterCoord, int patchWidth, int patchHeight, int edgeAction){
		
		
		LinkedHashMap<IFilter, Double> resultMap = new LinkedHashMap<IFilter, Double>();
		
		Iterator<IFilter> iter = filterBank.iterator();
		double response = 0;
		
		while(iter.hasNext()){
			
			IFilter filter = iter.next();
			
			response = filter.patchConvolveEnergy(srcImg, patchCenterCoord, patchWidth, patchHeight, edgeAction);
			
			resultMap.put(filter, new Double(response));
			
		}
		
		return resultMap;
		
		
	}

	
	public BufferedImage filterEntireImage(Spectrum srcImg, int edgeAction) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public int getKernelWidth() {
		// TODO Auto-generated method stub
		return 0;
	}

	
	public int getKernelHeight() {
		// TODO Auto-generated method stub
		return 0;
	}

	
	public void setTag(String tag) {
		this.tag = tag;
		
	}

	
	public String getTag() {
		// TODO Auto-generated method stub
		return tag;
	}

	@Override
	public Complex dotProduct(Spectrum srcImg, int posX, int posY,
			Coordinate2D coordA, Coordinate2D coordB, int edgeAction) {
		// TODO Auto-generated method stub
		return null;
	}

	

	

}
