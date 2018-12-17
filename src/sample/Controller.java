package sample;

import javafx.scene.input.MouseEvent;
import sample.model.ModelFacade;

public class Controller {
	
	View view;
	ModelFacade model;
	
	public Controller(View view, ModelFacade model) {
		this.view = view;
		this.model = model;
		
		view.getRootPane().addEventHandler(MouseEvent.MOUSE_CLICKED, this::clickListener);
	}
	
	private void clickListener(MouseEvent e) {
		
		if (e.getTarget() == view.switcherOffBg) {
			model.engineSupplierSwitcher();
		}
		
	}
	
}
