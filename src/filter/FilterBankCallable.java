package filter;

import imgUtil.Spectrum;

import java.awt.image.BufferedImage;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.concurrent.Callable;

import mathUtil.Complex;
import mathUtil.Coordinate2D;

public class FilterBankCallable implements Callable<LinkedHashMap>{
	
	
	private FilterBank filterBank;
	
//	private LinkedHashMap<IFilter, Double> resultMap;
	
	private int id; 
	
	
	private Spectrum srcImg;
	
	private Coordinate2D patchCenterCoord;
	
	private int patchWidth;
	
	private int patchHeight;
	
	private int edgeAction;
	
	
	
	public FilterBankCallable(){
		this(-1);
	}
	
	public FilterBankCallable(int id){
		
		this.id = id;
//		this.filterBanks = new LinkedList<FilterBank>();
//		this.resultMap = new LinkedHashMap<IFilter, Double>();
		
	}

	
	public void setFilterBank(FilterBank filterBank){
		this.filterBank = filterBank;
	}
	
	public void setFilteringParams(Spectrum srcImg, Coordinate2D patchCenterCoord, int patchWidth, int patchHeight, int edgeAction){
		
		
		this.srcImg = srcImg;
		this.patchCenterCoord = patchCenterCoord;
		this.patchWidth = patchWidth;
		this.patchHeight = patchHeight;
		this.edgeAction = edgeAction;
		
	}
	
	public void setId(int id){
		this.id = id;
	}

	
	public LinkedHashMap<IFilter, Double> call() throws Exception {
		// TODO Auto-generated method stub
		
		
		
		return filterBank.patchConvolveResultMap(srcImg, patchCenterCoord, patchWidth, patchHeight, edgeAction);
		
		
		
		
		
	}
}
