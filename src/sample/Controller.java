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
		view.getRootPane().addEventHandler(MouseEvent.MOUSE_PRESSED, this::pressListener);
		view.getRootPane().addEventHandler(MouseEvent.MOUSE_DRAGGED, this::dragListener);
		view.getRootPane().addEventHandler(MouseEvent.MOUSE_RELEASED, this::releaseListener);
		
	}
	
	// speedSlider
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
	private void releaseListener(MouseEvent e) {
		for (int i = 0; i < view.speedSlider.length; i++) { // release
			if (e.getTarget() == view.speedSlider[i]) {
				model.engineSupplierSpeedSliderReleased(i, e);
			}
		}
	}
	
	// anything  - click
	private void clickListener(MouseEvent e) {
		for (int i = 0; i < view.factoryUI.size(); i++) { // switch on\off
			if (e.getTarget() == view.switchBtn[i]) {
				model.engineSupplierSwitcher(i);
			}
		}
		for (int i = 0; i < view.xBtn.length; i++) { // X btn
			if (e.getTarget() == view.xBtn[i]) {
				model.engineWarehouseXBtn(i);
			}
		}
		for (int i = 0; i < view.warehouseTotal.length; i++) { // X btn
			if (e.getTarget() == view.warehouseTotal[i]) {
				model.engineWarehousePull(i);
			}
		}
	}
	
}
