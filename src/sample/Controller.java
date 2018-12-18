package sample;

import javafx.scene.input.MouseEvent;
import sample.model.ModelFacade;
import sample.view.View;

public class Controller {
	
	View view;
	ModelFacade model;
	
	public Controller(View view, ModelFacade model) {
		this.view = view;
		this.model = model;
		
		view.getRootPane().addEventHandler(MouseEvent.MOUSE_CLICKED, this::clickListener);
	}
	
	private void clickListener(MouseEvent e) {
		
		for (int i = 0; i < view.suppliers.length; i++) {
			if (view.suppliers[i] != null
			 && e.getTarget() == view.suppliers[i].switcherOff) {
				model.engineSupplierSwitcher(i);
			}
		}
		
	}
	
}
