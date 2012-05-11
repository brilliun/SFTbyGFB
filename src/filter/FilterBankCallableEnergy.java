package filter;

import imgUtil.Spectrum;

import java.awt.image.BufferedImage;
import java.util.LinkedHashMap;
import java.util.concurrent.Callable;

public class FilterBankCallableEnergy implements Callable<LinkedHashMap>{
	
	
	private FilterBank filterBank;
	
	private LinkedHashMap<IFilter, Double> resultMap;
	
	private int id; 
	
	
	private Spectrum srcImg;
	
	private int posX;
	
	private int posY;
	
	private int patchWidth;
	
	private int patchHeight;
	
	private int edgeAction;
	
	
	
	public FilterBankCallableEnergy(){
		this(-1);
	}
	
	public FilterBankCallableEnergy(int id){
		
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

	
	public LinkedHashMap<IFilter, Double> call() throws Exception {
		// TODO Auto-generated method stub
		
		
		
		return filterBank.patchConvolveResultMapEnergy(srcImg, posX, posY, patchWidth, patchHeight, edgeAction);
		
		
		
		
		
	}

}
