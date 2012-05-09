package filter;

import java.awt.image.BufferedImage;
import java.util.LinkedHashMap;
import java.util.Iterator;
import java.util.LinkedList;

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

	
	public double patchConvolve(BufferedImage srcImg, int posX, int posY,	int width, int height, int edgeAction) {
		
		double totalResponse = 0;
		
		Iterator<IFilter> iter = filterBank.iterator();
		
		while(iter.hasNext()){
			totalResponse += iter.next().patchConvolve(srcImg, posX, posY, width, height, edgeAction);
		}
		
				
		
		return totalResponse;
	}
	public double patchConvolveEnergy(BufferedImage srcImg, int posX, int posY, int width, int height, int edgeAction) {
		// TODO Auto-generated method stub
		double totalResponse = 0;
		
		Iterator<IFilter> iter = filterBank.iterator();
		
		while(iter.hasNext()){
			totalResponse += iter.next().patchConvolveEnergy(srcImg, posX, posY, width, height, edgeAction);
		}
		
		return totalResponse;
	}
	
	public LinkedHashMap<IFilter, Double> patchConvolveResultMap(BufferedImage srcImg, int posX, int posY, int patchWidth, int patchHeight, int edgeAction){
		
		
		LinkedHashMap<IFilter, Double> resultMap = new LinkedHashMap<IFilter, Double>();
		
		Iterator<IFilter> iter = filterBank.iterator();
		double response = 0;
		
		while(iter.hasNext()){
			
			IFilter filter = iter.next();
			
			response = filter.patchConvolve(srcImg, posX, posY, patchWidth, patchHeight, edgeAction);
			
			resultMap.put(filter, new Double(response));
			
		}
		
		return resultMap;
		
		
	}
	
	public LinkedHashMap<IFilter, Double> patchConvolveResultMapEnergy(BufferedImage srcImg, int posX, int posY, int patchWidth, int patchHeight, int edgeAction){
		
		
		LinkedHashMap<IFilter, Double> resultMap = new LinkedHashMap<IFilter, Double>();
		
		Iterator<IFilter> iter = filterBank.iterator();
		double response = 0;
		
		while(iter.hasNext()){
			
			IFilter filter = iter.next();
			
			response = filter.patchConvolveEnergy(srcImg, posX, posY, patchWidth, patchHeight, edgeAction);
			
			resultMap.put(filter, new Double(response));
			
		}
		
		return resultMap;
		
		
	}

	
	public BufferedImage filterEntireImage(BufferedImage srcImg, int edgeAction) {
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

	
	public double pointConvolve(BufferedImage srcImg, int posX, int posY,
			int edgeAction) {
		// TODO Auto-generated method stub
		return 0;
	}

	

}
