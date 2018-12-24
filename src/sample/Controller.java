package sample;

import javafx.event.Event;
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
		
		view.getRootPane().addEventHandler(MouseEvent.MOUSE_PRESSED, this::pressListener);
		view.getRootPane().addEventHandler(MouseEvent.MOUSE_DRAGGED, this::dragListener);
	}
	
	// speed slide
	private void pressListener(MouseEvent e) {
		for (int i = 0; i < view.speedSlider.length; i++) { // press
			if (e.getTarget() == view.speedSlider[i]) {
				model.engineSupplierSpeedSliderPressed(i, e);
			}
		}
	}
	private void dragListener(MouseEvent e) {
		for (int i = 0; i < view.speedSlider.length; i++) { // drag
			if (e.getTarget() == view.speedSlider[i]) {
				model.engineSupplierSpeedSliderDrag(i, e);
			}
		}
	}
	
	private void clickListener(MouseEvent e) {
		
		for (int i = 0; i < view.suppliers.length; i++) { // switch on\off
			if (e.getTarget() == view.switchBtn[i]) {
				model.engineSupplierSwitcher(i);
			}
		}
		
		for (int i = 0; i < view.xBtn.length; i++) { // X btn
			if (e.getTarget() == view.xBtn[i]) {
				model.engineWarehouseXBtn(i);
			}
		}
	}
	
}
