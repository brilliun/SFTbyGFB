import controller.IMyController;
import controller.MainController;
import model.FilterBankModel;
import model.IFilterBankModel;
import model.SFTModel;
import gui.IMyView;
import gui.MainView;

public class SFTbyGFB {

	public static void main(String[] args) {

		FilterBankModel filterBankModel = new FilterBankModel();
		
		SFTModel sftModel = new SFTModel(40.0, 512);

		MainView mainView = new MainView();

		MainController mainController = new MainController();

		
		
		
		filterBankModel.setController(mainController);
		
		mainController.setModel(filterBankModel);
			
		sftModel.setController(mainController);
		
		mainController.setModel(sftModel);
		

		
		
		
		mainView.setController(mainController);
		
		mainController.setView(mainView);
		
		
		
		
		mainController.init();
		
		mainController.start();

	}

}
