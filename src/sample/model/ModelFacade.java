package sample.model;

public class ModelFacade {
	Model model;
	
	static public EventDispatcher eventDispatcher;
	
	public ModelFacade() {
		model = new Model();
	}
	
	public void addEventListener(EventDispatcher listener) {
		eventDispatcher = listener;
	}
	
	public void engineSupplierSwitcher(int i) {
		model.suppliers[i].switcher();
	}
}
