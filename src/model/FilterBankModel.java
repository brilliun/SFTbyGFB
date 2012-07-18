package model;

import imgUtil.Spectrum;

import java.awt.image.BufferedImage;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import mathUtil.Complex;
import mathUtil.Coordinate2D;

import controller.IMyController;
import filter.FilterBank;
import filter.FilterBankCallable;
import filter.FilterBankCallableEnergy;
import filter.GaborFilter;
import filter.IFilter;

public class FilterBankModel implements IFilterBankModel{
	
	

	public static final int FILTERING_SUCCESS = 1;
	
	public static final int NO_SRCIMG = -1;
	
	private static final int ORIENTATIONS = 32;
	
	private static final int NUM_FILTERBANK_THREADS = 4;
	
	private LinkedList<FutureTask> filterBankTasks = new LinkedList<FutureTask>();
	
	private LinkedList<FutureTask> filterBankEnergyTasks = new LinkedList<FutureTask>();
	
	private LinkedList<FilterBankCallable> filterBankCallableList = new LinkedList<FilterBankCallable>();
	
	private LinkedList<FilterBankCallableEnergy> filterBankCallableEnergyList = new LinkedList<FilterBankCallableEnergy>();
	
	
	
	private static int kernelWidth = 64;
	
	private static int kernelHeight = 64;
	
//	private BufferedImage srcImg;
	
	
	private IMyController controller;
	
	private FilterBank entireFilterBank;
	
	private FilterBank entireFilterBankEnergy;
	
	private LinkedHashMap<IFilter, Double> resultMap;
	
	
	
	
	
//	public FilterBankModel(){
//		this(null);
//	}
	
	
//	public FilterBankModel(BufferedImage srcImg){
//		
//		this.srcImg = srcImg;
//	}
	

	
	public void setController(IMyController controller) {
		
		this.controller = controller;
	}

	
	public void init() {
		// TODO Auto-generated method stub
		
		
		prepareGaborFilterBankConcurrent();
		
		prepareGaborFilterBankConcurrentEnergy();
	}
	
	
	
	
	
	

	
	public int doFiltering(Spectrum srcImg, Coordinate2D coord) {
		// TODO Auto-generated method stub
		if(srcImg == null){
			return NO_SRCIMG;
		}
		
		
		
		resultMap = entireFilterBank.patchConvolveResultMap(srcImg, coord, controller.getPatchWidth(), controller.getPatchHeight(), IFilter.CONTINUOUS);
		
		
		
		return FILTERING_SUCCESS;
		
		
	}
	
	
	
	
	public int doFilteringConcurrent(Spectrum srcImg, Coordinate2D coord){
		
		if(srcImg == null){
			return NO_SRCIMG;
		}
		
		filterBankTasks.clear();
		
		for(int i = 0; i < filterBankCallableList.size(); i++){
			
			FilterBankCallable filterBankCallable = filterBankCallableList.get(i);
			filterBankCallable.setFilteringParams(srcImg, coord, controller.getPatchWidth(), controller.getPatchHeight(), IFilter.CONTINUOUS);
			
			FutureTask<LinkedHashMap> futureTask = new FutureTask<LinkedHashMap>(filterBankCallable);
			filterBankTasks.add(futureTask);

			Thread t = new Thread(futureTask);
			
			t.start();
			
		}
		
		
		
		return FILTERING_SUCCESS;
		
	}
	
	public int doFilteringConcurrentEnergy(Spectrum srcImg, Coordinate2D coord){
		
		if(srcImg == null){
			return NO_SRCIMG;
		}
		
		filterBankEnergyTasks.clear();
		
		for(int i = 0; i < filterBankCallableEnergyList.size(); i++){
			
			FilterBankCallableEnergy filterBankCallableEnergy = filterBankCallableEnergyList.get(i);
			filterBankCallableEnergy.setFilteringParams(srcImg, coord, controller.getPatchWidth(), controller.getPatchHeight(), IFilter.CONTINUOUS);
			
			FutureTask<LinkedHashMap> futureTask = new FutureTask<LinkedHashMap>(filterBankCallableEnergy);
			filterBankEnergyTasks.add(futureTask);

			Thread t = new Thread(futureTask);
			
			t.start();
			
		}
		
		
		
		return FILTERING_SUCCESS;
		
	}
	
	public double[] getCurrentResult(){
		
		int resultSize = resultMap.entrySet().size();
		
		double[] result = new double[resultSize];
		
		
		Iterator<Entry<IFilter, Double>> iter = resultMap.entrySet().iterator();
		
		int idx = 0;
		
		while(iter.hasNext()){
			
			Entry<IFilter, Double> resultEntry = iter.next();
		
			result[idx++] = resultEntry.getValue(); 
			
			
		}	
		
		return result;
	}

	
	
	public double[] getFilterResultConcurrent(){
		
		double[] results = new double[ORIENTATIONS];
		
		
		int idx = 0;
		
		Iterator<FutureTask> taskIter = filterBankTasks.iterator();
		
		while(taskIter.hasNext()){
			
			FutureTask<LinkedHashMap> filterBankTask = taskIter.next();
			
			
			try {
				LinkedHashMap subresultMap = filterBankTask.get();
				
				Iterator<Entry<IFilter, Double>> resultIter = subresultMap.entrySet().iterator();
				
				while(resultIter.hasNext()){
					
					Entry<IFilter, Double> resultEntry = resultIter.next();
					
					results[idx++] = resultEntry.getValue();
					
				}
				
				
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		return results;
		
	}
	
	public double[] getFilterResultConcurrentEnergy(){
		
		double[] results = new double[ORIENTATIONS];
		
		
		int idx = 0;
		
		Iterator<FutureTask> taskIter = filterBankEnergyTasks.iterator();
		
		while(taskIter.hasNext()){
			
			FutureTask<LinkedHashMap> filterBankTask = taskIter.next();
			
			
			try {
				LinkedHashMap subresultMap = filterBankTask.get();
				
				Iterator<Entry<IFilter, Double>> resultIter = subresultMap.entrySet().iterator();
				
				while(resultIter.hasNext()){
					
					Entry<IFilter, Double> resultEntry = resultIter.next();
					
					results[idx++] = resultEntry.getValue();
					
				}
				
				
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		return results;
		
	}
	
	public void printCurrentResult(){
		
		Iterator<Entry<IFilter, Double>> iter = resultMap.entrySet().iterator();
		
		StringBuilder stringBd = new StringBuilder();
		
		while(iter.hasNext()){
			
			Entry<IFilter, Double> resultEntry = iter.next();
			
//			stringBd.append(resultEntry.getKey().getTag() + ": ");
			stringBd.append(resultEntry.getValue() + ", ");
			
		}
		
		System.out.println(stringBd);
	}
	
	
	private void prepareGaborFilterBankConcurrent(){
		
//		double[] freq = {0.0972, 0.1994, 0.3105, 0.4182};
//		double[] sigma = {3.9067, 3.4455, 3.1908, 3.3877};
//		double[] gamma = {0.3496, 0.3777, 0.4334, 0.5501};
		
//		double[] freq = {0.1336, 0.2301, 0.3280, 0.4237};
//		double[] sigma = {4.3476, 4.1176, 3.7229, 3.9247};
//		double[] gamma = {0.2499, 0.3509, 0.4250, 0.5202};
		
		double[] freq = {0.0962, 0.2054, 0.3250, 0.4399};
		double[] sigma = {3.3767, 2.8740, 2.7665, 3.0363};
		double[] gamma = {0.1787, 0.2378, 0.3457, 0.4878};
		
		
		
		int numOfLevels = 4;
		
		entireFilterBank = new FilterBank();
		
		
//		int threadIndex;
		int subOrientNum = ORIENTATIONS / NUM_FILTERBANK_THREADS;
		
//		FilterBankCallable[] filterBankCallables = new FilterBankCallable[NUM_FILTERBANK_THREADS];
		;
		
		
		double theta = 0;
		
		double thetaGap = Math.PI / ORIENTATIONS;
		
		for(int orient = 0; orient < ORIENTATIONS; orient += subOrientNum){
			
			
			FilterBankCallable filterBankCallable = new FilterBankCallable();
			
			
//			FutureTask<LinkedHashMap> futerTask = new FutureTask<LinkedHashMap>(filterBankCallable);
			
			FilterBank subGroupFilterBank = new FilterBank();
			
			filterBankCallable.setFilterBank(subGroupFilterBank);
			
			
			filterBankCallableList.add(filterBankCallable);
			
//			filterBankTasks.add(futerTask);
			
			
			
			
			for(int subOrient = 0; subOrient < subOrientNum; subOrient++){
				
				
				
				FilterBank sameOrientFilterBank = new FilterBank("#" + (orient+subOrient));
				
				for(int level = 0; level < numOfLevels; level++){
					
					GaborFilter gaborFilter = new GaborFilter(freq[level], sigma[level], gamma[level], theta);
					
					sameOrientFilterBank.addFilter(gaborFilter);
					
				}
				
				
				subGroupFilterBank.addFilter(sameOrientFilterBank);
				
				
				
				entireFilterBank.addFilter(sameOrientFilterBank);
				
				theta += thetaGap;
				
				
			}
			
			
			
		}
		
		
		entireFilterBank.buildKernel(kernelWidth, kernelHeight);
		
	}

	private void prepareGaborFilterBank(){
		
		double[] freq = {0.0972, 0.1994, 0.3105, 0.4182};
		double[] sigma = {3.9067, 3.4455, 3.1908, 3.3877};
		double[] gamma = {0.3496, 0.3777, 0.4334, 0.5501};
		

		
		
		
		int orientations = 32;
		
		int numOfLevels = 4;
		
		entireFilterBank = new FilterBank();
		
		
		
		
		double theta = 0;
		
		double thetaGap = Math.PI / orientations;
		
		for(int orient = 0; orient < orientations; orient++){
			
			FilterBank sameOrientFilterBank = new FilterBank("#" + orient);
			
			for(int level = 0; level < numOfLevels; level++){
				
				GaborFilter gaborFilter = new GaborFilter(freq[level], sigma[level], gamma[level], theta);
				
				sameOrientFilterBank.addFilter(gaborFilter);
				
			}
			
			entireFilterBank.addFilter(sameOrientFilterBank);
			
			theta += thetaGap;
			
		}
		
		
		entireFilterBank.buildKernel(kernelWidth, kernelHeight);
		
		
	}
	
	private void prepareGaborFilterBankConcurrentEnergy(){
		

		
//		double[] freq = {0.1278, 0.2141, 0.3184, 0.4227};
//		double[] sigma = {3.7144, 2.8135, 2.4096, 2.4990};
//		double[] gamma = {0.1766, 0.2256, 0.2767, 0.3630};
		
		double[] freq = {0.1339, 0.2249, 0.3283, 0.4284};
		double[] sigma = {3.1937, 2.6562, 2., 2.3598};
		double[] gamma = {0.1711, 0.2293, 0.2790, 0.3720};
		
		
		
		int numOfLevels = 4;
		
		entireFilterBankEnergy = new FilterBank();
		
		
		int threadIndex;
		int subOrientNum = ORIENTATIONS / NUM_FILTERBANK_THREADS;
		
//		FilterBankCallable[] filterBankCallables = new FilterBankCallable[NUM_FILTERBANK_THREADS];
		;
		
		
		double theta = 0;
		
		double thetaGap = Math.PI / ORIENTATIONS;
		
		for(int orient = 0; orient < ORIENTATIONS; orient += subOrientNum){
			
			
			
			FilterBankCallableEnergy filterBankCallableEnergy = new FilterBankCallableEnergy();
			
//			FutureTask<LinkedHashMap> futerTask = new FutureTask<LinkedHashMap>(filterBankCallable);
			
			FilterBank subGroupFilterBank = new FilterBank();
			

			filterBankCallableEnergy.setFilterBank(subGroupFilterBank);
			
			
			filterBankCallableEnergyList.add(filterBankCallableEnergy);
			
//			filterBankTasks.add(futerTask);
			
			
			
			
			for(int subOrient = 0; subOrient < subOrientNum; subOrient++){
				
				
				
				FilterBank sameOrientFilterBank = new FilterBank("#" + (orient+subOrient));
				
				for(int level = 0; level < numOfLevels; level++){
					
					GaborFilter gaborFilter = new GaborFilter(freq[level], sigma[level], gamma[level], theta);
					
					sameOrientFilterBank.addFilter(gaborFilter);
					
				}
				
				
				subGroupFilterBank.addFilter(sameOrientFilterBank);
				
				
				
				entireFilterBankEnergy.addFilter(sameOrientFilterBank);
				
				theta += thetaGap;
				
				
			}
			
			
			
		}
		
		
		entireFilterBankEnergy.buildKernel(kernelWidth, kernelHeight);
		
	}

}
