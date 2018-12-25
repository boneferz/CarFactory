package sample.model;

import javafx.scene.input.MouseEvent;
import sample.model.observer.EventDispatcherTyped;
import sample.model.suppliers.detail.Detail;

public class FacadeModel extends EventDispatcherTyped {
	
	Model model;
	
	public void createModel() {
		model = new Model();
	}
	
	public void engineSupplierSwitcher(int i) {
		model.supplier.get(i).switcher();
	}
	public void engineWarehouseXBtn(int i) {
		model.supplier.get(i).warehouse.clear();
	}
	public void engineSupplierSpeedSliderPressed(int i, MouseEvent e) {
		model.supplier.get(i).mousePressed(e);
	}
	public void engineSupplierSpeedSliderDrag(int i, MouseEvent e) {
		model.supplier.get(i).mouseDrag(e);
	}
	public void engineSupplierSpeedSliderReleased(int i, MouseEvent e) {
		model.supplier.get(i).mouseRealised(e);
	}
	public void engineWarehousePull(int i) {
		
		Detail detail = model.supplier.get(i).warehouse.pull();
		
		if (detail != null) {
			System.out.println(" -pull: " + detail.id + ":id , " + detail.getClass().getSimpleName());
		} else {
			System.out.println(" -pull:: " + detail);
		}
	}
}
