package filter;

import imgUtil.Spectrum;

import java.awt.image.BufferedImage;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.concurrent.Callable;

import mathUtil.Complex;

public class FilterBankCallable implements Callable<LinkedHashMap>{
	
	
	private FilterBank filterBank;
	
	private LinkedHashMap<IFilter, Double> resultMap;
	
	private int id; 
	
	
	private Spectrum srcImg;
	
	private int posX;
	
	private int posY;
	
	private int patchWidth;
	
	private int patchHeight;
	
	private int edgeAction;
	
	
	
	public FilterBankCallable(){
		this(-1);
	}
	
	public FilterBankCallable(int id){
		
		this.id = id;
//		this.filterBanks = new LinkedList<FilterBank>();
		this.resultMap = new LinkedHashMap<IFilter, Double>();
		
	}

	
	public void setFilterBank(FilterBank filterBank){
		this.filterBank = filterBank;
	}
	
	public void setFilteringParams(Spectrum srcImg, int posX, int posY, int patchWidth, int patchHeight, int edgeAction){
		
		
		this.srcImg = srcImg;
		this.posX = posX;
		this.posY = posY;
		this.patchWidth = patchWidth;
		this.patchHeight = patchHeight;
		this.edgeAction = edgeAction;
		
	}
	
	public void setId(int id){
		this.id = id;
	}

	
	public LinkedHashMap<IFilter, Complex> call() throws Exception {
		// TODO Auto-generated method stub
		
		
		
		return filterBank.patchConvolveResultMap(srcImg, posX, posY, patchWidth, patchHeight, edgeAction);
		
		
		
		
		
	}
}
